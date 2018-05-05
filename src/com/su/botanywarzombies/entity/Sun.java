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

    // �������������
    private static final int TIME = 5 * 1000;

    private int xDirection;
    private int yDirection;

    // ����int���ͣ��������ΪС����int���;���0�ˣ�������
    private float xSpeed;
    private float ySpeed;

    private long brithTime;

    // ��������
    private Rect touchArea;

    /**
     * SHOW ��������TIME��Ч
     * 
     * MOVE ����������Ч
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

            // move״̬������λ�ƶ���
            if (mSunState == SunState.MOVE) {
                locationX = (int) (locationX - xSpeed);
                locationY = (int) (locationY - ySpeed);

                // ��С̫������̫����Ƭλ�õ�ʱ��С̫����ʾ�Ѿ��ռ���ϣ�������ʧ��
                if (locationX < 0 || locationY < 0) {
                    locationX = Config.sunDeadLocationX;
                    locationY = Config.sunDeadLocationY;
                    isLive = false;
                    Config.sunSize = Config.sunSize + 25;
                }
            } else if (mSunState == SunState.SHOW) {
                // ������ TIME ����ʧ
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

                // С̫����locationX��locationY������Ƭ״̬����̫����Config.sunDeadLocationX��Config.sunDeadLocationY
                // ��
                xDirection = locationX - Config.sunDeadLocationX;
                yDirection = locationY - Config.sunDeadLocationY;

                // �ٶȶ���
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
