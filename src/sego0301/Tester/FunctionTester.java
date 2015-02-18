package sego0301.Tester;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sego0301.RuleData.BasicAction;
import sego0301.RuleData.TypeOfBasicAction;
import sego0301.RuleData.TypeOfUnit;
import sego0301.function.GeneralFunction;
import sego0301.main.Devil;
import sego0301.main.Point;
import sego0301.main.Unit;

public class FunctionTester {

	// devilいらないテストもあるから、コンストラクタにはとらない
	private Devil devil;

	private FunctionTester() {

		// TODO 自動生成されたコンストラクター・スタブ
	}

	public FunctionTester(Devil devil) {
		this.devil = devil;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * @param args
	 *
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

		FunctionTester test = new FunctionTester();
		System.out.println("aaa");
		//test.checkGetCurrentTargetUnitViewTerritory();
		//test.checkAbstractUnitMapInMyViewTerritory();
		//test.checkGetSumOfdistanceFromUnitAToOthers();
		test.checkReturnNearestUnitFromA();
		test.checkGetDistanceFromUnitAToNearestUnit();


		//test.checkAbstractUnitMapInTargetTerritory();
		//test.checkMatchUnitAndAction();
		//test.checkTypeOfUnitToString();
		//test.checkEqualTypeOfBasicAction();

		// test.checkDamgeKeisan();

	}



	// void checKDevilInput(String readFileName) throws IOException{
	// BufferedReader br = new BufferedReader(new FileReader(readFileName));
	// String line;
	// while ((line = br.readLine()) != null) {
	//
	// }
	// }

	// void checKDevilInput() throws FileNotFoundException {
	// String writeFileName = "./inputTest.txt";
	// checkInputData(devil.getMyCurrentUnits(), devil.getOpCurrentUnits(),
	// writeFileName);
	// }



	// void checKDevilOldInput() throws FileNotFoundException {
	// String writeFileName = "./oldInputTest.txt";
	// File f = new File(writeFileName);
	// PrintStream ps = new PrintStream(f);
	//
	// checkInputData(devil.getMyCurrentUnits(), devil.getOpCurrentUnits(),
	// ps);
	// }

	private void printUnitsMap(Map<Integer, Unit> unitsMap, PrintStream ps) {
		Set<Integer> unitsKeySet = unitsMap.keySet();
		for (Integer key : unitsKeySet) {
			Unit unit = unitsMap.get(key);
			ps.println(unit.getId() + "	" + unit.getY() + "	" + unit.getX()
					+ "	" + unit.getHp() + "	"
					+ TypeOfUnit.convertTypeToNum(unit.getType())
					+ unit.getType());
		}

	}

	// 全てのユニット毎の行動が正しいかチェック
	public void checkMatchUnitAndAction() {
		TypeOfUnit[] typeOfUnitArray = TypeOfUnit.values();
		BasicAction[] basicActionArray = BasicAction.values();
		for (int i = 0; i < typeOfUnitArray.length; i++) {
			TypeOfUnit typeOfUnit = typeOfUnitArray[i];
			Unit unit = new Unit(0, new Point(1, 2), 11, typeOfUnit);
			for (int j = 0; j < basicActionArray.length; j++) {
				BasicAction basicAction = basicActionArray[j];
				basicAction.checkAction(unit);
//				if (basicAction.checkAction(unit)) {
//					System.out.println(unit.getType()+"は次を実行可能"+basicAction);
//				}
			}
			System.out.println();
		}

	}

	 public void checkAbstractUnitMapInTargetTerritory(){
		 Map<Integer, Unit> unitMap=new HashMap<Integer, Unit>();

		 Unit unit1=new Unit(0, new Point(0, 0), 100, TypeOfUnit.WOKER);
		 Unit unit2=new Unit(1, new Point(0, 0), 100, TypeOfUnit.WOKER);
		 Unit unit3=new Unit(2, new Point(4, 5), 100, TypeOfUnit.WOKER);
		 Unit unit4=new Unit(3, new Point(50, 50), 100, TypeOfUnit.WOKER);

		 unitMap.put(unit1.getId(), unit1);
		 unitMap.put(unit2.getId(), unit2);
		 unitMap.put(unit3.getId(), unit3);
		 unitMap.put(unit4.getId(), unit4);
		 boolean[][] territory=new boolean[100][100];

		 for (int i = 0; i < 40; i++) {
			for (int j = 0; j < 40; j++) {
				territory[i][j]=true;
			}
		}

		 for(Unit unit :unitMap.values()){
			 System.out.println(unit.getId()+"	"+unit.getType());
		 }

		 Map<Integer, Unit> creakedMap=GeneralFunction.abstractUnitMapInTargetTerritory(unitMap, territory);

		 for(Unit unit :creakedMap.values()){
			 System.out.println(unit.getId()+"	"+unit.getType());
		 }
	 }

	 public void checkAbstractUnitMapInMyViewTerritory(){
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
			 System.out.println(unit.getId()+"	"+unit.getType());
		 }

