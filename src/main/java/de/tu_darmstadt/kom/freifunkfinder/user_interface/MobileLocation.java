package de.tu_darmstadt.kom.freifunkfinder.user_interface;

import android.location.Location;

import java.io.Serializable;

/**
 * Created by govind on 12/10/2015.
 */
public class MobileLocation implements Serializable {

    private static final long serialVersionUID = -7060210544600464481L;

    private static Location location = null;

    public static Location getLocation() {
        return location;
    }

    public static void setLocation(Location location) {
        MobileLocation.location = location;
    }
}
