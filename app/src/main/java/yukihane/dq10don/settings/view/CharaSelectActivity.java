package yukihane.dq10don.settings.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import rx.Observable;
import rx.android.app.AppObservable;
import yukihane.dq10don.R;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.settings.presenter.CharaSelectPresenter;
import yukihane.dq10don.settings.presenter.CheckableCharacter;
import yukihane.dq10don.twitter.view.PrefUtils;

/**
 * Tweet対象を全員でなく特定のキャラクタ分のみに絞るときの設定アクティビティ.
 */
public class CharaSelectActivity extends ActionBarActivity implements CharaSelectPresenter.View {

    private CharaSelectPresenter presenter;

    private ArrayAdapter<CheckableCharacter> listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chara_select);

        presenter = new CharaSelectPresenter(this, new DbHelperFactory(this), new PrefUtils(this));

        ListView mainListView = (ListView) findViewById(R.id.tobatsuTweetCharaList);

        mainListView.setOnItemClickListener((AdapterView<?> parent, View item,
                                             int position, long id) -> {
            CheckableCharacter character = listAdapter.getItem(position);
            presenter.onCheckChange(position, !character.isChecked());
            CharacterViewHolder viewHolder = (CharacterViewHolder) item.getTag();
            viewHolder.getCheckBox().setChecked(character.isChecked());
        });

        presenter.onCreate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void bind(Observable<?> observable) {
        AppObservable.bindActivity(this, observable);
    }

    @Override
    public void setData(List<CheckableCharacter> checkableCharacters) {
        // Set our custom array adapter as the ListView's adapter.
        listAdapter = new CharacterArrayAdapter(this, checkableCharacters, presenter);
        ListView mainListView = (ListView) findViewById(R.id.tobatsuTweetCharaList);
        mainListView.setAdapter(listAdapter);
    }

    private static class CharacterViewHolder {
        private CheckBox checkBox;
        private TextView textView;

        public CharacterViewHolder() {
        }

        public CharacterViewHolder(TextView textView, CheckBox checkBox) {
            this.checkBox = checkBox;
            this.textView = textView;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public void setCheckBox(CheckBox checkBox) {
            this.checkBox = checkBox;
        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }
    }

    private static class CharacterArrayAdapter extends ArrayAdapter<CheckableCharacter> {

        private final CharaSelectPresenter presenter;
        private LayoutInflater inflater;

        public CharacterArrayAdapter(Context context, List<CheckableCharacter> list,
                                     CharaSelectPresenter presenter) {
            super(context, R.layout.list_tobatsu_tweet_character, R.id.tobatsuTweetCharacterView, list);
            // Cache the LayoutInflate to avoid asking for a new one each time.
            inflater = LayoutInflater.from(context);
            this.presenter = presenter;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            CheckableCharacter character = this.getItem(position);

            // The child views in each row.
            CheckBox checkBox;
            TextView textView;

            // Create a new row view
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_tobatsu_tweet_character, null);

                // Find the child views.
                textView = (TextView) convertView.findViewById(R.id.tobatsuTweetCharacterView);
                checkBox = (CheckBox) convertView.findViewById(R.id.tobatsuTweetableCheck);

                // Optimization: Tag the row with it's child views, so we don't have to
                // call findViewById() later when we reuse the row.
                convertView.setTag(new CharacterViewHolder(textView, checkBox));

                checkBox.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        presenter.onCheckChange(position, cb.isChecked());
                    }
                });
            }
            // Reuse existing row view
            else {
                // Because we use a ViewHolder, we avoid having to call findViewById().
                CharacterViewHolder viewHolder = (CharacterViewHolder) convertView.getTag();
                checkBox = viewHolder.getCheckBox();
                textView = viewHolder.getTextView();
            }

            checkBox.setChecked(character.isChecked());
            String name = character.getCharacterName() + " (" + character.getSmileUniqNo() + ")";
            textView.setText(name);

            return convertView;
        }

    }
}