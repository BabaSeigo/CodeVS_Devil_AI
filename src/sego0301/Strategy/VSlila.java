package sego0301.Strategy;

import java.util.List;
import java.util.Map;

import sego0301.Actions.LineUp;
import sego0301.Alert.Alert;
import sego0301.Alert.ShotLila;
import sego0301.Alert.SilberIsComing;
import sego0301.Alert.TypeOfAlert;
import sego0301.RuleData.BasicAction;
import sego0301.RuleData.TypeOfUnit;
import sego0301.function.GeneralFunction;
import sego0301.function.FunctionAboutScore;
import sego0301.main.Devil;
import sego0301.main.LogMaker;
import sego0301.main.Point;
import sego0301.main.Unit;

public class VSlila extends Strategy {

	public VSlila(Devil devil) {
		super(devil);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void doStrategy() {
		// TODO 自動生成されたメソッド・スタブ

		Point opCastle = devil.getOpCastle().getPoint();

		boolean migiAri = false;
		boolean sitaAri = false;
		// boolean hidariue;

		int harfDis = devil.getHarfdis();
		if (opCastle.getX() < 99 - harfDis) {
			migiAri = true;
		}
		if (opCastle.getY() < 99 - harfDis) {
			sitaAri = true;
		}

		// 最も近いリーダーの場所を出す
		Map<Integer, Unit> woker = GeneralFunction
				.abstractNearestUnitFromTargetPoint(opCastle, devil
						.getOuterDirector().getWokerLeader5s());

		List<Unit> leaderList = FunctionAboutScore
				.convertUnitMapToUnitList(woker);
		boolean kansiIeNeed = devil.isKanshiie();
		// 最も近いリーダが存在すればok
		if (woker.size() > 0) {
			Unit leader = leaderList.get(0);
			Point migi = new Point(opCastle.getX() + harfDis, opCastle.getY()
					- harfDis);
			Point sitaPoint = new Point(opCastle.getX() - harfDis,
					opCastle.getY() + harfDis);
			Point hidariUe = new Point(opCastle.getX() - harfDis,
					opCastle.getY() - harfDis);
			Point leaderP = leader.getPoint();

			Map<Integer, Unit> kyotenMap = GeneralFunction
					.abstractTargetTypeUnits(devil.getMyCurrentUnits(),
							TypeOfUnit.KYOTEN);

			// 指定の場所に到着したら、城を建てる
			if (leader.getPoint().equalsPoint(migi)
					|| leader.getPoint().equalsPoint(sitaPoint)
					|| leader.getPoint().equalsPoint(hidariUe)) {
				Map<Integer, Unit> muraMap = GeneralFunction
						.abstractTargetTypeUnits(devil.getMyCurrentUnits(),
								TypeOfUnit.MURA);
				Map<Integer, Unit> muraMapAtPoint = GeneralFunction
						.getUnitsAtPointA(muraMap, leaderP);
				if (kyotenMap.size() < 2) {
					leader.setNextAction(BasicAction.makeKyoten);
					leader.setActionLock(true);
				}
				// if(muraMapAtPoint.size()==0){
				// leader.setNextAction(BasicAction.makeMura);
				// leader.setActionLock(true);
				// }

				if (kansiIeNeed) {
					if (muraMapAtPoint.size() == 0) {
						System.err.println("dddddd");
						leader.setNextAction(BasicAction.makeMura);
						leader.setActionLock(true);
					}
				}
			}
			if (kyotenMap.size() == 2) {
				devil.setHarfdis(4);
				devil.setKanshiie(true);
				System.err.println("監視家建てに行くよ" + harfDis);
			}

			// 右有で城より右側にいれば右側に陣取る
			if (migiAri && leader.getX() >= opCastle.getX()) {
				GeneralFunction.setMoveUnitToPoint(leader, migi);
				leader.setActionLock(true);
			} else if (sitaAri && leader.getY() >= opCastle.getY()) {
				GeneralFunction.setMoveUnitToPoint(leader, sitaPoint);
				leader.setActionLock(true);
			} else {
				GeneralFunction.setMoveUnitToPoint(leader, hidariUe);
				leader.setActionLock(true);
			}

		}

		// 拠点の戦略
		Map<Integer, Unit> kyotenMap = GeneralFunction.abstractTargetTypeUnits(
				devil.getMyCurrentUnits(), TypeOfUnit.KYOTEN);

		if (kyotenMap.size() > 0) {
			// if (shotLila() && shot.getTurn()+4<devil.getCurrentTurn()) {
			// for (Unit kyoten : kyotenMap.values()) {
			// kyoten.setNextAction(BasicAction.makeAssassin);
			// kyoten.setActionLock(true);
			// }
			// }else {
			// BasicAction[]
			// makeAtacck={BasicAction.makeAssassin,BasicAction.makeFighter,BasicAction.makeKnight};
			int i = 0;
			List<Unit> KyotenList = FunctionAboutScore
					.convertUnitMapToUnitList(kyotenMap);
			for (int j = 0; j < KyotenList.size(); j++) {
				Unit kyoten = KyotenList.get(j);
				// kyoten.setNextAction(makeAtacck[i]);
				// double k = Math.random();
				// if (k < 0.3) {
				// kyoten.setNextAction(BasicAction.makeAssassin);
				// } else if (k < 0.7) {
				// kyoten.setNextAction(BasicAction.makeFighter);
				// }
				// {
				// kyoten.setNextAction(BasicAction.makeWoker);
				// }
				kyoten.setNextAction(BasicAction.makeAssassin);
				i++;
				kyoten.setActionLock(true);
			}
			// }
		}

//		1000のときだけリラがショットしたか判定をし続ける
		if (devil.getShotTurn()==1000) {
			didLilaShot();
		}
		TypeOfUnit[] attackerList = { TypeOfUnit.WOKER, TypeOfUnit.KYOTEN,
				TypeOfUnit.MURA, TypeOfUnit.CASTLE };

		Map<Integer, Unit> battlerMap = GeneralFunction
				.abstractTargetTypeUnits(devil.getMyCurrentUnits(),
						attackerList);
		LogMaker log =LogMaker.getInstance();
		log.addLog("devil.getCurrentTurn()/devil.getShotTurn()"+devil.getCurrentTurn()+"/"+devil.getShotTurn());
		if (devil.getCurrentTurn() > devil.getShotTurn() + 5) {

			log.addLog("リラ発射から5ターン経過");
			for (Unit battler : battlerMap.values()) {
				GeneralFunction.setMoveUnitToPoint(battler, devil.getOpCastle()
						.getPoint());
			}
		} else {
			LineUp seiretsu = new LineUp(devil);
			seiretsu.doActions(battlerMap);
		}

	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "lila倒す";
	}

	public boolean didLilaShot() {
		LogMaker log =LogMaker.getInstance();
		Map<Integer, Unit> currentLila = GeneralFunction.getUnitsAtPointA(
				devil.getOpCurrentUnits(), devil.getOpCastle().getPoint());
		Map<Integer, Unit> oldLila = GeneralFunction.getUnitsAtPointA(
				devil.getOpOldUnits(), devil.getOpCastle().getPoint());
		TypeOfUnit[] attackerList = { TypeOfUnit.WOKER, TypeOfUnit.KYOTEN,
				TypeOfUnit.MURA, TypeOfUnit.CASTLE };
		Map<Integer, Unit> battlerMap = GeneralFunction
				.abstractTargetTypeUnits(devil.getMyCurrentUnits(),
						attackerList);

		// System.err.println(oldLila.size());
		// System.err.println(currentLila.size());
		log.addLog("old敵/current敵/Myアサシン"+oldLila.size()+"/"+currentLila.size()+"/"+battlerMap.size());
		if ((oldLila.size() >(currentLila.size() +5)&& battlerMap.size() > 10)||battlerMap.size()>100) {
			// if ((currentLila.size() < oldLila.size() + 5)
			// && battlerMap.size() > currentLila.size()) {
			log.addLog("リラ発射");
			System.err.println("リラ発射");
			int turn = devil.getCurrentTurn();
			devil.setShotTurn(turn);
			devil.getAlertList().add(
					new ShotLila(devil.getOpCastle().getPoint(),
							TypeOfAlert.LilaShot, devil.getCurrentTurn()));
			return true;
		} else {
			// System.err.println("我慢");

			return false;
		}
	}

	// public boolean lilaShotIsUp(){
	// Point up=new Point(devil.getOpCastle().getX(),
	// devil.getOpCastle().getY()+1);
	// Map<Integer, Unit> currentLila = Function.getUnitsAtPointA(
	// devil.getOpCurrentUnits(), devil.getOpCastle().getPoint());
	//
	//
	// }
	// public static void moveDisFromPoint(Unit unit,Point point,int dis){
	//
	// }

	public static boolean isVSlila(Devil devil) {
		boolean isLila = false;
		if (devil.getOpCastle()!=null) {

			Map<Integer, Unit> opUnitsMap = GeneralFunction.getUnitsAtPointA(
					devil.getOpCurrentUnits(), devil.getOpCastle().getPoint());
			Map<Integer, Unit> opKyotenMap = GeneralFunction
					.abstractTargetTypeUnits(opUnitsMap, TypeOfUnit.KYOTEN);
			List<Alert> opmuraInSigen = Alert.abstractAlert(devil.getAlertList(),
					TypeOfAlert.OpMuraOnSigen);

			// 既にリラだったらリラ確定
			if (devil.getShotTurn() < 1000) {
				isLila = true;
			}
			// 敵の城に拠点があるか？
			if (opKyotenMap.size() > 0) {
				System.err.println("様子見");
				if ((opUnitsMap.size() > 10)
						&& (opmuraInSigen.size() > 1)) {
					System.err.println("lila確定");
					isLila=true;
				}
			}
		}
		return isLila;
	}

}
