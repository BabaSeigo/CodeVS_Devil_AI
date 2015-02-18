package sego0301.Actions;

import java.util.Map;

import sego0301.main.Devil;
import sego0301.main.Unit;

public abstract class Actions {

	protected Devil devil;

	public Actions(Devil devil) {
		this.devil=devil;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public abstract void doActions(Map<Integer, Unit> unitMap);

}



