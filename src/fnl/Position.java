package fnl;


public class Position {

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

		return y * x;
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