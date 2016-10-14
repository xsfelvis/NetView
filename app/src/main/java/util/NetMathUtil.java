package util;

/**
 * Created by 邵一哲_Native on 2016/10/14.
 */

public class NetMathUtil {
    public static float getXFromRAlpha(float r, float alpha) {
        return (float) (r * Math.cos(alpha));
    }
    public static float getYFromRAlpha(float r, float alpha) {
        return (float) (r * Math.sin(alpha));
    }

}
