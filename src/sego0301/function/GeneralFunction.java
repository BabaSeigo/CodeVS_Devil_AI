package sego0301.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import sego0301.RuleData.BasicAction;
import sego0301.RuleData.TypeOfBasicAction;
import sego0301.RuleData.TypeOfUnit;
import sego0301.main.Devil;
import sego0301.main.Point;
import sego0301.main.Unit;

public class GeneralFunction {

	public GeneralFunction() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public static boolean theUnitExist(Devil devil, TypeOfUnit type) {
		for (Integer key : devil.getMyCurrentUnits().keySet()) {
			if (devil.getMyCurrentUnits().get(key).equalsType(type)) {
				return true;
			}
		}
		return false;

	}

	public static boolean canMakeTheUnit(Devil devil, TypeOfUnit type) {
		if (devil.getCurrentResource() > type.getCOST() - 1) {
			return true;
		} else {
			return false;
		}
	}

	// Unitsが含まれていないときを考慮するために、先にそのユニットが存在するか判断した方がよい？でも計算時間が遅くなるかも
	public static Map<Integer, Unit> abstractTargetTypeUnits(Map<Integer, Unit> unitMap,
			TypeOfUnit type) {
		Map<Integer, Unit> targetTypeUnitMap = new HashMap<Integer, Unit>();
		for (Unit unit:unitMap.values()) {
			if (unit.equalsType(type)) {
				targetTypeUnitMap.put(unit.getId(), unit);
			}
		}
		return targetTypeUnitMap;
	}



	public static Map<Integer, Unit> abstractTargetTypeUnits(Map<Integer, Unit> unitMap,
			TypeOfUnit[] typeList) {
		Map<Integer, Unit> resultMap=new HashMap<Integer, Unit>();
		for(Integer id :unitMap.keySet()){
			resultMap.put(id, unitMap.get(id));
		}
		for(TypeOfUnit type:typeList){
			resultMap=GeneralFunction.abstractUnitsExceptTargetType(resultMap, type);
		}

		return resultMap;

	}



	public static Map<Integer, Unit> abstractUnitsExceptTargetType(Map<Integer, Unit> unitMap,
			TypeOfUnit type) {
		Map<Integer, Unit> targetTypeUnitMap = new HashMap<Integer, Unit>();
		//そのユニットでなければ加えるよ
		for (Unit unit:unitMap.values()) {
			if (!unit.equalsType(type)) {
				targetTypeUnitMap.put(unit.getId(), unit);
			}
		}
		return targetTypeUnitMap;
	}




	public static boolean discoveredNewSigen(Devil devil) {
		if (devil.getOldSigenList().size() < devil.getSigenList().size()) {
			return true;
		} else {
			return false;
		}
	}


//	public static List<Point> getDiscoveredNewSigen(Devil devil) {
//		List<Point> discoveredNewSigenList = new ArrayList<Point>();
//		List<Point> currentSigenList = devil.getSigenList();
//		List<Point> oldSigenList = devil.getOldSigenList();
//		for (Point newPoint : currentSigenList) {
//			oldSigenList.contains(newPoint);
//		}
//		return discoveredNewSigenList;
//	}
	public static List<Point> getDiscoveredNewSigen(Devil devil) {
		List<Point> discoveredNewSigenList = new ArrayList<Point>();
		int oldSize=devil.getOldSigenList().size();
		for (int i = oldSize; i < devil.getSigenList().size(); i++) {
			discoveredNewSigenList.add(devil.getSigenList().get(i));
		}
		return discoveredNewSigenList;
	}


