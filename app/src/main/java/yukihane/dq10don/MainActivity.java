package yukihane.dq10don;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;

import yukihane.dq10don.account.Account;
import yukihane.dq10don.account.Character;
import yukihane.dq10don.communication.dto.login.LoginDto;

import static yukihane.dq10don.Utils.RESULTCODE_OK;


public class MainActivity extends ActionBarActivity {

    private final Logger logger = LoggerFactory.getLogger(MainActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                if(dto.getResultCode() != RESULTCODE_OK) {
                    // TODO ログインが成功していない
                    logger.error("login failed: {}", dto.getResultCode());
                }
                Account account = Account.from(dto, sqexid);

                TextView sqexIdView = (TextView) findViewById(R.id.accountNameView);
                sqexIdView.setText(account.getSqexid());

                Iterator<Character> ite = account.getCharacters();
                while(ite.hasNext()) {
                    Character c = ite.next();
                    TextView charaNameView = (TextView) findViewById(R.id.charaNameView);
                    charaNameView.setText(c.getCharacterName());
                }

            } else {
                logger.error("LOGIN fail");
            }
        } catch (IOException e) {
            logger.error("parse error", e);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
