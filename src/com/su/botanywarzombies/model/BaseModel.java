package com.su.botanywarzombies.model;

import android.graphics.Canvas;
import android.graphics.Paint;

public class BaseModel implements Plant {

    // 对象的起始X坐标
    public int locationX;

    // 对象的起始 Y坐标
    public int locationY;

    // 是否还活着
    public boolean isLive;

    // 位置的自我绘制
    public void drawSelf(Canvas canvas, Paint paint) {

    }

    // 卡片放置区域的 mapIndex
    @Override
    public int getMapIndex() {
        return 0;
    }

    public int getModelWidth() {
        return 0;
    }
}
