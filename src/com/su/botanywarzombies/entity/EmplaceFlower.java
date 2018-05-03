package com.su.botanywarzombies.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.su.botanywarzombies.constant.Config;
import com.su.botanywarzombies.model.BaseModel;
import com.su.botanywarzombies.model.TouchAble;
import com.su.botanywarzombies.view.GameView;

/**
 * ����״̬���㶹����
 */
public class EmplaceFlower extends BaseModel implements TouchAble {

    // ��������
    private Rect touchArea;

    public EmplaceFlower(int locationX, int locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.isLive = true;
        touchArea = new Rect(locationX, locationY, locationX + Config.flowerFlames[0].getWidth(), locationY + Config.flowerFlames[0].getHeight());
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        if (isLive) {
            canvas.drawBitmap(Config.flowerFlames[0], locationX, locationY, paint);
        }
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (touchArea.contains(x, y)) {
            switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                // ��Ƭλ�ø���,�����ǿ�Ƭԭ��ı仯���������ĵ�(locationX, locationY)�ı仯
                locationX = x - Config.peaFlames[0].getWidth() / 2;
                locationY = y - Config.peaFlames[0].getHeight() / 2;

                // ��������棬�����Ƶ��µ�λ��
                touchArea.offsetTo(locationX, locationY);
                break;
            case MotionEvent.ACTION_UP:
                // �����־λʧЧ������������
                isLive = false;

                GameView.getInstance().applay4Plant(locationX, locationY, Config.TYPE_FLOWER);
                break;
            default:
                break;
            }
        }

        return false;
    }

}
