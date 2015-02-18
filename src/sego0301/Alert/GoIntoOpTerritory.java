package sego0301.Alert;

import sego0301.main.Devil;
import sego0301.main.Point;
import sego0301.main.Unit;

public class GoIntoOpTerritory extends Alert {

	public GoIntoOpTerritory(Point point, TypeOfAlert type) {
		super(point, type);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public String getAlertName() {
		// TODO 自動生成されたメソッド・スタブ
		return "敵の領域内";
	}

	public static  boolean goIntoOp(Unit unit,Devil devil){
		if((unit.getX()>69)&&(unit.getY()>69)){
		devil.getAlertList().add(new GoIntoOpTerritory(unit.getPoint(), TypeOfAlert.GoIntoOp));
		return true;
		}else{
			return false;
		}
	}

}
