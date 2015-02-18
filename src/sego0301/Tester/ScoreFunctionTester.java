package sego0301.Tester;

import java.util.HashMap;
import java.util.Map;

import sego0301.RuleData.BasicAction;
import sego0301.RuleData.TypeOfUnit;
import sego0301.function.GeneralFunction;
import sego0301.function.FunctionAboutScore;
import sego0301.main.Point;
import sego0301.main.Unit;

public class ScoreFunctionTester {

	public ScoreFunctionTester() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

		public static void main(String[] args) {
			ScoreFunctionTester s=new ScoreFunctionTester();
		//	s.checkGetAssumptionOfUnitMap();
			//s.checkConvert10_aryToN_ary();
			//s.checksetMaxScoreMoveThinkingAllUnit();
			//s.checkgetAssumptionSeenIfUnitAdd();
			//s.checkgetAssumptionSeenIfUnitMapAdd();
			s.checkcalDownArea();
	 }


		public static void checkGetAssumptionOfUnitMap(){
			 Map<Integer, Unit> unitMap=new HashMap<Integer, Unit>();

			 Unit unit1=new Unit(0, new Point(0, 0), 100, TypeOfUnit.WOKER);
			 Unit unit2=new Unit(1, new Point(0, 0), 100, TypeOfUnit.WOKER);
			 Unit unit3=new Unit(2, new Point(4, 5), 100, TypeOfUnit.WOKER);
			 Unit unit4=new Unit(3, new Point(50, 50), 100, TypeOfUnit.WOKER);

			 unitMap.put(unit1.getId(), unit1);
			 unitMap.put(unit2.getId(), unit2);
			 unitMap.put(unit3.getId(), unit3);
			 unitMap.put(unit4.getId(), unit4);


			 for(Unit unit :unitMap.values()){
				 System.out.println(unit.getId()+"	"+unit.getType()+"	"+unit.getX()+"	"+unit.getY()+"	"+unit.getNextAction());
			 }

			 Map<Integer, Unit> creakedMap=FunctionAboutScore.getAssumptionOfUnitMap(unitMap, unit1, BasicAction.moveToDown);

			 System.out.println();
			 for(Unit unit :creakedMap.values()){
				 System.out.println(unit.getId()+"	"+unit.getType()+"	"+unit.getX()+"	"+unit.getY()+"	"+unit.getNextAction());
			 }

		}


		public void checkConvert10_aryToN_ary(){
		int[] a=FunctionAboutScore.convert10_aryToN_ary(23, 4, 4);

		for (int i : a) {
			System.out.print(i+"	");
			}
		}

		public void checksetMaxScoreMoveThinkingAllUnit(){
			 Map<Integer, Unit> unitMap=new HashMap<Integer, Unit>();

			 Unit unit1=new Unit(0, new Point(0, 0), 100, TypeOfUnit.WOKER);
			 Unit unit2=new Unit(1, new Point(0, 0), 100, TypeOfUnit.WOKER);
			 Unit unit3=new Unit(2, new Point(4, 5), 100, TypeOfUnit.WOKER);

			 unitMap.put(unit1.getId(), unit1);
			 unitMap.put(unit2.getId(), unit2);
			 unitMap.put(unit3.getId(), unit3);
			 BasicAction[] move = {BasicAction.moveToUp,
						BasicAction.moveToLeft,BasicAction.moveToRight,
						};


			FunctionAboutScore.setMaxScoreMoveThinkingAllUnit(null, unitMap, move);
		}

		public void checkgetAssumptionSeenIfUnitAdd(){
			boolean[][] seen =new boolean[100][100];
			seen[1][1]=true;
			seen[10][1]=true;


			 Unit unit1=new Unit(0, new Point(0, 0), 100, TypeOfUnit.WOKER);
			 Unit unit2=new Unit(1, new Point(0, 0), 100, TypeOfUnit.WOKER);
			 Unit unit3=new Unit(2, new Point(4, 5), 100, TypeOfUnit.WOKER);

			 seen=FunctionAboutScore.getAssumptionSeenIfUnitAdd(unit1, seen);

			 GeneralFunction.printTrueSeen(seen);

		}

		public void checkgetAssumptionSeenIfUnitMapAdd(){
			boolean[][] seen =new boolean[100][100];
			seen[1][1]=true;
			seen[10][1]=true;

			 Map<Integer, Unit> unitMap=new HashMap<Integer, Unit>();

			 Unit unit1=new Unit(0, new Point(0, 0), 100, TypeOfUnit.WOKER);
			 Unit unit2=new Unit(1, new Point(0, 0), 100, TypeOfUnit.WOKER);
			 Unit unit3=new Unit(2, new Point(4, 5), 100, TypeOfUnit.WOKER);

			 unitMap.put(unit1.getId(), unit1);
			 unitMap.put(unit2.getId(), unit2);
			 unitMap.put(unit3.getId(), unit3);

			 seen=FunctionAboutScore.getAssumptionSeenIfUnitMapAdd(unitMap, seen);

			 GeneralFunction.printTrueSeen(seen);

		}


		public void checkcalDownArea(){
			 Unit unit1=new Unit(0, new Point(0, 0), 100, TypeOfUnit.WOKER);

			boolean[][] seen=new boolean[100][100];

			 System.out.println(FunctionAboutScore.calDownViewArea(unit1, seen));
		}

}
