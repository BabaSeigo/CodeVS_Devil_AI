package sego0301.Actions;

import java.util.List;
import java.util.Map;

import sego0301.RuleData.BasicAction;
import sego0301.RuleData.TypeOfUnit;
import sego0301.function.GeneralFunction;
import sego0301.main.Devil;
import sego0301.main.Point;
import sego0301.main.Unit;

public class MakeMuraInSigen extends Actions {

	public MakeMuraInSigen(Devil devil) {
		super(devil);
		// TODO 自動生成されたコンストラクター・スタブ
	}


	@Override
	public void doActions(Map<Integer, Unit> wokerMap) {
			//自分が資源の上にいて、まだ資源に村が立っていなければ村を建てる
			for (Unit woker : wokerMap.values()) {
//				if (devil.getOuterDirector().getWokerInSigen()==null){
//					System.err.println("nullpo");
//				}
//				if(woker==null){
//					System.err.println("wwww");
//				}else {
//					System.err.println(woker.getId());
//				}
				Point sigen=devil.getOuterDirector().getWokerInSigen().get(woker.getId());
				//一応、自分が資源の上にいるのかを調べる
				if(woker.getPoint().equalsPoint(sigen)){
					//資源上のユニット一覧をとる
					Map<Integer, Unit> unitsInSigen=GeneralFunction.getUnitsAtPointA(devil.getMyCurrentUnits(), sigen);
					//上記に村が含まれていなければ、村を建設
					if(GeneralFunction.abstractTargetTypeUnits(unitsInSigen, TypeOfUnit.MURA).size()==0){
						woker.setNextAction(BasicAction.makeMura);
						System.err.println(woker.brieafSelfIntro()+"村建設");
					}
				}

			}
			// TODO 自動生成されたメソッド・スタブ



	}

}
