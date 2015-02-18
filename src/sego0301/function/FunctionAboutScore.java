package sego0301.function;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sego0301.RuleData.BasicAction;
import sego0301.RuleData.TypeOfUnit;
import sego0301.main.Devil;
import sego0301.main.Point;
import sego0301.main.Unit;

public class FunctionAboutScore {

	private FunctionAboutScore() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	// maxscoreを出す方向をセットする
	/** 原則ワーカーで */
	public static void setMaxScoreMoveDirection(boolean[][] currentSeen,
			Map<Integer, Unit> wokerMap, Unit unit) {
		int maxScore = -1000000;

		// デフォルトでは右に行くこと
		BasicAction nextMove = BasicAction.moveToDown;
		// 次のどれが最適解か計算。同じ値だと、最初が最優先されるから、基本的には、右下の順番であることは考慮
		//BasicAction[] move = {	BasicAction.moveToRight, BasicAction.moveToDown};
		BasicAction[] move = {	BasicAction.moveToRight, BasicAction.moveToDown, BasicAction.moveToUp, BasicAction.moveToLeft,
		};
//
		// BasicAction[] move = {BasicAction.moveToRight,
		// BasicAction.moveToDown};
		for (int i = 0; i < move.length; i++) {
			BasicAction basicAction = move[i];
			int score=calculateScoreOfMove(currentSeen, wokerMap, unit,
					basicAction);
			if (maxScore < score) {
				nextMove = basicAction;
				maxScore = score;
			}
		}
		unit.setNextAction(nextMove);
		// System.err.println("unit"+unit.getId()+"は次を選択"+unit.getNextAction().getStringOfBasicAction());
	}

	// unitを一動かした後に、評価指標を計算。また、引数としてのユニットリストには城を含めない方がよい。
	public static int calculateScoreOfMove(boolean[][] currentSeen,
			Map<Integer, Unit> currentWokerMap, Unit unit,
			BasicAction nextAction) {
		int score = 0;
		// 動かした先のユニットMapとそのユニットを作成
		Map<Integer, Unit> assumptionWorkMap = getAssumptionOfUnitMap(
				currentWokerMap, unit, nextAction);
		Unit assumptionUnit = getAssumptionUnit(unit, nextAction);
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
		//	System.err.println("壁近いやばい離れないと");
			if (currentDistanceFromWall < assumptionDistanceFromWall) {
				score += 1000;
			}else if (currentDistanceFromWall > assumptionDistanceFromWall) {
				score+=-1000;
			}
		} else if (currentDistanceFromWall == 4) {
			if (assumptionDistanceFromWall < 4) {
			//	System.err.println("これ以上壁に近づくとは正気か？");
				score += -1000;
			}
		}

