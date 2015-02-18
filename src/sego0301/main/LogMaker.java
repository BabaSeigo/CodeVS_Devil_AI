package sego0301.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LogMaker {

	private static LogMaker logMaker = new LogMaker();
	private List<String> logList = new ArrayList<String>();
	private int currentStage = 0;
//	private String logFileName = "./Stage" + currentStage + "log.txt";

	private LogMaker() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public static LogMaker getInstance() {
		return logMaker;
	}

	public void addLog(String log) {
		logList.add(log);
	}

	public void printLogIfStageChanged(int stage, int turn)
			throws FileNotFoundException {
		System.err.println("stage/current"+stage+"/"+currentStage);
		// ステージが進んだら前のステージのログを排出
		if (currentStage < stage) {
			System.err.println("stageChanged");
			System.err.println("");
			String logFileName = "./Stage" + currentStage + "log.txt";
			File f = new File(logFileName);
			PrintStream ps = new PrintStream(f);
			for (String iterable_element : logList) {
				ps.println(iterable_element);
			}
			currentStage++;
			logList.clear();
			ps.close();
		} else {
//			System.err.println("ddddddddddddddddd");
			logList.add("");
			logList.add(String.valueOf(turn)+":turn");
			// System.err.println(turn);
		}

	}

	//全ステージが終了したら出力
	public void printLogIfGameEnd(int stage) throws FileNotFoundException {
		// ステージが進んだら前のステージのログを排出
		currentStage++;
		System.err.println("gggggggggggggg");
		String logFileName = "./Stage" + currentStage + "log.txt";
		File f = new File(logFileName);
		PrintStream ps = new PrintStream(f);
		for (String iterable_element : logList) {
			ps.println(iterable_element);
		}
	}

	/**
	 * @param args
	 */
	// public static void main(String[] args) {
	// // TODO 自動生成されたメソッド・スタブ
	//
	// }

}
