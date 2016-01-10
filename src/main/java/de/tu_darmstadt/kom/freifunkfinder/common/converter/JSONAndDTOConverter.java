package de.tu_darmstadt.kom.freifunkfinder.common.converter;

import android.location.Location;

import org.json.JSONException;
import org.json.JSONObject;

import de.tu_darmstadt.kom.freifunkfinder.common.GlobalParams;
import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;


/**
 * Created by govind on 12/17/2015.
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

    //// TODO: 1/8/2016 uptime, hardware info might not be avail for some active nodes
    @Override
    public WifiAccessPointDTO serialize(JSONObject wifiJsonNode) throws JSONException {
        WifiAccessPointDTO wifiAccessPointDTO = new WifiAccessPointDTO();
        wifiAccessPointDTO.setFirstSeen(wifiJsonNode.getString("firstseen"));
        wifiAccessPointDTO.setLastSeen(wifiJsonNode.getString("lastseen"));
        JSONObject nodeInfoKey = (JSONObject) wifiJsonNode.get("nodeinfo");
        JSONObject locationKey = nodeInfoKey.getJSONObject("location");
        Location location = new Location(GlobalParams.getBestLocationProvider());
        location.setLatitude((double) locationKey.getDouble("latitude"));
        location.setLongitude((double) locationKey.getDouble("longitude"));
        if (locationKey.has("altitude")) {
            location.setAltitude(locationKey.getDouble("altitude"));
        }
        wifiAccessPointDTO.setLocation(location);
        wifiAccessPointDTO.setNodeId((String) nodeInfoKey.get("node_id"));
        wifiAccessPointDTO.setHostName((String) nodeInfoKey.get("hostname"));
        JSONObject hardwareKey = (JSONObject)nodeInfoKey.get("hardware");
        wifiAccessPointDTO.setDescription(hardwareKey.getString("model"));
        JSONObject flagsKey = (JSONObject) wifiJsonNode.get("flags");
        wifiAccessPointDTO.setIsOnline(flagsKey.getBoolean("online"));
        JSONObject statisticKey = (JSONObject) wifiJsonNode.get("statistics");
        wifiAccessPointDTO.setUptime((double)statisticKey.getDouble("uptime"));
        return wifiAccessPointDTO;
    }

    @Override
    public JSONObject deSerialize(WifiAccessPointDTO entity) {
        return null;
    }
}
