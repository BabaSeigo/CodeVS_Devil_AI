package sego0301.Alert;

import java.util.List;
import java.util.Map;

import sego0301.RuleData.TypeOfUnit;
import sego0301.function.FunctionAboutScore;
import sego0301.function.GeneralFunction;
import sego0301.main.Devil;
import sego0301.main.Point;
import sego0301.main.Unit;

public class OpCastleIsDiscovered extends Alert {

	public OpCastleIsDiscovered(Point point, TypeOfAlert type) {
		super(point, type);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public String getAlertName() {
		// TODO 自動生成されたメソッド・スタブ
		return "城発見";
	}

	//
	public static void addOPCastleDiscovered(Devil devil) {
		Map<Integer, Unit> unitMap = GeneralFunction.abstractTargetTypeUnits(
				devil.getOpCurrentUnits(), TypeOfUnit.CASTLE);
		List<Unit> opCastle = FunctionAboutScore
				.convertUnitMapToUnitList(unitMap);
		if (unitMap.size() > 0) {
			devil.setOpCastle(opCastle.get(0));
			devil.getAlertList().add(
					new OpCastleIsDiscovered(opCastle.get(0).getPoint(),
							TypeOfAlert.OpcastleIsDiscovered));
		}
	}

	public static boolean isOPCastleDiscovered(Devil devil) {
		if (devil.getOpCastle() == null) {
			return false;
		} else {
			return true;
		}
	}

}