		// サイズが2以上でないとニアの計算を行えない
		if (currentWokerMap.size() > 1) {
			Map<Integer, Unit> nearCurrentUnitMap = GeneralFunction
					.abstractNearestUnitFromTargetUnit(unit, currentWokerMap);
			List<Unit> nearUnitList = convertUnitMapToUnitList(nearCurrentUnitMap);



			// System.err.println(nearCurrentUnitMap.size()+"	"+nearUnitList.get(0).getId());
			Point currentVector = GeneralFunction.returnRelativeDistanceFromAtoB(
					unit.getPoint(), nearUnitList.get(0).getPoint());

			// 自分から見たx、y座標が負→左側、上側に近接ユニット存在
			// その時は自分は右か下に離れる方向で。
			// if((currentVector.getX()>-8)&&(currentVector.getX()<0)||(currentVector.getY()>-8)&&(currentVector.getY()<0)){
			// if(currentDistanceFromNearest<10){
			// 現在の視野との重なりが大きいと駄目。
			if (callAreaMatchSeenAndView(currentSeen, assumptionUnit) > 30) {

				int moveDistanceX = assumptionUnit.getX() - unit.getX();
				int moveDistanceY = assumptionUnit.getY() - unit.getY();
				int nearUnitRightArea = calRightViewArea(nearUnitList.get(0),
						currentSeen);
				int nearUnitDownArea = calDownViewArea(nearUnitList.get(0),
						currentSeen);

				// 現在、自分が右側で次のステップでX方向に動いた;

				if ((currentVector.getX() < 1) && (moveDistanceX > 0)) {
					// 近傍ユニットが下側の方がより視界が開けていたら加点
					if (nearUnitDownArea > nearUnitRightArea) {
						score += Math.pow(nearUnitDownArea - nearUnitRightArea,
								2);
						score += nearUnitDownArea - nearUnitRightArea;

					}
					score += +(4 * unit.getY() - unit.getX());
					score += 100;
				}
				// 現在、自分が下側で次のステップでY方向に動いた
				if ((currentVector.getY() < 1) && (moveDistanceY > 0)) {
					// if(unit.getX()>unit.getY()){
					if (nearUnitRightArea > nearUnitDownArea) {
						score += Math.pow(nearUnitRightArea - nearUnitDownArea,
								2);
						score += nearUnitRightArea - nearUnitDownArea;

					}
					score += +(4 * unit.getX() - unit.getY());
					// }
					score += 100;
				}

			}

		}
		score += -Math
				.pow((callAreaMatchSeenAndView(currentSeen, assumptionUnit) - 32),
						2);

