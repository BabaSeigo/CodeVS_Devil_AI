package sego0301.Alert;

import java.util.Map;

import sego0301.RuleData.TypeOfUnit;
import sego0301.function.GeneralFunction;
import sego0301.main.Devil;
import sego0301.main.Point;
import sego0301.main.Unit;

public class OpMuraOnSigen extends Alert {

	private Devil devil;

	public OpMuraOnSigen(Point point, TypeOfAlert type,Devil devil) {
		super(point, type);
		this.devil=devil;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public OpMuraOnSigen(Point point, TypeOfAlert type) {
		super(point, type);
		this.devil=devil;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public String getAlertName() {
		// TODO 自動生成されたメソッド・スタブ
		return "敵の資源陣に村発見";
	}

	//敵の村を見つけたのなら、そのアラートをdevilに登録
	public static boolean discoverdOpMuraOnSigen(Point sigen,Devil devil){
		Map<Integer, Unit> opUnitMapOnSigen=GeneralFunction.getUnitsAtPointA(devil.getOpCurrentUnits(), sigen);
		Map<Integer, Unit> opMuraMap=GeneralFunction.abstractTargetTypeUnits(opUnitMapOnSigen, TypeOfUnit.MURA);
		if(opMuraMap.size()>0){
			devil.getAlertList().add(new OpMuraOnSigen(sigen,TypeOfAlert.OpMuraOnSigen));
			return true;
		}else {
			return false;
		}

	}

	//敵の村を見つけたのなら、そのアラートを返してもよい
//	public static Alert getAlertOnOpSigen(Point sigen,Map<Integer, Unit> opUnitMap){
//		Map<Integer, Unit> opUnitMapOnSigen=Function.getUnitsAtPointA(opUnitMap, sigen);
//		Map<Integer, Unit> opMuraMap=Function.abstractTargetTypeUnits(opUnitMapOnSigen, TypeOfUnit.MURA);
//		//Map<Integer, Unit> opWokerMap=Function.abstractTargetTypeUnits(opUnitMap, TypeOfUnit.MURA);
//		if(opMuraMap.size()>0){
//			return true;
//		}else {
//			return false;
//		}
//
//	}

}
