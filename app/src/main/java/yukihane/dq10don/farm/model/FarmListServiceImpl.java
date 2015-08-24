package yukihane.dq10don.farm.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import retrofit.client.Response;
import yukihane.dq10don.Utils;
import yukihane.dq10don.account.Account;
import yukihane.dq10don.account.Character;
import yukihane.dq10don.account.Farm;
import yukihane.dq10don.communication.HappyService;
import yukihane.dq10don.communication.HappyServiceFactory;
import yukihane.dq10don.communication_game.GameService;
import yukihane.dq10don.communication_game.GameServiceFactory;
import yukihane.dq10don.communication_game.dto.farm.info.GameInfoDto;
import yukihane.dq10don.communication_game.dto.farm.mowgrass.MowGrassDto;
import yukihane.dq10don.communication_game.dto.time.ServerTimeDto;
import yukihane.dq10don.db.AccountDao;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.FarmDao;
import yukihane.dq10don.exception.AppException;
import yukihane.dq10don.exception.HappyServiceException;

/**
 * Created by yuki on 2015/08/17.
 */
public class FarmListServiceImpl implements FarmListService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FarmListServiceImpl.class);

    private final DbHelper dbHelper;

    public FarmListServiceImpl(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public Map<Character, Farm> getContentsFromServer() throws SQLException {
        // 牧場は自動更新を実装しないので本メソッドは実装不要
        throw new UnsupportedOperationException();
    }

    @Override
    public Farm getContentFromDB(long webPcNo) throws SQLException {
        return FarmDao.create(dbHelper).query(webPcNo);
    }

    @Override
    public Farm getContentFromServer(long webPcNo) throws AppException, SQLException {
        AccountDao accountDao = AccountDao.create(dbHelper);
        Character character = accountDao.findCharacterByWebPcNo(webPcNo);
        return getContentFromServer(character);
    }

    @Override
    public int mowGrasses(long webPcNo, List<Long> tickets) throws SQLException, AppException {
        AccountDao accountDao = AccountDao.create(dbHelper);
        Character character = accountDao.findCharacterByWebPcNo(webPcNo);

        String sessionId = character.getAccount().getSessionId();
        LOGGER.info("update target character: {}", character);
        HappyService service = HappyServiceFactory.getService(sessionId);
        service.characterSelect(character.getWebPcNo());
        service.farmLogin();

        GameService gameService = GameServiceFactory.getService(sessionId);
        gameService.login();

        MowGrassDto mowGrassDto = gameService.mowGrass(tickets);
        return mowGrassDto.getData().getItemList().size();
    }

    /**
     * DBを見ずに直接サーバーに情報をリクエストします.
     * 結果はDBに永続化します.
     */
    private Farm getContentFromServer(Character character)
            throws SQLException, AppException {
        try {

            return getContentFromServerInternal(character);

        } catch (HappyServiceException e) {
            if (e.getType() == HappyServiceException.Type.HTTP) {
                // ネットワーク未接続の場合は response が null
                Response response = e.getCause().getResponse();
                if (response != null && response.getStatus() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    // 未認証のエラーで帰ってきたら account に印をつける
                    Account account = character.getAccount();
                    account.setInvalid(true);
                    AccountDao dao = AccountDao.create(dbHelper);
                    dao.updateAccountOnly(account);
                }
            }
            throw e;
        }
    }

    private Farm getContentFromServerInternal(Character character)
            throws SQLException, AppException {

        String sessionId = character.getAccount().getSessionId();
        LOGGER.info("update target character: {}", character);
        HappyService service = HappyServiceFactory.getService(sessionId);
        service.characterSelect(character.getWebPcNo());
        service.farmLogin();

        GameService gameService = GameServiceFactory.getService(sessionId);
        gameService.login();

        GameInfoDto gameInfoDto = gameService.getInfo();
        Farm farm = Farm.from(gameInfoDto);
        farm.setCharacter(character);

        ServerTimeDto timeDto = gameService.getServerTime();
        String serverTimeStr = timeDto.getData().getTime();
        farm.setLastUpdateDate(Utils.parseDate(serverTimeStr));

        FarmDao.create(dbHelper).persist(farm);

        return farm;
    }
}