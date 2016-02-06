package de.tu_darmstadt.kom.freifunkfinder.common.converter;

/*
ConverterInt - An interface to perform a Type conversion to an another type.
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

public interface ConverterInt<T, D> {

    // convert T to D
    D serialize(T entity) throws Exception;

    // convert D to T
    T deSerialize(D entity) throws Exception;
}
