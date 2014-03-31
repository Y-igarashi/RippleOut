package jp.gr.java_conf.riyul.rippleout;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends ActionBarActivity {
	static int width,height;
	GameView gameView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        Display dp = wm.getDefaultDisplay();
        height = dp.getHeight();
        width = dp.getWidth();
        gameView = new GameView(this);
        setContentView(gameView);
		//setContentView(R.layout.activity_main);
	}
	
	public void onPause(){
		super.onPause();
		gameView.finishLoop();
	}
	
	public void saveHighScore(int highScore){
		SharedPreferences pref = getSharedPreferences("user_data", Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putInt("HighScore", highScore);
		editor.commit();
	}

	public int getHighScore(){
		int highScore;
		SharedPreferences pref = getSharedPreferences("user_data", Context.MODE_PRIVATE);
		highScore = pref.getInt("HighScore", 0);
		return highScore;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    String action = "";
	    switch (event.getAction()) {
	    case MotionEvent.ACTION_DOWN:
	        action = "ACTION_DOWN";
	        break;
	    case MotionEvent.ACTION_UP:
	        action = "ACTION_UP";
        	gameView.touchOutEvent(event.getX(), event.getY());
	        break;
	    case MotionEvent.ACTION_MOVE:
	        action = "ACTION_MOVE";
	        break;
	    case MotionEvent.ACTION_CANCEL:
	        action = "ACTION_CANCEL";
	        break;
	    }
	    
	    Log.v("MotionEvent",
	        "action = " + action + ", " +
	        "x = " + String.valueOf(event.getX()) + ", " +
	        "y = " + String.valueOf(event.getY()));
	    	    
	    return super.onTouchEvent(event);
	}

}
