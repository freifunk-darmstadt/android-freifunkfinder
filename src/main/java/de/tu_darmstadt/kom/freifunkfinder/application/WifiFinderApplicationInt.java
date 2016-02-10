/* WifiFinderApplicationInt - An Interface of FreifunkFinder application for all internal/external entities.
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

import java.util.List;

import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;

public interface WifiFinderApplicationInt {

    /**
     * Returns a list of Wi-Fi nodes which are relevant to the user, to the UI layer.
     *
     * @return a list of Wi-Fi nodes relevant to the user.
     */
    List<WifiAccessPointDTO> getRelevantWifiNodes();

    /**
     * Persists all Wi-Fi nodes to a storage (permanent or temporary).
     */
    void persistWifiNode();

    /**
     * Gets all Wi-Fi nodes persisted from the storage.
     *
     * @return a list of all available Wi-Fi nodes.
     */
    List<WifiAccessPointDTO> getAllWifiNodes();
}
