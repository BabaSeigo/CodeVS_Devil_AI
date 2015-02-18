package sego0301.Strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sego0301.Alert.OpNearMura;
import sego0301.Alert.SilberIsComing;
import sego0301.RuleData.BasicAction;
import sego0301.RuleData.TypeOfUnit;
import sego0301.function.CalculatorOfSeen;
import sego0301.function.GeneralFunction;
import sego0301.function.FunctionAboutTerritory;
import sego0301.function.FunctionAboutScore;
import sego0301.main.Devil;
import sego0301.main.OuterDirector;
import sego0301.main.Point;
import sego0301.main.Unit;

/** 城がら空きの雑魚 */
public class VSWeakOp extends Strategy {

	public VSWeakOp(Devil devil) {
		super(devil);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void doStrategy() {
		// TODO 自動生成されたメソッド・スタブ

		Map<Integer, Unit> unitOnCastleMap = GeneralFunction.getUnitsAtPointA(
				devil.getMyCurrentUnits(), devil.getMyCastle().getPoint());
		Map<Integer, Unit> wokerOnCasleMap = GeneralFunction
				.abstractTargetTypeUnits(unitOnCastleMap, TypeOfUnit.WOKER);
		Map<Integer, Unit> muraOnCastleMap = GeneralFunction
				.abstractTargetTypeUnits(unitOnCastleMap, TypeOfUnit.MURA);

		if (muraOnCastleMap.size() < 5) {
			// 村人がいなかったら取り合えず作る
			if (wokerOnCasleMap.size() == 0) {
				devil.getMyCastle().setNextAction(BasicAction.makeWoker);
				devil.getMyCastle().setActionLock(true);
			} else {
				Map<Integer, Unit> kyotenMap = GeneralFunction
						.abstractTargetTypeUnits(devil.getMyCurrentUnits(),
								TypeOfUnit.KYOTEN);
				if (devil.getCurrentResource() > 599 - kyotenMap.size() * 500) {
					for (Unit unit : wokerOnCasleMap.values()) {
						unit.setNextAction(BasicAction.makeMura);
						unit.setActionLock(true);
					}
				}
			}
		}

		// 村人リーダーがやること
		if (devil.getOpCastle() == null) {
			// tst
			// Map<Integer, Unit>
			// unitMap=Function.abstractUnitMapInTargetTerritory(devil.getMyCurrentUnits(),
			// devil.getSeen());
			// CalculatorOfSeen cal=new CalculatorOfSeen(devil);
			OuterDirector outerDirector = devil.getOuterDirector();
			// outerDirectorが管理しているワーカリストを更新。
			// 探索中のワーカ
			Map<Integer, Unit> searchingWokerMap = outerDirector
					.getWokerLeader5s();

			// 通常のワーカに命令
			if (searchingWokerMap.size() > 0) {
				for (Unit woker : searchingWokerMap.values()) {
					SearchCastle.setMaxScoreMoveDirection(devil.getSeen(),
							searchingWokerMap, woker);

				}
			}

			Map<Integer, Unit> MuraMap = GeneralFunction
					.abstractTargetTypeUnits(devil.getMyCurrentUnits(),
							TypeOfUnit.MURA);
			List<Unit> muraList = FunctionAboutScore
					.convertUnitMapToUnitList(MuraMap);
			List<Unit> muraListInOp = new ArrayList<Unit>();
			for (Unit unit : muraList) {
				if ((unit.getX() > 69) && (unit.getY() > 69)) {
					muraListInOp.add(unit);
				}
			}

			// if (muraListInOp.size() > 0) {
			// muraListInOp.get(0).setNextAction(BasicAction.makeWoker);
			// }

			// for (Unit unit : searchingWokerMap.values()) {
			// Map<Integer, Unit> opMap = Function
			// .abstractUnitMapInMyViewTerritory(
			// devil.getOpCurrentUnits(), unit);
			// List<Unit> opList =
			// ScoreFunction.convertUnitMapToUnitList(opMap);
			// if (opList.size() > 0) {
			// for (Unit op : opList) {
			// opList.get(0);
			// Function.setMoveUnitToPoint(unit, opList.get(0).getPoint());
			// }
			// }
			// }

			// boolean[][]
			// d=Function.getCurrentTargetUnitViewTerritory(devil.getMyCastle());
			// int a=ScoreFunction.calTerritoryArea(d);
			// System.err.println("area"+a);
			// for (Unit iterable_element : unitMap.values()) {
			// System.err.println(iterable_element.getId());
			// }

			// System.err.println(devil.getOuterDirector().getWokerLeader5s().size());

			for (Unit unit : devil.getOuterDirector().getWokerLeader5s()
					.values()) {
				Map<Integer, Unit> Map = FunctionAboutTerritory
						.abstractUnitMapInView(devil.getMyCurrentUnits(),
								unit.getPoint(), 10);
				// System.err.println(unit.brieafSelfIntro()+"s"+Map.size());
				// Map<Integer, Unit>
				// muraMap=GeneralFunction.abstractTargetTypeUnits(Map,
				// TypeOfUnit.MURA);
				// if(muraMap.size()==0){
				// unit.setNextAction(BasicAction.makeMura);
				// unit.setActionLock(true);
				// }
			}

		} else {
			Point opCastlePoint = devil.getOpCastle().getPoint();
			Map<Integer, Unit> leader5s = devil.getOuterDirector()
					.getWokerLeader5s();

			// とりあえずリーダーは全員城に近づく
			for (Integer key : leader5s.keySet()) {
				GeneralFunction.setMoveUnitToPoint(leader5s.get(key),
						opCastlePoint);
			}

			// 近い奴は城を建てる
			Map<Integer, Unit> nearWokers = GeneralFunction
					.abstractNearestUnitFromTargetPoint(opCastlePoint, leader5s);
			List<Unit> nearList = FunctionAboutScore
					.convertUnitMapToUnitList(nearWokers);
			// Map<Integer, Unit>
			// kyotenMap=Function.abstractTargetTypeUnits(devil.getMyCurrentUnits(),
			// TypeOfUnit.KYOTEN);

			//
			if (nearList.size() > 0 && (devil.getCurrentResource() > 499)) {
				Unit nearW = nearList.get(0);
				nearW.setNextAction(BasicAction.makeKyoten);
				nearW.setActionLock(true);
				System.err.println(nearW.getId() + " " + nearW.getNextAction()
						+ "がら空きだし城建てるぜ");
			}

			// 殴られたら応戦する
			Map<Integer, Unit> unitMap = devil.getOpCurrentUnits();
			Map<Integer, Unit> wokerMap = GeneralFunction
					.abstractTargetTypeUnits(unitMap, TypeOfUnit.WOKER);
			Map<Integer, Unit> asassinMap = GeneralFunction
					.abstractTargetTypeUnits(unitOnCastleMap,
							TypeOfUnit.ASSASSIN);

			Map<Integer, Unit> knightMap = GeneralFunction
					.abstractTargetTypeUnits(unitOnCastleMap, TypeOfUnit.KNIGHT);

			// 拠点の作戦
			Map<Integer, Unit> kyotenMap = GeneralFunction
					.abstractTargetTypeUnits(devil.getMyCurrentUnits(),
							TypeOfUnit.KYOTEN);
			for (Unit kyoten : kyotenMap.values()) {
				kyoten.setNextAction(BasicAction.makeKnight);
				if (knightMap.size() > 5) {
					kyoten.setNextAction(BasicAction.makeFighter);
				}
			}

			// アタッカーの作戦(woker以外でok)
			TypeOfUnit[] attackerList = { TypeOfUnit.WOKER, TypeOfUnit.KYOTEN,
					TypeOfUnit.MURA, TypeOfUnit.CASTLE };

			Map<Integer, Unit> battlerMap = GeneralFunction
					.abstractTargetTypeUnits(devil.getMyCurrentUnits(),
							attackerList);

			for (Unit battler : battlerMap.values()) {
				GeneralFunction.setMoveUnitToPoint(battler, opCastlePoint);

			}

		}

	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "敵の城に全員突撃";
	}

	public static boolean isVSweakOp(Devil devil) {
		boolean isVSweakOp = false;
		if (devil.getOpCastle() != null) {
			Map<Integer, Unit> opUnitsMap = GeneralFunction.getUnitsAtPointA(
					devil.getOpCurrentUnits(), devil.getOpCastle().getPoint());
			Map<Integer, Unit> opKyotenMap = GeneralFunction
					.abstractTargetTypeUnits(opUnitsMap, TypeOfUnit.KYOTEN);
			// 拠点が0こだったら雑魚
			if (opKyotenMap.size() == 0) {
				System.err.println("城がら空きの雑魚");
				isVSweakOp=true;
			}
		}
		return isVSweakOp;
	}

}
