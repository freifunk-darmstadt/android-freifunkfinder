package de.tu_darmstadt.kom.freifunkfinder.user_interface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;

/**
 * Created by govind on 1/8/2016.
 */
public class NodeDescriptionActivity extends AppCompatActivity {

    TableLayout tableLayout;
    TextView textView;
    TableRow tableRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.node_description_table);
        Intent intent = getIntent();
        WifiAccessPointDTO selectedNode = (WifiAccessPointDTO) intent.getSerializableExtra("selected_node");
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
                    textView.setText(selectedNode.getDescription());
                    break;
                case 3:
                    textView.setText(selectedNode.getFirstSeen());
                    break;
                case 4:
                    textView.setText(selectedNode.getLastSeen());
                    break;
                case 5:
                    textView.setText(String.valueOf(selectedNode.getUptime()));
                    break;
                case 6:
                    textView.setText(String.valueOf(selectedNode.isOnline()));
                    break;
                case 7:
                    textView.setText(String.valueOf(selectedNode.getDistance()));
                    break;
                case 8:
                    textView.setText(String.valueOf(selectedNode.getLocation().getAltitude()));
                    break;
                case 9:
                    textView.setText(String.valueOf(selectedNode.getClients()));
                    break;
                case 10:
                    textView.setText(String.valueOf(selectedNode.getLoadAverage()));
                    break;
                default:
                    System.out.print("wrong index");

            }
        }
    }
}
