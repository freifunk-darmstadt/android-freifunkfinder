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

/**
 * Created by govind on 12/18/2015.
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


    private SQLiteDatabase database;

    private CursorAndDTOConverter cursorAndDTOConverter;

    public SqliteManager(Context context) {
        super(context, ApplicationConstants.DB_NAME, null, ApplicationConstants.DB_VERSION);
        cursorAndDTOConverter = CursorAndDTOConverter.getCursorAndDTOConverter();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ApplicationConstants.TABLE_NAME + " ( " + WIFI_NODE_ID + " VARCHAR,"
                + WIFI_NODE_NAME + " VARCHAR, "
                + DESCRIPTION + " VARCHAR, "
                + FIRST_SEEN + " VARCHAR, "
                + LAST_SEEN + " VARCHAR, "
                + UPTIME + " REAL, "
                + IS_ONLINE + " INTEGER, "
                + LATITUDE + " REAL, "
                + LONGITUDE + " REAL, "
                + ALTITUDE + " REAL);");

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
        if (rowCount <= 0) {
            isDatabaseEmpty = true;
        }
        return isDatabaseEmpty;
    }

    @Override
    public void write(WifiAccessPointDTO wifiAccessPointDTO) {
        database = this.getReadableDatabase();
        ContentValues dbRowValues = new ContentValues();
        String nodeId = wifiAccessPointDTO.getNodeId();
        dbRowValues.put(WIFI_NODE_ID, nodeId);
        dbRowValues.put(WIFI_NODE_NAME, wifiAccessPointDTO.getHostName());
        dbRowValues.put(DESCRIPTION, wifiAccessPointDTO.getDescription());
        dbRowValues.put(FIRST_SEEN, wifiAccessPointDTO.getFirstSeen());
        dbRowValues.put(LAST_SEEN, wifiAccessPointDTO.getLastSeen());
        dbRowValues.put(UPTIME, wifiAccessPointDTO.getUptime());
        // converting boolean to int for db
        int isOnline = (wifiAccessPointDTO.isOnline()) ? 1 : 0;
        dbRowValues.put(IS_ONLINE, isOnline);
        dbRowValues.put(LATITUDE, wifiAccessPointDTO.getLocation().getLatitude());
        dbRowValues.put(LONGITUDE, wifiAccessPointDTO.getLocation().getLongitude());
        dbRowValues.put(ALTITUDE, wifiAccessPointDTO.getLocation().getAltitude());
        long status = database.insert(ApplicationConstants.TABLE_NAME, null, dbRowValues);
        if (status != -1) {
            Log.d("DB INSERT : ", nodeId + " inserted successfully!");
        }
        database.close();
    }

    @Override
    public List<WifiAccessPointDTO> readAll() {
        List<WifiAccessPointDTO> wifiAccessPointDTOs = new ArrayList<WifiAccessPointDTO>();
        WifiAccessPointDTO wifiAccessPointDTO = null;
        database = this.getReadableDatabase();
        Cursor cursor = database.query(ApplicationConstants.TABLE_NAME, null, null, null, null, null, null);
        try {
                if(cursor.moveToFirst()) {
                    do {
                        wifiAccessPointDTO = cursorAndDTOConverter.serialize(cursor);
                        wifiAccessPointDTOs.add(wifiAccessPointDTO);
                    }while (cursor.moveToNext());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
        }
        cursor.close();
        database.close();
        Log.d("DB RETRIEVE :", "nodes retreived successfully!");
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        cursor.close();
        database.close();
        return wifiAccessPointDTO;
    }
}
