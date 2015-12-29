package application;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import common.GPSLocation;
import common.WifiAccessPointDAL;
import common.WifiAccessPointDTO;
import common.WifiAccessPointVO;
import common.converter.DTOAndDALConverter;
import common.converter.DTOAndVOConverter;
import data_access.DatabaseManagerInt;
import data_access.SqliteManager;

/**
 * Created by govind on 12/10/2015.
 */
public class WifiFinderApplication {

    private WifiAccessPointReader wifiReader;

    private GPSManager gpsManager;

    private GPSLocation gpsLocation;

    private WifiAccessPointCalculator wifiNodesCalculator;

    private WifiAccessPointDTO wifiAccessPointDTO;

    private DatabaseManagerInt databaseManager;

    private DTOAndDALConverter dtoAndDALConverter;

    private DTOAndVOConverter dtoandVOConverter;

    public WifiFinderApplication(Context applicationContext) {
        dtoAndDALConverter = DTOAndDALConverter.geDtoAndDALConverter();
        gpsManager = new GPSManager();
        dtoandVOConverter = DTOAndVOConverter.getDTOAndVOConverter();
        databaseManager = new SqliteManager(applicationContext);
        wifiReader = new WifiAccessPointReader();
    }

    public List<WifiAccessPointVO> getRelevantWifiNodes() {
        List<WifiAccessPointVO> relevantAccessPoints = new ArrayList<WifiAccessPointVO>();
        List<WifiAccessPointDAL> allNodesFromDB = databaseManager.readAll();
        List<WifiAccessPointDTO> allWifiNodes = new ArrayList<WifiAccessPointDTO>();
        for (WifiAccessPointDAL wifiAccessPointDAL : allNodesFromDB) {
            try {
                allWifiNodes.add(dtoAndDALConverter.deSerialize(wifiAccessPointDAL));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        List<WifiAccessPointDTO> relevantWifiNodes = wifiNodesCalculator.calculateRelevantWifiNodes(allWifiNodes);
        for (WifiAccessPointDTO wifiAccessPointDTO : relevantWifiNodes) {
            try {
                relevantAccessPoints.add(dtoandVOConverter.serialize(wifiAccessPointDTO));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return relevantAccessPoints;
    }

    /*
     * persist all the wi-fi nodes into db after reading them
     * from freifunk server */
    public void persistWifiNode() {
        gpsLocation = gpsManager.getGpsLocation();
        List<WifiAccessPointDTO> accessPointDTOs = wifiReader.getAllWifiNodes(gpsLocation);
        for (WifiAccessPointDTO wifiAccessPointDTO : accessPointDTOs) {
            try {
                databaseManager.write(dtoAndDALConverter.serialize(wifiAccessPointDTO));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // for db testing only
    public List<WifiAccessPointVO> getAllWifiNodes() {
        List<WifiAccessPointVO> relevantAccessPoints = new ArrayList<WifiAccessPointVO>();
        List<WifiAccessPointDAL> allNodesFromDB = databaseManager.readAll();
        List<WifiAccessPointDTO> allWifiNodes = new ArrayList<WifiAccessPointDTO>();
        for (WifiAccessPointDAL wifiAccessPointDAL : allNodesFromDB) {
            try {
                allWifiNodes.add(dtoAndDALConverter.deSerialize(wifiAccessPointDAL));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        for (WifiAccessPointDTO wifiAccessPointDTO : allWifiNodes) {
            try {
                relevantAccessPoints.add(dtoandVOConverter.serialize(wifiAccessPointDTO));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return relevantAccessPoints;
    }

    public WifiAccessPointVO getAccessPointDetails(String nodeId) {
        WifiAccessPointVO wifiAccessPointVO = null;
        WifiAccessPointDAL accessPointDAL = databaseManager.read(nodeId);
        try {
            WifiAccessPointDTO wifiNode = dtoAndDALConverter.deSerialize(accessPointDAL);
            wifiAccessPointVO = dtoandVOConverter.serialize(wifiNode);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wifiAccessPointVO;
    }


}
