package sego0301.Alert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sego0301.main.Devil;
import sego0301.main.Point;
import sego0301.main.Unit;

public class OpNearMura extends Alert {

	private Map<Integer, Unit> opMap=new HashMap<Integer, Unit>();
	private int turn;

	public Map<Integer, Unit> getOpMap() {
		return opMap;
	}

	public void setOpMap(Map<Integer, Unit> opMap) {
		this.opMap = opMap;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public OpNearMura(Point point, TypeOfAlert type,Map<Integer, Unit> opMap,int turn) {
		super(point, type);
		this.opMap=opMap;
		this.turn=turn;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public String getAlertName() {
		// TODO 自動生成されたメソッド・スタブ
		return "敵が村の近くにいる";
	}

	public static Map<Integer, Unit> getUnitsMapInAlert(Devil devil){
		List<Alert> alertList=Alert.abstractAlert(devil.getAlertList(), TypeOfAlert.OpNearMura);
		Map<Integer, Unit> unitMap=new HashMap<Integer, Unit>();
		for(Alert alert:alertList){
			OpNearMura op=(OpNearMura) alert;
			unitMap.putAll(op.getOpMap());
		}
		return unitMap;
	}

}
