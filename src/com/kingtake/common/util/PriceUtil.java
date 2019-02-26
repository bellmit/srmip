package com.kingtake.common.util;

public class PriceUtil {

	public static String getNum(String num) {
		String prefix = num.substring(0, num.length() - 2);
		String suffix = num.substring(num.length() - 2, num.length());
		int tmpInt = Integer.valueOf(suffix);
		tmpInt++;
		String numSuffix = String.valueOf(tmpInt);
		if (numSuffix.length() == 1) {
			numSuffix = "0" + numSuffix;
		}
		String numResult = prefix + numSuffix;
		return numResult;
	}

}
