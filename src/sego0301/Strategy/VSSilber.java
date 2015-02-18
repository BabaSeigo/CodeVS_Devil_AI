package sego0301.Strategy;

import java.util.Map;

import sego0301.RuleData.BasicAction;
import sego0301.RuleData.TypeOfUnit;
import sego0301.function.GeneralFunction;
import sego0301.main.Devil;
import sego0301.main.Unit;

public class VSSilber extends Strategy {

	public VSSilber(Devil devil) {
		super(devil);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void doStrategy() {
		// TODO 自動生成されたメソッド・スタブ

		Map<Integer, Unit> unitMap = GeneralFunction.getUnitsAtPointA(
				devil.getMyCurrentUnits(), devil.getMyCastle().getPoint());
		Map<Integer, Unit> wokerMap = GeneralFunction.abstractTargetTypeUnits(unitMap,
				TypeOfUnit.WOKER);
		Map<Integer, Unit> muraMap = GeneralFunction.abstractTargetTypeUnits(unitMap,
				TypeOfUnit.MURA);

		if (muraMap.size() < 5) {
			// 村人がいなかったら取り合えず作る
			if (wokerMap.size() == 0) {
				devil.getMyCastle().setNextAction(BasicAction.makeWoker);
				devil.getMyCastle().setActionLock(true);
			} else {
				for (Unit unit : wokerMap.values()) {
					unit.setNextAction(BasicAction.makeMura);
					unit.setActionLock(true);
				}

			}
		}

		// castleに村人いなかったら生産

	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "silber対策";
	}

}
