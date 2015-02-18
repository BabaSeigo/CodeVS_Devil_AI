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
import sego0301.main.TestHanbetsuki;
import sego0301.main.Unit;

public class TestStrategist extends Strategist {

	public TestStrategist(Devil devil) {
		super(devil);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void selectStrategy() {
		// TODO 自動生成されたメソッド・スタブ

		// Map<Integer, Unit>
		// Map=Function.abstractUnitMapInMyViewTerritory(devil.getMyCurrentUnits(),devil.getMyCastle());
		// System.err.println("aaas"+Map.size());

		// strategy=new RandomWalkStrategy(devil);
		devil.getOuterDirector().renewLeader5s();
		strategyList.add(new chokudaiTansakuStrategy(devil));

		Map<Integer, Unit> muraMap = GeneralFunction.abstractTargetTypeUnits(
				devil.getMyCurrentUnits(), TypeOfUnit.MURA);

		Map<Integer, Unit> leader5s = devil.getOuterDirector()
				.getWokerLeader5s();
		List<Unit> murabito = FunctionAboutScore
				.convertUnitMapToUnitList(leader5s);
		// 相手の村が見つかっていないならば,村は人を作る
		strategyList.add(new MuraNormalStrategy(devil));
		if (devil.getCurrentTurn() > 100) {
			System.err.println("leaderIsRunOutOf");
			if (muraMap.size() > 0 && (devil.getOpCastle() == null)) {
//				strategyList.add(new MuraStrategy(devil));
				if (leader5s.size() < 5) {
					for (Unit mura : muraMap.values()) {
						Map<Integer, Unit> unitMap = GeneralFunction
								.getUnitsAtPointA(devil.getMyCurrentUnits(),
										mura.getPoint());
						if (unitMap.size() == 1) {
							mura.setNextAction(BasicAction.makeWoker);
							mura.setActionLock(true);
						}
					}
				}
			}

			// リーダーがピンチなら村作って退散
			// for (Unit leader :
			// devil.getOuterDirector().getWokerLeader5s().values()) {
			// if (LeaderInDanger.leaderIsDanger(devil, leader)) {
			// LeaderInDanger.setLeaderActionInDanger(devil, leader);
			// }
			// }

			// 毎回城が見つかっていないかチェック。いないならば城領域内に入る
			checkOPCastle();

			// 敵が見つかっていないならば敵探しをする
			// 不正攻撃受けていないか？
			for (Unit leader : leader5s.values()) {
				if (OpCastleIsNear.isOpCastleNear(devil, leader)) {
					strategyList.add(new DoOpCastleIsNear(devil));
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

			// 判別できたらその行動をとる
			TestHanbetsuki hanbetsu = devil.getHabetsuki();
			if (hanbetsu.hanbetsuKakutei()) {
				System.err.println("判別確定");
				// strategyList.clear();
				strategyList.add(hanbetsu.doTaisyo());
			}
		}
	}

	@Override
	public String getMyName() {
		// TODO 自動生成されたメソッド・スタブ
		return "テスト戦略者";
	}

	//
	public void checkOPCastle() {
		Map<Integer, Unit> unitMap = GeneralFunction.abstractTargetTypeUnits(
				devil.getOpCurrentUnits(), TypeOfUnit.CASTLE);
		List<Unit> opCastle = FunctionAboutScore
				.convertUnitMapToUnitList(unitMap);
		if (unitMap.size() > 0) {
			devil.setOpCastle(opCastle.get(0));
			devil.getAlertList().add(
					new OpCastleIsDiscovered(opCastle.get(0).getPoint(),
							TypeOfAlert.OpcastleIsDiscovered));
		}
	}

}