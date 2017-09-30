package com.hencoder.hencoderpracticedraw4.practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw4.R;

public class Practice12CameraRotateFixedView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Point point1 = new Point(200, 200);
    Point point2 = new Point(600, 200);
    Camera camera = new Camera();
    float cX1, cX2, cY1, cY2;

    public Practice12CameraRotateFixedView(Context context) {
        super(context);
    }

    public Practice12CameraRotateFixedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice12CameraRotateFixedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
        cX1 = point1.x + bitmap.getWidth() / 2;
        cX2 = point2.x + bitmap.getWidth() / 2;
        cY1 = point1.y + bitmap.getHeight() / 2;
        cY1 = point2.y + bitmap.getHeight() / 2;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        camera.save();
        canvas.translate(cX1, cY1);

        camera.rotateX(30);
        camera.applyToCanvas(canvas);
        canvas.translate(-cX1, -cY1);
        canvas.drawBitmap(bitmap,point1.x , point1.y, paint);
        camera.restore();
        canvas.restore();

        canvas.save();
        camera.save();
        canvas.translate(cX2, cY2);
        camera.rotateY(30);
        camera.applyToCanvas(canvas);
        canvas.translate(-cX2, -cY2);
        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
        camera.restore();
        canvas.restore();
    }
}
