/* WifiAccessPointCalculator - This class calculates the relevant Wifi nodes depending upon user's current location.
 * Copyright (C) 2016  Govind Singh
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
 * govind.singh@stud.tu-darmstadt.de, Technical University Darmstadt
 *
 */

package de.tu_darmstadt.kom.freifunkfinder.application;

import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.tu_darmstadt.kom.freifunkfinder.common.GlobalParams;
import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;

public class WifiAccessPointCalculator {

    private WifiAccessPointSorter wifiSorter;

    private static final String DEBUG_TAG = "WifiNodeCalculator : ";

    /**
     * Calculates a set of Wi-Fi nodes which are near to user and as per the configured parameters.
     *
     * @param location     the current location of the user.
     * @param allWifiNodes the total available Wi-Fi nodes in the database.
     * @return a list of Wi-Fi nodes relevant to user's current location depending upon the user configuration.
     */
    public List<WifiAccessPointDTO> calculateRelevantWifiNodes(Location location, List<WifiAccessPointDTO> allWifiNodes) {
        Log.d(DEBUG_TAG, " latitude : " + location.getLatitude() + " and longitude : " + location.getLongitude());
        wifiSorter = new WifiAccessPointSorter(location);
        Collections.sort(allWifiNodes, wifiSorter);
        List<WifiAccessPointDTO> relevantWifiNodes = new ArrayList<WifiAccessPointDTO>();
        for (WifiAccessPointDTO wifiNode : allWifiNodes) {
            double nodeDistance = wifiNode.getDistance();
            Log.d(DEBUG_TAG, " distance of node id : " + wifiNode.getNodeId() + " = " + nodeDistance);
            if (nodeDistance < GlobalParams.getSearchRange()) {
                relevantWifiNodes.add(wifiNode);
            } else {
                break;
            }
        }
        int totalRelNodes = relevantWifiNodes.size();
        Log.d(DEBUG_TAG, " total relevant nodes : " + totalRelNodes);
        int endIndex = (GlobalParams.getNodesCount() < totalRelNodes) ? GlobalParams.getNodesCount() : totalRelNodes;
        return relevantWifiNodes.subList(0, endIndex);
    }
}
