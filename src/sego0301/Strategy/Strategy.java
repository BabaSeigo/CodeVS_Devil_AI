package sego0301.Strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sego0301.RuleData.BasicAction;
import sego0301.RuleData.TypeOfUnit;
import sego0301.function.GeneralFunction;
import sego0301.function.FunctionAboutScore;
import sego0301.main.Devil;
import sego0301.main.Point;
import sego0301.main.Unit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sego0301.Actions.OldActions;
import sego0301.Actions.DefaultAction;
import sego0301.main.Devil;
import sego0301.main.GeneralDirector;

public abstract class Strategy {

	protected GeneralDirector generalDirector;
	protected Devil devil;
	//protected List<Actions> actionsList = new ArrayList<Actions>();

	// public Strategy(GeneralDirector generalDirector){
	// this.generalDirector=generalDirector;
	// }

	public Strategy(Devil devil) {
		this.devil = devil;
		if(devil==null){
			System.err.println(getName()+"のDevilがnull");
		}
		if(devil.getGeneralDirector()==null){
			System.err.println("DevilのGeneralDirectorが次に代入不可");
		}
		this.generalDirector = devil.getGeneralDirector();
		if(this.generalDirector==null){
			System.err.println("StrategyのGeneralDirectorが代入されていません");
		}
		//System.err.println(this.getName());
		//doActions();
	}

	// private Strategy(){};

	public abstract void doStrategy();


	public abstract String getName();

//	public List<Actions> returnActions() {
//		if (actionsList == null) {
//			System.err.println("アクションが指定されていません");
//			//actionsMap.put(generalDirector.getWorkerDirectory(), new DefaultAction());
//			actionsList.add( new DefaultAction());
//			return actionsList;
//
//		} else {
//
//			return actionsList;
//		}
//	}

}
