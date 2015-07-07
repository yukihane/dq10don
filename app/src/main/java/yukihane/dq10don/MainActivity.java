package yukihane.dq10don;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import yukihane.dq10don.login.LoginAccountDto;


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
                String userId = data.getStringExtra("userId");
                String json = data.getStringExtra("result");
                logger.info("LOGIN success({}): {}", userId, json);
                LoginAccountDto dto = LoginAccountDto.fromJson(json);
            } else {
                logger.error("LOGIN fail");
            }
        } catch (IOException e) {
            logger.error("parse error", e);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
