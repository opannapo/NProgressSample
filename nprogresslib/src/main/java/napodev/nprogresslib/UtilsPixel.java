package napodev.nprogresslib;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by opannapo on 11/5/17.
 */

public class UtilsPixel {
    public static float px2dp(Resources resources, float px) {
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp) {
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }
}
