package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import util.NetMathUtil;

/**
 * @author xushangfei
 * @time Created at 2016/3/12.
 * @email xsf_uestc_ncl@163.com
 */
public class NetView extends View {
    private int count;
    private float angle;
    private float radius;  //外接圆半径
    private int centerX;
    private int centerY;
    private String[] titles;
    private double[] data;


    private int netColor;

    private int overlayColor;
    private int textColor;
    private int overlayalpha;
    private int tagsize;

    private NetViewAttrs netViewAttr;


    private Paint netPaint;
    private Paint valuePaint;
    private Paint textPaint;


    public NetView(Context context) {
        this(context, null);
        init(context);
    }

    public NetView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public NetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        netViewAttr = new NetViewAttrs(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        netColor = netViewAttr.getNetColor();
        overlayColor = netViewAttr.getOverlayColor();
        textColor = netViewAttr.getTextColor();
        overlayalpha = netViewAttr.getOverlayAlpha();
        tagsize = netViewAttr.getTagsize();
        count = netViewAttr.getTitleCount();
        angle = (float) (Math.PI * 2 / count);

        netPaint = new Paint();
        netPaint.setAntiAlias(true);
        netPaint.setColor(netColor);
        netPaint.setStyle(Paint.Style.STROKE);

        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setColor(overlayColor);
        valuePaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(tagsize);
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = Math.min(w, h) / 2 * 0.7f;
        centerX = w / 2;
        centerY = h / 2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize =MeasureSpec.getSize(widthMeasureSpec);
        int heighSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heighSpecSize =MeasureSpec.getSize(heightMeasureSpec);
        if(widthSpecMode==MeasureSpec.AT_MOST&&heighSpecMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(200,200);
        }else if(widthSpecMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(200,heighSpecSize);
        }else if(heighSpecMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize,200);

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawNet(canvas);
        drawText(canvas);
        drawRegion(canvas);
    }

    private void drawNet(Canvas canvas) {
        //绘制六边形
        Path path = new Path();
//        float r = radius / (count - 1); //每次递增的高度
        int density = 4;
        float deltaR = radius / density;
        for (int i = 1;i <= density;i++) {
            float currentR = deltaR * i;
            float currentAngle;
            path.reset();
            if (count % 2 == 0) {
                currentAngle = (float) (1.5 * Math.PI + Math.PI / count);
            } else {
                currentAngle = (float) (1.5 * Math.PI);
            }
            path.moveTo(centerX + NetMathUtil.getXFromRAlpha(currentR, currentAngle),
                    centerY + NetMathUtil.getYFromRAlpha(currentR,currentAngle));
            for (int j = 1; j < count; j++) {
                currentAngle += angle;
                path.lineTo(centerX + NetMathUtil.getXFromRAlpha(currentR, currentAngle),
                        centerY + NetMathUtil.getYFromRAlpha(currentR,currentAngle));
            }
            path.close();
            canvas.drawPath(path,netPaint);
        }


        //绘制轴线
        float currentAngle;
        if (count % 2 == 0) {
            currentAngle = (float) (1.5 * Math.PI + Math.PI / count);
        } else {
            currentAngle = (float) (1.5 * Math.PI);
        }
        for (int i = 0; i < count; i++) {
            path.reset();
            path.moveTo(centerX, centerY);
            float x = (float) (centerX + NetMathUtil.getXFromRAlpha(radius,currentAngle));
            float y = (float) (centerY + NetMathUtil.getYFromRAlpha(radius,currentAngle));
            path.lineTo(x, y);
            canvas.drawPath(path, netPaint);
            currentAngle += angle;
        }

    }

    private void drawText(Canvas canvas) {
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent; //文字的高度

        //修正标题
        float currentAngle;
        if (count % 2 == 0) {
            currentAngle = (float) (1.5 * Math.PI + Math.PI / count);
        } else {
            currentAngle = (float) (1.5 * Math.PI);
        }

        for (int i = 0; i < count; i++) {
            currentAngle %= Math.PI * 2;
            float x = (float) (centerX + NetMathUtil.getXFromRAlpha(radius + fontHeight / 2, currentAngle));
            float y = (float) (centerY + NetMathUtil.getYFromRAlpha(radius + fontHeight / 2, currentAngle));
            float dis = textPaint.measureText(titles[i]);//获取文本长度

            if (currentAngle > 0 && currentAngle < Math.PI) {
                canvas.drawText(titles[i], x-dis/2, y+fontHeight , textPaint);
            } else if (currentAngle >= Math.PI && currentAngle < 3 * Math.PI / 2) {
                canvas.drawText(titles[i], x - dis, y, textPaint);
            } else {
                canvas.drawText(titles[i], x, y, textPaint);
            }
            currentAngle += angle;
        }

    }


    private void drawRegion(Canvas canvas) {
        float currentAngle;
        if (count % 2 == 0) {
            currentAngle = (float) (1.5 * Math.PI + Math.PI / count);
        } else {
            currentAngle = (float) (1.5 * Math.PI);
        }
        Path path = new Path();
        for (int i = 0; i < count; i++) {
            float x = centerX + NetMathUtil.getXFromRAlpha((float) (radius * data[i]), currentAngle);
            float y = centerY + NetMathUtil.getYFromRAlpha((float) (radius * data[i]), currentAngle);
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
            canvas.drawCircle(x, y, 5, valuePaint);
            currentAngle += angle;
        }
        path.close();
        valuePaint.setAlpha(overlayalpha);
        canvas.drawPath(path, valuePaint);


    }


    /**
     * 设置网状线角标
     *
     * @param titles
     */
    public void setTitles(String[] titles) {
        this.titles = titles;
        int len = titles.length;
        if (len > count) {
            count = len;
            angle = (float) (Math.PI * 2 / count);
        }
    }

    /**
     * 设置绘制区域的占比
     *
     * @param data
     */
    public void setPercent(double[] data) {
        this.data = data;
        int len =data.length;
        if (len > count) {
            count = len;
            angle = (float) (Math.PI * 2 / count);
        }

    }


}
