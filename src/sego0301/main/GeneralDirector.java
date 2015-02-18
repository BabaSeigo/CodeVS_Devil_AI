package sego0301.main;

import sego0301.Actions.OldActions;
import sego0301.Strategist.DevilStrategist;
import sego0301.Strategist.HeiwaStrategist;
import sego0301.Strategist.Strategist;
import sego0301.Strategist.TestStrategist;
import sego0301.Strategy.Strategy;

public class GeneralDirector {

	private Devil devil;

	// SubDirector を結局使っていない
	// private SubDirector castleDirectory;
	// private SubDirector workerDirectory;
	// private SubDirector muraDirectory;
	// private SubDirector kyotenDirectory;
	// private SubDirector battlerDirectory;

	public GeneralDirector(Devil devil) {
		this.devil = devil;
		// updateSubdirector();
	}

	// サブディレクターを通じて、各々のユニット達に命令を指示
	public void direct() {

		// ストラテジストを生成し、ストラテジーを選択
//		Strategist strategist = new TestStrategist(devil);
		Strategist strategist = new DevilStrategist(devil);
		System.err.print(devil.getCurrentTurn() + "turn");
		if (devil.getCurrentTurn() == 0) {
			strategist.selfIntroduction();
		}

		strategist.selectStrategy();
		// ストラテジーを取り出し、アクションマップを取りだす
		for (Strategy strategy : strategist.returnStrategy()) {
			if (strategy != null) {
				strategy.doStrategy();
			}
		}

	}

}
