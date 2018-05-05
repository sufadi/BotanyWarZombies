package com.su.botanywarzombies.entity;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.su.botanywarzombies.model.BaseModel;
import com.su.botanywarzombies.view.GameView;

/**
 * ��ʬ������
 */
public class ZombieManager extends BaseModel {

    private int TIME = 15 * 1000;
    private long lastBirthTime = 0l;

    public ZombieManager() {
        this.isLive = true;
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        if (System.currentTimeMillis() - lastBirthTime > TIME) {
            lastBirthTime = System.currentTimeMillis();
            creatZombie();
        }
    }

    private void creatZombie() {
        // ��Ϸͼ����뽩ʬ
        GameView.getInstance().apply4CreatZombie();
    }
}
