package de.tu_darmstadt.kom.freifunkfinder.data_access;

import android.content.Context;

import java.util.List;

import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDAL;

/**
 * Created by govind on 12/10/2015.
 */
public class SqliteManager implements DatabaseManagerInt {

    private SQLiteHelper sqLiteHelper;

    public SqliteManager(Context applicationContext){
        sqLiteHelper = new SQLiteHelper(applicationContext);
    }

    @Override
    public void write(WifiAccessPointDAL wifiAccessPointDAL) {
        sqLiteHelper.insertRecord(wifiAccessPointDAL);
    }

    @Override
    public WifiAccessPointDAL read(String nodeId) {
        return sqLiteHelper.getRecord(nodeId);
    }

    @Override
    public List<WifiAccessPointDAL> readAll(){
       return sqLiteHelper.getAllRecords();
    }
}
