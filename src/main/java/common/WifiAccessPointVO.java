package common;

/**
 * Created by govind on 12/10/2015.
 */
public class WifiAccessPointVO extends GPSLocation {

    private double distance;

    private String hostName;

    private String nodeId;

    private String description;

    public double getDistance() {
        return distance;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
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
}
