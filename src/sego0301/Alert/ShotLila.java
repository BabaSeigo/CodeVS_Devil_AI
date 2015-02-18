package sego0301.Alert;

import sego0301.main.Point;

public class ShotLila extends Alert {

	private int turn;
	public ShotLila(Point point, TypeOfAlert type,int turn) {
		super(point, type);
		this.turn=turn;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	@Override
	public String getAlertName() {
		// TODO 自動生成されたメソッド・スタブ
		return "lilaShot";
	}

}
