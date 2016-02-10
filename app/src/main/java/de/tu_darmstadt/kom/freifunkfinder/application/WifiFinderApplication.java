/* WifiFinderApplication - An implementation of WifiFinderApplicationInt that performs its functionalities.
 * Copyright (C) 2016  Govind Singh
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * govind.singh@stud.tu-darmstadt.de, Technical University Darmstadt
 *
 */

package de.tu_darmstadt.kom.freifunkfinder.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import de.tu_darmstadt.kom.freifunkfinder.common.FreifunkFinderAppConstants;
import de.tu_darmstadt.kom.freifunkfinder.common.GlobalParams;
import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;
import de.tu_darmstadt.kom.freifunkfinder.data_access.DatabaseManagerInt;
import de.tu_darmstadt.kom.freifunkfinder.data_access.SqliteManager;
import de.tu_darmstadt.kom.freifunkfinder.common.MobileLocation;

public class WifiFinderApplication implements WifiFinderApplicationInt {

    private static final String DEBUG_TAG = "WifiFinderApp : ";

    private static WifiFinderApplication wifiFinderApplication;

    private WifiAccessPointReader wifiReader;

    private WifiAccessPointCalculator wifiNodesCalculator;

    private WifiAccessPointDTO wifiAccessPointDTO;

    private DatabaseManagerInt databaseManager;

    private Context applicationContext;

    /**
     * Private constructor.
     *
     * @param applicationContext the app context as set by the MainActivity.
     */
    private WifiFinderApplication(Context applicationContext) {
        this.applicationContext = applicationContext;
        wifiNodesCalculator = new WifiAccessPointCalculator();
        databaseManager = new SqliteManager(applicationContext);
        wifiReader = new WifiAccessPointReader();
    }

    /**
     * Returns a singleton object of this class.
     *
     * @param applicationContext the app context as set by the MainActivity.
     * @return a singleton Object.
     */
    public static WifiFinderApplication getWifiFinderApplication(Context applicationContext) {
        if (wifiFinderApplication == null) {
            wifiFinderApplication = new WifiFinderApplication(applicationContext);
        }
        return wifiFinderApplication;
    }

    /**
     * Checks if the duration threshold of 30 minutes has already reached.
     *
     * @param oldTimestamp the old timestamp as set in the shared preference.
     * @return true if the duration threshold of 30 minutes has reached, else false.
     */
    private boolean isDurationThresholdReached(long oldTimestamp) {
        boolean isDurationReached = false;
        long currentTimestamp = System.currentTimeMillis();
        Log.d(DEBUG_TAG, "Current timestamp = " + currentTimestamp);
        long timeDiff = currentTimestamp - oldTimestamp;
        long minutesOver = (timeDiff) / (1000 * 60);
        Log.d(DEBUG_TAG, "Total duration over = " + minutesOver);
        if (minutesOver > 30) {
            isDurationReached = true;
            SharedPreferences settings = applicationContext.getSharedPreferences(FreifunkFinderAppConstants.PREFS_TIMESTAMP, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putLong(FreifunkFinderAppConstants.PREFERENC_KEY, currentTimestamp);
            Log.d(DEBUG_TAG, "Setting new timestamp = " + currentTimestamp);
            editor.commit();
        }
        return isDurationReached;
    }

    /**
     * Returns a list of Wi-Fi nodes which are relevant to the user, to the UI layer.
     *
     * @return a list of Wi-Fi nodes relevant to the user.
     */
    @Override
    public List<WifiAccessPointDTO> getRelevantWifiNodes() {
        List<WifiAccessPointDTO> relevantWifiNodes = new ArrayList<WifiAccessPointDTO>();
        try {
            List<WifiAccessPointDTO> allWifiNodes = getAllWifiNodes();
            if (allWifiNodes.size() > 0) {
                relevantWifiNodes = wifiNodesCalculator.calculateRelevantWifiNodes(MobileLocation.getLocation(), allWifiNodes);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return relevantWifiNodes;
    }

    /**
     * Persists all Wi-Fi nodes to a database.
     */
    @Override
    public void persistWifiNode() {
        if (databaseManager.isDatabaseEmpty() || isDurationThresholdReached(GlobalParams.getOldTimeStamp())) {
            List<WifiAccessPointDTO> accessPointDTOs = wifiReader.getAllWifiNodes(MobileLocation.getLocation());
            if (accessPointDTOs.size() > 0) {
                for (WifiAccessPointDTO wifiAccessPointDTO : accessPointDTOs) {
                    try {
                        databaseManager.write(wifiAccessPointDTO);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } else {
            Log.d(DEBUG_TAG, "Setting IsWifiNodePersisted flag to True");
            GlobalParams.setIsWifiNodesPersisted(true);
            Log.d(DEBUG_TAG, "The duration threshold has not reached.");
        }
    }

    /**
     * Gets all Wi-Fi nodes persisted from the database.
     *
     * @return a list of all available Wi-Fi nodes.
     */
    @Override
    public List<WifiAccessPointDTO> getAllWifiNodes() {
        return databaseManager.readAll();
    }

}
