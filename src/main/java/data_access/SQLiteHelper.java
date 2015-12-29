package data_access;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import common.ApplicationConstants;
import common.WifiAccessPointDAL;
import common.converter.CursorAndDALConverter;

/**
 * Created by govind on 12/18/2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    /* column names */
    private static final String WIFI_NODE_ID = "ID";
    private static final String WIFI_NODE_NAME = "HOST_NAME";
    private static final String LATITUDE = "LATITUDE";
    private static final String LONGITUDE = "LONGITUDE";
    private static final String ALTITUDE = "ALTITUDE";

    private SQLiteDatabase database;

    private CursorAndDALConverter cursorAndDALConverter;

    public SQLiteHelper(Context context) {
        super(context, ApplicationConstants.DB_NAME, null, ApplicationConstants.DB_VERSION);
        cursorAndDALConverter = CursorAndDALConverter.getCursorAndDALConverter();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ApplicationConstants.TABLE_NAME + " ( " + WIFI_NODE_ID + " VARCHAR,"
                + WIFI_NODE_NAME + " VARCHAR, "
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

    public void insertRecord(WifiAccessPointDAL wifiAccessPointDAL) {
        database = this.getReadableDatabase();
        ContentValues dbRowValues = new ContentValues();
        String nodeId = wifiAccessPointDAL.getNodeId();
        dbRowValues.put(WIFI_NODE_ID, nodeId);
        dbRowValues.put(WIFI_NODE_NAME, wifiAccessPointDAL.getHostName());
        dbRowValues.put(LATITUDE, wifiAccessPointDAL.getLatitude());
        dbRowValues.put(LONGITUDE, wifiAccessPointDAL.getLongitude());
        dbRowValues.put(ALTITUDE, wifiAccessPointDAL.getAltitude());
        long status = database.insert(ApplicationConstants.TABLE_NAME, null, dbRowValues);
        if (status != -1) {
            Log.d("DB INSERT : ", nodeId + " inserted successfully!");
        }
        database.close();
    }

    public List<WifiAccessPointDAL> getAllRecords() {
        List<WifiAccessPointDAL> wifiAccessPointDALs = new ArrayList<WifiAccessPointDAL>();
        WifiAccessPointDAL wifiAccessPointDAL = null;
        database = this.getReadableDatabase();
        Cursor cursor = database.query(ApplicationConstants.TABLE_NAME, null, null, null, null, null, null);
        for (int i = 0; i < cursor.getCount(); i++) {
            try {
                cursor.moveToNext();
                wifiAccessPointDAL = cursorAndDALConverter.serialize(cursor);
                wifiAccessPointDALs.add(wifiAccessPointDAL);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        cursor.close();
        database.close();
        Log.d("DB RETRIEVE :", "nodes retreived successfully!");
        return wifiAccessPointDALs;
    }

    public WifiAccessPointDAL getRecord(String nodeId) {
        WifiAccessPointDAL wifiAccessPointDAL = null;
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + ApplicationConstants.TABLE_NAME +
                " where " + WIFI_NODE_NAME + " = " + nodeId, null);
       try {
            cursor.moveToNext();
            wifiAccessPointDAL = cursorAndDALConverter.serialize(cursor);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        cursor.close();
        database.close();
        return wifiAccessPointDAL;
    }
}
