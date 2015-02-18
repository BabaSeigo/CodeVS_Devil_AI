package sego0301.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sego0301.Alert.Alert;
import sego0301.Alert.TypeOfAlert;
import sego0301.RuleData.BasicAction;
import sego0301.RuleData.JobType;
import sego0301.RuleData.TypeOfBasicAction;
import sego0301.RuleData.TypeOfUnit;
import sego0301.function.GeneralFunction;
import sego0301.function.FunctionAboutScore;

//主に外部で初期の村人の行動と資源を目指すユニットを制御するディレクター
public class OuterDirector {

	private Devil devil;
	// 各々の行動をIDと目指す場所で管理
	private Map<Integer, Point> purposePointMap = new HashMap<Integer, Point>();
	// private String[] firstDirectionID =new String[5];
	private List<Point> purposePointList = new ArrayList<Point>();
	// private Map<Integer, Unit> searchingWoker = new HashMap<Integer, Unit>();
	private Map<Integer, Point> wokerInSigen = new HashMap<Integer, Point>();
	private Map<Integer, Point> wokerMovingToSigen = new HashMap<Integer, Point>();

	// 特殊な状況で使う。村を建設したら、資源が近かったときなど
	private boolean discoverdSigen = false;

	// 最初にLUのどちらかを選んだユニットが存在していたら
	private boolean unitLExist;
	private boolean unitUExist;
	private Map<String, Integer> unitLUMap = new HashMap<String, Integer>();
	private Map<Integer, Unit> wokerLeader5s = new HashMap<Integer, Unit>();

	// private Unit unitL;
	// private Unit unitU;
	// private Map<Integer, Unit> unitLUMap=new HashMap<Integer, Unit>();

