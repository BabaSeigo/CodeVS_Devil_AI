package sego0301.Alert;

import java.util.Map;

import sego0301.RuleData.TypeOfUnit;
import sego0301.function.GeneralFunction;
import sego0301.main.Devil;
import sego0301.main.Point;
import sego0301.main.Unit;

public class OpWokerOnSigen extends Alert {

	private Devil devil;

	public OpWokerOnSigen(Point point, TypeOfAlert type,Devil devil) {
		super(point, type);
		this.devil=devil;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public OpWokerOnSigen(Point point, TypeOfAlert type) {
		super(point, type);
		this.devil=devil;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public String getAlertName() {
		// TODO 自動生成されたメソッド・スタブ
		return "敵の資源陣にワーカ発見";
	}

	//敵の村を見つけたのなら、そのアラートをdevilに登録
	public static boolean discoverdOpWokerOnSigen(Point sigen,Devil devil){
		System.err.println(devil.getOpCurrentUnits().size());

		Map<Integer, Unit> opUnitMapOnSigen=GeneralFunction.getUnitsAtPointA(devil.getOpCurrentUnits(), sigen);
		System.err.println(opUnitMapOnSigen.size());
		Map<Integer, Unit> opWokerMap=GeneralFunction.abstractTargetTypeUnits(opUnitMapOnSigen, TypeOfUnit.WOKER);
		if(opWokerMap.size()>0){
			devil.getAlertList().add(new OpWokerOnSigen(sigen,TypeOfAlert.OpWokerOnSIgen));
			return true;
		}else {
			return false;
		}

	}



}
