package sego0301.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sego0301.Alert.Alert;
import sego0301.Alert.OpNearMura;
import sego0301.Alert.SilberIsComing;
import sego0301.Alert.TypeOfAlert;
import sego0301.RuleData.TypeOfUnit;
import sego0301.Strategy.Strategy;
import sego0301.Strategy.VSGelb;
import sego0301.Strategy.VSSilber;
import sego0301.Strategy.VSZIN;
import sego0301.Strategy.VSWeakOp;
import sego0301.Strategy.VSlila;
import sego0301.function.GeneralFunction;

public class Discriminant {

	private Devil devil;

	// 最初は0
	private int silber = 0;
	private int zin = 0;
	private int lila = 0;
	private int scw = 0;
	private int grun = 0;
	private int opIsweak = 0;
	private int yousumi = 0;
	private int grunOrZin = 0;
	private int gelb = 0;
	private Strategy vsStrategy;
	private LogMaker log = LogMaker.getInstance();

	private static Discriminant discriminant = new Discriminant();

	private Discriminant() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public static Discriminant getInstance(Devil devil) {
		discriminant.setDevil(devil);
		return discriminant;
	}

	public Devil getDevil() {
		return devil;
	}

	public void setDevil(Devil devil) {
		this.devil = devil;
	}

	public int getSilber() {
		return silber;
	}

	public void setSilber(int silber) {
		this.silber = silber;
	}

	// public Discriminant(Devil devil) {
	// this.devil = devil;
	// // TODO 自動生成されたコンストラクター・スタブ
	// }

	// 敵の判断をして、作戦を選択
	public boolean discriminate() {

		if (SilberIsComing.isSilber(devil)) {
			silber = 1;
		}

		// 資源での判定
		List<Alert> opmuraInSigen = Alert.abstractAlert(devil.getAlertList(),
				TypeOfAlert.OpMuraOnSigen);
		if (opmuraInSigen.size() > 0) {
			System.err.println("資源に村あるから、lila,tyokudaiくさい");
			scw = -1000;
			zin = -1000;
			grun = -1000;
		}

		// 敵の城での判定
		// 城が見つかった状態で
		// if (Alert.abstractAlert(devil.getAlertList(),
		// TypeOfAlert.OpcastleIsDiscovered).size() > 0) {
		//
		// Map<Integer, Unit> opUnitsMap = GeneralFunction.getUnitsAtPointA(
		// devil.getOpCurrentUnits(), devil.getOpCastle().getPoint());
		// Map<Integer, Unit> opKyotenMap = GeneralFunction
		// .abstractTargetTypeUnits(opUnitsMap, TypeOfUnit.KYOTEN);
		// // 既にリラだったらリラ確定
		// if (devil.getShotTurn() < 1000) {
		// lila = 1;
		// }
		//
		// // 拠点が1つ以下しかなかったら雑魚
		// if (opKyotenMap.size() == 0) {
		// System.err.println("がら空きの雑魚");
		// opIsweak = 1;
		//
		// } else {
		// System.err.println("様子見");
		// if ((lila > -1) && (opUnitsMap.size() > 10)
		// && (opmuraInSigen.size() > 1)) {
		// System.err.println("lila確定");
		// lila = 1;
		//
		// }
		// if (lila < -1) {
		// grunOrZin = 1;
		//
		// }
		// }
		// }

		if (VSWeakOp.isVSweakOp(devil)) {
			vsStrategy = new VSWeakOp(devil);
			log.addLog("vsWeak");
			return true;
		} else if (VSlila.isVSlila(devil)) {
			vsStrategy = new VSlila(devil);
			log.addLog("vsLila");
			return true;
		}

		// ここまでたどり着くことは、相手が決まっていないこと
		log.addLog("敵はまだわからない");
		boolean doneDiscriminate = false;
		return doneDiscriminate;
	}

	// 判断メソッドを動かしてから、その後に結果を返す
	// public boolean doneDiscriminate() {
	// discriminate();
	// return doneDiscriminate;
	// }

	public Strategy getVSStrategy() {
		return vsStrategy;
	}

	// 敵の判別を行う

