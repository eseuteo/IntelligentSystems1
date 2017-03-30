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
			for (Integer neighbor : neighbor(g, current)) {
				if (closed.contains(neighbor))
					continue;

				int tent = dist[current] + g[neighbor][current];
				if (!open.contains(neighbor) || tent < dist[neighbor]) {
					parent.put(neighbor, current);
					dist[neighbor] = tent;
					f[neighbor] = dist[neighbor] + h[neighbor];
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
		if (map[current.x + 1][current.y + 1] && current.x != maxCells && current.y != maxCells)
			list.add(new Position(current.x + 1, current.y + 1));
		if (map[current.x - 1][current.y + 1] && current.x != 0 && current.y != maxCells)
			list.add(new Position(current.x - 1, current.y + 1));
		if (map[current.x + 1][current.y - 1] && current.y != 0 && current.x != maxCells)
			list.add(new Position(current.x + 1, current.y - 1));
		if (map[current.x - 1][current.y - 1] && current.y != 0 && current.x != 0)
			list.add(new Position(current.x - 1, current.y - 1));

		return list;
	}
	/**
	 * Reconstruct path from ini to end. Using HashMap parent in which parent.get(x,y) is the predecessor of every expanded position.
	 * @param current - final position
	 * @return res = list of directions that should be followed by the robot
	 */
	public ArrayList<direction> constructPath(Position current) {
		ArrayList<direction> res;
		return null;
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
