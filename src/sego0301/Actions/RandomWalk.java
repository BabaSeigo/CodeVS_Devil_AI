package sego0301.Actions;
import java.util.Map;

import sego0301.RuleData.BasicAction;
import sego0301.main.Unit;


public class RandomWalk implements OldActions {

	public RandomWalk() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void doActions(Map<Integer, Unit> units) {
		for (Unit unit : units.values()) {
			double random=Math.random();
			if(random<0.25){
				unit.setNextAction(BasicAction.moveToUp);
			}else if (random<0.5) {
				unit.setNextAction(BasicAction.moveToRight);
			}else if (random<0.75) {
				unit.setNextAction(BasicAction.moveToLeft);
			}else {
				unit.setNextAction(BasicAction.moveToDown);
			}
		}
		// TODO 自動生成されたメソッド・スタブ

	}

}
