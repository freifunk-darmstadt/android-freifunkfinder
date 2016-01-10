package de.tu_darmstadt.kom.freifunkfinder.common;

import android.location.Location;

/**
 * Created by govind on 12/10/2015.
 */
public class WifiAccessPointDTO {

    private String hostName;

    private double distance;

    private String nodeId;

    private String description;

    private String firstSeen;

    private String lastSeen;

    private boolean isOnline;

    private double uptime;

    private Location location;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirstSeen() {
        return firstSeen;
    }

    public void setFirstSeen(String firstSeen) {
        this.firstSeen = firstSeen;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public double getUptime() {
        return uptime;
    }

    public void setUptime(double uptime) {
        this.uptime = uptime;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return hostName +"_" +nodeId +"_" +distance;
    }
}
