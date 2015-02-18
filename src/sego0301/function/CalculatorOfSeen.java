package sego0301.function;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import sego0301.main.Devil;
import sego0301.main.Unit;

public class CalculatorOfSeen {

	private Devil devil;

	public CalculatorOfSeen(Devil devil) {
		this.devil=devil;
	}

	// private boolean[][] seen;

	/**最初に作ったへぼい視野計算システム*/
	public void calNewSeenIfUnitAdd(Unit unit) {
		int view = unit.getViewRange();
		boolean[][] seen = devil.getSeen();

		for (int y = unit.getY() - view; y <= unit.getY() + view; y++) {
			for (int x = unit.getX() - view; x <= unit.getX() + view; x++) {
				if (0 <= y && y < 100 && 0 <= x && x < 100
						&& Devil.dist(unit.getY(), unit.getX(), y, x) <= view) {
					seen[y][x] = true;
				}
			}
		}
		devil.setSeen(seen);
	}

	public void calSeen(List<Unit> unitList) {
		boolean[][] seen = devil.getSeen();
		for (Unit unit : unitList) {
			calNewSeenIfUnitAdd(unit);
		}
		devil.setSeen(seen);

	}

}
