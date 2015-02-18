package sego0301.Strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sego0301.Actions.Actions;
import sego0301.Actions.MakeMuraInSigen;
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

public class SearchCastle extends Strategy {

	private Map<Integer, Unit> muraMap = new HashMap<Integer, Unit>();

	public SearchCastle(Devil devil) {
		super(devil);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void doStrategy() {
		// TODO 自動生成されたメソッド・スタブ
		// 自分の視野以内に、村なければ村を建てる

		OuterDirector outerDirector = devil.getOuterDirector();
		// outerDirectorが管理しているワーカリストを更新。
		// 探索中のワーカ
		Map<Integer, Unit> searchingWokerMap = outerDirector.getWokerLeader5s();

		// 通常のワーカに命令
		if (searchingWokerMap.size() > 0) {
			for (Unit woker : searchingWokerMap.values()) {
				setMaxScoreMoveDirection(devil.getSeen(), searchingWokerMap,
						woker);

			}
		}

		Map<Integer, Unit> MuraMap = GeneralFunction.abstractTargetTypeUnits(
				devil.getMyCurrentUnits(), TypeOfUnit.MURA);
		List<Unit> muraList = FunctionAboutScore.convertUnitMapToUnitList(MuraMap);
		List<Unit> muraListInOp = new ArrayList<Unit>();
		for (Unit unit : muraList) {
			if ((unit.getX() > 69) && (unit.getY() > 69)) {
				muraListInOp.add(unit);
			}

			Map<Integer, Unit> unitMap=FunctionAboutTerritory.abstractUnitMapInView(devil.getMyCurrentUnits(), unit.getPoint(), 20);

		}

//		if (muraListInOp.size() > 0) {
//			muraListInOp.get(0).setNextAction(BasicAction.makeWoker);
//		}

		for (Unit unit : searchingWokerMap.values()) {
			Map<Integer, Unit> opMap = GeneralFunction
					.abstractUnitMapInMyViewTerritory(
							devil.getOpCurrentUnits(), unit);
			List<Unit> opList = FunctionAboutScore.convertUnitMapToUnitList(opMap);
			if (opList.size() > 0) {
				for (Unit op : opList) {
					opList.get(0);
					GeneralFunction.setMoveUnitToPoint(unit, opList.get(0).getPoint());
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
		Map<Integer, Unit> unitMap = FunctionAboutTerritory.abstractUnitMapInView(
				devil.getMyCurrentUnits(), unit.getPoint(), 10);
		Map<Integer, Unit> muraMap = GeneralFunction.abstractTargetTypeUnits(unitMap,
				TypeOfUnit.MURA);
		if (muraMap.size() > 0) {
			System.err.println("近くに自分の村有");
			return true;
		} else {
			return false;
		}
	}

	public static void setMaxScoreMoveDirection(boolean[][] currentSeen,
			Map<Integer, Unit> wokerMap, Unit unit) {
		int maxScore = -1000000;

		// デフォルトでは右に行くこと
		BasicAction nextMove = BasicAction.moveToDown;
		BasicAction[] move = { BasicAction.moveToRight, BasicAction.moveToDown,
				BasicAction.moveToUp, BasicAction.moveToLeft, };

		for (int i = 0; i < move.length; i++) {
			BasicAction basicAction = move[i];
			int score = calculateScoreOfMove(currentSeen, wokerMap, unit,
					basicAction);
			if (maxScore < score) {
				nextMove = basicAction;
				maxScore = score;
			}
		}
		unit.setNextAction(nextMove);
	}

	// unitを一動かした後に、評価指標を計算。また、引数としてのユニットリストには城を含めない方がよい。
	public static int calculateScoreOfMove(boolean[][] currentSeen,
			Map<Integer, Unit> currentWokerMap, Unit unit,
			BasicAction nextAction) {
		int score = 0;
		// 動かした先のユニットMapとそのユニットを作成
		Map<Integer, Unit> assumptionWorkMap = FunctionAboutScore
				.getAssumptionOfUnitMap(currentWokerMap, unit, nextAction);
		Unit assumptionUnit = FunctionAboutScore.getAssumptionUnit(unit, nextAction);
		// 一番近いユニットを出す
		// Map<Integer, Unit>
		// nearestUnits=Function.abstractNearestUnitFromTargetPoint(unit.getPoint(),
		// assumptionUnitMap);

		if (GeneralFunction.containsTargetType(assumptionWorkMap, TypeOfUnit.CASTLE)) {
			System.err.println("城が村人の未来にいる");
		}

		// 実際の計算スタート。優先度は、壁が4以内。周りとの距離。新規視野探索量。
		// 現在の位置が壁から4未満→離れる方向でないと最悪
		// 現在の位置が壁とちょうど4→近づいたら最悪
		int currentDistanceFromWall = GeneralFunction
				.returnDistanceFromNearestWall(unit);
		int assumptionDistanceFromWall = GeneralFunction
				.returnDistanceFromNearestWall(assumptionUnit);
		if (currentDistanceFromWall < 4) {
			// System.err.println("壁近いやばい離れないと");
			if (currentDistanceFromWall < assumptionDistanceFromWall) {
				score += 1000;
			} else if (currentDistanceFromWall > assumptionDistanceFromWall) {
				score += -1000;
			}
		} else if (currentDistanceFromWall == 4) {
			if (assumptionDistanceFromWall < 4) {
				// System.err.println("これ以上壁に近づくとは正気か？");
				score += -1000;
			}
		}

		// サイズが2以上でないとニアの計算を行えない

		// System.err.println(nearCurrentUnitMap.size()+"	"+nearUnitList.get(0).getId());
		// 自分から見たx、y座標が負→左側、上側に近接ユニット存在
		// その時は自分は右か下に離れる方向で。
		// if((currentVector.getX()>-8)&&(currentVector.getX()<0)||(currentVector.getY()>-8)&&(currentVector.getY()<0)){
		// if(currentDistanceFromNearest<10){
		// 現在の視野との重なりが大きいと駄目。

		score += -Math.pow((FunctionAboutScore.callAreaMatchSeenAndView(currentSeen,
				assumptionUnit) - 32), 2);

		// 新しく増えた領域はそのまま加点
		boolean[][] assumptionSeen = FunctionAboutScore.getAssumptionSeenIfUnitAdd(
				assumptionUnit, currentSeen);
		int discoveredSeen = FunctionAboutScore.calTerritoryArea(assumptionSeen)
				- FunctionAboutScore.calTerritoryArea(currentSeen);
		// 見つからんのはやばい
		if (discoveredSeen < 1) {
			score += -1000;
		}
		// 最大探索をくそほど優遇
		// if (discoveredSeen ==9) {
		// System.err.println(unit.brieafSelfIntro()+nextAction.getStringOfBasicAction()+"最大探索");
		// }
		score += Math.pow(discoveredSeen, 3);
		return score;
	}

	public boolean isCaught(Unit unit) {
		int left = Math.max(0, unit.getX() + 5);
		int right = Math.min(99, unit.getX() + 5);
		int top = Math.max(0, unit.getY() - 5);
		int bottom = Math.min(99, unit.getY() + 5);
		boolean[][] unitSeen = new boolean[100][100];

		for (int i = left; i < right; i++) {
			for (int j = bottom; j < top; j++) {
				unitSeen[i][j] = true;
			}
		}
		boolean[][] seen = devil.getSeen();
		int score = calMatchArea(unitSeen, seen);
		if (score > 100) {
			System.err.println(unit.getId() + "埋まった");
			return true;
		} else {
			return false;
		}
	}

	public int calMatchArea(boolean[][] area1, boolean[][] area2) {
		int score = 0;
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				if (area1[i][j] && area2[i][j]) {
					score++;
				}
			}
		}
		return score;
	}

	// public void setMuraMapInOp(){
	// }

}
