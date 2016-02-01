package de.tu_darmstadt.kom.freifunkfinder.application;

import android.location.Location;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.tu_darmstadt.kom.freifunkfinder.application.server.HttpServerImpl;
import de.tu_darmstadt.kom.freifunkfinder.application.server.ServerInterface;
import de.tu_darmstadt.kom.freifunkfinder.common.ApplicationConstants;
import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;
import de.tu_darmstadt.kom.freifunkfinder.common.converter.JSONAndDTOConverter;


/**
 * Created by govind,sooraj,puneet on 12/10/2015.
 */
public class WifiAccessPointReader {

    private ServerInterface<String> httpServer;
    private JSONAndDTOConverter jsonAndDTOConverter;

    public WifiAccessPointReader() {
        httpServer = new HttpServerImpl();
        jsonAndDTOConverter = JSONAndDTOConverter.getJsonAndDTOConverter();
    }

    private JSONObject getWifiJsonNodes(Location location) {
        JSONObject jsonNodes = null;
        String response = httpServer.getRequest(ApplicationConstants.FREIFUNK_URL);
        try {
            JSONObject jsonResponse = new JSONObject(response);
            jsonNodes = (JSONObject) jsonResponse.get("nodes");

        } catch (JSONException jsonEx) {
            jsonEx.printStackTrace();
        }
        return jsonNodes;
    }

    public List<WifiAccessPointDTO> getAllWifiNodes(Location location) {
        List<WifiAccessPointDTO> wifiNodes = new ArrayList<WifiAccessPointDTO>();
        JSONObject wifiJsonNodes = getWifiJsonNodes(location);
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
        return wifiNodes;
    }
}
