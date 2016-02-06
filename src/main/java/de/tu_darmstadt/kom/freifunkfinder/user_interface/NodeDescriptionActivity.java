package de.tu_darmstadt.kom.freifunkfinder.user_interface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;


/**
 * Created by govind on 1/8/2016.
 */
public class NodeDescriptionActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private TextView textView;
    private TableRow tableRow;
    private static final String DISTANCE_UNIT = " m (ca.)";
    private static final String NOT_AVAIL = "-- Not Available --";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.node_description_table);
        Bundle b = getIntent().getExtras();
        WifiAccessPointDTO selectedNode = b.getParcelable("selected_node");
        tableLayout = (TableLayout) findViewById(R.id.tableLayout1);
        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            tableRow = (TableRow) tableLayout.getChildAt(i);
            textView = (TextView) tableRow.getChildAt(1);
            switch (i) {
                case 0:
                    textView.setText(selectedNode.getNodeId());
                    break;
                case 1:
                    textView.setText(selectedNode.getHostName());
                    break;
                case 2:
                    String desc = selectedNode.getDescription();
                    textView.setText((desc != null && desc.length() > 0) ? desc : NOT_AVAIL);
                    break;
                case 3:
                    textView.setText(selectedNode.getFirstSeen());
                    break;
                case 4:
                    textView.setText(selectedNode.getLastSeen());
                    break;
                case 5:
                    textView.setText(calUptime(selectedNode.getUptime()));
                    break;
                case 6:
                    textView.setText(String.valueOf(selectedNode.isOnline()));
                    break;
                case 7:
                    textView.setText(String.valueOf((int) selectedNode.getDistance()) + DISTANCE_UNIT);
                    break;
                case 8:
                    textView.setText(calAltitude(selectedNode.getLocation().getAltitude()));
                    break;
                case 9:
                    textView.setText(String.valueOf(selectedNode.getClients()));
                    break;
                default:
                    System.out.print("wrong index");
            }
        }
    }

    private String calUptime(double uptimeSecs) {
        String res = NOT_AVAIL;
        if (uptimeSecs > 0.0) {
            int days = (int) TimeUnit.SECONDS.toDays((long) uptimeSecs);
            long hours = TimeUnit.SECONDS.toHours((long) uptimeSecs) - TimeUnit.SECONDS.toHours(TimeUnit.SECONDS.toDays((long) uptimeSecs));
            if (days <= 0) {
                res = hours + " hours";
            } else {
                res = days + " days ";
            }
        }
        return res;
    }

    private String calAltitude(double altitude) {
        String res = NOT_AVAIL;
        if (altitude > 0.0) {
            res = String.valueOf((int) altitude) + DISTANCE_UNIT;
        }
        return res;
    }

}
