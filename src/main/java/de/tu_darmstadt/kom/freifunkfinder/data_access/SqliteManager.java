package de.tu_darmstadt.kom.freifunkfinder.data_access;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.tu_darmstadt.kom.freifunkfinder.common.ApplicationConstants;
import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;
import de.tu_darmstadt.kom.freifunkfinder.common.converter.CursorAndDTOConverter;

/*
SqliteManager - An implementation of DatabaseManagerInt to perform SQLite related operations.
Copyright (C) 2016  Author: Puneet Arora

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

puneet.arora@stud.tu-darmstadt.de, TU Darmstadt, Germany
*/

public class SqliteManager extends SQLiteOpenHelper implements DatabaseManagerInt {

    /* column names */
    private static final String WIFI_NODE_ID = "ID";
    private static final String WIFI_NODE_NAME = "HOST_NAME";
    private static final String LATITUDE = "LATITUDE";
    private static final String LONGITUDE = "LONGITUDE";
    private static final String ALTITUDE = "ALTITUDE";
    private static final String FIRST_SEEN = "FIRST_SEEN";
    private static final String LAST_SEEN = "LAST_SEEN";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String IS_ONLINE = "IS_ONLINE";
    private static final String UPTIME = "UPTIME";
    private static final String CLIENTS = "CLIENTS";
    private static final String LOAD_AVG = "LOAD_AVERAGE";

    private static final String DEBUG_TAG = "SQLiteManager : ";

    private SQLiteDatabase database;

    private CursorAndDTOConverter cursorAndDTOConverter;

    public SqliteManager(Context context) {
        super(context, ApplicationConstants.DB_NAME, null, ApplicationConstants.DB_VERSION);
        cursorAndDTOConverter = CursorAndDTOConverter.getCursorAndDTOConverter();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ApplicationConstants.TABLE_NAME + " ( " + WIFI_NODE_ID + " VARCHAR PRIMARY KEY,"
                + WIFI_NODE_NAME + " VARCHAR, "
                + DESCRIPTION + " VARCHAR, "
                + FIRST_SEEN + " VARCHAR, "
                + LAST_SEEN + " VARCHAR, "
                + UPTIME + " REAL, "
                + IS_ONLINE + " INTEGER, "
                + CLIENTS + " INTEGER, "
                + LOAD_AVG + " REAL, "
                + LATITUDE + " REAL, "
                + LONGITUDE + " REAL, "
                + ALTITUDE + " REAL);");
        Log.d(DEBUG_TAG, "Table created successfully.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // default implementation
        db.execSQL("DROP TABLE IF EXISTS " + ApplicationConstants.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public boolean isDatabaseEmpty() {
        boolean isDatabaseEmpty = false;
        database = this.getReadableDatabase();
        String count = "SELECT count(*) FROM " + ApplicationConstants.TABLE_NAME;
        Cursor cursor = database.rawQuery(count, null);
        cursor.moveToFirst();
        int rowCount = cursor.getInt(0);
        Log.d(DEBUG_TAG, "Row Count : " + rowCount);
        if (rowCount <= 0) {
            isDatabaseEmpty = true;
        }
        return isDatabaseEmpty;
    }

    @Override
    public void write(WifiAccessPointDTO wifiAccessPointDTO) {
        String nodeId = wifiAccessPointDTO.getNodeId();
        try {
            database = this.getReadableDatabase();
            ContentValues dbRowValues = new ContentValues();
            dbRowValues.put(WIFI_NODE_ID, nodeId);
            dbRowValues.put(WIFI_NODE_NAME, wifiAccessPointDTO.getHostName());
            dbRowValues.put(DESCRIPTION, wifiAccessPointDTO.getDescription());
            dbRowValues.put(FIRST_SEEN, wifiAccessPointDTO.getFirstSeen());
            dbRowValues.put(LAST_SEEN, wifiAccessPointDTO.getLastSeen());
            dbRowValues.put(UPTIME, wifiAccessPointDTO.getUptime());
            // converting boolean to int for db
            int isOnline = (wifiAccessPointDTO.isOnline()) ? 1 : 0;
            dbRowValues.put(IS_ONLINE, isOnline);
            dbRowValues.put(CLIENTS, wifiAccessPointDTO.getClients());
            dbRowValues.put(LOAD_AVG, wifiAccessPointDTO.getLoadAverage());
            dbRowValues.put(LATITUDE, wifiAccessPointDTO.getLocation().getLatitude());
            dbRowValues.put(LONGITUDE, wifiAccessPointDTO.getLocation().getLongitude());
            dbRowValues.put(ALTITUDE, wifiAccessPointDTO.getLocation().getAltitude());
            long status = database.replace(ApplicationConstants.TABLE_NAME, null, dbRowValues);
            if (status != -1) {
                Log.d(DEBUG_TAG, nodeId + " upserted successfully!");
            }
            database.close();
        } catch (Exception e) {
            Log.d(DEBUG_TAG, nodeId + " could not be upserted!" + e.getCause());
        }
    }

    @Override
    public List<WifiAccessPointDTO> readAll() {
        List<WifiAccessPointDTO> wifiAccessPointDTOs = new ArrayList<WifiAccessPointDTO>();
        WifiAccessPointDTO wifiAccessPointDTO = null;
        Cursor cursor = null;
        database = this.getReadableDatabase();
        try {
            cursor = database.query(ApplicationConstants.TABLE_NAME, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    wifiAccessPointDTO = cursorAndDTOConverter.serialize(cursor);
                    wifiAccessPointDTOs.add(wifiAccessPointDTO);
                } while (cursor.moveToNext());
                Log.d(DEBUG_TAG, "nodes retreived successfully!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        cursor.close();
        database.close();
        return wifiAccessPointDTOs;
    }

    @Override
    public WifiAccessPointDTO read(String nodeId) {
        WifiAccessPointDTO wifiAccessPointDTO = null;
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + ApplicationConstants.TABLE_NAME +
                " where " + WIFI_NODE_ID + " = " + nodeId, null);
        try {
            cursor.moveToNext();
            wifiAccessPointDTO = cursorAndDTOConverter.serialize(cursor);
            Log.d(DEBUG_TAG, nodeId + " retreived successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        cursor.close();
        database.close();
        return wifiAccessPointDTO;
    }
}
