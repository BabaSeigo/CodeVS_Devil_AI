package sego0301.main;

public class Point {
	private int x, y;
	private String id;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(int x, int y,String id) {
		this.x = x;
		this.y = y;
		this.id=id;
	}

	public Point(int x, int y,int id) {
		this.x = x;
		this.y = y;
		this.id=String.valueOf(id);
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIntId() {
		return Integer.parseInt(id);
	}

//	public void setId(String id) {
//		this.id = id;
//	}


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



	public boolean equalsPoint(Point targetPoint) {
		if (this.getX() == targetPoint.getX()
				&& this.getY() == targetPoint.getY()) {
			return true;
		}else {
			return false;
		}
	}
}