		// 新しく増えた領域はそのまま加点
		boolean[][] assumptionSeen = getAssumptionSeenIfUnitAdd(assumptionUnit,
				currentSeen);
		int discoveredSeen = calTerritoryArea(assumptionSeen)
				- calTerritoryArea(currentSeen);
		// 見つからんのはやばい
		if (discoveredSeen < 1) {
			score += -1000;
		}
		//最大探索をくそほど優遇
//		if (discoveredSeen ==9) {
//			System.err.println(unit.brieafSelfIntro()+nextAction.getStringOfBasicAction()+"最大探索");
//		}
		score += Math.pow(discoveredSeen, 2);
		return score;
	}

	/** 現在の視野にユニットが動いたら、どう増えるか */
	public static boolean[][] getAssumptionSeenIfUnitAdd(Unit unit,
			boolean[][] seen) {
		boolean[][] assumptionSeen = new boolean[100][100];

		// 仮定視野を現在の視野に合わせる
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				if (seen[i][j]) {
					assumptionSeen[i][j] = true;
				}
			}
		}

		int view = unit.getViewRange();
		// Unitにより増えた分を加味
		for (int y = unit.getY() - view; y <= unit.getY() + view; y++) {
			for (int x = unit.getX() - view; x <= unit.getX() + view; x++) {
				if (0 <= y && y < 100 && 0 <= x && x < 100
						&& Devil.dist(unit.getY(), unit.getX(), y, x) <= view) {
					assumptionSeen[y][x] = true;
				}
			}
		}
		return assumptionSeen;
	}

	// UnitMapを加えたらseenがどのように増えるのかを計算
	public static boolean[][] getAssumptionSeenIfUnitMapAdd(
			Map<Integer, Unit> unitMap, boolean[][] seen) {
		boolean[][] assumptionSeen = new boolean[100][100];
		// 仮定視野を現在の視野に合わせる
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				if (seen[i][j]) {
					assumptionSeen[i][j] = true;
				}
			}
		}
		for (Unit unit : unitMap.values()) {
			assumptionSeen = getAssumptionSeenIfUnitAdd(unit, assumptionSeen);
		}
		return assumptionSeen;
	}

	//
	public static int calTerritoryArea(boolean[][] territory) {
		int area = 0;
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				if (territory[i][j]) {
					area++;
				}
			}
		}
		return area;
	}

	public static Map<Integer, Unit> getAssumptionOfUnitMap(
			Map<Integer, Unit> unitMap, Unit targetUnit, BasicAction nextAction) {
		Map<Integer, Unit> assumptionOfUnitMap = new HashMap<Integer, Unit>();

		for (Unit unitA : unitMap.values()) {
			if (unitA.getId() == targetUnit.getId()) {
				// 移動したユニットを作成
				Unit assumptionUnit = getAssumptionUnit(targetUnit, nextAction);
				assumptionOfUnitMap.put(assumptionUnit.getId(), assumptionUnit);
			} else {
				assumptionOfUnitMap.put(unitA.getId(), unitA);
			}
		}
		return assumptionOfUnitMap;

	}

	// 動かした後のユニットを想定
	public static Unit getAssumptionUnit(Unit unit, BasicAction nextAction) {
		Unit assumptionUnit = new Unit(unit);

		// 各々の値に応じてユニットを動かす
		int currentX = assumptionUnit.getX();
		int currentY = assumptionUnit.getY();
		Point assumptionPoint = new Point(currentX, currentY);
		switch (nextAction) {
		case moveToDown:
			assumptionPoint.setY(currentY + 1);
			assumptionUnit.setPoint(assumptionPoint);
			break;
		case moveToUp:
			assumptionPoint.setY(currentY - 1);
			assumptionUnit.setPoint(assumptionPoint);
			break;

		case moveToRight:
			assumptionPoint.setX(currentX + 1);
			assumptionUnit.setPoint(assumptionPoint);
			break;

		case moveToLeft:
			assumptionPoint.setX(currentX - 1);
			assumptionUnit.setPoint(assumptionPoint);
			break;
		}
		return assumptionUnit;
	}

	// 10進数を任意のn進数の配列構造に変換。左が上位桁、右が下位桁になるように
	public static int[] convert10_aryToN_ary(int targetNumber, int nAry,
			int arrayLength) {
		int[] result = new int[arrayLength];
		int syou = targetNumber;
		int i = Math.max(0, arrayLength - 1);
		while (syou > 0) {
			int amari = syou % nAry;
			result[i] = amari;
			i--;
			syou = (syou - amari) / nAry;
		}
		return result;
	}

	// 全移動中のワーカユニットを動かした場合の最も高い点数の行動を登録。
	public static void setMaxScoreMoveThinkingAllUnit(boolean[][] currentSeen,
			Map<Integer, Unit> movingWokerMap, BasicAction[] baActions) {
		int maxScore = -10000;
		// 最強の行動パターン
		int[] maxPattern = new int[movingWokerMap.size()];
		// 全パターンの総数を出す。4方向移動でユニット5体なら、4の5乗

		int numOfAllPattern = (int) (Math.pow(movingWokerMap.size(),
				baActions.length) - 1);
		for (int i = numOfAllPattern; i > -1; i--) {
			// 各々のパターンをn進数の配列に。
			int[] pattern = convert10_aryToN_ary(i, baActions.length,
					movingWokerMap.size());

			// ここからパターンを登録したい。
			// 一個ずつ取り出したいから、Mapでなくリストで一時的に扱う
			List<Unit> assumptionUnitList = convertUnitMapToUnitList(movingWokerMap);
			// 各ユニットに行動を登録し、全体を動かしたユニットリストを構築。
			for (int j = 0; j < pattern.length; j++) {
				Unit currentUnit = assumptionUnitList.get(j);
				Unit assumptionUnit = FunctionAboutScore.getAssumptionUnit(
						currentUnit, baActions[pattern[j]]);
				// 未来のユニットにも今した行動を記録させる
				assumptionUnit.setNextAction(baActions[pattern[j]]);
				assumptionUnitList.set(j, assumptionUnit);
			}
			// 構築した想定ユニットリストを評価
			int patternScore = 0;
			// スコアを計算
			patternScore += sumScores(assumptionUnitList, currentSeen);

			if (maxScore < patternScore) {
				maxScore = patternScore;
				// マックスパターンに記憶
				for (int j = 0; j < pattern.length; j++) {
					maxPattern[j] = pattern[j];
				}
			}
		}

		// 最強のパターンを登録
		List<Unit> bestAssumptionUnitList = convertUnitMapToUnitList(movingWokerMap);
		for (int i = 0; i < maxPattern.length; i++) {
			bestAssumptionUnitList.get(i).setNextAction(
					baActions[maxPattern[i]]);
			// ps.println(bestAssumptionUnitList.get(i)+"	"+baActions[maxPattern[i]]);
		}

		movingWokerMap = convertUnitListToUnitMap(bestAssumptionUnitList);

	}

	public static int sumScores(List<Unit> assumptionUnitList,
			boolean[][] currentSeen) {
		int sum = 0;
		// 新発見した視野で加算
		sum += returnScoreOfDiscoveredSeen(assumptionUnitList, currentSeen) * 3;

		Map<Integer, Unit> unitMap = FunctionAboutScore
				.convertUnitListToUnitMap(assumptionUnitList);
		for (Unit unit : assumptionUnitList) {

			// 最も近いユニットとの距離が8以内なら問題
			int dis = GeneralFunction.getDistanceFromUnitAToNearestUnit(unit, unitMap);
			if (dis < 8) {
				sum += -(8 - dis) * 100;
			} else {
				sum += dis - 8;

			}

			// それぞれの近いユニットについて。x、yが8以内ならそれだけ減点
			// int disX=Function.returnXFromAtoB(unit.getPoint(),
			// nearUnit.getPoint());
			// int disY=Function.returnYFromAtoB(unit.getPoint(),
			// nearUnit.getPoint());
			// if(disX<8){
			// sum+=-(15-disX);
			// }else{
			// sum+=(disX-8);
			// }
			// if(disY<8){
			// sum+=-(15-disY);
			// }else{
			// sum+=(disY-8);
			// }

			Map<Integer, Unit> nearestUnits = GeneralFunction
					.abstractNearestUnitFromTargetUnit(unit, unitMap);
			int minX = 10000;
			int minY = 10000;
			for (Unit nearUnit : nearestUnits.values()) {

			}
			// 今の視野と未来の視野が重なっていたら大問題
			sum += -(callAreaMatchSeenAndView(currentSeen, unit) - 16) * 10;

			// 今した行動が右下のどちらかで、左上が埋まっていたのなら高評価
			if (unit.getNextAction() == BasicAction.moveToRight) {
				// System.err.println("ddddd");
				// if((unit.getX()<unit.getY())&&(unit.getX()<96)){

				int leftArea = calLeftSeenArea(unit, currentSeen);
				sum += leftArea;
				// System.err.println(unit.getX()+unit.getY()+"右に"+rightArea);
				// }
			} else if (unit.getNextAction() == BasicAction.moveToDown) {
				// if((unit.getY()<unit.getX())&&(unit.getY()<96)){
				int upArea = calUpSeenArea(unit, currentSeen);
				sum += upArea;
				// System.err.println(unit.getX()+unit.getY()+"下に"+downArea);
				// }
			}

			// 壁近いと急いで離れる
			int assumptionDistanceFromWall = GeneralFunction
					.returnDistanceFromNearestWall(unit);
			if (assumptionDistanceFromWall < 4) {
				sum += -(4 - assumptionDistanceFromWall) * 100;
			}
		}
		return sum;
	}

	// public static

	public static int returnScoreOfDiscoveredSeen(
			List<Unit> assumptionUnitList, boolean[][] currentSeen) {
		Map<Integer, Unit> assumptionUnitMap = convertUnitListToUnitMap(assumptionUnitList);
		boolean[][] newSeen = getAssumptionSeenIfUnitMapAdd(assumptionUnitMap,
				currentSeen);
		int discoveredArea = calTerritoryArea(newSeen)
				- calTerritoryArea(currentSeen);
		return discoveredArea;

	}

	public static Map<Integer, Unit> convertUnitListToUnitMap(
			List<Unit> unitList) {
		Map<Integer, Unit> unitMap = new HashMap<Integer, Unit>();
		for (Unit unit : unitList) {
			unitMap.put(unit.getId(), unit);
		}
		return unitMap;
	}

	public static List<Unit> convertUnitMapToUnitList(Map<Integer, Unit> unitMap) {
		List<Unit> unitList = new ArrayList<Unit>();
		for (Unit unit : unitMap.values()) {
			unitList.add(unit);
		}
		return unitList;
	}

	// 自分の右側の視野の直近の埋まっていない領域の計算
	public static int calRightViewArea(Unit unit, boolean[][] currentSeen) {
		// 視野の上側と下側を計算
		int topY = Math.max(0, unit.getY() - 4);
		int bottomY = Math.min(99, unit.getY() + 4);
		int area = 0;

		// 埋まっていないなら高評価
		for (int i = topY; i < bottomY; i++) {
			for (int j = unit.getX(); j < Math.min(99, unit.getX() + 5); j++) {
				if (!currentSeen[i][j]) {
					area++;
				}
			}
		}
		return area;
	}

	// 自分の下側の視野の埋まっていない領域の計算
	public static int calDownViewArea(Unit unit, boolean[][] currentSeen) {
		// 視野の上側と下側を計算
		int leftX = Math.max(0, unit.getX() - 4);
		int rightX = Math.min(99, unit.getX() + 4);
		int area = 0;

		// 埋まっていないなら高評価
		for (int i = leftX; i < rightX; i++) {
			for (int j = unit.getY(); j < Math.min(99, unit.getY() + 5); j++) {
				if (!currentSeen[j][i]) {
					area++;
				}
			}
		}
		return area;
	}

	// 自分の左側の埋まった領域の計算
	public static int calLeftSeenArea(Unit unit, boolean[][] currentSeen) {
		// 視野の上側と下側を計算
		int topY = Math.max(0, unit.getY() - 4);
		int bottomY = Math.min(99, unit.getY() + 4);
		int area = 0;

		for (int i = topY; i < bottomY; i++) {
			for (int j = Math.max(0, unit.getX() - 4); j < unit.getX(); j++) {
				if (currentSeen[i][j]) {
					area++;
				}
			}
		}
		return area;
	}

	// 視野の上側の埋まった領域を計算
	public static int calUpSeenArea(Unit unit, boolean[][] currentSeen) {
		int leftX = Math.max(0, unit.getX() - 4);
		int rightX = Math.min(99, unit.getX() + 4);
		int area = 0;

		for (int i = leftX; i < rightX; i++) {
			for (int j = Math.max(0, unit.getY() - 4); j < unit.getY(); j++) {
				if (currentSeen[j][i]) {
					area++;
				}
			}
		}
		return area;
	}

	// ユニットと今の視野の重なっている領域を返す。
	public static int callAreaMatchSeenAndView(boolean[][] currentSeen,
			Unit assumptionUnit) {
		int area = 0;
		boolean[][] unitView = GeneralFunction
				.getCurrentTargetUnitViewTerritory(assumptionUnit);
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				if (currentSeen[i][j] && unitView[i][j]) {
					area++;
				}
			}
		}
		return area;
	}

	// 視野を含めたユニット間の領域(8以内だったら、視野を考慮しない)
	public static boolean[][] getTerritoryBetweetUnits(Unit unitA, Unit unitB) {
		boolean[][] territory = new boolean[100][100];

		Point pointA = unitA.getPoint();
		Point pointB = unitB.getPoint();
		int minX = Math.min(pointA.getX(), pointB.getX());
		int maxX = Math.max(pointA.getX(), pointB.getX());
		int minY = Math.min(pointA.getY(), pointB.getY());
		int maxY = Math.max(pointA.getY(), pointB.getY());

		if (GeneralFunction.returnAbsoluteDistanceFromAtoB(unitA.getPoint(),
				unitB.getPoint()) < 8) {
			for (int i = minX; i < maxX; i++) {
				for (int j = minY; j < maxY; j++) {
					territory[j][i] = true;
				}
			}
		} else {

		}
		return territory;
	}

}
