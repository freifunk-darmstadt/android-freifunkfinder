package de.tu_darmstadt.kom.freifunkfinder.common;

import de.tu_darmstadt.kom.freifunkfinder.user_interface.MobileLocation;

/**
 * Created by govind on 12/10/2015.
 */
public class WifiAccessPointDAL extends MobileLocation {

    private double distance;

    private String nodeId;

    private String hostName;

    private String description;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
