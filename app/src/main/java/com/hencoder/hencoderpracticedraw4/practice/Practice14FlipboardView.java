package com.hencoder.hencoderpracticedraw4.practice;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.hencoder.hencoderpracticedraw4.R;

public class Practice14FlipboardView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Camera camera = new Camera();
    int degree;
    ObjectAnimator animator = ObjectAnimator.ofInt(this, "degree", 0, 180);

    public Practice14FlipboardView(Context context) {
        super(context);
    }

    public Practice14FlipboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice14FlipboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);

        animator.setDuration(2500);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.end();
    }

    @SuppressWarnings("unused")
    public void setDegree(int degree) {
        this.degree = degree;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX - bitmapWidth / 2;
        int y = centerY - bitmapHeight / 2;

        canvas.save();

        //先把画布切出一半，然后绘制bitmap到 原始画布的中央的位置
        //但是因为画布现在只有一半，所以绘制的时候，只会绘制出现有画布位置上的bitmap
        //其余部分虽然绘制了，但是因为这个特性，不会显示(这样是绘制了没显示还是Android底层机制会检测到不会去绘制？如果绘制了，性能是不是在这个地方有影响？)
        canvas.clipRect(0,0,getWidth(),centerY);
        canvas.drawBitmap(bitmap,x,y,paint);
        canvas.restore();
        //下面部分绘制原理同上，使用新的画布来覆盖剪切绘制的bitmap，使其只能显示一部分
        //判断当前下面的部分应该要绘制在哪里。
        //下面部分是要旋转的，如果旋转的度数少于90度，下面的部分不会覆盖到上面的bitmap，所以这个时候剪切的画布是下面一半这个部分，如果超出了下面这一半，就会显示上面已经绘制过的内容
        //如果大于90，下面的部分旋转到了上面的画布位置，上面一半的部分旋转到了下面画布的位置，如果这个时候剪切的画布是下面这个一半，这个时候显示的就是已经绘制的不动的那部分内容
        //所以这个时候，要剪切的画布其实是上面那一半的

        canvas.save();
        if(degree>90){
            canvas.clipRect(0,0,getWidth(),centerY);
        }else{
            canvas.clipRect(0,centerY,getWidth(),getHeight());
        }

        camera.save();
        camera.rotateX(degree);
        canvas.translate(centerX, centerY);
        camera.applyToCanvas(canvas);
        canvas.translate(-centerX, -centerY);
        camera.restore();

        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();
    }
}
