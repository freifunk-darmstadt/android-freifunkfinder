/* WifiAccessPointSorter - A Comparator that sorts the Wifi nodes based on their distance.
 * Copyright (C) 2016  Govind Singh
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
 * govind.singh@stud.tu-darmstadt.de, Technical University Darmstadt
 *
 */

package de.tu_darmstadt.kom.freifunkfinder.application;

import android.location.Location;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;

public class WifiAccessPointSorter implements Comparator<WifiAccessPointDTO> {

    private Location currentLocation;

    public WifiAccessPointSorter(Location location) {
        this.currentLocation = location;
    }

    /**
     * Calculates the Haversine distance between Location object 1 and Location object 2
     * Reference http://stackoverflow.com/questions/120283
     *
     * @param latitude1  the latitude of first object
     * @param longitude1 the longitude of first object
     * @param latitude2  the latitude of second object
     * @param longitude2 the longitude of second object
     * @return distance between these two objects
     */
    private double distanceCal(double latitude1, double longitude1, double latitude2, double longitude2) {
        // approximate radius of Earth in metres
        double radius = 6371000;
        double latitudeDiff = Math.toRadians(latitude2 - latitude1);
        double longitudeDiff = Math.toRadians(longitude2 - longitude1);
        double a = Math.sin(latitudeDiff / 2) * Math.sin(latitudeDiff / 2) +
                Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2)) *
                        Math.sin(longitudeDiff / 2) * Math.sin(longitudeDiff / 2);
        double angle = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return radius * angle;
    }


    /**
     * Compares the distance between the 2 objects from user's current location
     * Reference http://stackoverflow.com/questions/29711728
     *
     * @param lhs the object 1 in comparison
     * @param rhs the object 2 in comparison
     * @return positive, negative or zero depending upon distance of object 1 and object 2 from user's current location
     */
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