	public OuterDirector(Devil devil) {
		this.devil = devil;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public Map<Integer, Unit> getWokerLeader5s() {
		return wokerLeader5s;
	}

	public void setWokerLeader5s(Map<Integer, Unit> wokerLeader5s) {
		this.wokerLeader5s = wokerLeader5s;
	}

	public boolean isDiscoverdSigen() {
		return discoverdSigen;
	}

	public void setDiscoverdSigen(boolean discoverdSigen) {
		this.discoverdSigen = discoverdSigen;
	}

	public Map<Integer, Point> getWokerInSigen() {
		return wokerInSigen;
	}

	public void setWokerInSigen(Map<Integer, Point> wokerInSigen) {
		this.wokerInSigen = wokerInSigen;
	}

	public Map<Integer, Point> getWokerMovingToSigen() {
		return wokerMovingToSigen;
	}

	public void setWokerMovingToSigen(Map<Integer, Point> wokerMovingToSigen) {
		this.wokerMovingToSigen = wokerMovingToSigen;
	}

	// 資源移動中のワーカが殺されていないかを、デビルに聞いてチェック
	private void renewWokerMovingToSigen() {
		checkUnitInMapAExistInMapB(wokerMovingToSigen,
				devil.getMyCurrentUnits());
	}

	// 資源滞在中のワーカが殺されていないかを、デビルに聞いてチェック
	private void renewWokerInSigen() {
		checkUnitInMapAExistInMapB(wokerInSigen, devil.getMyCurrentUnits());

		// System.err.println(devil.getMyCurrentUnits().size());
		for (Integer iterable_element : wokerInSigen.keySet()) {
			if (devil.getMyCurrentUnits().get(iterable_element) == null) {
				System.err.println("nullInRenew" + iterable_element);
			}
		}

		// 資源滞在用のワーカができたら、それも加える
		List<Alert> alertList = Alert.abstractAlert(devil.getAlertList(),
				TypeOfAlert.WokerInSigenIsMade);
		if (alertList.size() > 0) {
			for (Alert alert : alertList) {
				Point sigen = alert.getPoint();
				// 村も含んでいる
				Map<Integer, Unit> unitMap = GeneralFunction.getUnitsAtPointA(
						devil.getMyCurrentUnits(), sigen);
				// そのポイントの村人のみ
				Map<Integer, Unit> wokers = GeneralFunction.abstractTargetTypeUnits(
						unitMap, TypeOfUnit.WOKER);
				for (Integer id : wokers.keySet()) {

					Unit woker = wokers.get(id);

					if (woker == null) {
						System.err.println(" null2" + id);
					}

					wokerInSigen.put(id, sigen);
					woker.setJobtype(JobType.stayInSigen);

					// if(!woker.equalsJobType(JobType.moveToSigen)){
					// wokerInSigen.put(id, sigen);
					// woker.setJobtype(JobType.stayInSigen);
					// }
				}
				Alert.removeAlert(devil.getAlertList(),
						TypeOfAlert.WokerInSigenIsMade, sigen);
			}
		}
		checkWokerInSigenJobType();

	}

	// リーダー5sが死んでいないかチェック
	public void renewLeader5s() {
		Map<Integer, Unit> newLeader = new HashMap<Integer, Unit>();

		for (Integer id : wokerLeader5s.keySet()) {
			if (devil.getMyCurrentUnits().containsKey(id)) {
				newLeader.put(id, devil.getMyCurrentUnits().get(id));
			}
		}


		// 村人で、資源にいないもの
		Map<Integer, Unit> wokerMap = GeneralFunction.abstractTargetTypeUnits(
				devil.getMyCurrentUnits(), TypeOfUnit.WOKER);
		if (wokerMap.size() > 0) {
			for (Unit unit : wokerMap.values()) {
				boolean inSigen = false;
				for (Point sigen : devil.getSigenList()) {
					// 資源にいるならリーダでない
					if (unit.getPoint().equalsPoint(sigen)) {
						inSigen = true;
					}
				}

				if (!inSigen) {
					//newLeaderも含んでいないのならば
					if (!newLeader.containsKey(unit.getId())) {
						newLeader.put(unit.getId(), unit);
					}
				}
			}
		}
		//チェック
//		for(Unit unit:newLeader.values()){
//			System.err.println("leader"+unit.getId());
//		}
		wokerLeader5s.clear();
		wokerLeader5s.putAll(newLeader);
		//
		// wokerLeader5s=newLeader;
	}

	// mapAのユニットがMapBに存在していなかったらAから取り除く
	public void checkUnitInMapAExistInMapB(Map<Integer, Point> pointMapA,
			Map<Integer, Unit> unitMapB) {
		// Bがからっぽだったら、Aも空っぽにする
		Map<Integer, Point> newPointMapA = new HashMap<Integer, Point>();
		if (unitMapB.size() > 0) {
			// Aが空っぽでないか
			if (pointMapA.size() > 0) {
				for (Integer key : pointMapA.keySet()) {
					if (unitMapB.containsKey(key)) {
						if (unitMapB.get(key) == null) {
							System.err.println("nullの癖に含んでいる");
						}
						newPointMapA.put(key, pointMapA.get(key));

					}
				}
			}
		}
		// pointMapA.clear();

		// pointMapA = newPointMapA;
		pointMapA.clear();

		pointMapA.putAll(newPointMapA);
		for (Integer iterable_element : pointMapA.keySet()) {
			if (unitMapB.get(iterable_element) == null) {
				System.err.println("nullinCheck" + iterable_element);
			}
		}

	}

	private void checkWokerInSigenJobType() {
		Map<Integer, Point> newWokerInSigenMap = new HashMap<Integer, Point>();
		for (Integer key : wokerInSigen.keySet()) {
			Unit unit = devil.getMyCurrentUnits().get(key);

			// if(!(unit==null)){
			if (unit == null) {
				System.err.println(" null3" + key);
			}
			if (unit.equalsJobType(JobType.stayInSigen)) {
				newWokerInSigenMap.put(key, wokerInSigen.get(key));
			}
			// }
		}
		wokerInSigen = newWokerInSigenMap;
	}

	// //探索中のワーカが殺されていないかをデビルに聞いてチェック
	// public void renewSearchingWoker() {
	// for (Integer key : searchingWoker.keySet()) {
	// // デビルのリストに既に含まれていなければ、pointMapAから取り除く。
	// if (!devil.getMyCurrentUnits().containsKey(key)) {
	// searchingWoker.remove(key);
	// }
	// }
	// }

	public Map<Integer, Unit> renewSearchingWoker() {
		Map<Integer, Unit> searchingWokerMap = GeneralFunction
				.abstractTargetTypeUnits(devil.getMyCurrentUnits(),
						TypeOfUnit.WOKER);

		// Map<Integer, Unit> newSearchingWokerMap=new HashMap<Integer, Unit>();
		// 既に移動
		for (Integer key : wokerMovingToSigen.keySet()) {
			if (devil.getMyCurrentUnits().containsKey(key)) {
				searchingWokerMap.remove(key);
			}
		}

		for (Integer key : wokerInSigen.keySet()) {
			if (devil.getMyCurrentUnits().containsKey(key)) {
				searchingWokerMap.remove(key);
			}
		}
		return searchingWokerMap;
	}

	// アウターが管理しているワーカリストを更新
	public void renewAllWokerList() {

		renewWokerMovingToSigen();
		renewWokerInSigen();
		//
		// System.err.println("資源");
		// for (Integer iterable_element : wokerInSigen.keySet()) {
		// System.err.println(iterable_element);
		// }
		// System.err.println("全部");
		// for (Integer iterable_element : devil.getMyCurrentUnits().keySet()) {
		// System.err.println(iterable_element);
		// }

		removeNullPointFromMap(wokerInSigen);
		removeNullPointFromMap(wokerMovingToSigen);

	}

	// 資源滞在中のワーカなどにヌルがあったら取り除く
	public static void removeNullPointFromMap(Map<Integer, Point> unitMap) {
		Map<Integer, Point> newUnitMap = new HashMap<Integer, Point>();
		for (Integer key : unitMap.keySet()) {
			if (!(unitMap.get(key) == null)) {
				newUnitMap.put(key, unitMap.get(key));
			}
		}
		unitMap = newUnitMap;
	}

	// 資源移動中のワーカを移動させる
	public void moveWokerMovingToSigen() {
		for (Integer key : wokerMovingToSigen.keySet()) {
			Unit woker = devil.getMyCurrentUnits().get(key);
			// System.err.println("dd");
			GeneralFunction.setMoveUnitToPoint(woker, this.wokerMovingToSigen.get(key));
			System.err.println(wokerMovingToSigen.get(key).getX() + " "
					+ wokerMovingToSigen.get(key).getX() + "には次が移動"
					+ woker.getId());

		}
	}

	public void moveWokerToPurposePoint(Map<Integer, Unit> woker5s) {
		for (Integer key : purposePointMap.keySet()) {
			// System.err.println(key + "は" + purposePointMap.get(key).getId());
			GeneralFunction.setMoveUnitToPoint(woker5s.get(key),
					purposePointMap.get(key));
		}
	}

	// purposePointとwokerを関連付け.LUユニットも
	public void setPurposePointMap(Map<Integer, Unit> woker5s) {
		int i = 0;
		for (Unit unit : woker5s.values()) {
			Point point = purposePointList.get(i);
			purposePointMap.put(unit.getId(), point);
			if (point.getId() == "L") {
				unitLUMap.put("L", unit.getId());
			}
			if (point.getId() == "U") {
				unitLUMap.put("U", unit.getId());
			}
			i++;
		}
	}

	// 資源へ移動中のワーカを資源にいるワーカに変える
	public void transferWokerMovingToInSigen() {
		Map<Integer, Point> newWokerMovingToSigen = new HashMap<Integer, Point>();
		for (Integer key : wokerMovingToSigen.keySet()) {
			Point sigen = wokerMovingToSigen.get(key);
			if (devil.getMyCurrentUnits().containsKey(key)) {
				Unit woker = devil.getMyCurrentUnits().get(key);

				// 移動中のワーカがたどり着いたら移籍
				if (woker.getPoint().equalsPoint(sigen)) {
					wokerInSigen.put(key, sigen);
					Alert.removeAlert(devil.getAlertList(),
							TypeOfAlert.wokerMovinToSigen, sigen);
					System.err.println(woker.getId() + "は到着" + sigen.getX()
							+ " " + sigen.getY());
					// たどり着いていなければ、引き続き移動中ワーカ
				} else {
					newWokerMovingToSigen.put(key, sigen);
				}
			}
		}
		wokerMovingToSigen = newWokerMovingToSigen;
	}

	// 優先度の高い順に移動先を登録。
	public void setFirstPurposePoint() {
		// 城の位置によって判断
		int x = devil.getMyCastle().getX();
		int y = devil.getMyCastle().getY();
		int additional = 0;

		// デフォルトは以下のみ
		purposePointList.add(new Point(6 + x, 0 + y, "R"));
		purposePointList.add(new Point(3 + x, 3 + y, "RD"));
		purposePointList.add(new Point(0 + x, 6 + y, "D"));

		// spaceあるなら以下を追加
		if (x > 9) {
			purposePointList.add(new Point(-6 + x, 0 + y, "L"));
			additional++;
			unitLExist = true;
		}
		if (y > 9) {
			purposePointList.add(new Point(0 + x, -6 + y, "U"));
			additional++;
			unitUExist = true;
		}
		// やや不利な状況。
		if (4 == x) {
			purposePointList.add(new Point(5, y + (9 - x), "5y"));
		} else if (4 < x) {
			purposePointList.add(new Point(4, y + (10 - x), "4y"));
		} else {
			purposePointList.add(new Point(4, y + (2 + x), "4y"));
		}

		if (4 == y) {
			purposePointList.add(new Point(x + (9 - y), 5, "x5"));
		} else if (4 < y) {
			purposePointList.add(new Point(x + (10 - y), 4, "x4"));
		} else {
			purposePointList.add(new Point(x + (2 + y), 4, "x4"));
		}

	}

	// 最初に、LorUを取ったものは方向を決める。LはUを取ってはならない。UはLを取ってはならない。
	public void moveUorLwoker(Map<Integer, Unit> wokerMap) {

		// LはUだめ
		if (unitLExist) {
			int id = unitLUMap.get("L");
			if (wokerMap.containsKey(id)) {
				Unit unitL = wokerMap.get(id);
				if (unitL.getNextAction() == BasicAction.moveToUp) {
					// System.err.println("Lは上禁止");
					unitL.setNextAction(BasicAction.moveToDown);
				}
			}
		}
		// LはUだめ
		if (unitUExist) {
			int id = unitLUMap.get("U");
			if (wokerMap.containsKey(id)) {
				// System.err.println("dd");
				Unit unitU = wokerMap.get(id);
				if (unitU.getNextAction() == BasicAction.moveToLeft) {
					// System.err.println("Uは左禁止");
					unitU.setNextAction(BasicAction.moveToRight);
				}
			}
		}
	}

	public void removeConcide(Map<Integer, Unit> woker5s) {
		List<Unit> unitList = FunctionAboutScore.convertUnitMapToUnitList(woker5s);
		// もしユニットIとJが重なっていたら、Iの方がストップ。
		for (int i = 0; i < unitList.size(); i++) {
			Unit unitI = unitList.get(i);
			for (int j = i + 1; j < unitList.size(); j++) {
				Unit unitJ = unitList.get(j);
				if (unitI.getPoint().equalsPoint(unitJ.getPoint())) {
					unitI.setFree();
				}
			}
		}
	}

	public void clearAll() {
		purposePointList.clear();
		purposePointMap.clear();
		wokerInSigen.clear();
		wokerMovingToSigen.clear();
		wokerInSigen.clear();
		unitLExist = false;
		unitUExist = false;
		unitLUMap.clear();
		wokerLeader5s.clear();
		discoverdSigen = false;
	}

	public void freeWokerInSigen() {

		// System.err.println("資源");
		// for (Integer iterable_element : wokerInSigen.keySet()) {
		// System.err.println(iterable_element);
		// }
		// System.err.println("全部");
		// for (Integer iterable_element : devil.getMyCurrentUnits().keySet()) {
		// System.err.println(iterable_element);
		// }

		for (Integer key : wokerInSigen.keySet()) {
			// System.err.println(key);
			Unit woker = devil.getMyCurrentUnits().get(key);
			if (!(woker == null)) {
				if (!(woker.getNextAction() == null)) {
					if (woker.getNextAction().getTypeOfBasicAction() == TypeOfBasicAction.move) {
						woker.setFree();
					}
				}
			}
		}
	}

}