	public static boolean[][] getCurrentTotalViewTerritory(Devil devil) {

		// まっさらな視野を作る
		boolean[][] CurrentTotalViewRange = new boolean[100][100];
		for (int i = 0; i < CurrentTotalViewRange.length; i++) {
			boolean[] bs = CurrentTotalViewRange[i];
			for (int j = 0; j < bs.length; j++) {
				boolean b = bs[j];
				b = false;
			}
		}

		// 全ユニットの視野を足す
		for (Integer key : devil.getMyCurrentUnits().keySet()) {
			Unit unit = devil.getMyCurrentUnits().get(key);
			int view = unit.getViewRange();
			for (int y = unit.getY() - view; y <= unit.getY() + view; y++) {
				for (int x = unit.getX() - view; x <= unit.getX() + view; x++) {
					if (0 <= y
							&& y < 100
							&& 0 <= x
							&& x < 100
							&& Devil.dist(unit.getY(), unit.getX(), y, x) <= view) {
						CurrentTotalViewRange[y][x] = true;
					}
				}
			}
		}
		return CurrentTotalViewRange;
	}



	public static boolean[][] getCurrentTargetUnitViewTerritory(Unit unit) {

		// まっさらな視野を作る
		boolean[][] CurrentTotalViewRange = new boolean[100][100];
//		for (int i = 0; i < 100; i++) {
//			for (int j = 0; j < 100; j++) {
//
//			}
//		}

		// ユニットの視野を足す
		int view = unit.getViewRange();
		for (int y = Math.max(0, unit.getY() - view); y <= unit.getY() + view; y++) {
			for (int x = Math.max(0, unit.getX() - view); x <= unit.getX() + view; x++) {
				if (0 <= y && y < 100 && 0 <= x && x < 100
						&& Devil.dist(unit.getY(), unit.getX(), y, x) <= view) {
					CurrentTotalViewRange[y][x] = true;
				}
			}
		}
		return CurrentTotalViewRange;
	}

	// 左右上下が逆になっていたら変換する。
	public static void convertActionTtoCorrectAction(Devil devil, Unit unit) {
		//トップレフトでなく、ムーブ系なら
		if (!devil.getIsTopLeft()) {
			if(!unit.isFree()){
			if (unit.getNextAction().getTypeOfBasicAction() == TypeOfBasicAction.move) {

				String nextAction=unit.getNextAction().getStringOfBasicAction();
				if(nextAction.equals("U")){
					unit.setNextAction(BasicAction.moveToDown);
				}else if (nextAction.equals("D")) {
					unit.setNextAction(BasicAction.moveToUp);
				}else if (nextAction.equals("R")) {
					unit.setNextAction(BasicAction.moveToLeft);
				}else if (nextAction.equals("L")){
					unit.setNextAction(BasicAction.moveToRight);
				}else {
					System.err.println("convertActionToCorrectError本来ありえない命令");
				}
			}
			}
		}
	}

	//Point同士の相対距離を出す
	public static Point returnRelativeDistanceFromAtoB(Point pointA,Point pointB){
		int relativeX=pointB.getX()-pointA.getX();
		int relativeY=pointB.getY()-pointA.getY();
		Point relativeDistance=new Point(relativeX,relativeY);
		return relativeDistance;
	}



	//Point同士の絶対距離を出す
		public static int returnAbsoluteDistanceFromAtoB(Point pointA,Point pointB){
			Point vector =GeneralFunction.returnRelativeDistanceFromAtoB(pointA, pointB);
			int distance=Math.abs(vector.getX())+Math.abs(vector.getY());
			return distance;
		}

	//Pointからの最小距離のユニットmapを返す
	public static Map<Integer,Unit> abstractNearestUnitFromTargetPoint(Point pointA,Map<Integer, Unit> unitMap){
		Map<Integer,Unit> minUnitMap=new HashMap<Integer,Unit>();
		int mindistance=10000;
		for (Unit unit : unitMap.values()) {
			int distanceFromAtoUnit=GeneralFunction.returnAbsoluteDistanceFromAtoB(pointA, unit.getPoint());
			//距離が減るなら全部消して追加。
			//また、ユニットIDが違うことがポイント
			if((distanceFromAtoUnit < mindistance)){
				mindistance=distanceFromAtoUnit;
				minUnitMap.clear();
				minUnitMap.put(unit.getId(), unit);
			//距離が一緒なら、最小距離マップに追加
			}else if (distanceFromAtoUnit==mindistance) {
				minUnitMap.put(unit.getId(), unit);
			}}

		return minUnitMap;
	}

