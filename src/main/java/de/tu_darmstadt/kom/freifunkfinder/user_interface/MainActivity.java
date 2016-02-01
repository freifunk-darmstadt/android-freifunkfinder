package de.tu_darmstadt.kom.freifunkfinder.user_interface;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import de.tu_darmstadt.kom.freifunkfinder.application.WifiFinderApplication;
import de.tu_darmstadt.kom.freifunkfinder.application.WifiFinderApplicationInt;
import de.tu_darmstadt.kom.freifunkfinder.common.GlobalParams;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private CameraView cameraView;

    private WifiOverlayView wifiOverlayView;

    public MobileLocationManager mobileLocationManager;

    private FrameLayout arView;

    private WifiFinderApplicationInt wifiFinderApplication;

    private Button button;

    private ProgressBar progressBar;

    private TextView progressBarTxt;

    private boolean isOverlayView = false;

    private boolean isParamChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        progressBarTxt = (TextView) findViewById(R.id.progress_bar_txt);
        progressBarTxt.setVisibility(View.INVISIBLE);


        //First, do MobileLocationManageer instantiation
        mobileLocationManager = new MobileLocationManager(getApplicationContext(), MainActivity.this);
        mobileLocationManager.initLocation();

        //Second, do WifiFinderApplication instantiation
        wifiFinderApplication = WifiFinderApplication.getWifiFinderApplication(getApplicationContext());

        //Third, do JSON read, need location info for this
        JsonReaderAsyncTask jsonReaderAsyncTask = new JsonReaderAsyncTask();
        jsonReaderAsyncTask.execute();

        //Fourth, do WifiOverlay instantiation
        wifiOverlayView = new WifiOverlayView(getApplicationContext(), this, mobileLocationManager);

        //Sixth, do CameraView instantiation
        cameraView = new CameraView(getApplicationContext(), MainActivity.this);

        //Seventh, do FrameLayout instantiation
        arView = (FrameLayout) findViewById(R.id.ar_view);

        //Eighth, wait for button press
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arView.addView(cameraView);
                arView.addView(wifiOverlayView);
                view.setVisibility(View.INVISIBLE);
                if (!GlobalParams.isWifiNodesPersisted()) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBarTxt.setVisibility(View.VISIBLE);
                }
                isOverlayView = true;
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final TextView seekTextView = (TextView) findViewById(R.id.seekTextView);
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int distance;
            Double lookupDistance = 999.000;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                isParamChanged = true;
                distance = (progresValue * 2000 / seekBar.getMax());
                GlobalParams.setSearchRange(distance);
                lookupDistance = (distance < 999 ? ((double) distance) : (distance / 1000.00));
                seekTextView.setText("Distnce : " + String.format("%.2f", lookupDistance) +
                        " " + (distance < 999 ? "Meters" : "Kilometers"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isParamChanged = true;
                distance = (seekBar.getProgress() * 2000 / seekBar.getMax());
                GlobalParams.setSearchRange(distance);
                lookupDistance = (distance < 999 ? ((double) distance) : (distance / 1000.00));
                seekTextView.setText("Distnce : " + String.format("%.2f", lookupDistance) +
                        " " + (distance < 999 ? "Meters" : "Kilometers"));
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isParamChanged = true;
                distance = (seekBar.getProgress() * 2000 / seekBar.getMax());
                GlobalParams.setSearchRange(distance);
                lookupDistance = (distance < 999 ? ((double) distance) : (distance / 1000.00));
                seekTextView.setText("Distnce : " + String.format("%.2f", lookupDistance) +
                        " " + (distance < 999 ? "Meters" : "Kilometers"));
            }
        });
		
		final EditText editText = (EditText) findViewById(R.id.countView);
        editText.addTextChangedListener(new TextWatcher() {
            int count;

            public void afterTextChanged(Editable s) {
                isParamChanged = true;

                if (!editText.getText().toString().equals("")) {
                    count = Integer.parseInt(editText.getText().toString());
                    GlobalParams.setNodesCount(count);
                } else {
                    GlobalParams.setNodesCount(10);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    // async inner class for db operation
    private class JsonReaderAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            wifiFinderApplication.persistWifiNode();
            return null;
        }

        protected void onPostExecute(Void param) {
            Log.d("mainactivity", "nPostExecute()");
            GlobalParams.setIsWifiNodesPersisted(true);
            progressBar.setVisibility(View.INVISIBLE);
            progressBarTxt.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onPause() {
        wifiOverlayView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        wifiOverlayView.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            if (isParamChanged){
                wifiOverlayView.loadRelevantHotSpots();
            }
        } else if (isOverlayView){
            arView.removeAllViews();
            button.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            progressBarTxt.setVisibility(View.INVISIBLE);
            isOverlayView = false;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

        if (id == R.id.help) {
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}