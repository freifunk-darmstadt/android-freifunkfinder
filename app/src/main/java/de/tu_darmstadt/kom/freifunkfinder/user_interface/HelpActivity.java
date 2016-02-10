/* HelpActivity - An activity to display Help information.
 * Copyright (C) 2016  Sooraj Mandotti
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
 * sooraj.mandotti@stud.tu-darmstadt.de, Technical University Darmstadt
 *
 */

package de.tu_darmstadt.kom.freifunkfinder.user_interface;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    /**
     * Called by Android framework when this activity is created.
     *
     * @param savedInstanceState the bundle of saved instance stats.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_activity_main);
        TextView helpTextView = (TextView)findViewById(R.id.help_text);
        StringBuilder helpContent = new StringBuilder(" Press the 'Start Search' button to start searching " +
                "for Available Wi-Fi nodes.\n\n\n");
        helpContent.append(" The compass displays a summary of available nodes and in which direction.\n\n\n ");
        helpContent.append(" Press the left-side drop down Button to select the User configurable parameters. \n\n");
        helpContent.append(" Application supports following parameters :\n ");
        helpContent.append("  1. Distance : Scan radius of the Application that is within how many metres " +
                "from the User's current location. Default value is 1km and max possible value is 2km. \n ");
        helpContent.append("  2. Count : Number of nodes to be displayed on the screen. " +
                "The minimum of (Count, number of actual nodes actually available) will be ultimately " +
                "displayed on the screen. :\n\n\n ");
        helpContent.append(" Touching any of the node being displayed on device screen displays the " +
                "information about the node. \n\n ");
        helpContent.append(" The information includes :\n ");
        helpContent.append("  1. Node Id: Selected Wi-Fi node Id provided by Freifunk.\n ");
        helpContent.append("  2. Host name : Selected Wi-Fi node name as registered with Freifunk.\n ");
        helpContent.append("  3. Host description: Descriptive information about the node selected. Hardware specification as retrieved from Freifunk.\n ");
        helpContent.append("  4. First Seen: It shows when the node was first registered with Freifunk.\n ");
        helpContent.append("  5. Last Seen: It shows when was this node seen for last time.\n ");
        helpContent.append("  6. Uptime: Time since this node is active.\n ");
        helpContent.append("  7. Online: Whether the node is on-line or off-line.\n ");
        helpContent.append("  8. Distance from user: As calculated by the Application.\n ");
        helpContent.append("  9. Altitude: Height information of the selected node.\n ");
        helpContent.append("  10. Clients Count: Number of clients currently connected with this node.\n\n\n ");
        helpContent.append(" Description of Wi-Fi nodes icons : \n ");
        helpContent.append("  1. Green icon : Wi-Fi node is On-line.\n ");
        helpContent.append("  2. Red icon : Wi-Fi node is On-line.\n ");
        helpContent.append("  3. Green tick : Height information is available.\n ");
        helpContent.append("  4. Red tick : Height information is not available.\n\n\n ");
        helpContent.append(" Note : Wi-Fi/Celluar network on the device is pre-requisite in order to read information regarding the Wi-Fi nodes from Freifunk server. \n ");
        helpTextView.setText(helpContent);
    }
}
