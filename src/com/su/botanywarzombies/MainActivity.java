package com.su.botanywarzombies;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;

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
        Config.screenHeight = mDeviceInfo[0];

        Config.gameBg = BitmapFactory.decodeResource(getResources(), R.drawable.bk);
        Config.scaleWidth = Config.screenWidth / (float) Config.gameBg.getWidth();
        Config.scaleHeight = Config.screenHeight / (float) Config.gameBg.getHeight();

        // 获取新的适配手机屏幕的背景图片
        Config.gameBg = DeviceTools.resizeBitmap(Config.gameBg);

        Config.seekBank = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.seedbank));

        mGameView = new GameView(this);
    }

    private void initListener() {

    }

}
