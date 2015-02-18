package sego0301.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import sego0301.RuleData.BasicAction;
import sego0301.function.GeneralFunction;
import sego0301.function.FunctionAboutScore;

//資源を目指すユニットを制御するディレクター
public class OuterDirectorSigenTansaku {

	private Devil devil ;
	//各々の行動をIDと目指す場所で管理
	private Map<Integer,Point> purposePointMap=new HashMap<Integer, Point>();
	//private String[] firstDirectionID =new String[5];
	private List<Point> purposePointList=new ArrayList<Point>();
	private Map<Integer, Unit> wokerInSigenMap=new HashMap<Integer, Unit>();
	private Map<Integer, Unit> wokerMovingToSigenMap=new HashMap<Integer, Unit>();
	//最初にLUのどちらかを選んだユニットが存在していたら

//	private Unit unitL;
//	private Unit unitU;
//	private Map<Integer, Unit> unitLUMap=new HashMap<Integer, Unit>();


	public Map<Integer, Unit> getWokerInSigenMap() {
		return wokerInSigenMap;
	}






	public void setWokerInSigenMap(Map<Integer, Unit> wokerInSigenMap) {
		this.wokerInSigenMap = wokerInSigenMap;
	}

	public Map<Integer, Unit> getWokerMovingToSigenMap() {
		return wokerMovingToSigenMap;
	}

	public OuterDirectorSigenTansaku(Devil devil) {
		this.devil=devil;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	//purposePointとwokerを関連付け.LUユニットも
	public void setPurposePointMap(Map<Integer, Unit> woker5s){
		int i=0;
		for (Unit unit : woker5s.values()) {
			Point point =purposePointList.get(i);
			purposePointMap.put(unit.getId(), point);
			if(point.getId()=="L"){
			}
			if(point.getId()=="U"){
			}
			i++;
		}
	}


	public void setWokerMoveToPurposePoint(Map<Integer, Unit> woker5s){
		for (Integer key : purposePointMap.keySet()) {
			System.err.println(key+"は"+purposePointMap.get(key).getId());
			GeneralFunction.setMoveUnitToPoint(woker5s.get(key), purposePointMap.get(key));
		}
	}


	//資源を発見する度に登録
	public void setPurposePoint(List<Point> discoveredNewSigen){
		for (Iterator iterator = discoveredNewSigen.iterator(); iterator
				.hasNext();) {
			Point point = (Point) iterator.next();
			purposePointList.add(point);
		}
	}


	public void clearAll(){
		purposePointList.clear();
		purposePointMap.clear();
		wokerInSigenMap.clear();
		wokerMovingToSigenMap.clear();
		wokerInSigenMap.clear();

	}


}
