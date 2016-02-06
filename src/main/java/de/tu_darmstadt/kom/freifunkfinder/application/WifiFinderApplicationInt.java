package de.tu_darmstadt.kom.freifunkfinder.application;

import java.util.List;

import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;

/*
WifiFinderApplicationInt - An Interface of FreifunkFinder application for all internal/external entities.
Copyright (C) 2016  Author: Govind Singh

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

govind.singh@stud.tu-darmstadt.de, TU Darmstadt, Germany
*/

public interface WifiFinderApplicationInt {

    /*
    fetch all the nodes from the db which are
    close to the user depending on the user
    configured parameters.
     */
    List<WifiAccessPointDTO> getRelevantWifiNodes();

    /*
     * persist all the wi-fi nodes into db after reading them
     * from freifunk server
    */
    void persistWifiNode();

    /*
    fetch all the nodes from the db
     */
    List<WifiAccessPointDTO> getAllWifiNodes();
}
