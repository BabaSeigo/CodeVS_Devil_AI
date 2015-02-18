package sego0301.Strategist;

import sego0301.Actions.MakeWokerFromCastle;
import sego0301.Strategy.RandomWalkStrategy;
import sego0301.Strategy.chokudaiTansakuStrategy;
import sego0301.main.Devil;

public class HeiwaStrategist extends Strategist {

	public HeiwaStrategist(Devil devil) {
		super(devil);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	//どのフェイズにおいても平和に資源ふやす戦略者
	public void selectStrategy() {
	decideSyokiTansaku();
		// TODO 自動生成されたメソッド・スタブ

	}


	//120ターンまでは初期探索ストラテジー
	public void decideSyokiTansaku(){
		if (devil.getCurrentTurn()<120) {
			if(devil==null){
				System.err.println("平和strategistのdevil=null");
			}
			//strategy=new chokudaiTansakuStrategy(devil);
			//strategy=new RandomWalkStrategy(devil);

		}

	}

	@Override
	public String getMyName() {
		return "平和ストラテジスト";
	}
}
