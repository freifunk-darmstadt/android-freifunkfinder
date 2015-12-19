package data_access;

import java.util.List;

import common.WifiAccessPointDAL;

/**
 * Created by govind on 12/10/2015.
 */
public class SqliteManager implements DatabaseManagerInt {

    private SQLiteHelper sqLiteHelper;

    public SqliteManager(){
        //sqLiteHelper = new SQLiteHelper();
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
