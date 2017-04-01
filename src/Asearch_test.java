import java.util.ArrayList;
import java.util.Random;

import fnl.Asearch;
import fnl.Position;
import fnl.direction;

public class Asearch_test {

	public static void main(String[] args) {
		Position ini = new Position(0, 0);
		Position end = new Position(12,12);
		boolean[][] map = mapGenerator(ini,13, 20); 
		Asearch search = new Asearch(13,ini, end, map);
		printMap(map);
		ArrayList<direction> lst = search.run();
		if(lst!=null){
			for(direction d : lst ){
				System.out.print(d.name()+", " );
			}
			System.out.println("\nsize: "+lst.size());
		}else System.out.println("ERROR");
	}
	
	private static boolean[][] mapGenerator(Position ini,int mapsize,int numDucks){
		long seed = System.currentTimeMillis();
		boolean[][] map = new boolean[mapsize][mapsize];
		map[ini.getX()][ini.getY()] = true;
		Random rand = new Random(seed);
		for (int i = 1; i < numDucks; i++) {

			int x = rand.nextInt();
			x = Math.abs(x);
			int y = (x/mapsize)%mapsize;
			x%=mapsize;
			boolean done = false;
			while (!done) {
				if(map[x][y]){
					 x = rand.nextInt();
					 x = Math.abs(x);
					 y = (x/mapsize)%mapsize;
					 x%=mapsize;
				}else{
					map[x][y]=true;
					done=true;
					}
			}
		}
		map[ini.getX()][ini.getY()] = false;
		return map;
	}

	private static void printMap(boolean[][] map){
		for (int j = map.length-1; j >=0 ; j--) {
			for (int i = 0; i < map.length; i++) {
				if(map[i][j]) System.out.print("#");
				else System.out.print("_");
			}
			System.out.println();
		}
	}
}