		 Map<Integer, Unit> creakedMap=GeneralFunction.abstractUnitMapInMyViewTerritory(unitMap,unit1);

		 for(Unit unit :creakedMap.values()){
			 System.out.println(unit.getId()+"	"+unit.getType());
		 }
	 }


	 public void checkGetCurrentTargetUnitViewTerritory(){
		 Unit unit1=new Unit(0, new Point(10, 10), 100, TypeOfUnit.WOKER);
		 boolean[][] teritoryy=GeneralFunction.getCurrentTargetUnitViewTerritory(unit1);

		 for (int i = 0; i < teritoryy.length; i++) {
			 for (int j = 0; j < teritoryy.length; j++) {
				 if(teritoryy[i][j]){
					 System.out.println(i+"	"+j);
				 }
			 }
		 }
	 }

	 public void checkGetSumOfdistanceFromUnitAToOthers(){
		Map<Integer, Unit> unitMap=new HashMap<Integer, Unit>();
		 Unit unit1=new Unit(0, new Point(1, 1), 100, TypeOfUnit.WOKER);
		 Unit unit2=new Unit(1, new Point(0, 0), 100, TypeOfUnit.WOKER);
		 Unit unit3=new Unit(2, new Point(4, 5), 100, TypeOfUnit.WOKER);
		 Unit unit4=new Unit(3, new Point(50, 50), 100, TypeOfUnit.WOKER);

		 unitMap.put(unit1.getId(), unit1);
		 unitMap.put(unit2.getId(), unit2);
		 unitMap.put(unit3.getId(), unit3);
		 unitMap.put(unit4.getId(), unit4);

		 for(Unit unit :unitMap.values()){
			 System.out.println(unit.getId()+"	"+unit.getType());
		 }

			 System.out.println(GeneralFunction.getSumOfdistanceFromUnitAToOthers(unitMap, unit4));

	 }

		public void checkReturnNearestUnitFromA(){
			Map<Integer, Unit> unitMap=new HashMap<Integer, Unit>();
			 Unit unit1=new Unit(0, new Point(1, 1), 100, TypeOfUnit.WOKER);
			 Unit unit2=new Unit(1, new Point(0, 0), 100, TypeOfUnit.WOKER);
			 Unit unit3=new Unit(2, new Point(4, 5), 100, TypeOfUnit.WOKER);
			 Unit unit4=new Unit(3, new Point(50, 50), 100, TypeOfUnit.WOKER);
			 Unit unit5=new Unit(5, new Point(1, 1), 100, TypeOfUnit.WOKER);

			 unitMap.put(unit1.getId(), unit1);
			 unitMap.put(unit2.getId(), unit2);
			 unitMap.put(unit3.getId(), unit3);
			 unitMap.put(unit4.getId(), unit4);
			 unitMap.put(unit5.getId(), unit5);

			 for(Unit unit :unitMap.values()){
				 System.out.println(unit.getId()+"	"+unit.getType());
			 }

			// Map<Integer, Unit> near=Function.abstractNearestUnitFromTargetUnit(unit2, unitMap);
			 Map<Integer, Unit> near=GeneralFunction.abstractNearestUnitFromTargetPoint(unit2.getPoint(), unitMap);

			 System.out.println();

				 for(Unit unit :near.values()){
					 System.out.println(unit.getId()+"	"+unit.getType());
				 }

		}


		public void checkGetDistanceFromUnitAToNearestUnit(){
			Map<Integer, Unit> unitMap=new HashMap<Integer, Unit>();
			 Unit unit1=new Unit(0, new Point(1, 1), 100, TypeOfUnit.WOKER);
			 Unit unit2=new Unit(1, new Point(0, 0), 100, TypeOfUnit.WOKER);
			 Unit unit3=new Unit(2, new Point(4, 5), 100, TypeOfUnit.WOKER);
			 Unit unit4=new Unit(3, new Point(50, 50), 100, TypeOfUnit.WOKER);
			 Unit unit5=new Unit(5, new Point(1, 1), 100, TypeOfUnit.WOKER);

			 unitMap.put(unit1.getId(), unit1);
			 unitMap.put(unit2.getId(), unit2);
			 unitMap.put(unit3.getId(), unit3);
			 unitMap.put(unit4.getId(), unit4);
			 unitMap.put(unit5.getId(), unit5);

			 for(Unit unit :unitMap.values()){
				 System.out.println(unit.getId()+"	"+unit.getType());
			 }



			 System.out.println(GeneralFunction.getDistanceFromUnitAToNearestUnit(unit1, unitMap));
		}





}




// void checkSeen(){
// PainterOfSeen painter=new PainterOfSeen();
// JFreeChart chart =
// ChartFactory.createTimeSeriesChart("PV推移",
// "月",
// "PV",
// createData(),
// true,
// false,
// false);
//
// XYPlot plot = (XYPlot)chart.getPlot();
// plot.setBackgroundPaint(Color.ORANGE);
//
// ChartPanel cpanel = new ChartPanel(chart);
// getContentPane().add(cpanel, BorderLayout.CENTER);
//
// }

