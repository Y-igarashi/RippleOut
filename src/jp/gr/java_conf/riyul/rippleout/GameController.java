package jp.gr.java_conf.riyul.rippleout;

import java.util.ArrayList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class GameController {
	static ArrayList<Ripple> rippleList;
	ArrayList<Target> targetList;
	int score;
	int highScore;
	boolean gameOver;

	public GameController(int highScore2){
		rippleList = new ArrayList<Ripple>();
		targetList = new ArrayList<Target>();
		score = 0;
		highScore = highScore2;
		gameOver = false;
		Target target;
		for(int i = 0; i<6; i++){
			for(int j = 0; j<6; j++){
				target = new Target( 100 + (MainActivity.width-100)/6*i, 100 +(MainActivity.width-100)/6*j );
				targetList.add(target);
			}
		}
	}
		
	public static void addRipple(float x, float y, Ripple ripple2){
		Ripple ripple = new Ripple(x, y, ripple2);
		rippleList.add(ripple);
	}

	public void addRipple(float x, float y){
		Target target;
		double dx,dy,distance;

		for(int i = 0; i < targetList.size(); i++){
			target = targetList.get(i);
		    if(target.alive){
				dx = Math.pow(x - target.point_x, 2);
				dy = Math.pow(y - target.point_y, 2);
				distance = Math.sqrt(dx + dy);
				if(distance - 30 > -20 && distance - 30 < 20){
					Ripple ripple = new Ripple(target.point_x, target.point_y);
					rippleList.add(ripple);
					calculateScore(target.point_x, target.point_y);
					target.delete();
				}
		    }
		}
	}

	public void upDate(){
		for(int i = 0; i < rippleList.size(); i++){
			rippleList.get(i).move();
			if(!rippleList.get(i).alive){
				rippleList.remove(i);
			}
		}
		for(int i = 0; i < targetList.size(); i++){
			if(!targetList.get(i).alive){
				targetList.remove(i);
			}
		}
		if(targetList.size() == 0 && rippleList.size() == 0 && !gameOver){
			gameOver = true;
			if(highScore < score){
				highScore = score;
			}
		}
	}
	
	public void draw(Canvas canvas){
		Target target;
		Ripple ripple;
		Paint paint = new Paint();
	    paint.setAntiAlias(true);

	    paint.setStrokeWidth(5);
	    paint.setStyle(Paint.Style.STROKE);
		for(int i = 0; i < targetList.size(); i++){
			target = targetList.get(i);
		    if(target.alive){
			    paint.setColor(Color.BLACK);
		    	canvas.drawCircle(target.point_x, target.point_y, 20, paint);
		    }
		}

		paint.setStrokeWidth(5);
	    paint.setStyle(Paint.Style.STROKE);
		for(int i = 0; i < rippleList.size(); i++){
			ripple = rippleList.get(i);
		    if(ripple.alive){
			    paint.setColor(Color.argb(ripple.alfa,255,0,255));
		    	canvas.drawCircle(ripple.point_x, ripple.point_y, ripple.size, paint);
		    }
		}
		
		paint.setColor(Color.BLACK);
		canvas.drawLine(0, MainActivity.width, MainActivity.width, MainActivity.width, paint);

		paint.setColor(Color.WHITE);
	    paint.setStyle(Paint.Style.FILL);
		Rect rect = new Rect(0, MainActivity.width, MainActivity.width, MainActivity.height);
		canvas.drawRect(rect, paint);
		
		paint.setTextSize(50);
		paint.setColor(Color.BLACK);
		canvas.drawText("SCORE:" + Integer.toString(score), 20, MainActivity.width + 50, paint);
		canvas.drawText("HIGHSCORE:" + Integer.toString(highScore), MainActivity.width/2, MainActivity.width + 50, paint);
		if(gameOver){
			canvas.drawText("GAME OVER", 20, MainActivity.width + 150, paint);			
		}
		canvas.drawText("RESTART", MainActivity.width/2, MainActivity.width + 150, paint);			
		
	}
	
	public void calculateScore(float x, float y){
		Ripple ripple;
		double dx,dy,distance;
		int near = 0;

		for(int i = 0; i < rippleList.size(); i++){
			ripple = rippleList.get(i);
			if(ripple.alive){
				dx = Math.pow(x - ripple.point_x, 2);
				dy = Math.pow(y - ripple.point_y, 2);
				distance = Math.sqrt(dx + dy);
				if(distance - ripple.size > -20 && distance - ripple.size < 20){
					near++;
				}
			}
		}
		
		score += near*near;
	}
}
