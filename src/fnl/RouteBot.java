package fnl;

import robocode.Robot;
import java.util.ArrayList;

public class RouteBot extends Robot {
	private static int MAP_SIZE = 19;
	private static int NUM_ROBOTS = 100;
	private static int GOAL_POS_X = MAP_SIZE-1;
	private static int GOAL_POS_Y = MAP_SIZE-1;
	private ArrayList<Direction> route = new ArrayList<Direction>();
	private int i = 0;
	boolean scanned = false;

	public void run() {
		boolean[][] map = MapGenerator.gen(MAP_SIZE, NUM_ROBOTS);
		Astar astar = new Astar(MAP_SIZE, new Position(0, 0), new Position(GOAL_POS_X, GOAL_POS_Y), map);
		route = astar.run();
		while (i < route.size()) {
			turnTowards(route.get(i));
			ahead(64);
			i++;
		}
	}
	

	/**
	 * 
	 * Changes robot's orientation to a given Direction (d)
	 * 
	 * */
	public void turnTowards(Direction d) {
		double ang;
		switch (d) {
		case NORTH:
			ang = 0 - getHeading();
			break;
		case EAST:
			ang = 90 - getHeading();
			break;
		case SOUTH:
			ang = 180 - getHeading();
			break;
		default:
			ang = -90 - getHeading();
			break;
		}
		
		if (ang > 180.0)
			ang = ang - 360.0;
		else if (ang < -180.0)
			ang = ang + 360.0;

		turnRight(ang);
	}
}
