package de.tu_darmstadt.kom.freifunkfinder.application;

import java.util.List;

import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;


/**
 * Created by govind on 12/10/2015.
 */
public interface WifiFinderApplicationInt {

    /*
    fetch all the nodes from the db which are
    close to the user depending on the user
    configured parameters.
     */
    List<WifiAccessPointDTO> getRelevantWifiNodes();

    /*
     * persist all the wi-fi nodes into db after reading them
     * from freifunk server
    */
    void persistWifiNode();

    /*
    fetch all the nodes from the db
     */
    List<WifiAccessPointDTO> getAllWifiNodes();
}
