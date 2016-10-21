package com.example.maq.sdr.presentation.friends;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.maq.sdr.R;

public class CircleImageView extends ImageView {

    private int backgroundColor;

    public CircleImageView(Context context) {
        super(context);
        setBackgroundColor(ContextCompat.getColor(context, R.color.cardview_bg));
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(ContextCompat.getColor(context, R.color.cardview_bg));
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(ContextCompat.getColor(context, R.color.cardview_bg));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final float halfWidth = canvas.getWidth()/2;
        final float halfHeight = canvas.getHeight()/2;
        final float radius = Math.min(halfWidth, halfHeight);
        final Path path = new Path();
        final Paint pathPaint = new Paint();
        final Paint border = new Paint();

        pathPaint.setColor(backgroundColor);
        pathPaint.setStyle(Paint.Style.FILL);
        path.addCircle(halfWidth, halfHeight, radius-1, Path.Direction.CCW);
        path.toggleInverseFillType();

        border.setColor(Color.GRAY);
        border.setStrokeWidth(2);
        border.setAntiAlias(true);
        border.setStyle(Paint.Style.STROKE);

        super.onDraw(canvas);
        canvas.drawPath(path, pathPaint);
        canvas.drawCircle(halfWidth, halfHeight, radius-1, border);
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}

