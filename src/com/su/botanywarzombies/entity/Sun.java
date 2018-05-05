package com.su.botanywarzombies.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.su.botanywarzombies.constant.Config;
import com.su.botanywarzombies.model.BaseModel;
import com.su.botanywarzombies.model.TouchAble;
import com.su.botanywarzombies.view.GameView;

public class Sun extends BaseModel implements TouchAble {

    // 阳光的生命周期
    private static final int TIME = 5 * 1000;

    private int xDirection;
    private int yDirection;

    // 不用int类型，如果数组为小数点int类型就是0了，动不了
    private float xSpeed;
    private float ySpeed;

    private long brithTime;

    // 触摸区域
    private Rect touchArea;

    /**
     * SHOW 生命周期TIME有效
     * 
     * MOVE 生命周期无效
     * 
     */
    public enum SunState {
        SHOW, MOVE
    }

    private SunState mSunState;

    public Sun(int locationX, int locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.isLive = true;
        touchArea = new Rect(locationX, locationY, locationX + Config.sun.getWidth(), locationY + Config.sun.getHeight());
        brithTime = System.currentTimeMillis();
        mSunState = SunState.SHOW;
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        if (isLive) {

            // move状态的阳光位移动画
            if (mSunState == SunState.MOVE) {
                locationX = (int) (locationX - xSpeed);
                locationY = (int) (locationY - ySpeed);

                // 当小太阳升到太阳卡片位置的时候，小太阳表示已经收集完毕，可以消失了
                if (locationX < 0 || locationY < 0) {
                    locationX = Config.sunDeadLocationX;
                    locationY = Config.sunDeadLocationY;
                    isLive = false;
                    Config.sunSize = Config.sunSize + 25;
                }
            } else if (mSunState == SunState.SHOW) {
                // 让阳光 TIME 后消失
                if (System.currentTimeMillis() - brithTime > TIME) {
                    isLive = false;
                }

            }

            canvas.drawBitmap(Config.sun, locationX, locationY, paint);

        }
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            if (touchArea.contains(x, y)) {
                mSunState = SunState.MOVE;

                // 小太阳（locationX，locationY）到卡片状态栏的太阳（Config.sunDeadLocationX，Config.sunDeadLocationY
                // ）
                xDirection = locationX - Config.sunDeadLocationX;
                yDirection = locationY - Config.sunDeadLocationY;

                // 速度定义
                xSpeed = xDirection / 10f;
                ySpeed = yDirection / 10f;

                return true;
            }
            break;

        default:
            break;
        }

        return false;
    }

}
