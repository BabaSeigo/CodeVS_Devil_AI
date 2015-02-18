package sego0301.main;

//import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import sego0301.Alert.Alert;
import sego0301.RuleData.TypeOfUnit;
import sego0301.Strategist.Strategist;
import sego0301.Tester.TesterKun;
import sego0301.function.CalculatorOfSeen;
import sego0301.function.GeneralFunction;

//
// codevs 4.0 Sample Program Java
//
public class Devil {

	// Devilは他の場所で生成されない
	private Devil() {

	}

	public static int dist(int y1, int x1, int y2, int x2) {
		return Math.abs(y1 - y2) + Math.abs(x1 - x2);
	}

	//stage0が始まるから、最初はー1
	private int currentStage=-1;
	private int currentTurn;
	private int currentResource; // 現在の資源数
	private GeneralDirector generalDirector;
	private OuterDirector outerDirector;
	private List<Alert> alertList=new ArrayList<Alert>();
	private TestHanbetsuki habetsuki;
	private int harfdis=6;
	private boolean kanshiie=false;
	private int shotTurn=1000;
	private boolean fired=false;

	public boolean isFired() {
		return fired;
	}

	public void setFired(boolean fired) {
		this.fired = fired;
	}

	public int getShotTurn() {
		return shotTurn;
	}

	public void setShotTurn(int shotTurn) {
		this.shotTurn = shotTurn;
	}

	public boolean isKanshiie() {
		return kanshiie;
	}

	public void setKanshiie(boolean kanshiie) {
		this.kanshiie = kanshiie;
	}

	public int getHarfdis() {
		return harfdis;
	}

	public void setHarfdis(int harfdis) {
		this.harfdis = harfdis;
	}

	public TestHanbetsuki getHabetsuki() {
		return habetsuki;
	}

	public void setHabetsuki(TestHanbetsuki habetsuki) {
		this.habetsuki = habetsuki;
	}

	private boolean isTopLeft = true; // 1P側か2P側か

	private Unit myCastle = null; // 自分の城

	// stageStart時にクリアする必要性あり
	// ユニットリスト関連
	private Map<Integer, Unit> myCurrentUnits = new HashMap<Integer, Unit>(); // ユニットIDをキーとする自分のユニットの一覧。
	private Map<Integer, Unit> myOldUnits = new HashMap<Integer, Unit>(); // ユニットIDをキーとする自分のユニットの一覧。
	private boolean[][] oldSeen = new boolean[100][100]; // 前ターンのseen

	private List<Point> oldSigenList = new ArrayList<Point>();

	private Map<Integer, Unit> opCurrentUnits = new HashMap<Integer, Unit>(); // ユニットIDをキーとする（視界内の）敵ユニットの一覧。
	private Map<Integer, Unit> opOldUnits = new HashMap<Integer, Unit>(); // ユニットIDをキーとする（視界内の）敵ユニットの一覧。

	// 城関連
	private Unit opCastle = null; // 敵の城（敵の城を見つけていないならnull）
	private Scanner scanner = new Scanner(System.in);

	/**先にyなのに注意*/
	private boolean[][] seen = new boolean[100][100]; // そのマスが一度でも視界に入るとtrue
	// 資源・視野関連リスト
	private List<Point> sigenList = new ArrayList<Point>();
	// input時に強制的に変更される。ステージスタートで更新必要なし
	private int remainingTime; // 残り持ち時間

	public List<Alert> getAlertList() {
		return alertList;
	}

	public void setAlertList(List<Alert> alertList) {
		this.alertList = alertList;
	}

	public int getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(int currentStage) {
		this.currentStage = currentStage;
	}

	public GeneralDirector getGeneralDirector() {
		return generalDirector;
	}

	public boolean[][] getOldSeen() {
		return oldSeen;
	}

	public void setOldSeen(boolean[][] oldSeen) {
		this.oldSeen = oldSeen;
	}

