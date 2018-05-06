package com.su.botanywarzombies.entity;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.su.botanywarzombies.constant.Config;
import com.su.botanywarzombies.model.BaseModel;
import com.su.botanywarzombies.view.GameView;

/**
 * ��ʬ��
 */
public class Zombie extends BaseModel {

    // �ܵ�������ײ���
    private int raceWay;

    // �ƶ��Ķ���֡
    private int farmeIndex;

    // ��ʬ�ƶ����ٶ�
    private int seepX = 1;

    public Zombie(int locationX, int locationY, int raceWay) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.raceWay = raceWay;
        this.isLive = true;
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        if (isLive) {
            canvas.drawBitmap(Config.zombieFlames[farmeIndex], locationX, locationY, paint);
            farmeIndex = (++farmeIndex) % 7;

            locationX = locationX - seepX;

            if (locationX < 0) {
                isLive = false;
            }

            // ��ʬ������ײ���
            GameView.getInstance().checkCollision(this, raceWay);
        }
    }

    @Override
    public int getModelWidth() {
        return Config.zombieFlames[0].getWidth();
    }
}
