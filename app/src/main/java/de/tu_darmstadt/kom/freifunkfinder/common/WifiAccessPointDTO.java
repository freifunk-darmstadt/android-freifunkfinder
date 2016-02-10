/* WifiAccessPointDTO - The domain object used across the layers.
 * Copyright (C) 2016  Puneet Arora
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * puneet.arora@stud.tu-darmstadt.de, Technical University Darmstadt
 *
 */

package de.tu_darmstadt.kom.freifunkfinder.common;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

public class WifiAccessPointDTO implements Parcelable {

    /**
     * Default constructor.
     */
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

    /**
     * Gets the node unique Id.
     *
     * @return the nodeId.
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     * Sets the node unique Id.
     *
     * @param nodeId the nodeId.
     */
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    /**
     * Gets the node Name.
     *
     * @return the hostName.
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Sets the node Name.
     *
     * @param hostName the hostName.
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * Gets the node description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the node description.
     *
     * @param description the description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the node firstSeen timestamp.
     *
     * @return the firstSeen timestamp.
     */
    public String getFirstSeen() {
        return firstSeen;
    }

    /**
     * Sets the node firstSeen timestamp.
     *
     * @param firstSeen the firstSeen timestamp.
     */
    public void setFirstSeen(String firstSeen) {
        this.firstSeen = firstSeen;
    }

    /**
     * Gets the node lastSeen timestamp.
     *
     * @return the lastSeen timestamp.
     */
    public String getLastSeen() {
        return lastSeen;
    }

    /**
     * Sets the node lastSeen timestamp.
     *
     * @param lastSeen the firstSeen timestamp.
     */
    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    /**
     * Gets the distance.
     *
     * @return the distance.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Sets the distance.
     *
     * @param distance the distance.
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * Gets isOnline flag.
     *
     * @return the isOnline.
     */
    public boolean isOnline() {
        return isOnline;
    }

    /**
     * Sets the isOnline flag.
     *
     * @param isOnline the isOnline.
     */
    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    /**
     * Gets the uptime in hours/days.
     *
     * @return the uptime.
     */
    public double getUptime() {
        return uptime;
    }

    /**
     * Sets the uptime in hours/days.
     *
     * @param uptime the uptime.
     */
    public void setUptime(double uptime) {
        this.uptime = uptime;
    }

    /**
     * Gets number of connected clients.
     *
     * @return the connected clients.
     */
    public int getClients() {
        return clients;
    }

    /**
     * Sets the number of connected clients.
     *
     * @param clients the connected clients.
     */
    public void setClients(int clients) {
        this.clients = clients;
    }

    /**
     * Gets the Average load.
     *
     * @return the Average load.
     */
    public double getLoadAverage() {
        return loadAverage;
    }

    /**
     * Sets the Average load.
     *
     * @param loadAverage the Average load.
     */
    public void setLoadAverage(double loadAverage) {
        this.loadAverage = loadAverage;
    }

    /**
     * Gets the current location.
     *
     * @return the current location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the  current location.
     *
     * @param location the current location.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Gets the current Bearing.
     *
     * @return the current Bearing.
     */
    public float getCurrentBearing() {
        return currentBearing;
    }

    /**
     * Sets the current Bearing.
     *
     * @param currentBearing the current Bearing.
     */
    public void setCurrentBearing(float currentBearing) {
        this.currentBearing = currentBearing;
    }

    /**
     * Gets x coordinate.
     *
     * @return the x coordinate.
     */
    public float getDx() {
        return dx;
    }

    /**
     * Sets the x coordinate.
     *
     * @param dx the x coordinate.
     */
    public void setDx(float dx) {
        this.dx = dx;
    }

    /**
     * Gets y coordinate.
     *
     * @return the y coordinate.
     */
    public float getDy() {
        return dy;
    }

    /**
     * Sets the y coordinate.
     *
     * @param dy the y coordinate.
     */
    public void setDy(float dy) {
        this.dy = dy;
    }

    /**
     * Parameterized constructor.
     * Reference http://www.parcelabler.com/
     *
     * @param in the input Parcel object.
     */
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

    /**
     * @return 0.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Writes to Parcel object.
     * Reference http://www.parcelabler.com/
     *
     * @param dest  the destination Parcel object.
     * @param flags the flag for inner object
     */
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

    /**
     * Inner class as required by Parcelable interface.
     * Reference Reference http://www.parcelabler.com/
     */
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
