package de.tu_darmstadt.kom.freifunkfinder.common.converter;

import org.json.JSONException;
import org.json.JSONObject;

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

    @Override
    public WifiAccessPointDTO serialize(JSONObject wifiJsonNode) throws JSONException{
        WifiAccessPointDTO wifiAccessPointDTO = new WifiAccessPointDTO();
        JSONObject nodeInfoKey = (JSONObject)wifiJsonNode.get("nodeinfo");
        JSONObject locationKey = nodeInfoKey.getJSONObject("location");
        //wifiAccessPointDTO.setLatitude((double) locationKey.getDouble("latitude"));
        //wifiAccessPointDTO.setLongitude((double) locationKey.getDouble("longitude"));
        if(locationKey.has("altitude")) {
            //wifiAccessPointDTO.setAltitude(locationKey.getDouble("altitude"));
        }
        wifiAccessPointDTO.setNodeId((String)nodeInfoKey.get("node_id"));
        wifiAccessPointDTO.setHostName((String)nodeInfoKey.get("hostname"));
        return wifiAccessPointDTO;
    }

    @Override
    public JSONObject deSerialize(WifiAccessPointDTO entity) {
        return null;
    }
}
