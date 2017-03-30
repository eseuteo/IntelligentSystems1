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
		int[][] trace=new int[MAP_SIZE][MAP_SIZE];
		for(int k=0;k<MAP_SIZE;k++){
			for(int j=0;j<MAP_SIZE;j++){
				map[k][j]=true;
				trace[k][j]=Integer.MAX_VALUE;
			}
		}
		int x=(int)getX()/64;
		int y=(int)getY()/64;
		map[x][y]=false;
		
		Random rand = new Random((int)getHeading());
		for (int j = 1; j < 31; j++) {
			x = rand.nextInt();
			x = Math.abs(x);
			y = (x/MAP_SIZE)%MAP_SIZE;
			x%=MAP_SIZE;
			boolean done = false;
			while (!done) {
				if(!map[x][y]||(x==MAP_SIZE-1&&y==x)){
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
		backtrack((int)getX()/64, (int)getY()/64, MAP_SIZE-1, MAP_SIZE-1, 0, trace, map);
		while (true) {
			moveTowards(route.get(i));
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
	
	public boolean available(int x, int y, boolean[][] map){
		if(x<0 || x>=map.length ||y<0 || y>=map.length){
			return false;
		}
		return map[x][y];
	}
	public boolean backtrack(int x, int y, int destX, int destY, int count, int[][]trace, boolean[][] map){
		boolean ok=false;
		trace[x][y]=count;
		if(x==destX&&y==destY){
			return true;
		}
		if(!ok&&available(x, y+1, map)&&count+1<trace[x][y+1]){
			route.add(direction.NORTH);
			ok=backtrack(x, y+1, destX, destY,count+1, trace, map);
			if(!ok){
				route.remove(route.size()-1);
			}
		}
		if(!ok&&available(x+1, y, map)&&count+1<trace[x+1][y]){
			route.add(direction.EAST);
			ok=backtrack(x+1, y, destX, destY,count+1,trace, map);
			if(!ok){
				route.remove(route.size()-1);
			}
		}
		if(!ok&&available(x, y-1, map)&&count+1<trace[x][y-1]){
			route.add(direction.SOUTH);
			ok=backtrack(x, y-1, destX, destY,count+1,trace, map);
			if(!ok){
				route.remove(route.size()-1);
			}
		}
		if(!ok&&available(x-1, y, map)&&count+1<trace[x-1][y]){
			route.add(direction.WEST);
			ok=backtrack(x-1, y, destX, destY,count+1,trace, map);
			if(!ok){
				route.remove(route.size()-1);
			}
		}
		return ok;
		
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
