package com.eldoraludo.ppafadministration.tools;

public class ConvertionAscii {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String line = "depôt";
		for (int index = 0; index < line.length(); index++) {

			// Convert the integer to a hexadecimal code.
			String hexCode = Integer.toHexString(line.codePointAt(index))
					.toUpperCase();

			// but the it must be a four number value.
			String hexCodeWithAllLeadingZeros = "0000" + hexCode;
			String hexCodeWithLeadingZeros = hexCodeWithAllLeadingZeros
					.substring(hexCodeWithAllLeadingZeros.length() - 4);

			System.out.println("\\u" + hexCodeWithLeadingZeros);
		}

	}
}
