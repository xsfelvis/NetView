package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.xsf.netview.R;

/**
 * @author xushangfei
 * @time Created at 2016/3/12.
 * @email xsf_uestc_ncl@163.com
 */
public class NetViewAttrs {
    private int netColor;
    private int overlayColor;
    private int textColor;
    private int overlayAlpha;
    private int tagsize;
    private int titleCount;

    public NetViewAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NetView, defStyleAttr, 0);
        netColor = ta.getColor(R.styleable.NetView_netColor, context.getResources().getColor(R.color.slategray));
        overlayAlpha = ta.getInteger(R.styleable.NetView_overlayAlpha, 130);
        tagsize = ta.getDimensionPixelSize(R.styleable.NetView_tagsize, 20);

        overlayColor = ta.getColor(R.styleable.NetView_overlayColor, context.getResources().getColor(R.color.colorPrimaryDark));
        textColor = ta.getColor(R.styleable.NetView_textColor, context.getResources().getColor(R.color.skyblue));
        titleCount = ta.getInteger(R.styleable.NetView_title_count,6);
        ta.recycle();
    }

    public int getNetColor() {
        return netColor;
    }

    public int getOverlayAlpha() {
        return overlayAlpha;
    }

    public int getTextColor() {

        return textColor;
    }

    public int getTagsize() {
        return tagsize;
    }

    public int getOverlayColor() {
        return overlayColor;
    }

    public int getTitleCount() { return titleCount; }
}
