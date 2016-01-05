package de.tu_darmstadt.kom.freifunkfinder.data_access;

import java.util.List;

import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDAL;

/**
 * Created by govind on 12/17/2015.
 */
public interface DatabaseManagerInt {

    // persist the DAL object into the DB
    void write(WifiAccessPointDAL wifiAccessPointDAL);

    // retrieve the DAL object from the DB using the id
    WifiAccessPointDAL read(String nodeId);

    // retrieve all DAL objects
    List<WifiAccessPointDAL> readAll();
}