	//上と異なり、最小距離のユニットを返す。それは自分のIDを含めない前提で。
	public static Map<Integer,Unit> abstractNearestUnitFromTargetUnit(Unit targetUnit,Map<Integer, Unit> unitMap){
		Map<Integer,Unit> unitMapExceptTargetUnit=new HashMap<Integer,Unit>();

		for(Unit unit :unitMap.values()){
			if(unit.getId()!=targetUnit.getId()){
				unitMapExceptTargetUnit.put(unit.getId(), unit);
			}
		}
		Map<Integer,Unit> minUnitMap=abstractNearestUnitFromTargetPoint(targetUnit.getPoint(), unitMapExceptTargetUnit);

		return minUnitMap;
	}

	public static int returnDistanceFromNearestWall(Unit unit){
		int wall1=Math.min(unit.getX(), unit.getY());
		int wall2=Math.min(99-unit.getX(), 99-unit.getY());
		return Math.min(wall1, wall2);

	}




	//自分と近いユニットとの距離を返す
	public static int getDistanceFromUnitAToNearestUnit(Unit unitA,Map<Integer, Unit> unitMap){
		Map<Integer,Unit> nearestUnitMap=abstractNearestUnitFromTargetUnit(unitA, unitMap);

		int dis=0;
		//ちょっとずるだけど、最も近いユニット群の一番最初のユニットとの距離を出す
		for (Unit unit : nearestUnitMap.values()) {
			dis= returnAbsoluteDistanceFromAtoB(unitA.getPoint(), unit.getPoint());
		}
		return dis;
	}

	//(0,0)と(99,99)を結ぶ線よりも、城の初期位置が右上
	public static boolean isCastleAboveSlantingLine(Devil devil){
		Point castlePoint=devil.getMyCastle().getPoint();
		if(castlePoint.getY()>=castlePoint.getX()){
			if(devil.getCurrentTurn()==0){
			System.err.println("城が斜め線より下");
			}
			return false;
		}else {if(devil.getCurrentTurn()==0){
			System.err.println("城が斜め線より上");}
			return true;
		}
	}

	//城の位置が斜め線より下での行動を上での行動に変換する
	public static void convertBelowActionToAboveAction(Devil devil,Unit unit){
		//一応チェックとして、上かどうか判断。下なら変換を行う。
		if(!isCastleAboveSlantingLine(devil)){
			BasicAction nextAction=unit.getNextAction();
			//move系なら変換
			if(nextAction.getTypeOfBasicAction()==TypeOfBasicAction.move){
				if(nextAction== BasicAction.moveToUp){
					unit.setNextAction(BasicAction.moveToLeft);

				}else if(nextAction== BasicAction.moveToLeft){
					unit.setNextAction(BasicAction.moveToUp);

				}else if(nextAction== BasicAction.moveToDown){
					unit.setNextAction(BasicAction.moveToRight);

				}else if(nextAction== BasicAction.moveToRight){
					unit.setNextAction(BasicAction.moveToDown);
				}
			}
		}
	}

	//特定の領域内のユニット一覧を返す
	public static Map<Integer, Unit> abstractUnitMapInTargetTerritory(Map<Integer, Unit> unitMap, boolean[][] territory){
		Map<Integer, Unit> abstractUnitMap=new HashMap<Integer,Unit>();

		//マスがtrueのところのみを考える。
		for (int x = 0; x < territory.length; x++) {
			for (int y = 0; y < territory.length; y++) {
				if(territory[y][x]){
					//trueマス内のユニットを返す
					for(Unit unit: unitMap.values()){
						//System.err.println("aa");
						if(unit.getPoint().equalsPoint(new Point(x, y))){
						//	System.err.println("aa");
							abstractUnitMap.put(unit.getId(), unit);
						}
					}
				}

			}
		}
		return abstractUnitMap;
	}



