package de.tu_darmstadt.kom.freifunkfinder.user_interface;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.List;
import de.tu_darmstadt.kom.freifunkfinder.application.WifiFinderApplication;
import de.tu_darmstadt.kom.freifunkfinder.application.WifiFinderApplicationInt;
import de.tu_darmstadt.kom.freifunkfinder.common.GlobalParams;
import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;


public class MainActivity extends AppCompatActivity {

    private CameraView cameraView;

    private WifiOverlayView wifiOverlayView;

    public MobileLocationManager mobileLocationManager;

    private FrameLayout arView;

    private ListView listView;

    private WifiFinderApplicationInt wifiFinderApplication;

    private Button button;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //First, do MobileLocationManageer instatiation
        mobileLocationManager = new MobileLocationManager(getApplicationContext());
        mobileLocationManager.initLocation();

        //Second, do WifiFinderApplication instantiation
        wifiFinderApplication = WifiFinderApplication.getWifiFinderApplication(getApplicationContext());

        //Third, do JSON read, need location info for this
        JsonReaderAsyncTask jsonReaderAsyncTask = new JsonReaderAsyncTask();
        jsonReaderAsyncTask.execute();

        //Fourth, do WifiOverlay instatiation
        wifiOverlayView = new WifiOverlayView(getApplicationContext(), mobileLocationManager);

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
            }
        });
    }

    // async inner class for db operation
    private class JsonReaderAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this, " Loading...", "");
        }

        @Override
        protected Void doInBackground(Void... params) {
            wifiFinderApplication.persistWifiNode();
            return null;
        }

        protected void onPostExecute() {
            GlobalParams.setIsWifiNodesPersisted(true);
            progressDialog.dismiss();
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
}