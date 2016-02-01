package de.tu_darmstadt.kom.freifunkfinder.common.converter;

import android.database.Cursor;
import android.location.Location;

import de.tu_darmstadt.kom.freifunkfinder.common.GlobalParams;
import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;


/**
 * Created by govind,sooraj,puneet on 12/18/2015.
 */
public class CursorAndDTOConverter implements ConverterInt<Cursor, WifiAccessPointDTO> {

    private static CursorAndDTOConverter cursorAndDTOConverter = null;

    // singleton converter
    private CursorAndDTOConverter() {

    }

    public static CursorAndDTOConverter getCursorAndDTOConverter() {
        if (cursorAndDTOConverter == null) {
            cursorAndDTOConverter = new CursorAndDTOConverter();
        }
        return cursorAndDTOConverter;
    }

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

    @Override
    public Cursor deSerialize(WifiAccessPointDTO entity) throws Exception {
        return null;
    }
}
