package com.su.botanywarzombies.model;

import android.graphics.Canvas;
import android.graphics.Paint;

public class BaseModel implements Plant{

    // �������ʼX����
    public int locationX;

    // �������ʼ Y����
    public int locationY;

    // �Ƿ񻹻���
    public boolean isLive;

    // λ�õ����һ���
    public void drawSelf(Canvas canvas, Paint paint) {

    }

    @Override
    public int getMapIndex() {
        return 0;
    }
}
