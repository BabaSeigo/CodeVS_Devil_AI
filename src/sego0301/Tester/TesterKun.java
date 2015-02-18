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

public class TesterKun {

	// devilいらないテストもあるから、コンストラクタにはとらない
	private Devil devil;

	private TesterKun() {

		// TODO 自動生成されたコンストラクター・スタブ
	}

	public TesterKun(Devil devil) {
		this.devil = devil;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * @param args
	 *
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

		TesterKun test = new TesterKun();
		System.out.println("aaa");
		test.checkMap();
		//test.checkMatchUnitAndAction();
		//test.checkTypeOfUnitToString();
		//test.checkEqualTypeOfBasicAction();

		// test.checkDamgeKeisan();

	}

	// enum型はイコールで結べるのか？
	public void checkEqualTypeOfBasicAction() {
		TypeOfBasicAction typeAction1 = TypeOfBasicAction.move;
		TypeOfBasicAction typeAction2 = TypeOfBasicAction.move;
		if (typeAction1 == typeAction2) {
			System.out.println("equal");

		}

	}

	// unitの定義が正しいか確認。ダメージ計算も含めて
	public void checkDamgeKeisan() {
		TypeOfUnit[] unittable = { TypeOfUnit.WOKER, TypeOfUnit.KNIGHT,
				TypeOfUnit.FIGHTER, TypeOfUnit.ASSASSIN, TypeOfUnit.CASTLE,
				TypeOfUnit.MURA, TypeOfUnit.KYOTEN };

		System.out.println(1111);

		for (int i = 0; i < TypeOfUnit.getNUMBEROFUNITS(); i++) {
			TypeOfUnit unit = unittable[i];
			System.out.println(unit.name() + "	" + unit.getMAXHP() + "	"
					+ unit.getVIEWRANGE() + "	" + unit.getATTACKRANGE() + "	"
					+ unit.getCOST() + "	" + unit.getTYPE());
			for (int j = 0; j < TypeOfUnit.getNUMBEROFUNITS(); j++) {
				TypeOfUnit enemy = unittable[j];
				System.out.print(unit.name() + "が" + enemy.name() + "に与える"
						+ unit.attackDamage(enemy) + "	");

			}
			System.out.println();
			for (int j = 0; j < TypeOfUnit.getNUMBEROFUNITS(); j++) {
				TypeOfUnit enemy = unittable[j];
				System.out.print(unit.name() + "が" + enemy.name() + "に受ける"
						+ unit.receiveDamage(enemy) + "	");
			}
			System.out.println();
			System.out.println();
		}
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

	public void checkDevilInput(PrintStream ps) throws FileNotFoundException {
		checkInputData(devil.getMyCurrentUnits(), devil.getOpCurrentUnits(), ps);
	}

	public void checkDevilOldInput(PrintStream ps) throws FileNotFoundException {
		checkInputData(devil.getMyOldUnits(), devil.getOpOldUnits(), ps);
	}

	// void checKDevilOldInput() throws FileNotFoundException {
	// String writeFileName = "./oldInputTest.txt";
	// File f = new File(writeFileName);
	// PrintStream ps = new PrintStream(f);
	//
	// checkInputData(devil.getMyCurrentUnits(), devil.getOpCurrentUnits(),
	// ps);
	// }

	private void checkInputData(Map<Integer, Unit> myUnitMap,
			Map<Integer, Unit> enemyUnitMap, PrintStream ps)
			throws FileNotFoundException {
		ps.println("turn	" + devil.getCurrentTurn());
		ps.println("今の資源	" + devil.getCurrentResource());

		ps.println("MyUnit数	" + myUnitMap.size());
		printUnitsMap(myUnitMap, ps);

		ps.println("敵unit数	" + enemyUnitMap.size());
		printUnitsMap(enemyUnitMap, ps);
		ps.println();
	}

	public void checkSeen(int[] turnSet,int stage) throws FileNotFoundException {
		for (int turn : turnSet) {
			if (turn == devil.getCurrentTurn()) {
				String writeFileName = "./stage"+stage+"turn" + turn + "seen.txt";
				File f = new File(writeFileName);
				PrintStream ps = new PrintStream(f);
				for (int i = 0; i < 100; i++) {
					for (int j = 0; j < 100; j++) {
						if (devil.getSeen()[i][j]) {
							ps.println(j + "	" + i);
						}
					}
				}
				ps.close();
			}
		}

	}

	public void testUnitCommand() {
		Unit unit = new Unit(0, new Point(3, 4), 100, TypeOfUnit.ASSASSIN);

	}

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

	public void checkTypeOfUnitToString() {

		TypeOfUnit[] typeList = { TypeOfUnit.WOKER, TypeOfUnit.KNIGHT,
				TypeOfUnit.FIGHTER, TypeOfUnit.ASSASSIN, TypeOfUnit.MURA,
				TypeOfUnit.KYOTEN };

		for (int i = 0; i < typeList.length; i++) {
			System.out.println(typeList[i]);
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

	public void checkMap(){
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


			Map<Integer, Unit> unitMap2=new HashMap<Integer, Unit>();
			unitMap2.put(unit5.getId(), unit5);
			unitMap=unitMap2;
			for (Integer iterable_element : unitMap.keySet()) {
				System.out.println(iterable_element);
			}

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

