package de.tu_darmstadt.kom.freifunkfinder.common.converter;

import android.location.Location;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import de.tu_darmstadt.kom.freifunkfinder.common.GlobalParams;
import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;


/**
 * Created by govind,sooraj,puneet on 12/17/2015.
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

    @Override
    public WifiAccessPointDTO serialize(JSONObject wifiJsonNode) throws JSONException {
        WifiAccessPointDTO wifiAccessPointDTO = new WifiAccessPointDTO();
        wifiAccessPointDTO.setFirstSeen(wifiJsonNode.getString("firstseen"));
        wifiAccessPointDTO.setLastSeen(wifiJsonNode.getString("lastseen"));
        JSONObject nodeInfoKey = (JSONObject) wifiJsonNode.get("nodeinfo");
        String nodeId = (String) nodeInfoKey.get("node_id");
        if (nodeInfoKey.has("location")) {
            JSONObject locationKey = nodeInfoKey.getJSONObject("location");
            Location location = new Location(GlobalParams.getBestLocationProvider());
            location.setLatitude((double) locationKey.getDouble("latitude"));
            location.setLongitude((double) locationKey.getDouble("longitude"));
            if (locationKey.has("altitude")) {
                location.setAltitude(locationKey.getDouble("altitude"));
            }
            wifiAccessPointDTO.setLocation(location);
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
