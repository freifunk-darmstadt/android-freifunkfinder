package de.tu_darmstadt.kom.freifunkfinder.data_access;

import java.util.List;

import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;

/*
DatabaseManagerInt - An interface for the database used by the application.
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

public interface DatabaseManagerInt {

    // checks if the database is empty
    boolean isDatabaseEmpty();

    // persist the DTO object into the DB
    void write(WifiAccessPointDTO wifiAccessPointDTO);

    // retrieve the DTO object from the DB using the id
    WifiAccessPointDTO read(String nodeId);

    // retrieve all DTO objects
    List<WifiAccessPointDTO> readAll();
}
