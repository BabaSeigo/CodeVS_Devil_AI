package sego0301.Strategy;

import java.util.Map;

import sego0301.Alert.OpNearMura;
import sego0301.RuleData.TypeOfUnit;
import sego0301.function.GeneralFunction;
import sego0301.main.Devil;
import sego0301.main.Unit;

public class VSSch extends Strategy {

	public VSSch(Devil devil) {
		super(devil);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void doStrategy() {
		// TODO 自動生成されたメソッド・スタブ

		Map<Integer, Unit> unitMap = devil.getMyCurrentUnits();
		Map<Integer, Unit> KyotenMap = GeneralFunction.abstractTargetTypeUnits(
				unitMap, TypeOfUnit.KYOTEN);

		// Map<Integer, Unit> asassinMap = Function.abstractTargetTypeUnits(
		// unitMap, TypeOfUnit.ASSASSIN);
		//
		// Map<Integer, Unit> knightMap = Function.abstractTargetTypeUnits(
		// unitMap, TypeOfUnit.KNIGHT);

		// if(){}

		if (devil.getOpCastle() == null) {

		}

	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public static boolean isVSSch(Devil devil) {
		boolean isVSSch = false;

		Map<Integer, Unit> unitMap = OpNearMura.getUnitsMapInAlert(devil);
		Map<Integer, Unit> asassinMap = GeneralFunction
				.abstractTargetTypeUnits(unitMap, TypeOfUnit.ASSASSIN);

		if (asassinMap.size() > 10) {
			isVSSch = true;
			// result=true;
		}

		return isVSSch;
	}
}