	// public boolean doneDisciriminate() {
	// boolean result = false;
	//
	// if (SilberIsComing.isSilber(devil)) {
	// silber = 1;
	// result = true;
	// return result;
	// }
	//
	// // 資源での判定
	// List<Alert> opmuraInSigen = Alert.abstractAlert(devil.getAlertList(),
	// TypeOfAlert.OpMuraOnSigen);
	// if (opmuraInSigen.size() > 0) {
	// System.err.println("資源に村あるから、lila,tyokudaiくさい");
	// scw = -1000;
	// zin = -1000;
	// grun = -1000;
	// }
	//
	// // 敵の攻めてくる兵種類によって特定
	// Map<Integer, Unit> unitMap = OpNearMura.getUnitsMapInAlert(devil);
	// Map<Integer, Unit> wokerMap = GeneralFunction.abstractTargetTypeUnits(
	// unitMap, TypeOfUnit.WOKER);
	// Map<Integer, Unit> asassinMap = GeneralFunction
	// .abstractTargetTypeUnits(unitMap, TypeOfUnit.ASSASSIN);
	//
	// Map<Integer, Unit> knightMap = GeneralFunction.abstractTargetTypeUnits(
	// unitMap, TypeOfUnit.KNIGHT);
	//
	// TypeOfUnit[] attackerList = { TypeOfUnit.WOKER, TypeOfUnit.KYOTEN,
	// TypeOfUnit.MURA, TypeOfUnit.CASTLE, TypeOfUnit.ASSASSIN };
	//
	// Map<Integer, Unit> kfMap = GeneralFunction.abstractTargetTypeUnits(
	// unitMap, attackerList);
	// if ((knightMap.size() > 10) && (asassinMap.size() == 0)
	// || wokerMap.size() > 10) {
	// gelb = 1;
	// result = true;
	// return result;
	// }
	//
	// if (kfMap.size() > 0) {
	// lila = -1000;
	// scw = -1000;
	// gelb = 1;
	// }
	//
	// if (asassinMap.size() > 4) {
	// lila = -1000;
	// gelb = -1000;
	// if (asassinMap.size() > 10 && scw > -1) {
	// scw = 1;
	// // result=true;
	// }
	// }
	//
	// // 敵の城での判定
	// // 城が見つかった状態で
	// if (Alert.abstractAlert(devil.getAlertList(),
	// TypeOfAlert.OpcastleIsDiscovered).size() > 0) {
	//
	// Map<Integer, Unit> opUnitsMap = GeneralFunction.getUnitsAtPointA(
	// devil.getOpCurrentUnits(), devil.getOpCastle().getPoint());
	// Map<Integer, Unit> opKyotenMap = GeneralFunction
	// .abstractTargetTypeUnits(opUnitsMap, TypeOfUnit.KYOTEN);
	// // 既にリラだったらリラ確定
	// if (devil.getShotTurn() < 1000) {
	// lila = 1;
	// result = true;
	// return result;
	// }
	//
	// // 拠点が1つ以下しかなかったら雑魚
	// if (opKyotenMap.size() == 0) {
	// System.err.println("がら空きの雑魚");
	// opIsweak = 1;
	// result = true;
	// return result;
	// } else {
	// System.err.println("様子見");
	// if ((lila > -1) && (opUnitsMap.size() > 10)
	// && (opmuraInSigen.size() > 1)) {
	// System.err.println("lila確定");
	// lila = 1;
	// result = true;
	// return result;
	// }
	// if (lila < -1) {
	// grunOrZin = 1;
	// result = true;
	// return result;
	// }
	// }
	//
	// // }else if (opKyotenMap.size()==1) {
	// // if (opUnitsMap.size() < 10) {
	// // System.err.println("様子見");
	// // yousumi=1;
	// // result=true;
	// // }
	// // }else {
	// //
	// // System.err.println("敵はリラ、zin,grun");
	// // }
	// }
	//
	// // if(){}
	//
	// return result;
	// }

	public Strategy doTaisyo() {
		if (lila == 1) {
			System.err.println("lila");
			return new VSlila(devil);
		} else if (opIsweak == 1) {
			System.err.println("xako");
			return new VSWeakOp(devil);
		} else if (silber == 1) {
			System.err.println("silber");
			return new VSWeakOp(devil);
		} else if (gelb == 1) {
			System.err.println("gelb");
			return new VSGelb(devil);
		} else if (grunOrZin == 1) {
			System.err.println("Zin");
			return new VSZIN(devil);
		} else {
			System.err.println("敵は確定していない");
			return null;
		}

	}

}
