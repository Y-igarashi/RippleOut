package jp.gr.java_conf.riyul.rippleout;

public class Target {
	float point_x, point_y;
	boolean alive;
	
	public Target(float x, float y){
		point_x = x;
		point_y = y;
		alive = true;
	}

	void delete(){
		alive = false;
	}
}
