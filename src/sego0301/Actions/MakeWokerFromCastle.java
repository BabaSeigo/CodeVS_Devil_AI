package sego0301.Actions;

import java.util.Map;

import sego0301.RuleData.BasicAction;
import sego0301.main.Devil;
import sego0301.main.Unit;

public class MakeWokerFromCastle implements OldActions {

	private Devil devil;
	public MakeWokerFromCastle(Devil devil) {
		this.devil=devil;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void doActions(Map<Integer, Unit> units) {
		System.err.println("MakeWokerFromCastle内");
		BasicAction makeWoker=BasicAction.makeWoker;
		devil.getMyCastle().setNextAction(makeWoker);
		// TODO 自動生成されたメソッド・スタブ

	}

}
