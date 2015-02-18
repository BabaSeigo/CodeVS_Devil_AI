package sego0301.Strategist;

import java.util.ArrayList;
import java.util.List;

import sego0301.Strategy.DefaulStrategy;
import sego0301.Strategy.Strategy;
import sego0301.main.Devil;

public abstract class Strategist {

	 protected Devil devil;
	 protected List<Strategy> strategyList=new ArrayList<Strategy>();
	//private GeneralDirector generalDirector;
	public Strategist(Devil devil) {
		if(devil==null){
			System.err.println("StrategistはDevilを把握していません");
		}
		this.devil =devil;
		//generalDirector=devil.getGeneralDirector();
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * @param args
	 */
	//どうやって戦略を決めるか。これは状況判断を考える必要がある
	public abstract void selectStrategy();

	public abstract String getMyName();

	public void selfIntroduction(){
		System.err.println(getMyName());
	}

	public List<Strategy> returnStrategy(){
		if(strategyList.isEmpty()){
			System.err.println("strategyが選択されていません");
			strategyList.add(new DefaulStrategy(devil));
			return strategyList;
		}else{
			return strategyList;
		}
	}
}
