package application;

import java.util.List;

import common.WifiAccessPointDTO;

/**
 * Created by govind on 12/10/2015.
 */
public class WifiAccessPointCalculator {

    private WifiAccessPointSorter wifiSorter;

    public List<WifiAccessPointDTO> calculateRelevantWifiNodes(List<WifiAccessPointDTO> wifiNodes){
        // // TODO: 12/18/2015 logic to calculate the related wifi nodes
        return wifiSorter.sortWifiNodes(wifiNodes);
    }
}
