/* MobileLocation - A wrapper class over the Android.Location.
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

import java.io.Serializable;

public class MobileLocation implements Serializable {

    private static final long serialVersionUID = -7060210544600464481L;

    private static Location location = null;

    /**
     * Gets the global wrapper Location.
     *
     * @return the Location object.
     */
    public static Location getLocation() {
        return location;
    }

    /**
     * Sets the global wrapper Location.
     *
     * @param location the Location object.
     */
    public static void setLocation(Location location) {
        if (location != null) {
            MobileLocation.location = location;
        }
    }
}