	/**これは、自分も含めて自分のテリトリー内のユニットリストを返す*/
	public static Map<Integer, Unit> abstractUnitMapInMyViewTerritory(Map<Integer, Unit> unitMap,Unit unit){
		boolean[][] unitViewTerritory=getCurrentTargetUnitViewTerritory(unit);
		return abstractUnitMapInTargetTerritory(unitMap, unitViewTerritory);
	}




	public static int getSumOfdistanceFromUnitAToOthers(Map<Integer, Unit> unitMap,Unit targetUnit){
		int sumOfdistance=0;
		for (Unit otherUnit : unitMap.values()) {
			sumOfdistance+=returnAbsoluteDistanceFromAtoB(targetUnit.getPoint(), otherUnit.getPoint());
		}
		return sumOfdistance;
	}

	public static boolean containsTargetType(Map<Integer, Unit> unitMap,TypeOfUnit type){

		boolean contains=false;
		for (Unit otherUnit : unitMap.values()) {
			if (otherUnit.getType()==type) {
				contains =true;
			}
		}
		return contains;
	}


	public static void  printTrueSeen(boolean[][] teritoryy){
		 for (int i = 0; i < teritoryy.length; i++) {
			 for (int j = 0; j < teritoryy.length; j++) {
				 if(teritoryy[i][j]){
					 System.out.println(i+"	"+j);
				 }
			 }
		 }
	}

	public static int returnXFromAtoB(Point pointA,Point pointB){
		Point vector=GeneralFunction.returnRelativeDistanceFromAtoB(pointA, pointB);
		return Math.abs(vector.getX());
	}

	public static int returnYFromAtoB(Point pointA,Point pointB){
		Point vector=GeneralFunction.returnRelativeDistanceFromAtoB(pointA, pointB);
		return Math.abs(vector.getY());
	}

	//距離が縮まる方向に行動を選択
	public static void setMoveUnitToPoint(Unit unit ,Point point){
		Point vector=GeneralFunction.returnRelativeDistanceFromAtoB(unit.getPoint(), point);
		if(vector.getX()>0){
			unit.setNextAction(BasicAction.moveToRight);
		}else if (vector.getX()<0) {
			unit.setNextAction(BasicAction.moveToLeft);
		}else if (vector.getY()>0) {
			unit.setNextAction(BasicAction.moveToDown);
		}else if (vector.getY()<0) {
			unit.setNextAction(BasicAction.moveToUp);
		}
	}

	//UnitsBから一致したUnitsAを除く
	public static void removeUnitsAFromUnitsB(Map<Integer, Unit> unitA,Map<Integer, Unit> unitsB){
		for (Integer key:unitA.keySet()) {
			if(unitsB.containsKey(key)){
			unitsB.remove(key);
			}
		}
	}

	//特定のポイントの上に存在するユニットの一覧を返す。
	public static Map<Integer, Unit> getUnitsAtPointA(Map<Integer, Unit> unitMap, Point pointA){
		Map<Integer, Unit> unitsOnPointA=new HashMap<Integer, Unit>();
		for (Unit unit : unitMap.values()) {
			if(unit.getPoint().equalsPoint(pointA)){
				unitsOnPointA.put(unit.getId(), unit);
			}
		}

		return unitsOnPointA;
	}

	//Map<Integer, Point> をbaseIUmapを参照して次に変換Map<Integer, Unit>
	public static Map<Integer, Unit> convertIPMapToIUMap(Map<Integer, Point> IPmap,Map<Integer, Unit> baseIUmap){
		Map<Integer, Unit> unitMap=new HashMap<Integer, Unit>();
		for (Integer key : IPmap.keySet()) {
			if(baseIUmap.containsKey(key)){
			unitMap.put(key, baseIUmap.get(key));
			}
		}
		return unitMap;
	}





}
