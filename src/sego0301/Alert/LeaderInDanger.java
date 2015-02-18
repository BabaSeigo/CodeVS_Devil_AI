package sego0301.Alert;

import java.util.Map;

import sego0301.RuleData.BasicAction;
import sego0301.RuleData.TypeOfUnit;
import sego0301.function.GeneralFunction;
import sego0301.function.FunctionAboutTerritory;
import sego0301.main.Devil;
import sego0301.main.Point;
import sego0301.main.Unit;

public class LeaderInDanger extends Alert {

	public LeaderInDanger(Point point, TypeOfAlert type) {
		super(point, type);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public String getAlertName() {
		// TODO 自動生成されたメソッド・スタブ
		return "リーダーの近くに敵";
	}

	public static boolean leaderIsDanger(Devil devil,Unit leader){
		Map<Integer, Unit> opMap =GeneralFunction.abstractUnitMapInMyViewTerritory(devil.getOpCurrentUnits(), leader);

		int nokoriTairyoku=leader.getHp();
		for(Unit op :opMap.values()){
			nokoriTairyoku+=-leader.getType().receiveDamage(op.getType());
		}
		if(nokoriTairyoku<=100){
			System.err.println(leader.brieafSelfIntro()+"危ない");
			devil.getAlertList().add(new LeaderInDanger(leader.getPoint(), TypeOfAlert.LeaderInDanger));
			return true;
		}else {
			return false;
		}

	}

	public static void setLeaderActionInDanger(Devil devil,Unit leader){
		Map<Integer, Unit> unitMap=FunctionAboutTerritory.abstractUnitMapInView(devil.getMyCurrentUnits(), leader.getPoint(), 100);
		Map<Integer, Unit> muraMap=GeneralFunction.abstractTargetTypeUnits(unitMap, TypeOfUnit.MURA);
		if(muraMap.size()==0){
			leader.setNextAction(BasicAction.makeMura);
			leader.setActionLock(true);
		}

	}

}
