package com.jaylon.ray.clockview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;
import java.util.Locale;


/**
 * Created by Jaylon on 16/9/12.
 * Info: a simple clock
 */

public class ClockView extends View {

    private Paint paint = new Paint();
    private int curHour;
    private int curMinute;
    private int curSecond;

    public ClockView(Context context) {
        super(context);
        init();
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(System.currentTimeMillis());
        curHour = calendar.get(Calendar.HOUR_OF_DAY);
        curMinute = calendar.get(Calendar.MINUTE);
        curSecond = calendar.get(Calendar.SECOND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int x = width / 2;
        int y = height / 2;
        int radius = Math.min(width / 2, height / 2) / 4 * 3;
        canvas.translate(x, y);
        paint.setStrokeWidth(5);
        paint.setTextSize(30);
        paint.setAntiAlias(true);
        paint.setColor(Color.CYAN);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(0, 0, radius, paint);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        int hour = 12;
        int minute = 60;
        int round = 360;
        paint.setStrokeWidth(3);
        paint.setColor(Color.MAGENTA);
        canvas.save();
        for (int i = 0; i < minute; i++) {
            canvas.drawLine(0, -radius, 0, -radius - 8, paint);
            canvas.rotate(round / minute);
        }
        canvas.restore();
        canvas.save();
        paint.setStrokeWidth(5);
        for (int i = 0; i < hour; i++) {
            canvas.drawLine(0, -radius + 12, 0, -radius - 12, paint);
            canvas.rotate(round / hour);
        }
        canvas.restore();
        canvas.save();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        String text;
        for (int i = 0; i < hour; i++) {
            text = i == 0 ? "12" : i + "";
            canvas.drawText(text, -2, -radius - 15, paint);
            canvas.rotate(round / hour);
        }
        canvas.restore();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(40);

        canvas.save();
        canvas.rotate(curHour * 360 / 12 + curMinute * 360 / 12 / 60);
        canvas.drawLine(0, 0, 0, -radius / 2, paint);
        canvas.restore();

        paint.setStrokeWidth(20);
        paint.setColor(Color.RED);
        canvas.save();
        canvas.rotate(curMinute * 6);
        canvas.drawLine(0, 0, 0, -radius / 4f * 3, paint);
        canvas.restore();

        paint.setStrokeWidth(15);
        paint.setColor(Color.GREEN);
        canvas.save();
        canvas.rotate(curSecond * 6);
        canvas.drawLine(0, 0, 0, -radius / 8f * 7, paint);
        canvas.restore();
    }

    public void update(long mills) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(mills);
        curHour = calendar.get(Calendar.HOUR_OF_DAY);
        curMinute = calendar.get(Calendar.MINUTE);
        curSecond = calendar.get(Calendar.SECOND);
        invalidate();
    }

}
