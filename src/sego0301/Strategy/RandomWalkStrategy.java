package sego0301.Strategy;

import java.util.Map;

import sego0301.Actions.RandomWalk;
import sego0301.RuleData.TypeOfUnit;
import sego0301.function.GeneralFunction;
import sego0301.main.Devil;
import sego0301.main.Unit;

public class RandomWalkStrategy extends Strategy {

	public RandomWalkStrategy(Devil devil) {
		super(devil);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void doStrategy() {
		Map<Integer, Unit> wokerMap=GeneralFunction.abstractTargetTypeUnits(devil.getMyCurrentUnits(), TypeOfUnit.WOKER);
		new RandomWalk().doActions(wokerMap);

		//actionsMap.put(, value)

		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "ランダムに動くよ";
	}

}
