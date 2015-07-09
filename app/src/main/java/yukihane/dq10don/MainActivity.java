package yukihane.dq10don;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;
import rx.schedulers.Schedulers;
import yukihane.dq10don.account.Account;
import yukihane.dq10don.account.Character;
import yukihane.dq10don.communication.HappyService;
import yukihane.dq10don.communication.HappyServiceFactory;
import yukihane.dq10don.communication.dto.login.LoginDto;
import yukihane.dq10don.communication.dto.tobatsu.TobatsuDataList;
import yukihane.dq10don.communication.dto.tobatsu.TobatsuDto;
import yukihane.dq10don.communication.dto.tobatsu.TobatsuList;
import yukihane.dq10don.view.TobatsuItem;
import yukihane.dq10don.view.TobatsuViewAdapter;

import static yukihane.dq10don.Utils.RESULTCODE_OK;


public class MainActivity extends ActionBarActivity {

    private final Logger logger = LoggerFactory.getLogger(MainActivity.class);

    /**
     * このインスタンスが表示対象としているキャラクター
     */
    private Character character;
    private TobatsuViewAdapter tobatsuViewAdapter;

    private DbHelper m_dbHelper;


    private DbHelper getDbHelper() {
        if (m_dbHelper == null) {
            m_dbHelper = OpenHelperManager.getHelper(this, DbHelper.class);
        }
        return m_dbHelper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tobatsuViewAdapter = new TobatsuViewAdapter(
                (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        ListView countryListView = (ListView) findViewById(R.id.tobatsuListView);
        countryListView.setAdapter(tobatsuViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (m_dbHelper != null) {
            OpenHelperManager.releaseHelper();
            m_dbHelper = null;
        }
    }

    public void onLoginClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        // TODO ユーザーIDを設定する
        intent.putExtra("userId", "");
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        logger.debug("requestCode: {}, resultCode: {}, intent: {}", requestCode, resultCode, data != null);

        try {
            if (data != null) {
                String sqexid = data.getStringExtra("userId");
                String json = data.getStringExtra("result");
                logger.info("LOGIN success({}): {}", sqexid, json);
                LoginDto dto = new ObjectMapper().readValue(json, LoginDto.class);
                if (dto.getResultCode() != RESULTCODE_OK) {
                    // TODO ログインが成功していない
                    logger.error("login failed: {}", dto.getResultCode());
                }
                Account account = Account.from(dto, sqexid);

                TextView sqexIdView = (TextView) findViewById(R.id.accountNameView);
                sqexIdView.setText(account.getSqexid());

                Iterator<Character> ite = account.getCharacters();
                if (ite.hasNext()) {
                    this.character = ite.next();
                    TextView charaNameView = (TextView) findViewById(R.id.charaNameView);
                    charaNameView.setText(this.character.getCharacterName());

                }

            } else {
                logger.error("LOGIN fail");
            }
        } catch (IOException e) {
            logger.error("parse error", e);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onUpdateClick(View view) {

        Observable observable = Observable.create(new Observable.OnSubscribe<TobatsuDto>() {
            @Override
            public void call(Subscriber<? super TobatsuDto> subscriber) {
                subscriber.onStart();
                if (character == null) {
                    logger.error("need login");
                    subscriber.onError(new NullPointerException("need login"));
                } else {
                    String sessionId = character.getAccount().getSessionId();
                    HappyService service = HappyServiceFactory.getService(sessionId);
                    service.characterSelect(character.getWebPcNo());
                    TobatsuDto res = service.getTobatsuList();
                    subscriber.onNext(res);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());

        ViewObservable.bindView(view, observable);

        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<TobatsuDto>() {
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
                tobatsuViewAdapter.addItem(String.class, "じゅちゅーちゅー");
                for (TobatsuDataList data : dto.getAcceptedTobatsuDataList()) {
                    logger.info("getTobatsuList size: {}", data.getTobatsuList().size());
                    for (TobatsuList tl : data.getTobatsuList()) {
                        logger.info("monster: {}", tl.getMonsterName());
                        TobatsuItem item = new TobatsuItem(tl.getMonsterName(),
                                tl.getArea() + "," + tl.getCount(), tl.getPoint());
                        tobatsuViewAdapter.addItem(TobatsuItem.class, item);
                    }
                }

                tobatsuViewAdapter.addItem(String.class, "リスト");
                logger.info("getCountryTobatsuDataList size: {}", dto.getCountryTobatsuDataList().size());
                for (TobatsuDataList data : dto.getCountryTobatsuDataList()) {
                    logger.info("getTobatsuList size: {}", data.getTobatsuList().size());
                    for (TobatsuList tl : data.getTobatsuList()) {
                        logger.info("monster: {}", tl.getMonsterName());
                        TobatsuItem item = new TobatsuItem(tl.getMonsterName(),
                                tl.getArea() + "," + tl.getCount(), tl.getPoint());
                        tobatsuViewAdapter.addItem(TobatsuItem.class, item);
                    }
                }

                tobatsuViewAdapter.notifyChanged();
            }
        });
    }
}
