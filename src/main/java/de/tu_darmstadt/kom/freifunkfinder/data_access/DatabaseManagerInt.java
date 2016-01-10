package de.tu_darmstadt.kom.freifunkfinder.data_access;

import java.util.List;

import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;

/**
 * Created by govind on 12/17/2015.
 */
public interface DatabaseManagerInt {

    // checks if the database is empty
    boolean isDatabaseEmpty();

    // persist the DTO object into the DB
    void write(WifiAccessPointDTO wifiAccessPointDTO);

    // retrieve the DTO object from the DB using the id
    WifiAccessPointDTO read(String nodeId);

    // retrieve all DTO objects
    List<WifiAccessPointDTO> readAll();
}
