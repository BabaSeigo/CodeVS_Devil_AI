package sego0301.Alert;

import sego0301.main.Point;

public class WokerMovingToSigen extends Alert {

	public WokerMovingToSigen(Point point,TypeOfAlert type) {
		super(point,type);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public String getAlertName() {
		// TODO 自動生成されたメソッド・スタブ
		return "村人が資源へ移動中。村人生産気をつけて";
	}

}
