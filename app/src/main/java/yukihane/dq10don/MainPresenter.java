package yukihane.dq10don;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yukihane.dq10don.account.Account;
import yukihane.dq10don.account.Character;
import yukihane.dq10don.account.TobatsuItem;
import yukihane.dq10don.communication.HappyService;
import yukihane.dq10don.communication.HappyServiceFactory;
import yukihane.dq10don.communication.dto.login.LoginDto;
import yukihane.dq10don.communication.dto.tobatsu.TobatsuDataList;
import yukihane.dq10don.communication.dto.tobatsu.TobatsuDto;
import yukihane.dq10don.communication.dto.tobatsu.TobatsuList;
import yukihane.dq10don.db.AccountDao;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;

import static yukihane.dq10don.Utils.RESULTCODE_OK;

/**
 * Created by yuki on 15/07/13.
 */
public class MainPresenter {

    private static final Logger logger = LoggerFactory.getLogger(MainPresenter.class);

    private final View view;
    private final DbHelper dbHelper;

    /**
     * このインスタンスが表示対象としているキャラクター
     */
    private Character character;


    public MainPresenter(View view, DbHelperFactory dbHFactory) {
        this.view = view;
        dbHelper = dbHFactory.create();

    }

    public void onCreate() {

        try {
            AccountDao dao = AccountDao.create(dbHelper);
            List<Account> accounts = dao.queryAll();

            for (Account a : accounts) {
                logger.info("db account: {}", a);
            }

            if (!accounts.isEmpty()) {
                Account a = accounts.get(0);
                view.setSqexid(a.getSqexid());
                Iterator<Character> ite = a.getCharacters();
                if (ite.hasNext()) {
                    this.character = ite.next();
                    view.setCharacterName(this.character.getCharacterName());
                    logger.info("character's parent: {}", this.character.getAccount());
                }
            } else {
                logger.info("no db accout");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        OpenHelperManager.releaseHelper();
    }

    public void onActivityResult(String sqexid, String json) throws IOException {

        LoginDto dto = new ObjectMapper().readValue(json, LoginDto.class);
        if (dto.getResultCode() != RESULTCODE_OK) {
            // TODO ログインが成功していない
            logger.error("login failed: {}", dto.getResultCode());
        }
        Account account = Account.from(dto, sqexid);
        try {
            AccountDao dao = AccountDao.create(dbHelper);
            dao.persist(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        view.setSqexid(account.getSqexid());

        Iterator<Character> ite = account.getCharacters();
        if (ite.hasNext()) {
            this.character = ite.next();
            view.setCharacterName(this.character.getCharacterName());
        }
    }

    public void onUpdateClick() {

        Observable observable = Observable.create(new Observable.OnSubscribe<TobatsuDto>() {
            @Override
            public void call(Subscriber<? super TobatsuDto> subscriber) {
                subscriber.onStart();
                if (character == null) {
                    logger.error("need login");
                    subscriber.onError(new NullPointerException("need login"));
                } else {
                    String sessionId = character.getAccount().getSessionId();
                    logger.info("update target account: {}", character.getAccount());
                    logger.info("update target character: {}", character);
                    HappyService service = HappyServiceFactory.getService(sessionId);
                    service.characterSelect(character.getWebPcNo());
                    TobatsuDto res = service.getTobatsuList();
                    subscriber.onNext(res);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());

        view.bindToList(observable);

        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<TobatsuDto>() {

            private final yukihane.dq10don.account.TobatsuList list = new yukihane.dq10don.account.TobatsuList();

            @Override
            public void onCompleted() {
                logger.info("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                logger.error("onError", e);
            }

            @Override
            public void onNext(TobatsuDto dto) {
                logger.info("onNext");
                logger.info("getAcceptedTobatsuDataList size: {}", dto.getAcceptedTobatsuDataList().size());
                for (TobatsuDataList data : dto.getAcceptedTobatsuDataList()) {
                    logger.info("getTobatsuList size: {}", data.getTobatsuList().size());
                    for (TobatsuList tl : data.getTobatsuList()) {
                        logger.info("monster: {}", tl.getMonsterName());
                        TobatsuItem item = new TobatsuItem(tl.getMonsterName(),
                                tl.getArea() + "," + tl.getCount(), tl.getPoint());
                        list.addAccepting(item);
                    }
                }

                logger.info("getCountryTobatsuDataList size: {}", dto.getCountryTobatsuDataList().size());
                for (TobatsuDataList data : dto.getCountryTobatsuDataList()) {
                    logger.info("getTobatsuList size: {}", data.getTobatsuList().size());
                    for (TobatsuList tl : data.getTobatsuList()) {
                        logger.info("monster: {}", tl.getMonsterName());
                        TobatsuItem item = new TobatsuItem(tl.getMonsterName(),
                                tl.getArea() + "," + tl.getCount(), tl.getPoint());
                        list.addListing(item);
                    }
                }

                view.tobatsuListUpdate(list);
            }
        });
    }

    public interface View {
        void setSqexid(String sqexid);

        void setCharacterName(String characterName);

        void bindToList(Observable observable);

        void tobatsuListUpdate(yukihane.dq10don.account.TobatsuList list);
    }
}
