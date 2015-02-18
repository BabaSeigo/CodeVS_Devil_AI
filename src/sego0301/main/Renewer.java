package sego0301.main;

import java.util.Map;
import java.util.Set;

public class Renewer {

	private Devil devil;

	public Renewer(Devil devil) {
		this.devil = devil;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * @param args
	 */

	//これは今の資源リストをクリアする必要はない。とっておく
	private void renewSigenList() {
		devil.getOldSigenList().clear();
		for (Point sigen : devil.getSigenList()) {
			devil.getOldSigenList().add(sigen);
		}
	}

	private void renewUnitList(Map<Integer, Unit> oldUnits,
			Map<Integer, Unit> currentUnits) {
		oldUnits.clear();
		Set<Integer> keyset = currentUnits.keySet();
		for (int key : keyset) {
			Unit unit = currentUnits.get(key);
			oldUnits.put(key, unit);
		}
		currentUnits.clear();
	}

	private void renewMyUnitList() {
		renewUnitList(devil.getMyOldUnits(), devil.getMyCurrentUnits());
	}

	private void renewEnemyUnitList() {
		renewUnitList(devil.getOpOldUnits(), devil.getOpCurrentUnits());
	}

	public void renewAllList() {
		renewMyUnitList();
		renewEnemyUnitList();
		renewSigenList();
	}
}
