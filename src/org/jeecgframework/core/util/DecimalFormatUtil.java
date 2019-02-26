package org.jeecgframework.core.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DecimalFormatUtil {
    public static String format(BigDecimal number) {
        NumberFormat formatter = new DecimalFormat(",###,###.00");
        return formatter.format(number);
    }
}
