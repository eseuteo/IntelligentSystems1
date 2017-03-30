package fnl;

import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import java.util.ArrayList;
import java.util.Random;

public class RouteBot extends Robot {
	protected enum direction{
		NORTH, EAST, SOUTH, WEST;
	}
	private ArrayList<direction> route = new ArrayList<direction>();
	static int MAP_SIZE = 13;
	int i = 0;
	boolean scanned=false;

	public void run() {
		boolean[][] map=new boolean[MAP_SIZE][MAP_SIZE];
		for(int k=0;k<MAP_SIZE;k++){
			for(int j=0;j<MAP_SIZE;j++){
				map[k][j]=true;
			}
		}
		//route=Astar(getX(), getY(), goalX, goalY, map);
		//la idea es guardar en map qu� baldosas est�n ocupadas seg�n se van generando las posiciones aleatorias
		//y calcular la ruta antes de empezar a mover el robot
		Random rand = new Random(seed);
		for (int j = 1; j < 51; j++) {

			int x = rand.nextInt();
			x = Math.abs(x);
			int y = (x/MAP_SIZE)%MAP_SIZE;
			x%=MAP_SIZE;
			boolean done = false;
			while (!done) {
				if(!map[x][y]){
					 x = rand.nextInt();
					 x = Math.abs(x);
					 y = (x/MAP_SIZE)%MAP_SIZE;
					 x%=MAP_SIZE;
				}else{
					done=true;
					map[x][y]=false;
				}
			}
		}
		route.add(direction.NORTH);
		route.add(direction.EAST);
		route.add(direction.SOUTH);
		route.add(direction.WEST);
		while (true) {
			do{
				scanned=false;
				moveTowards(route.get(i%route.size()));
				scan();
			}while(scanned);
			ahead(64);
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
	}

	protected static int Manhattan(int orX, int orY, int destX, int destY){
		return Math.abs(destX-orX)+Math.abs(destY-orY);
	}
	/*
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		if (event.getDistance() <= 64 &&!scanned) {
			scanned=true;
			changeDir();
		}
	}
	public void changeDir(){
		direction d;
		switch(route.get(i%route.size())){
		case NORTH:
			d=direction.EAST;
			break;
		case EAST:
			d=direction.SOUTH;
			break;
		case SOUTH:
			d=direction.WEST;
			break;
		default:
			d=direction.NORTH;
			break;
		}

		route.set(i%route.size(), d);
	}

	public double trigonometry(double DestX, double DestY) {
		double x = DestX - getX();
		double y = DestY - getY();
		double ang = Math.toDegrees(Math.atan(x / y));
		if (y < 0) {
			ang = ang + 180.0;
		}
		return ang;
	}



	public void onHitRobot(HitRobotEvent event) {
		turnRight(event.getBearing());
		back(48);
	}
*/
}
