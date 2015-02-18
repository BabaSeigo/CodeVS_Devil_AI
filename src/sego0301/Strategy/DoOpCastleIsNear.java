package sego0301.Strategy;

import java.util.List;
import java.util.Map;

import sego0301.Alert.Alert;
import sego0301.Alert.OpCastleIsNear;
import sego0301.Alert.TypeOfAlert;
import sego0301.RuleData.BasicAction;
import sego0301.RuleData.TypeOfUnit;
import sego0301.function.GeneralFunction;
import sego0301.function.FunctionAboutTerritory;
import sego0301.function.FunctionAboutScore;
import sego0301.main.Devil;
import sego0301.main.Unit;

public class DoOpCastleIsNear extends Strategy {

	public DoOpCastleIsNear(Devil devil) {
		super(devil);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void doStrategy() {
		// TODO 自動生成されたメソッド・スタブ
		List<Alert> opIsNearList = Alert.abstractAlert(devil.getAlertList(),
				TypeOfAlert.OpCastleIsNear);
		System.err.println("城から攻撃を受けているから村建てる");
		if (devil.getOpCastle() == null) {
			if (opIsNearList.size() > 0) {
				OpCastleIsNear opCastleIsNear = (OpCastleIsNear) opIsNearList
						.get(0);
				Unit finder = devil.getMyCurrentUnits().get(
						opCastleIsNear.getId());
				Map<Integer, Unit> map = GeneralFunction.getUnitsAtPointA(devil.getMyCurrentUnits(), finder.getPoint());
				Map<Integer, Unit> muraMap=GeneralFunction.abstractTargetTypeUnits(map, TypeOfUnit.MURA);
				//System.err.println("dddd"+muraMap.size());
				if (muraMap.size() == 0) {
				//	System.err.println("dddddd");
					if (finder != null) {
						finder.setNextAction(BasicAction.makeMura);
						finder.setActionLock(true);
					}
				}
			}
		}
		devil.getAlertList().removeAll(opIsNearList);
	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
