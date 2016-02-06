package de.tu_darmstadt.kom.freifunkfinder.application;

import android.location.Location;

import java.util.Comparator;

import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;

/*
WifiAccessPointSorter - A Comparator that sorts the sorts the Wifi nodes based on their distance
from the  user's current location.
Copyright (C) 2016  Author: Govind Singh

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

govind.singh@stud.tu-darmstadt.de, TU Darmstadt, Germany
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
