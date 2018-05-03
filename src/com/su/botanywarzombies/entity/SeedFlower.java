package com.su.botanywarzombies.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import com.su.botanywarzombies.constant.Config;
import com.su.botanywarzombies.model.BaseModel;
import com.su.botanywarzombies.model.TouchAble;
import com.su.botanywarzombies.view.GameView;

public class SeedFlower extends BaseModel implements TouchAble {

    // 触摸区域
    private Rect touchArea;

    public SeedFlower(int locationX, int locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.isLive = true;
        touchArea = new Rect(locationX, locationY, locationX + Config.seedFlower.getWidth(), locationY + Config.seedFlower.getHeight());
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        if (isLive) {
            canvas.drawBitmap(Config.seedFlower, locationX, locationY, paint);
        }
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        // x,y坐标是否在触摸区域
        if (touchArea.contains(x, y)) {
            Log.d("sufadi", "touch seed flower");
            apply4EmplaceFlower();
            return true;
        }

        return false;
    }

    private void apply4EmplaceFlower() {
        GameView.getInstance().applyEmplacePlant(locationX, locationY, this);

    }

}
