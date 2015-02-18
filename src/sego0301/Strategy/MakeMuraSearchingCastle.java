package sego0301.Strategy;

import java.util.List;
import java.util.Map;

import sego0301.Actions.Actions;
import sego0301.Actions.MakeMuraInSigen;
import sego0301.Alert.GoIntoOpTerritory;
import sego0301.Alert.OpMuraOnSigen;
import sego0301.Alert.OpWokerOnSigen;
import sego0301.Alert.TypeOfAlert;
import sego0301.Alert.WokerMovingToSigen;
import sego0301.RuleData.BasicAction;
import sego0301.RuleData.JobType;
import sego0301.RuleData.TypeOfUnit;
import sego0301.function.CalculatorOfSeen;
import sego0301.function.GeneralFunction;
import sego0301.function.FunctionAboutTerritory;
import sego0301.function.FunctionAboutScore;
import sego0301.main.Devil;
import sego0301.main.OuterDirector;
import sego0301.main.Point;
import sego0301.main.Unit;

public class MakeMuraSearchingCastle extends Strategy {

	public MakeMuraSearchingCastle(Devil devil) {
		super(devil);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void doStrategy() {
		// TODO 自動生成されたメソッド・スタブ
		// 自分の視野以内に、村なければ村を建てる

		for (Unit unit : devil.getOuterDirector().getWokerLeader5s().values()) {
			// 自分が領域内に入れば
			if (GoIntoOpTerritory.goIntoOp(unit, devil)) {
				// 自分の視野内になければ
				if (!isMyMuraNear(devil, unit)) {
					unit.setNextAction(BasicAction.makeMura);
					System.err.println(unit.brieafSelfIntro()+ "探索村作戦");
				}
			}
		}

	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "城探索";
	}

	// 自分の視野マス以内に村があるか否か
	public static boolean isMyMuraNear(Devil devil, Unit unit) {
		Map<Integer, Unit> unitMap = FunctionAboutTerritory.abstractUnitMapInView(devil.getMyCurrentUnits(), unit.getPoint(), 20);
		Map<Integer, Unit> muraMap = GeneralFunction.abstractTargetTypeUnits(unitMap,
				TypeOfUnit.MURA);
		if (muraMap.size() > 0) {
			System.err.println("近くに自分の村有");
			return true;
		} else {
			return false;
		}
	}

}
