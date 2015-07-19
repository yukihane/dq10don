package yukihane.dq10don.tobatsu.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yukihane.dq10don.account.Account;
import yukihane.dq10don.account.Character;
import yukihane.dq10don.account.TobatsuList;
import yukihane.dq10don.communication.HappyService;
import yukihane.dq10don.communication.HappyServiceFactory;
import yukihane.dq10don.communication.dto.tobatsu.TobatsuDto;
import yukihane.dq10don.db.AccountDao;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.TobatsuListDao;

/**
 * Created by yuki on 15/07/18.
 */
public class TobatsuServiceImpl implements TobatsuService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TobatsuServiceImpl.class);

    private final DbHelper dbHelper;

    public TobatsuServiceImpl(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public Map<Character, TobatsuList> getTobatsuList() throws SQLException {

        AccountDao accountDao = AccountDao.create(dbHelper);
        List<Account> accounts = accountDao.queryAll();
        Map<Character, TobatsuList> result = new HashMap<>();
        for (Account account : accounts) {
            for (Character character : account.getCharacters()) {
                TobatsuList tl = getTobatsuList(character);
                result.put(character, tl);
            }
        }
        return result;
    }

    @Override
    public TobatsuList getTobatsuList(long webPcNo) throws SQLException {
        AccountDao accountDao = AccountDao.create(dbHelper);
        Character character = accountDao.findCharacterByWebPcNo(webPcNo);
        return getTobatsuList(character);
    }

    private TobatsuList getTobatsuList(Character character) throws SQLException {
        TobatsuList dbRes = getTobatsuListFromDB(character);
        if(dbRes != null) {
            LOGGER.debug("found on DB");
            return dbRes;
        }
        // DBにない場合はサーバへ要求
        LOGGER.debug("not found on DB");
        return getTobatsuListFromServer(character);
    }


    /**
     * DBを見ずに直接サーバーに情報をリクエストします.
     * 結果はDBに永続化します.
     */
    private TobatsuList getTobatsuListFromServer(Character character) throws SQLException {
        String sessionId = character.getAccount().getSessionId();
        LOGGER.info("update target character: {}", character);
        HappyService service = HappyServiceFactory.getService(sessionId);
        service.characterSelect(character.getWebPcNo());
        TobatsuDto dto = service.getTobatsuList();

        // 現状は大国のみを対象とする
        TobatsuList res = TobatsuList.from(dto, TobatsuList.COUNTY_SIZE_TAIKOKU);
        res.setCharacter(character);
        TobatsuListDao.create(dbHelper).persist(res);
        return res;
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
}
