package sego0301.Actions;

import java.util.Map;

import sego0301.main.Devil;
import sego0301.main.Unit;

public class GoFrontieaIfCaughtInSeen extends Actions {

	public GoFrontieaIfCaughtInSeen(Devil devil) {
		super(devil);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	// 自分の視野の周りが全部seenだったときの対処方法
	@Override
	public void doActions(Map<Integer, Unit> unitMap) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public static boolean isCaughtInSeen(Unit unit) {
		boolean isCaughtInSeen = false;
		//自分の視界の+-1を見る
		int left = Math.max(0,unit.getX() - unit.getViewRange()-1);
		int right = Math.min(99, unit.getX() + unit.getViewRange()+1);
		int top = Math.max(0,unit.getY() - unit.getViewRange()-1);
		int bottom = Math.min(99, unit.getY() + unit.getViewRange()+1);


		return isCaughtInSeen;
	}

}
