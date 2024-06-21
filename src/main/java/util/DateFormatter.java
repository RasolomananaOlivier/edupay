package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {
    public static String format(Date date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
        return dateFormatter.format(date);
    }
}
