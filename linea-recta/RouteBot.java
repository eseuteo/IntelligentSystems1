package fnl;

import robocode.Robot;

public class RouteBot extends Robot {
	
	public void run() {	
		double ang;
		while(!(getX()==200.0&&getY()==200.0)){
			ang=trigonometry(200.0, 200.0)-getHeading();
			if(ang>180.0){
				ang=ang-360.0;
			}
			else if(ang<-180.0){
				ang=ang+360.0;
			}
			turnRight(ang);
			ahead(10);
		}
	}
	
	public double trigonometry(double DestX, double DestY){
		double x=DestX-getX(); double y=DestY-getY();
		double ang=Math.toDegrees(Math.atan(x/y));
		if(y<0){
			ang=ang+180.0;
		}
		return ang;
	}
}
