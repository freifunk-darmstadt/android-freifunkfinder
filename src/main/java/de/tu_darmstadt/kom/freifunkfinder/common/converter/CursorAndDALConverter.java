package de.tu_darmstadt.kom.freifunkfinder.common.converter;

import android.database.Cursor;

import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDAL;

/**
 * Created by govind on 12/18/2015.
 */
public class CursorAndDALConverter implements ConverterInt<Cursor, WifiAccessPointDAL> {

    private static CursorAndDALConverter cursorAndDALConverter = null;

    // singleton converter
    private CursorAndDALConverter() {

    }

    public static CursorAndDALConverter getCursorAndDALConverter() {
        if (cursorAndDALConverter == null) {
            cursorAndDALConverter = new CursorAndDALConverter();
        }
        return cursorAndDALConverter;
    }

    @Override
    public WifiAccessPointDAL serialize(Cursor cursor) throws Exception {
        WifiAccessPointDAL wifiAccessPointDAL = new WifiAccessPointDAL();
        wifiAccessPointDAL.setNodeId(cursor.getString(0));
        wifiAccessPointDAL.setHostName(cursor.getString(1));
        //wifiAccessPointDAL.setLatitude(cursor.getDouble(2));
        //wifiAccessPointDAL.setLongitude(cursor.getDouble(3));
        //wifiAccessPointDAL.setAltitude(cursor.getDouble(4));
        return wifiAccessPointDAL;
    }

    @Override
    public Cursor deSerialize(WifiAccessPointDAL entity) throws Exception {
        return null;
    }
}
