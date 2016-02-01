package de.tu_darmstadt.kom.freifunkfinder.common;

import android.location.Location;

import java.io.Serializable;

/**
 * Created by govind on 12/10/2015.
 */
public class WifiAccessPointDTO implements Serializable {

    private static final long serialVersionUID = -7060210544600464481L;

    private String hostName;

    private double distance;

    private String nodeId;

    private String description;

    private String firstSeen;

    private String lastSeen;

    private boolean isOnline;

    private double uptime;

    private Location location;

    private float currentBearing = 0.0f;

    private float dx = 0.0f;

    private float dy = 0.0f;

    private int clients;

    private double loadAverage;

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

    public float getCurrentBearing() {
        return currentBearing;
    }

    public void setCurrentBearing(float currentBearing) {
        this.currentBearing = currentBearing;
    }

    public float getDx() {
        return dx;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public float getDy() {
        return dy;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    public int getClients() {
        return clients;
    }

    public void setClients(int clients) {
        this.clients = clients;
    }

    public double getLoadAverage() {
        return loadAverage;
    }

    public void setLoadAverage(double loadAverage) {
        this.loadAverage = loadAverage;
    }

}
