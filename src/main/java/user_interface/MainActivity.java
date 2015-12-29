package user_interface;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import application.WifiFinderApplication;
import common.WifiAccessPointVO;
import de.tu_darmstadt.wififinderlatest.R;

public class MainActivity extends AppCompatActivity {

    private CameraView cameraView;

    private WifiOverlayView wifiOverlayView;

    private ListView listView;

    private WifiFinderApplication wifiFinderApplication;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifiFinderApplication = new WifiFinderApplication(getApplicationContext());
        button = (Button) findViewById(R.id.fetch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setVisibility(View.INVISIBLE);
                JsonReaderAsyncTask jsonReaderAsyncTask = new JsonReaderAsyncTask();
                jsonReaderAsyncTask.execute();
            }
        });

    }

    // async inner class for db operation
    private class JsonReaderAsyncTask extends AsyncTask<Void, Void, List<WifiAccessPointVO>> {

        @Override
        protected List<WifiAccessPointVO> doInBackground(Void... params) {
            wifiFinderApplication.persistWifiNode();
            return wifiFinderApplication.getAllWifiNodes();
        }

        protected void onPostExecute(final List<WifiAccessPointVO> result) {
            final ListView listView = (ListView) findViewById(R.id.listView1);
            ArrayAdapter<WifiAccessPointVO> arrayAdapter = new ArrayAdapter<WifiAccessPointVO>(MainActivity.this, android.R.layout.simple_list_item_1, result);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                    String selectedItem = result.get(position).toString();
                    Toast.makeText(getApplicationContext(), "Node Selected : " + selectedItem, Toast.LENGTH_LONG).show();
                }
            });
        }

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
