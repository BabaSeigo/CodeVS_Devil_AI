package sego0301.Actions;

import java.util.List;
import java.util.Map;

import sego0301.Alert.Alert;
import sego0301.Alert.OpNearMura;
import sego0301.Alert.TypeOfAlert;
import sego0301.Alert.WokerInSigenIsMade;
import sego0301.RuleData.BasicAction;
import sego0301.RuleData.TypeOfUnit;
import sego0301.Strategy.MuraNormalStrategy;
import sego0301.function.GeneralFunction;
import sego0301.main.Devil;
import sego0301.main.LogMaker;
import sego0301.main.Unit;

public class MakeWokerInSigen extends Actions {

	public MakeWokerInSigen(Devil devil) {
		super(devil);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void doActions(Map<Integer, Unit> muraMap) {

	LogMaker log =LogMaker.getInstance();
		// リーダーが危険になって建てた場合は別
		List<Alert> leaderInDanger = Alert.abstractAlert(devil.getAlertList(),
				TypeOfAlert.LeaderInDanger);
		for (Unit mura : muraMap.values()) {
			// そもそも自分が資源の上にいないと行動しない
			if (MuraNormalStrategy.isInSigen(mura, devil)) {
				// 自分の視野に敵がいたら何もしません。
				Map<Integer, Unit> opMap = GeneralFunction
						.abstractUnitMapInMyViewTerritory(
								devil.getOpCurrentUnits(), mura);
				if (opMap.size() > 0) {
				//	System.err.println("敵いる！");
					devil.getAlertList().add(
							new OpNearMura(mura.getPoint(),
									TypeOfAlert.OpNearMura, opMap, devil
											.getCurrentTurn()));
				} else {
					Map<Integer, Unit> unitMap = GeneralFunction.getUnitsAtPointA(
							devil.getMyCurrentUnits(), mura.getPoint());
					Map<Integer, Unit> wokerInSigen = GeneralFunction
							.abstractTargetTypeUnits(unitMap, TypeOfUnit.WOKER);
					if (wokerInSigen.size() < 5) {
						mura.setNextAction(BasicAction.makeWoker);
						mura.setActionLock(true);
						devil.getAlertList().add(
								new WokerInSigenIsMade(mura.getPoint(),
										TypeOfAlert.WokerInSigenIsMade));
//						log.addLog("MakeWokerInSigenの中。"+mura.getId());
					}

				}

			}

		}

		// リーダーが危機に瀕していたら、最も相手に近い拠点で新リーダ生産
		if ((leaderInDanger.size() > 0)&&(devil.getOuterDirector().getWokerLeader5s().size()<5)) {
			int xplusY = 0;
			int id = 0;
			for (Unit mura : muraMap.values()) {
				int plus = mura.getX() + mura.getY();
				if (plus > xplusY) {
					xplusY = plus;
					id = mura.getId();
				}
			}
			Unit frontMura = devil.getMyCurrentUnits().get(id);
			frontMura.setNextAction(BasicAction.makeWoker);
			frontMura.setActionLock(true);
			log.addLog(frontMura.brieafSelfIntro() + "リーダーピンチ。補充します");
			System.err.println(frontMura.brieafSelfIntro() + "リーダーピンチ。補充します");
		}

	}
	// TODO 自動生成されたメソッド・スタブ

}
