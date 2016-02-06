package de.tu_darmstadt.kom.freifunkfinder.common;

/*
GlobalParams - A common class having all globally accessible variables.
Copyright (C) 2016  Author: Puneet Arora

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

puneet.arora@stud.tu-darmstadt.de, TU Darmstadt, Germany
*/

public class GlobalParams {

    private static boolean isWifiNodesPersisted = false;

    private static String bestLocationProvider = "network";

    // search range in metres
    private static int searchRange = 1000;

    // no. of nodes to display
    private static int nodesCount = 10;

    private static long oldTimeStamp;

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

    public static long getOldTimeStamp() {
        return oldTimeStamp;
    }

    public static void setOldTimeStamp(long oldTimeStamp) {
        GlobalParams.oldTimeStamp = oldTimeStamp;
    }
}
