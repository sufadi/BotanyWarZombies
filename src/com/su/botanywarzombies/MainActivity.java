package com.su.botanywarzombies;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;

import com.su.botanywarzombies.constant.Config;
import com.su.botanywarzombies.utils.DeviceTools;
import com.su.botanywarzombies.view.GameView;

public class MainActivity extends Activity {

    private GameView mGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initValue();
        initListener();

        setContentView(mGameView);
    }

    private void initView() {

    }

    private void initValue() {
        int[] mDeviceInfo = DeviceTools.getDeviceInfo(this);
        Config.screenWidth = mDeviceInfo[0];
        Config.screenHeight = mDeviceInfo[1];

        Config.gameBg = BitmapFactory.decodeResource(getResources(), R.drawable.bk);
        Config.scaleWidth = Config.screenWidth / (float) Config.gameBg.getWidth();
        Config.scaleHeight = Config.screenHeight / (float) Config.gameBg.getHeight();

        // ��ȡ�µ������ֻ���Ļ�ı���ͼƬ
        Config.gameBg = DeviceTools.resizeBitmap(Config.gameBg);
        Config.seekBank = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.seedbank));

        int seedPicWidth = Config.seekBank.getWidth() / 6;
        int seedPicHeight = Config.seekBank.getHeight();

        // ͼƬ���ܵȱ�����
        Config.seedFlower = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.seed_flower), seedPicWidth, seedPicHeight);
        Config.seedPea = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.seed_pea), seedPicWidth, seedPicHeight);
        Config.sun = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.sun), seedPicWidth, seedPicHeight);

        Config.flowerFlames[0] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.p_1_01), seedPicWidth, seedPicHeight);
        Config.flowerFlames[1] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.p_1_02), seedPicWidth, seedPicHeight);
        Config.flowerFlames[2] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.p_1_03), seedPicWidth, seedPicHeight);
        Config.flowerFlames[3] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.p_1_04), seedPicWidth, seedPicHeight);
        Config.flowerFlames[4] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.p_1_05), seedPicWidth, seedPicHeight);
        Config.flowerFlames[5] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.p_1_06), seedPicWidth, seedPicHeight);
        Config.flowerFlames[6] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.p_1_07), seedPicWidth, seedPicHeight);
        Config.flowerFlames[7] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.p_1_08), seedPicWidth, seedPicHeight);

        Config.peaFlames[0] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.p_2_01), seedPicWidth, seedPicHeight);
        Config.peaFlames[1] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.p_2_02), seedPicWidth, seedPicHeight);
        Config.peaFlames[2] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.p_2_03), seedPicWidth, seedPicHeight);
        Config.peaFlames[3] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.p_2_04), seedPicWidth, seedPicHeight);
        Config.peaFlames[4] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.p_2_05), seedPicWidth, seedPicHeight);
        Config.peaFlames[5] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.p_2_06), seedPicWidth, seedPicHeight);
        Config.peaFlames[6] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.p_2_07), seedPicWidth, seedPicHeight);
        Config.peaFlames[7] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.p_2_08), seedPicWidth, seedPicHeight);

        Config.zombieFlames[0] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.z_1_01), seedPicWidth, seedPicHeight);
        Config.zombieFlames[1] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.z_1_02), seedPicWidth, seedPicHeight);
        Config.zombieFlames[2] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.z_1_03), seedPicWidth, seedPicHeight);
        Config.zombieFlames[3] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.z_1_04), seedPicWidth, seedPicHeight);
        Config.zombieFlames[4] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.z_1_05), seedPicWidth, seedPicHeight);
        Config.zombieFlames[5] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.z_1_06), seedPicWidth, seedPicHeight);
        Config.zombieFlames[6] = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.z_1_07), seedPicWidth, seedPicHeight);

        mGameView = new GameView(this);
    }

    private void initListener() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGameView.onTouchEvent(event);
    }

}
