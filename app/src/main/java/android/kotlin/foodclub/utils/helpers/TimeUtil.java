package android.kotlin.foodclub.utils.helpers;

import java.util.concurrent.TimeUnit;

public class TimeUtil {
    public static String getHoursAgoFromNow(long timestamp) {
        long currentTime = System.currentTimeMillis();
        long hoursDiff = TimeUnit.HOURS.convert(currentTime - timestamp*1000, TimeUnit.MILLISECONDS);

        return hoursDiff + " hours";
    }
}
