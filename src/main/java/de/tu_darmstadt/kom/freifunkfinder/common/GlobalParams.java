package de.tu_darmstadt.kom.freifunkfinder.common;

/**
 * Created by govind on 1/6/2016.
 */
public class GlobalParams {

    private static boolean isWifiNodesPersisted = false;

    private static String bestLocationProvider = "network";

    // search range in metres
    private static int searchRange = 10000;

    // no. of nodes to display
    private static int nodesCount = 5;

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

    public static int getSearchRange() {
        return searchRange;
    }

    public static void setSearchRange(int searchRange) {
        GlobalParams.searchRange = searchRange;
    }

    public static int getNodesCount() {
        return nodesCount;
    }

    public static void setNodesCount(int nodesCount) {
        GlobalParams.nodesCount = nodesCount;
    }
}
