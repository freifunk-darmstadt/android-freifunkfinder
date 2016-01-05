package de.tu_darmstadt.kom.freifunkfinder.user_interface;

import android.location.Location;

/**
 * Created by govind on 12/10/2015.
 */
public class MobileLocation {

    private static Location location = null;

    public static Location getLocation() {
        return location;
    }

    public static void setLocation(Location location) {
        MobileLocation.location = location;
    }
}
