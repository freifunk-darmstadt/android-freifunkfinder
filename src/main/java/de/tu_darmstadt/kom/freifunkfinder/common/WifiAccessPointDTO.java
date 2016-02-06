package de.tu_darmstadt.kom.freifunkfinder.common;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/*
WifiAccessPointDTO - The domain object used across the layers.
Copyright (C) 2016  Author: Puneet Arora

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

puneet.arora@stud.tu-darmstadt.de, TU Darmstadt, Germany
*/

public class WifiAccessPointDTO implements Parcelable {

    public WifiAccessPointDTO() {

    }

    private String nodeId;

    private String hostName;

    private String description;

    private String firstSeen;

    private String lastSeen;

    private double distance;

    private boolean isOnline;

    private double uptime;

    private int clients;

    private double loadAverage;

    private Location location;

    private float currentBearing = 0.0f;

    private float dx = 0.0f;

    private float dy = 0.0f;

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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
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

    protected WifiAccessPointDTO(Parcel in) {
        hostName = in.readString();
        distance = in.readDouble();
        nodeId = in.readString();
        description = in.readString();
        firstSeen = in.readString();
        lastSeen = in.readString();
        isOnline = (in.readByte() != 0x00);
        uptime = in.readDouble();
        clients = in.readInt();
        loadAverage = in.readDouble();
        location = (Location) in.readValue(Location.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hostName);
        dest.writeDouble(distance);
        dest.writeString(nodeId);
        dest.writeString(description);
        dest.writeString(firstSeen);
        dest.writeString(lastSeen);
        dest.writeByte((byte) (isOnline ? 0x01 : 0x00));
        dest.writeDouble(uptime);
        dest.writeInt(clients);
        dest.writeDouble(loadAverage);
        dest.writeValue(location);
    }

    public static final Creator<WifiAccessPointDTO> CREATOR = new Creator<WifiAccessPointDTO>() {
        @Override
        public WifiAccessPointDTO createFromParcel(Parcel in) {
            return new WifiAccessPointDTO(in);
        }

        @Override
        public WifiAccessPointDTO[] newArray(int size) {
            return new WifiAccessPointDTO[size];
        }
    };
}
