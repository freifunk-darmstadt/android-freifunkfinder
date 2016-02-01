package de.tu_darmstadt.kom.freifunkfinder.application;

import android.location.Location;

import java.util.Comparator;

import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;


/**
 * Created by govind,sooraj,puneet on 12/10/2015.
 */
public class WifiAccessPointSorter implements Comparator<WifiAccessPointDTO> {

    private Location currentLocation;

    public WifiAccessPointSorter(Location location) {
        this.currentLocation = location;
    }

    private double distanceCal(double latitude1, double longitude1, double latitude2, double longitude2) {
        double radius = 6371000;   // approximate Earth radius, *in meters*
        double latitudeDiff = Math.toRadians(latitude2 - latitude1);
        double longitudeDiff = Math.toRadians(longitude2 - longitude1);
        double a = Math.sin(latitudeDiff/2) * Math.sin(latitudeDiff/2) +
                Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2)) *
                        Math.sin(longitudeDiff/2) * Math.sin(longitudeDiff/2);
        double angle = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return radius * angle;
    }


    @Override
    public int compare(final WifiAccessPointDTO lhs, final WifiAccessPointDTO rhs) {
        double lat1 = lhs.getLocation().getLatitude();
        double long1 = lhs.getLocation().getLongitude();
        double lat2 = rhs.getLocation().getLatitude();
        double long2 = rhs.getLocation().getLongitude();
        double distanceToLhs = distanceCal(currentLocation.getLatitude(), currentLocation.getLongitude(), lat1, long1);
        lhs.setDistance(distanceToLhs);
        double distanceToRhs = distanceCal(currentLocation.getLatitude(), currentLocation.getLongitude(), lat2, long2);
        rhs.setDistance(distanceToRhs);
        return (int) (distanceToLhs - distanceToRhs);
    }
}
