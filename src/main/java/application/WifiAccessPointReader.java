package application;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import application.server.HttpServerImpl;
import application.server.ServerInterface;
import common.ApplicationConstants;
import common.GPSLocation;
import common.WifiAccessPointDTO;
import common.converter.JSONAndDTOConverter;

/**
 * Created by govind on 12/10/2015.
 */
public class WifiAccessPointReader {

    private WifiAccessPointDTO wifiAccessPointDTO;
    private ServerInterface<String> httpServer;
    private JSONAndDTOConverter jsonAndDTOConverter;

    public WifiAccessPointReader() {
        httpServer = new HttpServerImpl();
        jsonAndDTOConverter = JSONAndDTOConverter.getJsonAndDTOConverter();
    }

    private JSONObject getWifiJsonNodes(GPSLocation gpsLocation) {
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

    public List<WifiAccessPointDTO> getAllWifiNodes(GPSLocation gpsLocation) {
        List<WifiAccessPointDTO> wifiNodes = new ArrayList<WifiAccessPointDTO>();
        JSONObject wifiJsonNodes = getWifiJsonNodes(gpsLocation);
        for (int i = 0; i < wifiJsonNodes.length(); i++) {
            try {
                JSONObject wifiJsonNode = (JSONObject) ((JSONObject) wifiJsonNodes.getJSONObject(wifiJsonNodes.names().getString(i)));
                wifiNodes.add(jsonAndDTOConverter.serialize(wifiJsonNode));
            } catch (JSONException jsonEx) {
                jsonEx.printStackTrace();
            }
        }
        return wifiNodes;
    }
}
