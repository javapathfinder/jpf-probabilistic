/*
 * Copyright (C) 2020  Franck van Breugel
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
 * You can find a copy of the GNU General Public License at
 * <http://www.gnu.org/licenses/>.
 */

package java.lang;

/**
 * Models the method log.
 *
 * @author Franck van Breugel
 */
public class StrictMath {

    /**
     * Returns the natural logarithm (base e) of a double value. Special cases:
     * <ul>
     * <li>If the argument is NaN or less than zero, then the result is NaN.</li>
     * <li>If the argument is positive infinity, then the result is positive infinity.</li>
     * <li>If the argument is positive zero or negative zero, then the result is negative infinity.</li>
     * </ul>
     *
     * @param a a value
     * @return the value ln a, the natural logarithm of a
     */
    public static double log(double a) {
        return Math.log(a);
    }
}