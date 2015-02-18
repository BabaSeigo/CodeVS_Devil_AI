package sego0301.RuleData;

import java.util.HashMap;
import java.util.Map;

import sego0301.main.Unit;

public enum BasicAction {

	// private final String MOVE="MOVE";


	// move系action
	moveToUp("U", TypeOfBasicAction.move),
	moveToDown("D",TypeOfBasicAction.move),
	moveToLeft("L", TypeOfBasicAction.move),
	moveToRight("R", TypeOfBasicAction.move),

	// muraAndCatle系action
	makeWoker("0", TypeOfBasicAction.muraAndCastle),

	// Battler系action
	makeKnight("1", TypeOfBasicAction.kyoten),
	makeFighter("2",TypeOfBasicAction.kyoten),
	makeAssassin("3",TypeOfBasicAction.kyoten),

	// woker系action
	makeMura("5", TypeOfBasicAction.woker),
	makeKyoten("6",TypeOfBasicAction.woker);


	private String stringOfAction;
	private TypeOfBasicAction typeOfBasicAction;

	//
	// private Map<String, String> moveCommand = new HashMap<String, String>();
	// {
	// moveCommand.put("right", "R");
	// moveCommand.put("left", "L");
	// moveCommand.put("up", "U");
	// moveCommand.put("down", "D");
	// // TODO 自動生成されたコンストラクター・スタブ
	// }
	//
	BasicAction(String stringOfAction, TypeOfBasicAction type) {
		this.stringOfAction = stringOfAction;
		this.typeOfBasicAction = type;
	}

	public String getStringOfBasicAction() {
		return stringOfAction;
	}

	public TypeOfBasicAction getTypeOfBasicAction() {
		return typeOfBasicAction;
	}

	//この行動を引数のユニットは実行可能か判断
	public boolean checkAction(Unit unit) {
		TypeOfBasicAction nextAction = getTypeOfBasicAction();
		TypeOfUnit unitType = unit.getType();

		//move系は実行可能なユニットか？
		if (nextAction == TypeOfBasicAction.move) {
			//動くユニットなら実行可能。それ以外は不可
			if (unitType == TypeOfUnit.WOKER || unitType == TypeOfUnit.KNIGHT
					|| unitType == TypeOfUnit.FIGHTER
					|| unitType == TypeOfUnit.ASSASSIN) {
				return true;
			}else {
				return allertErrorOfCantDoAction(unit);
			}
		//muraAndCastle系は実行可能なユニットか？
		}else if (nextAction == TypeOfBasicAction.muraAndCastle) {
			//村か城なら実行可能。それ以外は不可
			if(unitType==TypeOfUnit.CASTLE||unitType==TypeOfUnit.MURA){
				return true;
			}else{
				return allertErrorOfCantDoAction(unit);
			}
		//kyoten系は実行可能なユニットか？
		}else if(nextAction ==typeOfBasicAction.kyoten){
			if(unitType==TypeOfUnit.KYOTEN){
				return true;
			}else{
				return allertErrorOfCantDoAction(unit);
			}
		//woker系は実行可能なユニットか？
		}else if (nextAction==TypeOfBasicAction.woker) {
			if(unitType==TypeOfUnit.WOKER){
				return true;
			}else {
				return allertErrorOfCantDoAction(unit);
			}
		}else {
			return allertErrorOfCantDoAction(unit);
		}
	}

	private boolean allertErrorOfCantDoAction(Unit unit){
		System.err.println(unit.getType()+"は"+this+"を実行不可");
		return false;
	}
}
