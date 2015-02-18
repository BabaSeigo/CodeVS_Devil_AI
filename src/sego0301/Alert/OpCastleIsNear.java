package sego0301.Alert;

import java.util.List;
import java.util.Map;

import sego0301.RuleData.TypeOfUnit;
import sego0301.function.GeneralFunction;
import sego0301.function.FunctionAboutTerritory;
import sego0301.function.FunctionAboutScore;
import sego0301.main.Devil;
import sego0301.main.Point;
import sego0301.main.Unit;

public class OpCastleIsNear extends Alert {

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public OpCastleIsNear(Point point, TypeOfAlert type, Integer id) {
		super(point, type);
		this.id = id;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public String getAlertName() {
		// TODO 自動生成されたメソッド・スタブ
		return "城近い";
	}

	public static boolean isOpCastleNear(Devil devil, Unit leader) {
		Unit currentLeader = devil.getMyCurrentUnits().get(leader.getId());
		Unit oldLeader = devil.getMyOldUnits().get(leader.getId());
		// 現在、近い敵ユニットいない。いても4マス以上
		// Map<Integer, Unit> nearOpUnits = Function
		// .abstractNearestUnitFromTargetUnit(currentLeader,
		// devil.getOpCurrentUnits());
		// // List<Unit>
		// nearOpList=ScoreFunction.convertUnitMapToUnitList(nearOpUnits);

		// 近接OPリストがそもそもいない
		// boolean unitsNotIn4seen = false;
		// if (nearOpUnits.size() > 0) {
		// if (Function.getDistanceFromUnitAToNearestUnit(currentLeader,
		// nearOpUnits) > 4) {
		// unitsNotIn4seen = true;
		// }
		// } else {
		// unitsNotIn4seen = true;
		// }
		// int damageInThisTurn = 0;

		if (oldLeader != null) {
			Map<Integer, Unit> oldOpunitMap = FunctionAboutTerritory
					.abstractUnitMapInView(devil.getOpOldUnits(),
							oldLeader.getPoint(), 4);
			int oldhp = oldLeader.getHp();
			// for (Unit unit : oldOpunitMap.values()) {
			// oldhp += -leader.getType().receiveDamage(unit.getType());
			// }
			if (oldOpunitMap.size() == 0) {
				if (oldhp > currentLeader.getHp()) {
					devil.getAlertList().add(
							new OpCastleIsNear(currentLeader.getPoint(),
									TypeOfAlert.OpCastleIsNear, currentLeader
											.getId()));
					return true;
				} else {
					return false;
				}
			}else {
				return false;
			}
		} else {
			return false;
		}
	}
	// // damageInThisTurn=oldLeader.getType().receiveDamage();
	//
	// if (oldLeader != null) {
	// if (unitsNotIn4seen && oldLeader.getHp() != currentLeader.getHp()) {
	//
	// devil.getAlertList().add(
	// new OpCastleIsNear(currentLeader.getPoint(),
	// TypeOfAlert.OpCastleIsNear, currentLeader
	// .getId()));
	// return true;
	// } else {
	// return false;
	// }
	// } else {
	// return false;
	// }
}
