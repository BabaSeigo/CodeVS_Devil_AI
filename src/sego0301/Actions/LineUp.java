package sego0301.Actions;

import java.util.Map;

import sego0301.RuleData.BasicAction;
import sego0301.function.GeneralFunction;
import sego0301.function.FunctionAboutTerritory;
import sego0301.function.FunctionAboutScore;
import sego0301.main.Devil;
import sego0301.main.Point;
import sego0301.main.Unit;

public class LineUp extends Actions {

	public LineUp(Devil devil) {
		super(devil);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void doActions(Map<Integer, Unit> battlerMap) {
		// TODO 自動生成されたメソッド・スタブ
		BasicAction[] move = { BasicAction.moveToRight, BasicAction.moveToDown,
				BasicAction.moveToUp, BasicAction.moveToLeft, };

		for (Unit unit : battlerMap.values()) {
			int maxScore = -10000;
			int a = 0;
			for (int i = 0; i < move.length; i++) {
				Unit assumptionUnit = FunctionAboutScore.getAssumptionUnit(unit,
						move[i]);
				int score = calScore(assumptionUnit);
				if (score > maxScore) {
					a = i;
					maxScore = score;
				}
			}
			unit.setNextAction(move[a]);
		}
	}

	public int calScore(Unit unit) {
		int score = 0;
		Point opCastle = devil.getOpCastle().getPoint();
		int dis = GeneralFunction.returnAbsoluteDistanceFromAtoB(opCastle,
				unit.getPoint());
		if (dis == 11) {
			score++;
		} else if (dis == 12) {
		} else {
			score+=-dis;
		}
		Map<Integer, Unit> unitMap=GeneralFunction.getUnitsAtPointA(devil.getMyCurrentUnits(), unit.getPoint());
		if(unitMap.size()>10){
			score+=-unitMap.size()*2;
		}else if(unitMap.size()==9){
			score+=10000000;
		}else if (unitMap.size()<9) {
			score+=unitMap.size()*2;
		}

		return score;
	}

}
