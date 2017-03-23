package fnl;

import java.util.ArrayList;

import robocode.Robot;

public class RouteBot extends Robot {
	ArrayList<Double> arrayDeg = new ArrayList<Double>(); //new ArrayList<Double>({90,90,90,90,90,90,90,90});
	ArrayList<Double> arrayLen = new ArrayList<Double>();
	
	public void run() {
		routeGen(200.0, 200.0);
		int max = arrayDeg.size();
		int i=0;
		turnLeft(-getHeading());
		while(i < max) {
			ahead(arrayLen.get(i));
			turnRight(arrayDeg.get(i));
			i++;
		}
	}
	
	public void routeGen(double goalX, double goalY){
		double originX = getX();
		double originY = getY();
		arrayLen.add(Math.abs(goalX-originX));
		if (goalX>originX ){
			arrayDeg.add(90.0);
		}else{
			arrayDeg.add(-90.0);
		}
		arrayLen.add(Math.abs(goalY-originY));
		if (goalY>originY ){
			arrayDeg.add(90.0);
		}else{
			arrayDeg.add(-90.0);
		}
	}
}
