package sego0301.main;

import java.util.HashMap;
import java.util.Map;

import sego0301.Actions.OldActions;
import sego0301.Strategist.HeiwaStrategist;
import sego0301.Strategist.Strategist;
import sego0301.Strategist.TestStrategist;
import sego0301.Strategy.Strategy;

public class TestGeneralDirector {

	private Devil devil;
//	private SubDirector castleDirectory;
//	private SubDirector workerDirectory;
//	private SubDirector muraDirectory;
//	private SubDirector kyotenDirectory;
//	private SubDirector battlerDirectory;

//	private OldActions castleActions;
//	private OldActions wokerActions;
//	private OldActions muraActions;
//	private OldActions kyotenActions;
//	private OldActions battlerActions;



	public TestGeneralDirector(Devil devil) {
		this.devil = devil;
		//updateSubdirector();
	}

	//サブディレクターを通じて、各々のユニット達に命令を指示
	public void direct() {
//		clearActions();
	//	updateSubdirector();

		//ストラテジストを生成し、ストラテジーを選択
		//Strategist strategist=new HeiwaStrategist(devil);
		Strategist strategist=new TestStrategist(devil);
		System.err.print(devil.getCurrentTurn()+"turn");
		if(devil.getCurrentTurn()==0){
			strategist.selfIntroduction();
		}
		strategist.selectStrategy();

		//ストラテジーを取り出し、アクションマップを取りだす

		for (Strategy strategy : strategist.returnStrategy()) {
			if(strategy!=null){
			strategy.doStrategy();}
		}
		//Map<SubDirector, Actions> actionsMap=strategy.returnActions();

		//下記の0125コメントアウト参照。キャッスルなのは気にしない。
//		castleActions=actionsMap.get(castleDirectory);
//		castleDirectory.doActions(castleActions);

		//0125別にディレクター毎に分ける必要なかったのでは？というわけでコメントアウト。
		//各々のディレクターへのアクションmapを作成
//		castleActions=actionsMap.get(castleDirectory);
//		wokerActions=actionsMap.get(workerDirectory);
//		muraActions=actionsMap.get(muraDirectory);
//		kyotenActions=actionsMap.get(kyotenDirectory);
//		battlerActions=actionsMap.get(battlerDirectory);
//
//		//各々のディレクターがアクションをメンバーに指示
//		castleDirectory.doActions(castleActions);
//		workerDirectory.doActions(wokerActions);
//		muraDirectory.doActions(muraActions);
//		kyotenDirectory.doActions(kyotenActions);
//		battlerDirectory.doActions(battlerActions);
	}

//	private void clearActions() {
//		castleActions = null;
//		wokerActions = null;
//		muraActions = null;
//		kyotenActions = null;
//		battlerActions = null;
//	}

//	private void updateSubdirector() {
//		Map<Integer, Unit> units = devil.getMyCurrentUnits();
//		castleDirectory = new SubDirector(units);
//		workerDirectory = new SubDirector(units);
//		muraDirectory = new SubDirector(units);
//		kyotenDirectory = new SubDirector(units);
//		battlerDirectory = new SubDirector(units);
//	}

}
