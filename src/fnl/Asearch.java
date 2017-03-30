package fnl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Asearch {

	// Every array is accessed as in map[x][y]
	 int maxCells;
	boolean[][] map;
	Position ini;
	Position end;
	HashSet<Position> closed;
	HashSet<Position> open;
	HashMap<Position, Position> parent;
	int[][] f;
	int[][] g;
	Position current;

	public Asearch(int maxCells,Position ini, Position end, boolean[][] map) {
		this.maxCells = maxCells;
		this.ini = ini;
		this.current = this.ini;
		this.end = end;
		this.map = map;
		closed = new HashSet<Position>();
		open = new HashSet<Position>();
		open.add(ini);
		parent = new HashMap<Position, Position>();
		f = new int[maxCells][maxCells];
		f[ini.x][ini.y] = heuristic(ini);
		g = new int[maxCells][maxCells];
		g[ini.x][ini.y] = 0;

	}

	

	public ArrayList<direction> run() {
		while (!open.isEmpty()) {
			 this.current = min();
			if (current.equals(this.end)) {
				return constructPath(current);
			}
			open.remove(current);
			closed.add(current);
			for (Position neighbor : neighbor(current)) {
				if (closed.contains(neighbor))
					continue;

				int tent = g[current.x][current.y] + 1;
				if (!open.contains(neighbor) || tent < g[neighbor.x][neighbor.y]) {
					parent.put(neighbor, current);
					g[neighbor.x][neighbor.y] = tent;
					g[neighbor.x][neighbor.y] = tent + heuristic(neighbor);
					if (!open.contains(neighbor)) {
						open.add(neighbor);
					}
				}
			}

		}
		return null;
	}

	private ArrayList<Position> neighbor(Position current) {
		ArrayList<Position> list = new ArrayList<Position>(4);
		if (current.x != maxCells-1 && !map[current.x + 1][current.y]) list.add(new Position(current.x + 1, current.y));
		if (current.x != 0 && !map[current.x - 1][current.y]) list.add(new Position(current.x - 1, current.y));
		if (current.y != maxCells-1 && !map[current.x][current.y + 1]) list.add(new Position(current.x, current.y + 1));
		if (current.y != 0 && !map[current.x][current.y - 1]) list.add(new Position(current.x, current.y - 1));

		return list;
	}
	/**
	 * Reconstruct path from ini to end. Using HashMap parent in which parent.get(x,y) is the predecessor of every expanded position.
	 * @param current - final position
	 * @return res = list of directions that should be followed by the robot
	 */
	public ArrayList<direction> constructPath(Position current) {
		ArrayList<Position> p = new ArrayList<Position>();
		ArrayList<direction> res = new ArrayList<>();
		Position i = this.end;
		while(g[i.x][i.y]>0){
			p.add(i);
			i = parent.get(i);
		}
		
		for (int j = p.size()-1; j > 0; j--) {
			int x = p.get(j).x - p.get(j-1).x;
			int y = p.get(j).y - p.get(j-1).y;
			if(x==0){
				if(y==1){
					res.add(direction.SOUTH);
				}else{
					res.add(direction.NORTH);
				}
			}else{
				if(x==1){
					res.add(direction.WEST);
				}else{
					res.add(direction.EAST);
				}
			}
			
		}
		return res;
	}

	public int heuristic(Position actual) {
		return Math.abs(end.x - actual.x) + Math.abs(end.y - actual.y);
	}

	private Position min() {

		Position min = null;
		int val = Integer.MAX_VALUE;
		for (Position i : this.open) {
			if (f[i.x][i.y] < val) {
				min = i;
				val = f[i.x][i.y];
			}
		}

		return min;
	}
	
	@Override
	public String toString() {
		StringBuilder st = new StringBuilder();
		for (int j = map.length-1; j >=0 ; j--) {
			for (int i = 0; i < map.length; i++) {
				if(!map[i][j]) {
				if(current.x==i&&current.y==j){
					 st.append("^");
				}else if(end.x==i&&end.y==j){
					 st.append("X");
				}else if(ini.x==i&&ini.y==j){
					 st.append("O");
				}else{
					 st.append("_");
				}	
				}else st.append("*");
			}
			st.append("\n");
		}
		return st.toString();
	}
	
}
