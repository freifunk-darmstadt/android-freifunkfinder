/* ConverterInt - An interface to perform a Type conversion to an another type.
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

package de.tu_darmstadt.kom.freifunkfinder.common.converter;

public interface ConverterInt<T, D> {

    /**
     * Serializes an object of type T into an object of type D.
     *
     * @param entity an object of type T.
     * @return a new serialized object of type D from T.
     * @throws Exception if an error occurs due to type mismatch.
     */
    D serialize(T entity) throws Exception;

    /**
     * De-serializes an object of type D into an object of type T.
     *
     * @param entity an object of type D.
     * @return a new de-serialized object of type T from D.
     * @throws Exception if an error occurs due to type mismatch.
     */
    T deSerialize(D entity) throws Exception;
}
