# NetView
自定义蜘蛛网图控件，支持多种属性
>先看效果图
>
![](https://github.com/xsfelvis/NetView/blob/master/test.jpg)

#Usage
>支持属性：
- netColor 网的颜色
- overlayColor 生成覆盖区域的颜色
- overlayAlpha 覆盖区域的透明度
- textColor 文本的字体颜色
- tagsize 文本的大小

详细属性可以参阅[详细属性](https://github.com/xsfelvis/NetView/blob/master/app/src/main/res/values/attrs.xml)

>支持方法

一个是tag可以随意换，一个是绘制区域的占比
```java
    /**
     * 设置网状线角标
     *
     * @param titles
     */
    public void setTitles(String[] titles) {
        this.titles = titles;
    }
```

```java
    /**
     * 设置绘制区域的占比
     *
     * @param data
     */
    public void setPercent(double[] data) {
        this.data = data;
    }
```
>支持wrap_content
重写了onMeasure方法，支持具体值和wrap_content

#Example
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:NetView="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    tools:context="com.xsf.netview.MainActivity">

    <NetView.netView
        android:id="@+id/netView"
        NetView:netColor="@color/colorPrimaryDark"
        NetView:overlayColor="@color/Yellow"
        NetView:textColor="@color/sienna"
        NetView:tagsize="20"
        android:layout_width="300dp"
        android:layout_height="match_parent"/>
</LinearLayout>
```

>在Activity中的使用

```java
 private netView netView;
    private String[] titles = {"android", "javascript", "java", "python", "c++", "ios"};
    private double[] percent = {1, 0.4, 0.6, 0.5, 0.8, 0.3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        netView = (NetView.netView) findViewById(R.id.netView);
        netView.setTitles(titles);
        netView.setPercent(percent);
    }

```
