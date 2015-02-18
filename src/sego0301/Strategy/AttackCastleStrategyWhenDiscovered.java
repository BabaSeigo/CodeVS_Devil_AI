package sego0301.Strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sego0301.RuleData.BasicAction;
import sego0301.RuleData.TypeOfUnit;
import sego0301.function.GeneralFunction;
import sego0301.function.FunctionAboutScore;
import sego0301.main.Devil;
import sego0301.main.Point;
import sego0301.main.Unit;

public class AttackCastleStrategyWhenDiscovered extends Strategy {

	public AttackCastleStrategyWhenDiscovered(Devil devil) {
		super(devil);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void doStrategy() {
		// TODO 自動生成されたメソッド・スタブ

		// 村人リーダーがやること
		Point opCastlePoint = devil.getOpCastle().getPoint();
		Map<Integer, Unit> leader5s = devil.getOuterDirector()
				.getWokerLeader5s();

		// とりあえずリーダーは全員城に近づく
		for (Integer key : leader5s.keySet()) {
			GeneralFunction.setMoveUnitToPoint(leader5s.get(key), opCastlePoint);
		}

		// 近い奴は城を建てる
		Map<Integer, Unit> nearWokers = GeneralFunction
				.abstractNearestUnitFromTargetPoint(opCastlePoint, leader5s);
		List<Unit> nearList = FunctionAboutScore
				.convertUnitMapToUnitList(nearWokers);
		// Map<Integer, Unit>
		// kyotenMap=Function.abstractTargetTypeUnits(devil.getMyCurrentUnits(),
		// TypeOfUnit.KYOTEN);

		if (devil.getHabetsuki().getSilber() == 1) {
			// シルバ相手ならば資源500以上あるならば建てる
			if (nearList.size() > 0 && (devil.getCurrentResource() > 499)) {
				Unit nearW = nearList.get(0);
				nearW.setNextAction(BasicAction.makeKyoten);
				System.err.println(nearW.getId() + " " + nearW.getNextAction()
						+ "城建てるぜ");
			}

		} else {
			// 資源1000以上あるならば建てる
			if (nearList.size() > 0 && (devil.getCurrentResource() > 1000)) {
				Unit nearW = nearList.get(0);
				nearW.setNextAction(BasicAction.makeKyoten);
				System.err.println(nearW.getId() + " " + nearW.getNextAction()
						+ "城建てるぜ");
			}
		}

		// 拠点の作戦
		Map<Integer, Unit> kyotenMap = GeneralFunction.abstractTargetTypeUnits(
				devil.getMyCurrentUnits(), TypeOfUnit.KYOTEN);
		if (devil.getHabetsuki().getSilber() == 1) {
			for (Unit kyoten : kyotenMap.values()) {
				kyoten.setNextAction(BasicAction.makeKnight);
			}
		}else {//一時的にナイト生産固定になっている
			for (Unit kyoten : kyotenMap.values()) {
				kyoten.setNextAction(BasicAction.makeKnight);
			}
		}
		// アタッカーの作戦(woker以外でok)
		TypeOfUnit[] attackerList = { TypeOfUnit.WOKER, TypeOfUnit.KYOTEN,
				TypeOfUnit.MURA, TypeOfUnit.CASTLE };

		Map<Integer, Unit> battlerMap = GeneralFunction.abstractTargetTypeUnits(
				devil.getMyCurrentUnits(), attackerList);

		for (Unit battler : battlerMap.values()) {
			GeneralFunction.setMoveUnitToPoint(battler, opCastlePoint);

		}

	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "敵の城に全員突撃";
	}

}
