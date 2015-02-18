package sego0301.Strategist;

import java.util.List;
import java.util.Map;

import sego0301.Actions.MakeWokerInSigen;
import sego0301.Alert.Alert;
import sego0301.Alert.GoIntoOpTerritory;
import sego0301.Alert.LeaderInDanger;
import sego0301.Alert.OpCastleIsDiscovered;
import sego0301.Alert.OpCastleIsNear;
import sego0301.Alert.TypeOfAlert;
import sego0301.RuleData.BasicAction;
import sego0301.RuleData.TypeOfUnit;
import sego0301.Strategy.AttackCastleStrategyWhenDiscovered;
import sego0301.Strategy.DoOpCastleIsNear;
import sego0301.Strategy.MakeMuraSearchingCastle;
import sego0301.Strategy.MuraNormalStrategy;
import sego0301.Strategy.RandomWalkStrategy;
import sego0301.Strategy.SearchCastle;
import sego0301.Strategy.chokudaiTansakuStrategy;
import sego0301.function.GeneralFunction;
import sego0301.function.FunctionAboutScore;
import sego0301.main.Devil;
import sego0301.main.Discriminant;
import sego0301.main.TestHanbetsuki;
import sego0301.main.LogMaker;
import sego0301.main.Unit;

public class DevilStrategist extends Strategist {

	public DevilStrategist(Devil devil) {
		super(devil);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void selectStrategy() {
		// TODO 自動生成されたメソッド・スタブ

		// リーダーをリニューしてチョクダイ探索をセット（チョクダイ探索は村には指示をしない）
		devil.getOuterDirector().renewLeader5s();
		strategyList.add(new chokudaiTansakuStrategy(devil));
		// 資源上の村の作戦。襲われていなく、5人未満なら人を作る
		strategyList.add(new MuraNormalStrategy(devil));


		// 100ターン立ってからはチョクダイ以外の行動パターンもとる
		if (devil.getCurrentTurn() > 100) {
			OpCastleIsDiscovered.addOPCastleDiscovered(devil);
//			敵を判別できていたらその行動を取る

			Discriminant discriminant=Discriminant.getInstance(devil);
			if(discriminant.discriminate()){
				strategyList.add(discriminant.getVSStrategy());
			}
			// 敵の城が見つかっていないなら見つけるように努力する
			if (devil.getOpCastle() == null) {
				selectStrOpCalseNotDis();
			}

			// 判別できたらその行動をとる
//			TestHanbetsuki hanbetsu = devil.getHabetsuki();
//			if (hanbetsu.hanbetsuKakutei()) {
//				System.err.println("判別確定");
//				// strategyList.clear();
//				strategyList.add(hanbetsu.doTaisyo());
//			}
		}


	}

	public void selectStrOpCalseNotDis() {

		Map<Integer, Unit> leader5s = devil.getOuterDirector()
				.getWokerLeader5s();
		strategyList.add(new MuraNormalStrategy(devil));
		Map<Integer, Unit> muraMap = GeneralFunction.abstractTargetTypeUnits(
				devil.getMyCurrentUnits(), TypeOfUnit.MURA);
		// if (devil.getCurrentTurn() > 100) {

		LogMaker log=LogMaker.getInstance();
//
		if (muraMap.size() > 0) {
			// strategyList.add(new MuraStrategy(devil));
			if (leader5s.size() < 5) {
				log.addLog("リーダーワーカ不足。以下のリーダーのみ");
				String leaderList="";
				for (Unit iterable_element : leader5s.values()) {
					leaderList+=(iterable_element.getId());
					leaderList+=" ";
				}
				log.addLog(leaderList);

				for (Unit mura : muraMap.values()) {
					Map<Integer, Unit> unitMap = GeneralFunction
							.getUnitsAtPointA(devil.getMyCurrentUnits(),
									mura.getPoint());
					if (unitMap.size() == 1) {
						log.addLog("リーダワーカ補充:muraID"+String.valueOf(mura.getId()));
						mura.setNextAction(BasicAction.makeWoker);
//						mura.setActionLock(true);
					}
				}
			}
		}

		// 不正攻撃受けていないか？
		for (Unit leader : leader5s.values()) {
			if (OpCastleIsNear.isOpCastleNear(devil, leader)) {
				strategyList.add(new DoOpCastleIsNear(devil));
				log.addLog("城からの攻撃を検知");
			}
		}



		// 敵の領域内に入ったことがある
		if (Alert.abstractAlert(devil.getAlertList(), TypeOfAlert.GoIntoOp)
				.size() > 0) {
			System.err.println("進入中");
			strategyList.add(new SearchCastle(devil));
		} else {
			// 今回初めて進入したかどうか
			for (Unit woker : leader5s.values()) {
				if (GoIntoOpTerritory.goIntoOp(woker, devil)) {
					System.err.println("進入");
					strategyList.add(new MakeMuraSearchingCastle(devil));
				}
			}
		}
	}



	public void selectStrOpCalseDis() {

	}

	@Override
	public String getMyName() {
		// TODO 自動生成されたメソッド・スタブ
		return "devil戦略者";
	}

	//
	// public void checkOPCastle() {
	// Map<Integer, Unit> unitMap = GeneralFunction.abstractTargetTypeUnits(
	// devil.getOpCurrentUnits(), TypeOfUnit.CASTLE);
	// List<Unit> opCastle = FunctionAboutScore
	// .convertUnitMapToUnitList(unitMap);
	// if (unitMap.size() > 0) {
	// devil.setOpCastle(opCastle.get(0));
	// devil.getAlertList().add(
	// new OpCastleIsDiscovered(opCastle.get(0).getPoint(),
	// TypeOfAlert.OpcastleIsDiscovered));
	// }
	// }

}