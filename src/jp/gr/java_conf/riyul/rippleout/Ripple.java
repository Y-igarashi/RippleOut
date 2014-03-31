package jp.gr.java_conf.riyul.rippleout;

public class Ripple {
	boolean alive;
	boolean reflection_top,reflection_bottom,reflection_right,reflection_left;
	float size;
	float point_x, point_y;
	int alfa;

	public Ripple(float x, float y, Ripple ripple){
		alive = true;
		alfa = ripple.alfa;

		reflection_top = ripple.reflection_top;
		reflection_bottom = ripple.reflection_bottom;
		reflection_right = ripple.reflection_right;
		reflection_left = ripple.reflection_left;

		point_x = x;
		point_y = y;
		size = ripple.size;
		
	}
		
	public Ripple(float x, float y){
		alive = true;
		alfa = 255;

		reflection_top = false;
		reflection_bottom = false;
		reflection_right = false;
		reflection_left = false;

		point_x = x;
		point_y = y;
		size = 20;
	}
	
	void move(){
		if(alive){
			size+=2;
		}
		if(size>510){
			delete();
		}
		
		if(size%2<1){
			alfa--;
		}
		reflection();		
	}
	
	void reflection(){
		if(point_x - size < 0 && !reflection_left){
			reflection_left = true;
	        GameController.addRipple(-size, point_y, this);
		}
		if(point_y - size < 0 && !reflection_top){
			reflection_top = true;
	        GameController.addRipple(point_x, -size, this);
		}
		if(point_x + size > MainActivity.width  && !reflection_right){
			reflection_right = true;
	        GameController.addRipple(point_x + size*2, point_y, this);
		}
		if(point_y + size > MainActivity.width  && !reflection_bottom){
			reflection_bottom = true;
	        GameController.addRipple(point_x, point_y + size*2, this);
		}
	}
	
	void delete(){
		alive = false;
	}	
}
