package com.su.botanywarzombies.entity;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.su.botanywarzombies.constant.Config;
import com.su.botanywarzombies.model.BaseModel;

public class Pea extends BaseModel {

    private int farmeIndex = 0;
    // ��ֹһ��λ�÷�2��������Ƭ
    private int mapIndex;

    public Pea(int locationX, int locationY, int mapIndex) {
        this.locationX = locationX;
        this.locationY = locationY;
        isLive = true;
        this.mapIndex = mapIndex;
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        super.drawSelf(canvas, paint);
        if (isLive) {
            canvas.drawBitmap(Config.peaFlames[farmeIndex], locationX, locationY, paint);

            /*
             * farmeIndex ++; if (farmeIndex == Config.peaFlames.length -1) {
             * farmeIndex = 0; }
             */
            farmeIndex = (++farmeIndex) % 8;
        }
    }

    @Override
    public int getMapIndex() {
        return mapIndex;
    }

    @Override
    public int getModelWidth() {
        return Config.peaFlames[0].getWidth();
    }
}
