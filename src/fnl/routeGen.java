package fnl;

import java.util.ArrayList;

public class routeGen {
	public static void createRoute(ArrayList<Direction> route, boolean[][] map){
		Position init = new Position(0,0);
		Position end = new Position(12,12);
		Astar astar = new Astar(13, init, end, map);
		route = astar.run();
//		for(int i=0; i<3; i++){
//			route.add(direction.EAST);
//		}
//		route.add(direction.NORTH);
//		for(int i=0; i<8; i++){
//			route.add(direction.EAST);
//		}
//		for(int i=0; i<9; i++){
//			route.add(direction.NORTH);
//		}
//		route.add(direction.EAST);
//		for(int i=0; i<2; i++){
//			route.add(direction.NORTH);
//		}
//		route.add(direction.SOUTH);
	}
}
