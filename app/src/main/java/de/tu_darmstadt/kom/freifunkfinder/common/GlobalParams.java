/* GlobalParams - A common class having all globally accessible variables.
 * Copyright (C) 2016  Puneet Arora
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
 * puneet.arora@stud.tu-darmstadt.de, Technical University Darmstadt
 *
 */

package de.tu_darmstadt.kom.freifunkfinder.common;

public class GlobalParams {

    // flag to check if nodes has already been persisted in the database
    private static boolean isWifiNodesPersisted = false;

    // best location provider
    private static String bestLocationProvider;

    // search range in metres
    private static int searchRange = 1000;

    // no. of nodes to display
    private static int nodesCount = 10;

    // old time stamp reading as saved in shared preferences
    private static long oldTimeStamp;

    /**
     * Gets the isWifiNodesPersisted flag.
     *
     * @return true if nodes has already been persisted in the database, else false.
     */
    public static boolean isWifiNodesPersisted() {
        return isWifiNodesPersisted;
    }

    /**
     * Sets the isWifiNodesPersisted flag.
     *
     * @param isWifiNodesPersisted the isWifiNodesPersisted flag.
     */
    public static void setIsWifiNodesPersisted(boolean isWifiNodesPersisted) {
        GlobalParams.isWifiNodesPersisted = isWifiNodesPersisted;
    }

    /**
     * Gets the bestLocationProvider.
     *
     * @return the bestLocationProvider.
     */
    public static String getBestLocationProvider() {
        return bestLocationProvider;
    }

    /**
     * Sets the bestLocationProvider.
     *
     * @param bestLocationProvider the available bestLocationProvider.
     */
    public static void setBestLocationProvider(String bestLocationProvider) {
        GlobalParams.bestLocationProvider = bestLocationProvider;
    }

    /**
     * Gets the searchRange.
     *
     * @return the searchRange.
     */
    public static int getSearchRange() {
        return searchRange;
    }

    /**
     * Sets the searchRange.
     *
     * @param searchRange the searchRange.
     */
    public static void setSearchRange(int searchRange) {
        GlobalParams.searchRange = searchRange;
    }

    /**
     * Gets the nodesCount.
     *
     * @return the nodesCount.
     */
    public static int getNodesCount() {
        return nodesCount;
    }

    /**
     * Sets the nodesCount.
     *
     * @param nodesCount the nodesCount.
     */
    public static void setNodesCount(int nodesCount) {
        GlobalParams.nodesCount = nodesCount;
    }

    /**
     * Gets the persisted oldTimeStamp.
     *
     * @return the oldTimeStamp.
     */
    public static long getOldTimeStamp() {
        return oldTimeStamp;
    }

    /**
     * Sets the oldTimeStamp.
     *
     * @param oldTimeStamp the oldTimeStamp.
     */
    public static void setOldTimeStamp(long oldTimeStamp) {
        GlobalParams.oldTimeStamp = oldTimeStamp;
    }
}
