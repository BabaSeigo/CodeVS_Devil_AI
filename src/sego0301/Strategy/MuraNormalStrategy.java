package sego0301.Strategy;

import java.util.Map;

import sego0301.Actions.MakeWokerInSigen;
import sego0301.Alert.Alert;
import sego0301.Alert.TypeOfAlert;
import sego0301.Alert.WokerMovingToSigen;
import sego0301.RuleData.TypeOfUnit;
import sego0301.function.GeneralFunction;
import sego0301.main.Devil;
import sego0301.main.LogMaker;
import sego0301.main.Point;
import sego0301.main.Unit;

public class MuraNormalStrategy extends Strategy {

	public MuraNormalStrategy(Devil devil) {
		super(devil);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void doStrategy() {
		Map<Integer, Unit> muraMap = GeneralFunction.abstractTargetTypeUnits(
				devil.getMyCurrentUnits(), TypeOfUnit.MURA);

		for (Unit mura : muraMap.values()) {
			// そもそも自分が資源の上にいなければ何もしない。

				// 移動中のワーカがいる場合、村人を作らない
				int movingToSigenSize = Alert.abstractAlert(
						devil.getAlertList(), TypeOfAlert.wokerMovinToSigen)
						.size();
				if (movingToSigenSize > 0
						&& (devil.getCurrentResource() < 40 + movingToSigenSize * 2)) {
				//	System.err.println("資源移動中村人" + movingToSigenSize
					//		+ "人いるため村人を作らない");
				} else {
					MakeWokerInSigen makeW = new MakeWokerInSigen(devil);
					makeW.doActions(muraMap);
//					LogMaker log=LogMaker.getInstance();
//					log.addLog("muraNoramalStrategyの中"+mura.getId());
				}

		}

		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "村の戦略";
	}

	// そもそも村が資源の上にいるのか
	public static boolean isInSigen(Unit mura,Devil devil) {
		boolean inSigen = false;
		for (Point sigen : devil.getSigenList()) {
			// 1つでも自分の上に資源があったら
			if (mura.getPoint().equalsPoint(sigen)) {
				inSigen = true;
				break;
			}
		}

		return inSigen;
	}

	// 村の視野内に資源があり、視野内に他にワーカがいなければ

}
