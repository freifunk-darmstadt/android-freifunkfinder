/* DatabaseManagerInt - An interface for the database used by the application.
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

package de.tu_darmstadt.kom.freifunkfinder.data_access;

import java.util.List;

import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;

public interface DatabaseManagerInt {

    /**
     * Checks and returns true if DB is empty, else false.
     *
     * @return true if DB is empty, else false.
     */
    boolean isDatabaseEmpty();

    /**
     * Writes a Wi-Fi node to the underlying DB.
     *
     * @param wifiAccessPointDTO the Wi-Fi node to be written to underlying DB.
     */
    void write(WifiAccessPointDTO wifiAccessPointDTO);

    /**
     * Reads a Wi-Fi node from the underlying DB.
     *
     * @param nodeId the key to be searched.
     * @return the corresponding  Wi-Fi node.
     */
    WifiAccessPointDTO read(String nodeId);

    /**
     * Reads all persisted Wi-Fi nodes in the underlying DB.
     *
     * @return a list of all persisted Wi-Fi nodes.
     */
    List<WifiAccessPointDTO> readAll();
}
