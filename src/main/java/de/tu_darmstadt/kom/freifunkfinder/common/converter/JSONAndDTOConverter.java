package de.tu_darmstadt.kom.freifunkfinder.common.converter;

import android.location.Location;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import de.tu_darmstadt.kom.freifunkfinder.common.GlobalParams;
import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;

/*
JSONAndDTOConverter - An implementation of ConverterInt to convert Json object read from Freifunk server to Domain object.
Copyright (C) 2016  Author: Puneet Arora

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

puneet.arora@stud.tu-darmstadt.de, TU Darmstadt, Germany
*/

public class JSONAndDTOConverter implements ConverterInt<JSONObject, WifiAccessPointDTO> {

    private static JSONAndDTOConverter jsonAndDTOConverter = null;

    // singleton converter
    private JSONAndDTOConverter() {

    }

    public static JSONAndDTOConverter getJsonAndDTOConverter() {
        if (jsonAndDTOConverter == null) {
            jsonAndDTOConverter = new JSONAndDTOConverter();
        }
        return jsonAndDTOConverter;
    }

    private Location getLocation(JSONObject locationKey) throws JSONException{
        Location location = new Location(GlobalParams.getBestLocationProvider());
        location.setLatitude((double) locationKey.getDouble("latitude"));
        location.setLongitude((double) locationKey.getDouble("longitude"));
        if (locationKey.has("altitude")) {
            location.setAltitude(locationKey.getDouble("altitude"));
        }
        return location;
    }

    @Override
    public WifiAccessPointDTO serialize(JSONObject wifiJsonNode) throws JSONException {
        WifiAccessPointDTO wifiAccessPointDTO = new WifiAccessPointDTO();
        wifiAccessPointDTO.setFirstSeen(wifiJsonNode.getString("firstseen"));
        wifiAccessPointDTO.setLastSeen(wifiJsonNode.getString("lastseen"));
        JSONObject nodeInfoKey = (JSONObject) wifiJsonNode.get("nodeinfo");
        String nodeId = (String) nodeInfoKey.get("node_id");
        if (nodeInfoKey.has("location")) {
            wifiAccessPointDTO.setLocation(getLocation(nodeInfoKey.getJSONObject("location")));
        } else {
            Log.d("JSON_NODE", "No Location information and thus not persisting node : " +nodeId);
            return null;
        }
        wifiAccessPointDTO.setNodeId(nodeId);
        wifiAccessPointDTO.setHostName((String) nodeInfoKey.get("hostname"));
        if (nodeInfoKey.has("hardware")) {
            JSONObject hardwareKey = (JSONObject) nodeInfoKey.get("hardware");
            if (hardwareKey.has("model")) {
                wifiAccessPointDTO.setDescription(hardwareKey.getString("model"));
            }
        }
        JSONObject flagsKey = (JSONObject) wifiJsonNode.get("flags");
        wifiAccessPointDTO.setIsOnline(flagsKey.getBoolean("online"));
        JSONObject statisticKey = (JSONObject) wifiJsonNode.get("statistics");
        wifiAccessPointDTO.setClients((int)statisticKey.getInt("clients"));
        if (statisticKey.has("uptime")) {
            wifiAccessPointDTO.setUptime((double) statisticKey.getDouble("uptime"));
        }
        if(statisticKey.has("loadavg")){
            wifiAccessPointDTO.setLoadAverage((double) statisticKey.getDouble("loadavg"));
        }
        return wifiAccessPointDTO;
    }

    @Override
    public JSONObject deSerialize(WifiAccessPointDTO entity) {
        return null;
    }
}
