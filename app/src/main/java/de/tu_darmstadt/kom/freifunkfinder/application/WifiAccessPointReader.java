/* WifiAccessPointReader - This class reads data from the Freifunk server.
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.tu_darmstadt.kom.freifunkfinder.application.server.HttpServerImpl;
import de.tu_darmstadt.kom.freifunkfinder.application.server.ServerInterface;
import de.tu_darmstadt.kom.freifunkfinder.common.FreifunkFinderAppConstants;
import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;
import de.tu_darmstadt.kom.freifunkfinder.common.converter.JSONAndDTOConverter;

public class WifiAccessPointReader {

    private static final String DEBUG_TAG = "WifiNodeReader : ";

    private ServerInterface<String> httpServer;

    private JSONAndDTOConverter jsonAndDTOConverter;

    public WifiAccessPointReader() {
        httpServer = new HttpServerImpl();
        jsonAndDTOConverter = JSONAndDTOConverter.getJsonAndDTOConverter();
    }

    /**
     * Gets the Json response from Freifunk server.
     *
     * @param location the current location of the user.
     * @return a Json Object having the response received from Freifunk server.
     */
    private JSONObject getWifiJsonNodes(Location location) {
        JSONObject jsonNodes = null;
        String response = httpServer.getRequest(FreifunkFinderAppConstants.FREIFUNK_URL);
        try {
            if (response != null) {
                JSONObject jsonResponse = new JSONObject(response);
                jsonNodes = (JSONObject) jsonResponse.get("nodes");
            } else {
                Log.d(DEBUG_TAG, "Server response is NULL.");
            }
        } catch (JSONException jsonEx) {
            jsonEx.printStackTrace();
        }
        return jsonNodes;
    }

    /**
     * Gets a list of all available Wi-Fi nodes from Freifunk server.
     *
     * @param location the current location of the user.
     * @return a list of all available Wi-Fi nodes.
     */
    public List<WifiAccessPointDTO> getAllWifiNodes(Location location) {
        List<WifiAccessPointDTO> wifiNodes = new ArrayList<WifiAccessPointDTO>();
        JSONObject wifiJsonNodes = getWifiJsonNodes(location);
        if (wifiJsonNodes != null) {
            for (int i = 0; i < wifiJsonNodes.length(); i++) {
                try {
                    JSONObject wifiJsonNode = (JSONObject) ((JSONObject) wifiJsonNodes.getJSONObject(wifiJsonNodes.names().getString(i)));
                    WifiAccessPointDTO wifiNode = jsonAndDTOConverter.serialize(wifiJsonNode);
                    if (wifiNode != null) {
                        wifiNodes.add(wifiNode);
                    }
                } catch (JSONException jsonEx) {
                    jsonEx.printStackTrace();
                }
            }
        } else {
            Log.d(DEBUG_TAG, "JSONObject received is NULL.");
        }
        return wifiNodes;
    }
}
