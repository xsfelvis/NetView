package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author xushangfei
 * @time Created at 2016/3/12.
 * @email xsf_uestc_ncl@163.com
 */
public class NetView extends View {
    private int count = 4;
    private float angle = (float) (Math.PI * 2 / count);
    private float radius;  //外接圆半径
    private int centerX;
    private int centerY;
    private String[] titles = {"A", "B", "C", "D"};
    private double[] data = {1, 0.30, 0.6, 0.5};


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
        count = Math.min(data.length, titles.length);

        netColor = netViewAttr.getNetColor();
        overlayColor = netViewAttr.getOverlayColor();
        textColor = netViewAttr.getTextColor();
        overlayalpha = netViewAttr.getOverlayAlpha();
        tagsize = netViewAttr.getTagsize();

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
        super.onDraw(canvas);
        drawNet(canvas);
        drawText(canvas);
        drawRegion(canvas);
    }

    private void drawNet(Canvas canvas) {
        //绘制六边形
        Path path = new Path();
        float r = radius / (count - 1); //每次递增的高度
        for (int i = 0; i < count; i++) {
            float currentRadius = r * i;
            path.reset();
            for (int j = 0; j < count; j++) {
                if (j == 0) {
                    path.moveTo(centerX + currentRadius, centerY);
                } else {
                    float x = (float) (centerX + currentRadius * Math.cos(angle * j));
                    float y = (float) (centerY + currentRadius * Math.sin(angle * j));
                    path.lineTo(x, y);
                }
            }

            path.close();
            canvas.drawPath(path, netPaint);
        }


        //绘制轴线
        for (int i = 1; i < count + 1; i++) {
            path.reset();
            path.moveTo(centerX, centerY);
            float x = (float) (centerX + radius * Math.cos(angle * i));
            float y = (float) (centerY + radius * Math.sin(angle * i));
            path.lineTo(x, y);
            canvas.drawPath(path, netPaint);
        }

    }

    private void drawText(Canvas canvas) {
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent; //文字的高度

        //修正标题

        for (int i = 0; i < count; i++) {
            float x = (float) (centerX + (radius + fontHeight / 2) * Math.cos(angle * i));
            float y = (float) (centerY + (radius + fontHeight / 2) * Math.sin(angle * i));
            float dis = textPaint.measureText(titles[i]);//获取文本长度

            if (angle * i > 0 && angle * i < Math.PI) {
                canvas.drawText(titles[i], x-dis/2, y+fontHeight , textPaint);
            } else if (angle * i >= Math.PI && angle * i < 3 * Math.PI / 2) {
                canvas.drawText(titles[i], x - dis, y, textPaint);
            } else {
                canvas.drawText(titles[i], x, y, textPaint);
            }

        }

    }


    private void drawRegion(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < count; i++) {
            float x = (float) (centerX + radius * Math.cos(angle * i) * data[i]);
            float y = (float) (centerY + radius * Math.sin(angle * i) * data[i]);
            if (i == 0) {
                path.moveTo(x, centerY);
            } else {
                path.lineTo(x, y);
            }
            canvas.drawCircle(x, y, 5, valuePaint);
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

    }

    /**
     * 设置绘制区域的占比
     *
     * @param data
     */
    public void setPercent(double[] data) {
        this.data = data;

    }


}
