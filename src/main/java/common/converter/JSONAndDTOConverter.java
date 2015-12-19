package common.converter;

import org.json.JSONException;
import org.json.JSONObject;

import common.WifiAccessPointDTO;

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
        JSONObject locationKey = (JSONObject)nodeInfoKey.getJSONObject("location");
        wifiAccessPointDTO.setLatitude((double) locationKey.get("latitude"));
        wifiAccessPointDTO.setLongitude((double) locationKey.get("longitude"));
        wifiAccessPointDTO.setAltitude((double) locationKey.get("altitude"));
       wifiAccessPointDTO.setNodeId((String)nodeInfoKey.get("node_id"));
        wifiAccessPointDTO.setHostName((String)nodeInfoKey.get("hostname"));
        return wifiAccessPointDTO;
    }

    @Override
    public JSONObject deSerialize(WifiAccessPointDTO entity) {
        return null;
    }
}
