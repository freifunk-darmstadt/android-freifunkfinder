/* CursorAndDTOConverter - An implementation of ConverterInt to convert Database object to Domain object.
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

package de.tu_darmstadt.kom.freifunkfinder.common.converter;

import android.database.Cursor;
import android.location.Location;

import de.tu_darmstadt.kom.freifunkfinder.common.GlobalParams;
import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;

public class CursorAndDTOConverter implements ConverterInt<Cursor, WifiAccessPointDTO> {

    private static CursorAndDTOConverter cursorAndDTOConverter = null;

    /**
     * Private constructor.
     */
    private CursorAndDTOConverter() {

    }

    /**
     * Returns a singleton object of this class.
     *
     * @return a singleton Object.
     */
    public static CursorAndDTOConverter getCursorAndDTOConverter() {
        if (cursorAndDTOConverter == null) {
            cursorAndDTOConverter = new CursorAndDTOConverter();
        }
        return cursorAndDTOConverter;
    }

    /**
     * Serializes an object of type database Cursor into an object of type WifiAccessPointDTO.
     *
     * @param cursor an object of type Cursor.
     * @return a new serialized object of type WifiAccessPointDTO from Cursor.
     * @throws Exception if an error occurs due to type mismatch.
     */
    @Override
    public WifiAccessPointDTO serialize(Cursor cursor) throws Exception {
        WifiAccessPointDTO wifiAccessPointDTO = new WifiAccessPointDTO();
        wifiAccessPointDTO.setNodeId(cursor.getString(0));
        wifiAccessPointDTO.setHostName(cursor.getString(1));
        wifiAccessPointDTO.setDescription(cursor.getString(2));
        wifiAccessPointDTO.setFirstSeen(cursor.getString(3));
        wifiAccessPointDTO.setLastSeen(cursor.getString(4));
        wifiAccessPointDTO.setUptime(cursor.getDouble(5));
        // converting int (from db) to boolean
        boolean isOnline = (cursor.getInt(6) == 1) ? true : false;
        wifiAccessPointDTO.setIsOnline(isOnline);
        wifiAccessPointDTO.setClients(cursor.getInt(7));
        wifiAccessPointDTO.setLoadAverage(cursor.getDouble(8));
        Location location = new Location(GlobalParams.getBestLocationProvider());
        location.setLatitude(cursor.getDouble(9));
        location.setLongitude(cursor.getDouble(10));
        location.setAltitude(cursor.getDouble(11));
        wifiAccessPointDTO.setLocation(location);
        return wifiAccessPointDTO;
    }

    /**
     * De-serializes an object of type WifiAccessPointDTO into an object of type database Cursor.
     *
     * @param entity an object of type WifiAccessPointDTO.
     * @return a new de-serialized object of type Cursor from WifiAccessPointDTO.
     * @throws Exception if an error occurs due to type mismatch.
     */
    @Override
    public Cursor deSerialize(WifiAccessPointDTO entity) throws Exception {
        return null;
    }
}
