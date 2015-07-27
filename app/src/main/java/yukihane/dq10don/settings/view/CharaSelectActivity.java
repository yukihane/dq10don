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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import yukihane.dq10don.R;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.settings.presenter.CharaSelectPresenter;
import yukihane.dq10don.twitter.view.PrefUtils;

public class CharaSelectActivity extends ActionBarActivity implements CharaSelectPresenter.View {

    private CharaSelectPresenter presenter;

    private Planet[] planets;
    private ArrayAdapter<Planet> listAdapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chara_select);

        presenter = new CharaSelectPresenter(this, new DbHelperFactory(this), new PrefUtils(this));

        // Find the ListView resource.
        ListView mainListView = (ListView) findViewById(R.id.tobatsuTweetCharaList);

        // When item is tapped, toggle checked properties of CheckBox and Planet.
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item,
                                    int position, long id) {
                Planet planet = listAdapter.getItem(position);
                planet.toggleChecked();
                PlanetViewHolder viewHolder = (PlanetViewHolder) item.getTag();
                viewHolder.getCheckBox().setChecked(planet.isChecked());
            }
        });


        // Create and populate planets.
        planets = (Planet[]) getLastCustomNonConfigurationInstance();
        if (planets == null) {
            planets = new Planet[]{
                    new Planet("Mercury"), new Planet("Venus"), new Planet("Earth"),
                    new Planet("Mars"), new Planet("Jupiter"), new Planet("Saturn"),
                    new Planet("Uranus"), new Planet("Neptune"), new Planet("Ceres"),
                    new Planet("Pluto"), new Planet("Haumea"), new Planet("Makemake"),
                    new Planet("Eris"), new Planet("Epsilon Eridani"), new Planet("Gliese 876 b"),
                    new Planet("HD 209458 b")
            };
        }
        ArrayList<Planet> planetList = new ArrayList<Planet>();
        planetList.addAll(Arrays.asList(planets));

        // Set our custom array adapter as the ListView's adapter.
        listAdapter = new PlanetArrayAdapter(this, planetList);
        mainListView.setAdapter(listAdapter);
    }

    public Object onRetainCustomNonConfigurationInstance() {
        return planets;
    }

    /**
     * Holds planet data.
     */
    private static class Planet {
        private String name = "";
        private boolean checked = false;

        public Planet() {
        }

        public Planet(String name) {
            this.name = name;
        }

        public Planet(String name, boolean checked) {
            this.name = name;
            this.checked = checked;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public String toString() {
            return name;
        }

        public void toggleChecked() {
            checked = !checked;
        }
    }

    /**
     * Holds child views for one row.
     */
    private static class PlanetViewHolder {
        private CheckBox checkBox;
        private TextView textView;

        public PlanetViewHolder() {
        }

        public PlanetViewHolder(TextView textView, CheckBox checkBox) {
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

    /**
     * Custom adapter for displaying an array of Planet objects.
     */
    private static class PlanetArrayAdapter extends ArrayAdapter<Planet> {

        private LayoutInflater inflater;

        public PlanetArrayAdapter(Context context, List<Planet> planetList) {
            super(context, R.layout.list_tobatsu_tweet_character, R.id.rowTextView, planetList);
            // Cache the LayoutInflate to avoid asking for a new one each time.
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Planet to display
            Planet planet = this.getItem(position);

            // The child views in each row.
            CheckBox checkBox;
            TextView textView;

            // Create a new row view
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_tobatsu_tweet_character, null);

                // Find the child views.
                textView = (TextView) convertView.findViewById(R.id.rowTextView);
                checkBox = (CheckBox) convertView.findViewById(R.id.CheckBox01);

                // Optimization: Tag the row with it's child views, so we don't have to
                // call findViewById() later when we reuse the row.
                convertView.setTag(new PlanetViewHolder(textView, checkBox));

                // If CheckBox is toggled, update the planet it is tagged with.
                checkBox.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        Planet planet = (Planet) cb.getTag();
                        planet.setChecked(cb.isChecked());
                    }
                });
            }
            // Reuse existing row view
            else {
                // Because we use a ViewHolder, we avoid having to call findViewById().
                PlanetViewHolder viewHolder = (PlanetViewHolder) convertView.getTag();
                checkBox = viewHolder.getCheckBox();
                textView = viewHolder.getTextView();
            }

            // Tag the CheckBox with the Planet it is displaying, so that we can
            // access the planet in onClick() when the CheckBox is toggled.
            checkBox.setTag(planet);

            // Display planet data
            checkBox.setChecked(planet.isChecked());
            textView.setText(planet.getName());

            return convertView;
        }

    }
}