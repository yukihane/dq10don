package yukihane.dq10don.tobatsu.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.client.Response;
import yukihane.dq10don.account.Account;
import yukihane.dq10don.account.Character;
import yukihane.dq10don.account.TobatsuList;
import yukihane.dq10don.communication.HappyService;
import yukihane.dq10don.communication.HappyServiceFactory;
import yukihane.dq10don.communication.dto.tobatsu.TobatsuDto;
import yukihane.dq10don.db.AccountDao;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.TobatsuListDao;
import yukihane.dq10don.exception.AppException;
import yukihane.dq10don.exception.HappyServiceException;

/**
 * Created by yuki on 15/07/18.
 */
public class TobatsuListServiceImpl implements TobatsuListService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TobatsuListServiceImpl.class);

    private final DbHelper dbHelper;

    public TobatsuListServiceImpl(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public Map<Character, TobatsuList> getTobatsuListsFromServer() throws SQLException {
        AccountDao accountDao = AccountDao.create(dbHelper);
        List<Account> accounts = accountDao.queryAll();
        Map<Character, TobatsuList> result = new HashMap<>();
        for (Account account : accounts) {
            if (account.isInvalid()) {
                continue;
            }

            for (Character character : account.getCharacters()) {
                // TODO Rx で実装すると良い感じかも
                TobatsuList tl = null;
                if (character.isTobatsuInvalid()) {
                    LOGGER.info("invalid user skipped: {}", character);
                } else {
                    try {
                        tl = getTobatsuListFromServer(character);
                    } catch (Exception e) {
                        LOGGER.error("討伐リクエスト失敗" + character, e);
                    }
                }
                result.put(character, tl);
            }
        }
        return result;
    }

    @Override
    public TobatsuList getTobatsuListFromDB(long webPcNo) throws SQLException {
        AccountDao accountDao = AccountDao.create(dbHelper);
        Character character = accountDao.findCharacterByWebPcNo(webPcNo);
        return getTobatsuListFromDB(character);
    }

    /**
     * DB情報を見ずにサーバーから情報を取得します(強制更新).
     * 結果はDBに永続化します.
     *
     * @param webPcNo
     */
    @Override
    public TobatsuList getTobatsuListFromServer(long webPcNo) throws AppException, SQLException {
        AccountDao accountDao = AccountDao.create(dbHelper);
        Character character = accountDao.findCharacterByWebPcNo(webPcNo);
        return getTobatsuListFromServer(character);
    }


    private TobatsuList getTobatsuListFromDB(Character character) throws SQLException {
        List<TobatsuList> lists = TobatsuListDao.create(dbHelper).queryLatest(character);
        // 現状は大国の分のみ永続化するためリストは1種類だけ
        assert lists.size() == 0 || lists.size() == 1;
        if (lists.isEmpty()) {
            return null;
        }
        return lists.get(0);
    }

    /**
     * DBを見ずに直接サーバーに情報をリクエストします.
     * 結果はDBに永続化します.
     */
    private TobatsuList getTobatsuListFromServer(Character character) throws SQLException, AppException {
        try {
            String sessionId = character.getAccount().getSessionId();
            LOGGER.info("update target character: {}", character);
            HappyService service = HappyServiceFactory.getService(sessionId);
            service.characterSelect(character.getWebPcNo());
            TobatsuDto dto = service.getTobatsuList();

            AccountDao dao = AccountDao.create(dbHelper);
            character.setLastTobatsuResultCode(dto.getResultCode());
            dao.persist(character);

            Account account = character.getAccount();
            account.setInvalid(false);
            dao.updateAccountOnly(account);


            // 現状は大国のみを対象とする
            TobatsuList res = TobatsuList.from(dto, TobatsuList.COUNTY_SIZE_TAIKOKU);
            res.setCharacter(character);
            TobatsuListDao.create(dbHelper).persist(res);
            return res;
        } catch (HappyServiceException e) {
            if (e.getType() == HappyServiceException.Type.HTTP) {
                // ネットワーク未接続の場合は response が null
                Response response = e.getCause().getResponse();
                if (response != null && response.getStatus() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    Account account = character.getAccount();
                    account.setInvalid(true);
                    AccountDao dao = AccountDao.create(dbHelper);
                    dao.updateAccountOnly(account);
                }
            } else if (e.getType() == HappyServiceException.Type.SERVERSIDE) {
                character.setLastTobatsuResultCode(e.getResultCode());
                AccountDao.create(dbHelper).persist(character);
            }
            throw e;
        }
    }
}
