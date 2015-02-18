package sego0301.Alert;

import java.util.Map;

import sego0301.function.GeneralFunction;
import sego0301.function.FunctionAboutScore;
import sego0301.main.Devil;
import sego0301.main.Point;
import sego0301.main.Unit;

public class SilberIsComing extends Alert {

	public SilberIsComing(Point point, TypeOfAlert type) {
		super(point, type);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public String getAlertName() {
		// TODO 自動生成されたメソッド・スタブ
		return "silberや";
	}

	public static boolean isSilber(Devil devil) {
		if (devil.getCurrentTurn() < 300) {
			Unit castle = devil.getMyCastle();
			Map<Integer, Unit> opMap = GeneralFunction
					.abstractUnitMapInMyViewTerritory(
							devil.getOpCurrentUnits(), castle);
			if (opMap.size() > 0) {
				return true;
			} else {
				return false;
			}
		}else {
			return false;
		}
	}

}
