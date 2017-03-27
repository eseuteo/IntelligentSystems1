package fnl;

import robocode.Robot;

public class RouteBot extends Robot {
	
	public void run() {	
		while(true){
			moveTowards(200.0, 200.0);
		}
	}
	
	public void moveTowards(double x, double y){
		double ang;
		ang=trigonometry(x, y)-getHeading();
		if(ang>180.0){
			ang=ang-360.0;
		}
		else if(ang<-180.0){
			ang=ang+360.0;
		}
		turnRight(ang);
		ahead(Math.sqrt((x-getX())*(x-getX())+(y-getY())*(y-getY())));
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
