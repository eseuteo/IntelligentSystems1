package fnl;


import robocode.Robot;
import java.util.ArrayList;

public class RouteBot extends Robot {
	private ArrayList<direction> route = new ArrayList<direction>();
	static int MAP_SIZE = 13;
	int i = 0;
	boolean scanned=false;

	public void run() {
		boolean[][] map=new boolean[MAP_SIZE][MAP_SIZE];
//		for(int k=0;k<MAP_SIZE;k++){
//			for(int j=0;j<MAP_SIZE;j++){
//				map[k][j]=true;
//			}
//		}
		int x=(int)getX()/64;
		int y=(int)getY()/64;
		map[x][y]=true;
		map = MapGenerator.gen(MAP_SIZE, 31, 68);
		x=(int)getX()/64;
		y=(int)getY()/64;
		map[x][y]=false;
		Astar astar = new Astar(MAP_SIZE, new Position(0,0), new Position(12,12), map);
		route = astar.run();
		while (i<route.size()) {
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
}
