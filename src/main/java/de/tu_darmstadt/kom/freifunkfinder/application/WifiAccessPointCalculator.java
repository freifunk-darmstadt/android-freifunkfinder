package de.tu_darmstadt.kom.freifunkfinder.application;

import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.tu_darmstadt.kom.freifunkfinder.common.GlobalParams;
import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;


/**
 * Created by govind on 12/10/2015.
 */
public class WifiAccessPointCalculator {

    private WifiAccessPointSorter wifiSorter;

    public List<WifiAccessPointDTO> calculateRelevantWifiNodes(Location location , List<WifiAccessPointDTO> allWifiNodes){
        Log.d("GPS LOCATION", " latitude : " +location.getLatitude() + " and longitude : " +location.getLongitude());
        wifiSorter = new WifiAccessPointSorter(location);
        Collections.sort(allWifiNodes, wifiSorter);
        List<WifiAccessPointDTO> relevantWifiNodes = new ArrayList<WifiAccessPointDTO>();
        for (WifiAccessPointDTO wifiNode : allWifiNodes){
            Log.d("DISTANCE", " distance of node id : " +wifiNode.getNodeId() + " = " +wifiNode.getDistance());
            if (wifiNode.getDistance() < GlobalParams.getSearchRange()){
                relevantWifiNodes.add(wifiNode);
            }else{
                break;
            }
        }
        return relevantWifiNodes;
    }
}
