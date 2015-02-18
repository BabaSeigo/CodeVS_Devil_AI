package sego0301.main;

import java.util.Map;
import java.util.Scanner;

import sego0301.RuleData.TypeOfUnit;
import sego0301.function.CalculatorOfSeen;

public class Inputter {

	public Inputter(Devil devil) {
		this.devil=devil;
		this.scanner = devil.getScanner();
		//	this.isTopLeft = devil.getIsTopLeft();
			// this.unitsMap = devil.getMyCurrentUnits();
			this.calculatorOfSeen = new CalculatorOfSeen(devil);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	private Scanner scanner;
	private Devil devil;
//	private boolean isTopLeft;
	// private Map<Integer, Unit> unitsMap;
	private CalculatorOfSeen calculatorOfSeen;

//	public void buildInputter() {
//		this.scanner = devil.getScanner();
//	//	this.isTopLeft = devil.getIsTopLeft();
//		// this.unitsMap = devil.getMyCurrentUnits();
//		this.directorOfSeen = new DirectorOfSeen();
//	}

	// unitを指定された行まで読む
	public void inputUnits(int numOfUnitsOnInput,
			Map<Integer, Unit> unitsMap) {
		for (int i = 0; i < numOfUnitsOnInput; i++) {
			// 取り合えず、書かれてある情報を読み込む。
			int id = scanner.nextInt();
			int y = scanner.nextInt();
			int x = scanner.nextInt();
			Point point = new Point(x, y);
			int hp = scanner.nextInt();
			TypeOfUnit type = TypeOfUnit.convertNumToType(scanner.nextInt());

			// トップレフトかどうかを判断
			if (devil.getIsTopLeft()) {
				Unit unit = new Unit(id, point, hp, type);
				calculatorOfSeen.calNewSeenIfUnitAdd(unit);
				unitsMap.put(unit.getId(), unit);
			} else {
				Point point_2p = new Point(99 - point.getX(), 99 - point.getY());
				Unit unit = new Unit(id, point_2p, hp, type);
				calculatorOfSeen.calNewSeenIfUnitAdd(unit);
				unitsMap.put(unit.getId(), unit);
			}
		}
	}

	// public void inputOpCurrentUnits() {
	//
	// }

	public void inputMyCastle() {
		int castleId = scanner.nextInt();
		int castleY = scanner.nextInt();
		int castleX = scanner.nextInt();
		Point castlePoint = new Point(castleX, castleY);
		int castleHp = scanner.nextInt();
		TypeOfUnit castleType = TypeOfUnit.convertNumToType(scanner.nextInt());
		Unit myCastle;
		// 一番上が城なのか確認
		if (castleType == TypeOfUnit.CASTLE) {
			// 城が左上と右下のコーナーどちらに近いかで 1P側・2P側を判断
			Unit unit = new Unit(castleId, castlePoint, castleHp, castleType);
			boolean isTopLeft = Devil.dist(unit.getPoint().getY(), unit.getPoint()
					.getX(), 0, 0) < Devil.dist(unit.getPoint().getY(), unit
					.getPoint().getX(), 99, 99);

			devil.setIsTopLeft(isTopLeft);
			// 1ターン目のみエラーに報告
			if(devil.getCurrentTurn()==0){
			System.err.println("MyCastleIsTopLeft=" + isTopLeft+"	"+castleX+"	"+castleY);
			}
			if (isTopLeft) {
				myCastle = unit;
			} else {
				Point point_2p = new Point(99 - castlePoint.getX(),
						99 - castlePoint.getY());
				myCastle = new Unit(castleId, point_2p, castleHp, castleType);
			}

			devil.setMyCastle(myCastle);
			devil.getMyCurrentUnits().put(myCastle.getId(), myCastle);
		} else {
			System.err.println("重大なエラー。城がインプットの一番上ではない。");
		}

	}

	// 城以外の自分のユニットを読み込む
	public void inputMyCurrentUnitsExceptCastle(int numOfMyUnitsOnInput) {
		inputUnits(numOfMyUnitsOnInput - 1, devil.getMyCurrentUnits());

	}

	// 1ターン目以降に使用する、全ユニットを読み込むメソッド
	public void inputAllMyCurrentUnits(int numOfMyUnitsOnInput) {
		devil.getMyCurrentUnits().clear();
		inputMyCastle();
		inputUnits(numOfMyUnitsOnInput-1, devil.getMyCurrentUnits());

	}

	// 敵のユニットを読み込む。今回は数を減らさない
	public void inputEnemyCurrentUnits(int numOfEnemyUnitsOnInput) {
		inputUnits(numOfEnemyUnitsOnInput, devil.getOpCurrentUnits());
	}


	public void inputSigen(){
	//	buildInputter(devil);
		int resNum = scanner.nextInt();
		for (int i = 0; i < resNum; i++) {
			int y = scanner.nextInt();
			int x = scanner.nextInt();
			Point sigenInViewRange=new Point(x, y);
			boolean discoveredNewSigen=true;

			//今までのものに含まれていたら、新しく含めない
			for (Point sigenInList : devil.getSigenList()) {
				if(sigenInList.equalsPoint(sigenInViewRange)){
					discoveredNewSigen=false;
					break;
				}
			}
			//新しいのなら含める
			if(discoveredNewSigen){
				devil.getSigenList().add(sigenInViewRange);
			}
		}
		//ここだと怒られるから、breakは一個上のループ。当たり前か
		//if(true){break;}

	}
	// いらん気がする
	// public void clearInputter() {
	// this.scanner.close();
	// this.isTopLeft = false;
	// // this.unitsMap.clear();
	// }

}
