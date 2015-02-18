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
public class VSGelb extends Strategy {

	public VSGelb(Devil devil) {
		super(devil);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void doStrategy() {
		// TODO 自動生成されたメソッド・スタブ

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

			for (Unit unit : searchingWokerMap.values()) {
				Map<Integer, Unit> opMap = GeneralFunction
						.abstractUnitMapInMyViewTerritory(
								devil.getOpCurrentUnits(), unit);
				List<Unit> opList = FunctionAboutScore
						.convertUnitMapToUnitList(opMap);
				if (opList.size() > 0) {
					for (Unit op : opList) {
						opList.get(0);
						GeneralFunction.setMoveUnitToPoint(unit, opList.get(0)
								.getPoint());
					}
				}
			}

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
				Map<Integer, Unit> muraMap = GeneralFunction
						.abstractTargetTypeUnits(Map, TypeOfUnit.MURA);
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

			// シルバ相手ならば資源500以上あるならば建てる
			if (nearList.size() > 0 && (devil.getCurrentResource() > 499)) {
				Unit nearW = nearList.get(0);
				nearW.setNextAction(BasicAction.makeKyoten);
				System.err.println(nearW.getId() + " " + nearW.getNextAction()
						+ "がら空きだし城建てるぜ");
			}

			// 殴られたら応戦する

			// 拠点の作戦
			Map<Integer, Unit> kyotenMap = GeneralFunction
					.abstractTargetTypeUnits(devil.getMyCurrentUnits(),
							TypeOfUnit.KYOTEN);
			for (Unit kyoten : kyotenMap.values()) {
				kyoten.setNextAction(BasicAction.makeKnight);
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

	public boolean isVSGelb(Devil devil) {
		boolean isVSGelb = false;
		Map<Integer, Unit> unitMap = OpNearMura.getUnitsMapInAlert(devil);
		Map<Integer, Unit> wokerMap = GeneralFunction.abstractTargetTypeUnits(
				unitMap, TypeOfUnit.WOKER);
		Map<Integer, Unit> asassinMap = GeneralFunction
				.abstractTargetTypeUnits(unitMap, TypeOfUnit.ASSASSIN);

		Map<Integer, Unit> knightMap = GeneralFunction.abstractTargetTypeUnits(
				unitMap, TypeOfUnit.KNIGHT);

		// アサシンいなく、ナイトが多く村人多いとゲルブ
		if ((knightMap.size() > 10) && (asassinMap.size() == 0)
				|| wokerMap.size() > 10) {
			isVSGelb = true;

		}
		return isVSGelb;
	}

}
