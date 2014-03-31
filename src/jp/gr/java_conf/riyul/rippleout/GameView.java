package jp.gr.java_conf.riyul.rippleout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	private SurfaceHolder holder;
	private Thread thread;
	private MainActivity mainActivity;
	private long t1 = 0, t2 = 0;
	static boolean stop = false;
	boolean loop = true;
	enum sequenceList{START,PLAY,END};
	sequenceList sequence;
	GameController gameController;

	public GameView(Context context) {
		super(context);
		getHolder().addCallback(this);
		mainActivity = (MainActivity)context;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		holder = getHolder();
		//callbackメソッドを登録
		holder.addCallback(this);

		sequence = sequenceList.START;
		
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		synchronized (thread) {
			// ループを抜ける
			loop = false;
		}
		try{
			// スレッドの終了を待つ
			thread.join();
		} catch( InterruptedException ex ){
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public void run() {
		while (loop) {
			t1 = System.currentTimeMillis();
			switch(sequence){
			case START:
				gameController = new GameController(mainActivity.getHighScore());
				sequence = sequenceList.PLAY;
				break;
			case PLAY:
				gameController.upDate();
				if( gameController.gameOver ){
					mainActivity.saveHighScore(gameController.highScore);
					sequence = sequenceList.END;					
				}
				break;
			case END:
				break;
			}
			//描画処理
			doDraw();
			// スリープ
			t2 = System.currentTimeMillis();
			if(t2 - t1 < 16){ // 1000 / 60 = 16.6666
				try {
					Thread.sleep(16 - (t2 - t1));
				} catch (InterruptedException e) {
				}
			}
		}
	}
		
	private void doDraw(){
	    Canvas canvas = holder.lockCanvas();
	    canvas.drawColor(Color.WHITE);

	    switch(sequence){
		case START:
			break;
		case PLAY:
			gameController.draw(canvas);
			break;
		case END:
			gameController.draw(canvas);
			break;
		}

	    holder.unlockCanvasAndPost(canvas);
	}
		
	public void finishLoop(){
		surfaceDestroyed(holder);
	}
	
	public void touchOutEvent(float x, float y){
	    switch(sequence){
		case START:
			break;
		case PLAY:
			gameController.addRipple(x, y);
			break;
		case END:
			break;
		}
	    if(x - MainActivity.width/2 > 0 && y - (MainActivity.width + 125) > 0 && x - MainActivity.width/2 < MainActivity.width && y - (MainActivity.width + 125) < 50){
	    	sequence = sequenceList.START;
	    	Log.d("test","OK");
	    }
	}
}
