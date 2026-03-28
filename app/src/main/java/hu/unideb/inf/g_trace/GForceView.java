package hu.unideb.inf.g_trace;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;

public class GForceView extends View {
    private Paint gridPaint, pointPaint, fadePaint;
    private float posX = 0, posY = 0, posZ = 0;
    private float maxG = 5.0f;

    public GForceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        gridPaint = new Paint();
        gridPaint.setColor(Color.parseColor("#444444"));
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setStrokeWidth(3);
        gridPaint.setAntiAlias(true);

        pointPaint = new Paint();
        pointPaint.setColor(Color.YELLOW);
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setAntiAlias(true);
        pointPaint.setShadowLayer(20, 0, 0, Color.YELLOW);

        fadePaint = new Paint();
        fadePaint.setColor(Color.argb(15, 0, 0, 0));
        fadePaint.setStyle(Paint.Style.FILL);

        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        float radius = Math.min(centerX, centerY) - 30;

        canvas.drawRect(0, 0, getWidth(), getHeight(), fadePaint);

        canvas.drawCircle(centerX, centerY, radius, gridPaint);
        canvas.drawCircle(centerX, centerY, radius * 0.66f, gridPaint);
        canvas.drawCircle(centerX, centerY, radius * 0.33f, gridPaint);
        canvas.drawLine(centerX - radius, centerY, centerX + radius, centerY, gridPaint);
        canvas.drawLine(centerX, centerY - radius, centerX, centerY + radius, gridPaint);

        float drawX = centerX + (-posX / maxG) * radius;
        float drawY = centerY + (-posY / maxG) * radius;

        canvas.drawCircle(drawX, drawY, 20, pointPaint);
    }

    public void updatePosition(float x, float y, float z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        invalidate();
    }
}

