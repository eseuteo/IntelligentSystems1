package fnl;

import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import java.util.ArrayList;

public class RouteBot extends Robot {
	protected enum direction{
		NORTH, EAST, SOUTH, WEST;
	}
	private ArrayList<direction> route = new ArrayList<direction>();
	
	private class Map{ //esto todavia no hace na
		private boolean[][] map;
		public Map(int sizeX, int sizeY){
			map=new boolean[sizeX/64][sizeY/64];
			for(int i=0;i<sizeX;i++){
				for(int j=0;j<sizeY;j++){
					map[i][j]=true;
				}
			}
		}
		public void block(int i, int j){
			map[i][j]=false;
		}
		public boolean get(int i, int j){
			if(i<0 || i>map.length || j<0 || j>map[i].length){
				return false;
			}else{
				return map[i][j];
			}
		}
	}
	
	public void run() {
		int i = 0;
		//Map map=new Map(1280, 640);
		//la idea es guardar en map qué tiles están ocupadas según se van generando las posiciones aleatorias 
		//y calcular la ruta antes de empezar a mover el robot
		route.add(direction.NORTH);
		route.add(direction.EAST);
		route.add(direction.SOUTH);
		route.add(direction.WEST);
		while (true) {
			moveTowards(route.get(i%route.size()));
			i++;
		}
	}

	public void moveTowards(direction d) {
		double ang;
		switch(d){
			case NORTH:
				ang=0-getHeading();
				break;
			case EAST:
				ang=90-getHeading();
				break;
			case SOUTH:
				ang=180-getHeading();
				break;
			default:
				ang=-90-getHeading();
				break;
		}
		if (ang > 180.0) {
			ang = ang - 360.0;
		} else if (ang < -180.0) {
			ang = ang + 360.0;
		}
		turnRight(ang);
		ahead(64);
	}
/*
	public double trigonometry(double DestX, double DestY) {
		double x = DestX - getX();
		double y = DestY - getY();
		double ang = Math.toDegrees(Math.atan(x / y));
		if (y < 0) {
			ang = ang + 180.0;
		}
		return ang;
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		if (event.getDistance() <= 64) {
			stop();
			turnRight(90);
			scan();
			if (event.getDistance() <= 64){
				turnLeft(180);
				scan();
			}
			ahead(64);
			resume();
		}
	}

	public void onHitRobot(HitRobotEvent event) {
		turnRight(event.getBearing());
		back(48);
	}
*/
}
