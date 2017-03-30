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

	public Asearch(int maxCells, Position ini, Position end, boolean[][] map) {
		this.maxCells = maxCells;
		this.ini = ini;
		this.end = end;
		this.map = map;
		closed = new HashSet<Position>();
		open = new HashSet<Position>();
		parent = new HashMap<Position, Position>();
		f = new int[maxCells][maxCells];
		f[ini.x][ini.y] = heuristic(ini);
		g = new int[maxCells][maxCells];
		g[ini.x][ini.y] = 0;

	}

	public ArrayList<direction> run() {
		while (!open.isEmpty()) {
			Position current = min();
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
		ArrayList<Position> list = new ArrayList<>(4);
		if (current.x != maxCells && !map[current.x + 1][current.y ])
			list.add(new Position(current.x + 1, current.y));
		if (current.x != 0 && !map[current.x - 1][current.y])
			list.add(new Position(current.x - 1, current.y));
		if (current.y != 0 && !map[current.x][current.y - 1])
			list.add(new Position(current.x, current.y - 1));
		if (current.y != 0 && !map[current.x][current.y - 1])
			list.add(new Position(current.x, current.y - 1));

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
		
		for (int j = p.size(); j > 0; j--) {
			int x = p.get(j).x - p.get(j-1).x;
			int y = p.get(j).y - p.get(j-1).y;
			if(x==0){
				if(y==1){
					res.add(direction.NORTH);
				}else{
					res.add(direction.SOUTH);
				}
			}else{
				if(x==1){
					res.add(direction.EAST);
				}else{
					res.add(direction.WEST);
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

	protected class Position {

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		int x, y;

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {

			return y * maxCells + x;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Position) {
				Position ob = (Position) obj;
				if (ob.x == this.x && ob.y == this.y) {
					return true;
				} else
					return false;
			} else
				return false;
		}
	}
}
