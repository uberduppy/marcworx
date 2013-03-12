/* MarcWorx MARC Library - Utilities for manipulation of MARC records
    Copyright (C) 2013  Todd Walker, Talwood Solutions

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
*/
package org.talwood.marcworx.marc.constants;

public class MarcReaderConstants {
    public static final char END_OF_RECORD = (char)0x1d;
    public static final char END_OF_TAG = (char)0x1e;
    public static final int MINIMUM_RECORD = 50;

    public static final int INCREMENT_COUNT = 1000;
    public static final int INCREMENT_SIZE = 10000;
    public final static int BUFFER_SIZE = 100000;
}
