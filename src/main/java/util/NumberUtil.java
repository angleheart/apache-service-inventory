package util;

import java.text.DecimalFormat;

public class NumberUtil {

    public static double currencyRound(double d){
        var format = new DecimalFormat("0.00");
        return Double.parseDouble(format.format(d));
    }


}
