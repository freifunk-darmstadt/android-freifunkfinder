package de.tu_darmstadt.kom.freifunkfinder.user_interface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;


/**
 * Created by govind on 1/8/2016.
 */
public class NodeDescriptionActivity extends AppCompatActivity {

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    TextView textView8;
    TextView textView9;
    TextView textView10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_description);
        Intent intent = getIntent();
        WifiAccessPointDTO selectedNode = (WifiAccessPointDTO) intent.getSerializableExtra("selected_node");
        textView1 = (TextView) findViewById(R.id.node_id);
        textView1.setText("node id = " +selectedNode.getNodeId());
        textView2 = (TextView) findViewById(R.id.hostname);
        textView2.setText("host name = " +selectedNode.getHostName());
        textView3 = (TextView) findViewById(R.id.latitude);
        textView3.setText("latitude = " +selectedNode.getLocation().getLatitude());
        textView4 = (TextView) findViewById(R.id.longitude);
        textView4.setText("longitude = " +selectedNode.getLocation().getLongitude());
        textView5 = (TextView) findViewById(R.id.description);
        textView5.setText("description = " +selectedNode.getDescription());
        textView6= (TextView) findViewById(R.id.firstSeen);
        textView6.setText("first seen = " +selectedNode.getFirstSeen());
        textView7 = (TextView) findViewById(R.id.lastSeen);
        textView7.setText("last seen = " +selectedNode.getLastSeen());
        textView8 = (TextView) findViewById(R.id.uptime);
        textView8.setText("uptime = " +selectedNode.getUptime());
        textView9 = (TextView) findViewById(R.id.isOnline);
        textView9.setText("is online = " +selectedNode.isOnline());
        textView10 = (TextView)findViewById(R.id.distance);
        textView10.setText("distance = " +selectedNode.getDistance());
    }
}
