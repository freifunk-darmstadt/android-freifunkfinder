package de.tu_darmstadt.kom.freifunkfinder.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.List;

import de.tu_darmstadt.kom.freifunkfinder.common.ApplicationConstants;
import de.tu_darmstadt.kom.freifunkfinder.common.GlobalParams;
import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;
import de.tu_darmstadt.kom.freifunkfinder.data_access.DatabaseManagerInt;
import de.tu_darmstadt.kom.freifunkfinder.data_access.SqliteManager;
import de.tu_darmstadt.kom.freifunkfinder.common.MobileLocation;


/**
 * Created by govind,sooraj,puneet on 12/10/2015.
 */
public class WifiFinderApplication implements WifiFinderApplicationInt {

    private static WifiFinderApplication wifiFinderApplication;

    private WifiAccessPointReader wifiReader;

    private WifiAccessPointCalculator wifiNodesCalculator;

    private WifiAccessPointDTO wifiAccessPointDTO;

    private DatabaseManagerInt databaseManager;

    private Context applicationContext;

    private WifiFinderApplication(Context applicationContext) {
        this.applicationContext = applicationContext;
        wifiNodesCalculator = new WifiAccessPointCalculator();
        databaseManager = new SqliteManager(applicationContext);
        wifiReader = new WifiAccessPointReader();
    }

    public static WifiFinderApplication getWifiFinderApplication(Context applicationContext){
        if(wifiFinderApplication == null){
            wifiFinderApplication = new WifiFinderApplication(applicationContext);
        }
        return wifiFinderApplication;
    }

    /* check if duration threshold to make http
     * request has been reached
      * */
    private boolean isDurationThresholdReached(long oldTimestamp) {
        boolean isDurationReadched = false;
        long currentTimestamp = System.currentTimeMillis();
        System.out.println(currentTimestamp + " milliseconds using system");
        long timeDiff = currentTimestamp - oldTimestamp;
        long minutesOver = (timeDiff) / (1000 * 60);
        System.out.println("minutes over = " + minutesOver);
        if (minutesOver > 30){
            isDurationReadched = true;
            SharedPreferences settings = applicationContext.getSharedPreferences(ApplicationConstants.PREFS_TIMESTAMP, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putLong(ApplicationConstants.PREFERENC_KEY, currentTimestamp);
            System.out.println(" setting old timestamp value = " +currentTimestamp);
            editor.commit();
        }
        return isDurationReadched;
    }

    @Override
    public List<WifiAccessPointDTO> getRelevantWifiNodes() {
        List<WifiAccessPointDTO> relevantWifiNodes = null;
        try {
            List<WifiAccessPointDTO> allWifiNodes = getAllWifiNodes();
            relevantWifiNodes = wifiNodesCalculator.calculateRelevantWifiNodes(MobileLocation.getLocation(), allWifiNodes);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return relevantWifiNodes;
    }

    @Override
    public void persistWifiNode() {
        if(databaseManager.isDatabaseEmpty()) {
            List<WifiAccessPointDTO> accessPointDTOs = wifiReader.getAllWifiNodes(MobileLocation.getLocation());
            for (WifiAccessPointDTO wifiAccessPointDTO : accessPointDTOs) {
                try {
                    databaseManager.write(wifiAccessPointDTO);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }else{
            GlobalParams.setIsWifiNodesPersisted(true);
            Log.d("DATABASE", "Database is already full.");
        }
    }

    @Override
    public List<WifiAccessPointDTO> getAllWifiNodes() {
        return databaseManager.readAll();
    }

}
