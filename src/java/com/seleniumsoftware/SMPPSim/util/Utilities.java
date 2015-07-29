/****************************************************************************
 * Utilities.java
 *
 * Copyright (C) Selenium Software Ltd 2006
 *
 * This file is part of SMPPSim.
 *
 * SMPPSim is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * SMPPSim is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SMPPSim; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * @author martin@seleniumsoftware.com
 * http://www.woolleynet.com
 * http://www.seleniumsoftware.com
 * 
 ****************************************************************************/

package com.seleniumsoftware.SMPPSim.util;

import com.seleniumsoftware.SMPPSim.exceptions.InvalidHexStringlException;

public class Utilities {

	public static byte[] makeBinaryMessage(String hex)
			throws InvalidHexStringlException {
		int i = 0;
		String hexNoSpaces = Utilities.removeSpaces(hex);
		hexNoSpaces = hexNoSpaces.toUpperCase();
		int l = hexNoSpaces.length();

		if (!Utilities.isEven(l)) {
			throw new InvalidHexStringlException("Invalid hex string");
		}

		if (!Utilities.validHexChars(hexNoSpaces)) {
			throw new InvalidHexStringlException("Invalid hex string");
		}
		l = l / 2;
		byte[] result = new byte[l];
		while (i < l) {
			String byteAsHex = hexNoSpaces.substring((i * 2), ((i * 2) + 2));
			int b = (int) (Integer.parseInt(byteAsHex, 16) & 0x000000FF);
			if (b < 0)
				b = 256 + b;
			result[i] = (byte) b;
			i++;
		}
		return result;

	}

	public static boolean isEven(int number) {
		int half = number / 2;
		if ((half * 2) == number)
			return true;
		else
			return false;
	}

	public static String removeSpaces(String text) {
		int l = text.length();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < l; i++) {
			if (text.charAt(i) != ' ')
				sb.append(text.charAt(i));
		}
		return sb.toString();
	}

	public static boolean validHexChars(String hex) {
		int l = hex.length();
		for (int i = 0; i < l; i++) {
			if (!hexChar(hex.charAt(i)))
				return false;
		}
		return true;

	}

	public static boolean hexChar(char h) {
		switch (h) {
		case '0':
			return true;
		case '1':
			return true;
		case '2':
			return true;
		case '3':
			return true;
		case '4':
			return true;
		case '5':
			return true;
		case '6':
			return true;
		case '7':
			return true;
		case '8':
			return true;
		case '9':
			return true;
		case 'A':
			return true;
		case 'B':
			return true;
		case 'C':
			return true;
		case 'D':
			return true;
		case 'E':
			return true;
		case 'F':
			return true;
		}
		return false;
	}

}