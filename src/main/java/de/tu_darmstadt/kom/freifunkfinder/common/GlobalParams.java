package de.tu_darmstadt.kom.freifunkfinder.common;

/**
 * Created by govind on 1/6/2016.
 */
public class GlobalParams {

    private static boolean isWifiNodesPersisted = false;

    private static String bestLocationProvider = "network";

    public static boolean isWifiNodesPersisted() {
        return isWifiNodesPersisted;
    }

    public static void setIsWifiNodesPersisted(boolean isWifiNodesPersisted) {
        GlobalParams.isWifiNodesPersisted = isWifiNodesPersisted;
    }

    public static String getBestLocationProvider() {
        return bestLocationProvider;
    }

    public static void setBestLocationProvider(String bestLocationProvider) {
        GlobalParams.bestLocationProvider = bestLocationProvider;
    }
}
