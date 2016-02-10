/* NodeDescriptionActivity - An activity to display Wi-Fi node specific details.
 * Copyright (C) 2016  Puneet Arora
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * puneet.arora@stud.tu-darmstadt.de, Technical University Darmstadt
 *
 */

package de.tu_darmstadt.kom.freifunkfinder.user_interface;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;
import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;

public class NodeDescriptionActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private TextView textView;
    private TableRow tableRow;
    private static final String DISTANCE_UNIT = " m (ca.)" ;
    private static final String NOT_AVAIL = "--- Not Available ---";

    /**
     * Called by Android framework when this activity is created.
     *
     * @param savedInstanceState the bundle of saved instance stats.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.node_description_table);
        /*Intent intent = getIntent();
        WifiAccessPointDTO selectedNode = (WifiAccessPointDTO) intent.getParcelableExtra("selected_node");*/
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
                    textView.setText(String.valueOf((int)selectedNode.getDistance()) + DISTANCE_UNIT);
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

    /**
     * Converts the uptime in seconds to uptime in number of hours (if less than 24 hours) or days.
     *
     * @param uptimeSecs the uptime in seconds.
     * @return uptime in hours/days.
     */
    private String calUptime(double uptimeSecs){
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

    /**
     * Returns the altitude in a user-friendly displayable value.
     *
     * @param altitude the altitude in metres.
     * @return a formatted altitude.
     */
    private String calAltitude(double altitude){
        String res = NOT_AVAIL;
        if(altitude > 0.0){
            res = String.valueOf((int) altitude) + DISTANCE_UNIT;
        }
        return res;
    }

}
