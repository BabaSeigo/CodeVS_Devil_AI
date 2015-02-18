package sego0301.function;

import java.util.HashMap;
import java.util.Map;

import sego0301.main.Devil;
import sego0301.main.Point;
import sego0301.main.Unit;

public class FunctionAboutTerritory {

	public FunctionAboutTerritory() {
		// TODO 自動生成されたコンストラクター・スタブ
	}


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



	public static Map<Integer, Unit> abstractUnitMapInView(Map<Integer, Unit> unitMap,Point point,int view){
		boolean[][] unitViewTerritory=getTerritory(point, view);
		return abstractUnitMapInTargetTerritory(unitMap, unitViewTerritory);
	}



	public static boolean[][] getTerritory(Point point,int viewRange) {

		// まっさらな視野を作る
		boolean[][] CurrentTotalViewRange = new boolean[100][100];

		// ユニットの視野を足す
		int view = viewRange;
		for (int y = Math.max(0, point.getY() - view); y <= point.getY() + view; y++) {
			for (int x = Math.max(0, point.getX() - view); x <= point.getX() + view; x++) {
				if (0 <= y && y < 100 && 0 <= x && x < 100
						&& Devil.dist(point.getY(), point.getX(), y, x) <= view) {
					CurrentTotalViewRange[y][x] = true;
				}
			}
		}
		return CurrentTotalViewRange;
	}



}