	public int getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}

	// int currentNumOfMyUnits;// 現在の自分のユニット数
	// int currentNumOfOPUnits;// 現在の敵のユニット数



	public boolean getIsTopLeft() {
		return isTopLeft;
	}

	public void setIsTopLeft(boolean isTopLeft) {
		this.isTopLeft = isTopLeft;
	}

	public int getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(int remainingTime) {
		this.remainingTime = remainingTime;
	}

	public int getCurrentResource() {
		return currentResource;
	}

	public void setCurrentResource(int currentResource) {
		this.currentResource = currentResource;
	}

	public Unit getMyCastle() {
		return myCastle;
	}

	public void setMyCastle(Unit myCastle) {
		this.myCastle = myCastle;
	}

	public Map<Integer, Unit> getMyCurrentUnits() {
		return myCurrentUnits;
	}

	public void setMyCurrentUnits(Map<Integer, Unit> myCurrentUnits) {
		this.myCurrentUnits = myCurrentUnits;
	}

	public Map<Integer, Unit> getMyOldUnits() {
		return myOldUnits;
	}

	public void setMyOldUnits(Map<Integer, Unit> myOldUnits) {
		this.myOldUnits = myOldUnits;
	}

	public Map<Integer, Unit> getOpCurrentUnits() {
		return opCurrentUnits;
	}

	public void setOpCurrentUnits(Map<Integer, Unit> opCurrentUnits) {
		this.opCurrentUnits = opCurrentUnits;
	}

	public Map<Integer, Unit> getOpOldUnits() {
		return opOldUnits;
	}

	public void setOpOldUnits(Map<Integer, Unit> opOldUnits) {
		this.opOldUnits = opOldUnits;
	}

	public Unit getOpCastle() {
		return opCastle;
	}

	public void setOpCastle(Unit opCastle) {
		this.opCastle = opCastle;
	}

	public Scanner getScanner() {
		return scanner;
	}


	public boolean[][] getSeen() {
		return seen;
	}

	public void setSeen(boolean[][] seen) {
		this.seen = seen;
	}

	public List<Point> getSigenList() {
		return sigenList;
	}

	public void setSigenList(List<Point> sigenList) {
		this.sigenList = sigenList;
	}

	private void setGeneralDirector(GeneralDirector generalDirector) {
		this.generalDirector = generalDirector;
	}

	public List<Point> getOldSigenList() {
		return oldSigenList;
	}

	public void setOldSigenList(List<Point> oldSigenList) {
		this.oldSigenList = oldSigenList;
	}

	private void stageStart() {
		currentStage++;
		// unitリストをクリア
		myCurrentUnits.clear();
		myOldUnits.clear();
		opCurrentUnits.clear();
		opOldUnits.clear();
		alertList.clear();
		harfdis=6;
		shotTurn=1000;
		fired=false;

		// 城関連をクリア
		myCastle = null;
		opCastle = null;
		outerDirector.clearAll();
		habetsuki=new TestHanbetsuki(this);

		//outerDirector=new OuterDirector(this);
		// 資源リストをクリア
		sigenList.clear();
		oldSigenList.clear();

		// 視野をクリア
		for (int y = 0; y < 100; y++) {
			for (int x = 0; x < 100; x++) {
				seen[y][x] = false;
				oldSeen[y][x] = false;
			}
		}
	}

	public OuterDirector getOuterDirector() {
		return outerDirector;
	}

	public void setOuterDirector(OuterDirector outerDirector) {
		this.outerDirector = outerDirector;
	}

	private boolean input() {
		Inputter inputter = new Inputter(this);
		remainingTime = scanner.nextInt();
		int currentStage = scanner.nextInt();
		currentTurn = scanner.nextInt();
		if (currentTurn == 0) {
			// ターンをまたいで維持される変数はステージが新しくなった時点で初期化を行う。
			stageStart();
			// ステージが始まったことをデバッグ出力。
			// （クライアントで実行すると標準エラー出力は ./log/io/ 配下にログが出力される）
			System.err.println("stage:" + currentStage);
		}

		currentResource = scanner.nextInt();
		int numOfMyUnitsOnInput = scanner.nextInt();

		// Director達を生成
		CalculatorOfSeen calculatorOfSeen = new CalculatorOfSeen(this);

		if (currentTurn == 0) {
			// 1行目を城かどうか判断
			inputter.inputMyCastle();

			// 城の視野を考慮
			calculatorOfSeen.calNewSeenIfUnitAdd(myCastle);
			// これで、0ターン目の城に関する処理は終了

			// ここから、0ターン目の残りの初期ユニットを格納。同時に視野も埋める
			inputter.inputMyCurrentUnitsExceptCastle(numOfMyUnitsOnInput);

			// ここから、1ターン目以降の話
		} else {
			// 1ターン目以降は城も含めて全部読み込み。分けると城の判定がうざい
			inputter.inputAllMyCurrentUnits(numOfMyUnitsOnInput);
		}

		// MYunits読み込み終わり。

		// 視野内の敵軍ユニット読み込み
		int numOfEnemyUnitsOnInput = scanner.nextInt();
		inputter.inputEnemyCurrentUnits(numOfEnemyUnitsOnInput);

		// 資源の位置を読み込み
		inputter.inputSigen();

		String end = scanner.next();
		if ("END".equals(end)) {
			return true;
		}
		return false;
	}

	//毎回、generalDirectorを生成するように注意する。アップデートされていない可能性があるから
	private void think() {


		//7ターン目までは、outerに任せよう
		if (getCurrentTurn()==0) {
			outerDirector.setFirstPurposePoint();
			Map<Integer, Unit> woker5s=GeneralFunction.abstractTargetTypeUnits(myCurrentUnits, TypeOfUnit.WOKER);
			outerDirector.setPurposePointMap(woker5s);
			outerDirector.setWokerLeader5s(woker5s);
		}

		if(getCurrentTurn()<6){
			Map<Integer, Unit> woker5s=GeneralFunction.abstractTargetTypeUnits(myCurrentUnits, TypeOfUnit.WOKER);
			outerDirector.moveWokerToPurposePoint(woker5s);
		}
		else {
			GeneralDirector generalDirector=new GeneralDirector(this);
			this.setGeneralDirector(generalDirector);
			generalDirector.direct();
		}
		// ユニットに命令を設定する
		// ユニットに設定された命令を文字列として出力する

		ArrayList<String> outputs = new ArrayList<String>();
		for (Unit u : myCurrentUnits.values()) {
			//1つ1つのユニットの命令がおかしくないか設定。
			GeneralFunction.convertActionTtoCorrectAction(this, u);
			String s = u.toOrderString();
			if (s.length() > 0) {
				outputs.add(s);
			}
		}

		System.out.println(outputs.size()); // 出力のはじめに命令の個数を出力
		for (String s : outputs)
			System.out.println(s); // 命令を一行ずつ出力
	}

	public static void main(String[] args) throws IOException {
		Devil devil = new Devil();
//		TesterKun test = new TesterKun(devil);
		// AI の名前を出力
		System.out.println("Devilman");
		System.out.flush(); // 忘れずに標準出力をフラッシュする
		Renewer renewer=new Renewer(devil);

//		String writeFileName = "./inputTest.txt";
//		File f = new File(writeFileName);
//		PrintStream ps = new PrintStream(f);
//
//		String writeOldFileName = "./oldInputTest.txt";
//		File oldF = new File(writeOldFileName);
//		PrintStream oldps = new PrintStream(oldF);

		//ループ外で管理したい場合はここでデータを保存
		devil.outerDirector=new OuterDirector(devil);
		LogMaker logMaker=LogMaker.getInstance();
		while (devil.input()) { // 入力が読めない場合には false を返すのでループを抜ける
			devil.think();
			System.out.flush(); // 忘れずに標準出力をフラッシュする
			//ここからはテスト:
//			test.checkDevilInput(ps);
//			test.checkDevilOldInput(oldps);
//			int[] turnset = { 0, 130 };
//			test.checkSeen(turnset,devil.getCurrentStage());
			//renewerは最後！
			logMaker.printLogIfStageChanged(devil.getCurrentStage(),devil.getCurrentTurn());
			renewer.renewAllList();
		}
		System.err.println("All stage is end");
		logMaker.printLogIfGameEnd(devil.getCurrentStage());

	}
}
