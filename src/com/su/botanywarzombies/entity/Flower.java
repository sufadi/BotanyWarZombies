package com.su.botanywarzombies.entity;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.su.botanywarzombies.constant.Config;
import com.su.botanywarzombies.model.BaseModel;
import com.su.botanywarzombies.view.GameView;

public class Flower extends BaseModel {

    private int farmeIndex = 0;
    // 禁止一个位置放2个动画卡片
    private int mapIndex;

    private long TIME = 10 * 1000;
    private long lastBrithTime = 0l;

    public Flower(int locationX, int locationY, int mapIndex) {
        this.locationX = locationX;
        this.locationY = locationY;
        isLive = true;
        this.mapIndex = mapIndex;
        lastBrithTime = System.currentTimeMillis();
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        super.drawSelf(canvas, paint);
        if (isLive) {
            canvas.drawBitmap(Config.flowerFlames[farmeIndex], locationX, locationY, paint);

            /*
             * farmeIndex ++; if (farmeIndex == Config.peaFlames.length -1) {
             * farmeIndex = 0; }
             */
            farmeIndex = (++farmeIndex) % 8;

            if (System.currentTimeMillis() - lastBrithTime > TIME) {
                lastBrithTime = System.currentTimeMillis();
                creatSun(locationX, locationY);

            }
        }
    }

    @Override
    public int getMapIndex() {
        return mapIndex;
    }

    // 产生阳光
    private void creatSun(int locationX, int locationY) {
        GameView.getInstance().creatSun(locationX, locationY);
    }
}
