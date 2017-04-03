package fnl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Astar {
	private int maxCells;
	private boolean[][] map;
	private Position ini;
	private Position end;
	private HashSet<Position> closed;
	private HashSet<Position> open;
	private HashMap<Position, Position> parent;
	private int[][] f;
	private int[][] g;
	private Position current;

	/**
	 * Constructor. Initial and ending position, besides the grid, provided as
	 * arguments
	 * 
	 * @return Astar object.
	 */
	public Astar(int maxCells, Position ini, Position end, boolean[][] map) {
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
		f[ini.getX()][ini.getY()] = heuristic(ini);
		g = new int[maxCells][maxCells];
		g[ini.getX()][ini.getY()] = 0;
	}

	/**
	 * A* method.
	 * 
	 * @return an ArrayList which is the route of the robot
	 * 
	 * */
	public ArrayList<Direction> run() {
		while (!open.isEmpty()) {
			this.current = min();
			if (current.equals(this.end))
				return constructPath(current);
			open.remove(current);
			closed.add(current);
			for (Position neighbor : neighbor(current)) {
				if (closed.contains(neighbor))
					continue;
				int tent = g[current.getX()][current.getY()] + 1;
				if (!open.contains(neighbor) || tent < g[neighbor.getX()][neighbor.getY()]) {
					parent.put(neighbor, current);
					g[neighbor.getX()][neighbor.getY()] = tent;
					f[neighbor.getX()][neighbor.getY()] = tent + heuristic(neighbor);
					if (!open.contains(neighbor)) {
						open.add(neighbor);
					}
				}
			}

		}
		return null;
	}

	/**
	 * @return an ArrayList with each one of the free neighbors
	 * */
	private ArrayList<Position> neighbor(Position current) {
		ArrayList<Position> list = new ArrayList<Position>(4);

		if (current.getX() != maxCells - 1 && !map[current.getX() + 1][current.getY()])
			list.add(new Position(current.getX() + 1, current.getY()));
		if (current.getX() != 0 && !map[current.getX() - 1][current.getY()])
			list.add(new Position(current.getX() - 1, current.getY()));
		if (current.getY() != maxCells - 1 && !map[current.getX()][current.getY() + 1])
			list.add(new Position(current.getX(), current.getY() + 1));
		if (current.getY() != 0 && !map[current.getX()][current.getY() - 1])
			list.add(new Position(current.getX(), current.getY() - 1));

		return list;
	}

	/**
	 * Reconstruct path from ini to end. Using HashMap parent in which
	 * parent.get(x,y) is the predecessor of every expanded position.
	 * 
	 * @param current
	 *            - final position
	 * @return res = list of directions that should be followed by the robot
	 */
	public ArrayList<Direction> constructPath(Position current) {
		ArrayList<Position> p = new ArrayList<Position>();
		ArrayList<Direction> res = new ArrayList<>();
		Position i = this.end;
		while (g[i.getX()][i.getY()] > 0) {
			p.add(i);
			i = parent.get(i);
		}
		p.add(ini);

		for (int j = p.size() - 1; j > 0; j--) {
			int x = p.get(j).getX() - p.get(j - 1).getX();
			int y = p.get(j).getY() - p.get(j - 1).getY();
			if (x == 0) {
				if (y == 1) {
					res.add(Direction.SOUTH);
				} else {
					res.add(Direction.NORTH);
				}
			} else {
				if (x == 1) {
					res.add(Direction.WEST);
				} else {
					res.add(Direction.EAST);
				}
			}

		}
		return res;
	}

	/**
	 * 
	 * @return Manhattan distance from the current to the goal position.
	 *  
	 * */
	public int heuristic(Position current) {
		return Math.abs(end.getX() - current.getX()) + Math.abs(end.getY() - current.getY());
	}

	/**
	 * 
	 * @return the position whose "cost" is minimum from the open set.
	 * 
	 * */
	private Position min() {

		Position min = null;
		int val = Integer.MAX_VALUE;
		for (Position i : this.open) {
			if (f[i.getX()][i.getY()] < val) {
				min = i;
				val = f[i.getX()][i.getY()];
			}
		}

		return min;
	}

	/**
	 * 
	 * Auxiliary method to print the map (debugging)
	 * 
	 * */
	@Override
	public String toString() {
		StringBuilder st = new StringBuilder();
		for (int j = map.length - 1; j >= 0; j--) {
			for (int i = 0; i < map.length; i++) {
				if (!map[i][j]) {
					if (current.getX() == i && current.getY() == j) {
						st.append("^");
					} else if (end.getX() == i && end.getY() == j) {
						st.append("X");
					} else if (ini.getX() == i && ini.getY() == j) {
						st.append("O");
					} else {
						st.append("_");
					}
				} else
					st.append("*");
			}
			st.append("\n");
		}
		return st.toString();
	}

}
