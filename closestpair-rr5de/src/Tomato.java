import java.util.*;
import java.math.*;
public class Tomato {
	private float point_x;
	private float point_y;
	
	//declares the tomato object
	public Tomato(float point_x, float point_y){
		this.point_x = point_x;
		this.point_y = point_y;
	}
	
	//need to get functions
	public float getX(){
		return this.point_x;
	}
	
	public float getY(){
		return this.point_y;
	}
	
	//function to actually calculate distance between two points
	public float distance_f(Tomato a, Tomato b){
		return  (float)Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(),2));
	}
	

}
