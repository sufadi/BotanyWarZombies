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

public class SeedPea extends BaseModel implements TouchAble {

    // ��������
    private Rect touchArea;

    public SeedPea(int locationX, int locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.isLive = true;

        touchArea = new Rect(locationX, locationY, locationX + Config.seedPea.getWidth(), locationY + Config.seedPea.getHeight());
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        if (isLive) {
            canvas.drawBitmap(Config.seedPea, locationX, locationY, paint);
        }
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            // �ж��Ƿ����˿�Ƭ�������Ƿ񽹵��ڴ�������Χ��
            if (touchArea.contains(x, y)) {
                Log.d("sufadi", "touch seed pea");
                applyEmplacePea();
                return true;
            }

        default:
            break;
        }

        return false;
    }

    // ���󰲷�һ����Ƭ
    private void applyEmplacePea() {
        // �� GameViewͼ���
        GameView.getInstance().applyEmplacePlant(locationX, locationY, this);
    }

}
