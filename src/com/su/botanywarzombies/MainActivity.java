package com.su.botanywarzombies;

import com.su.botanywarzombies.view.GameView;

import android.app.Activity;
import android.os.Bundle;


public class MainActivity extends Activity {

    private GameView mGameView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGameView = new GameView(this);

        setContentView(mGameView);
        
    }

}
