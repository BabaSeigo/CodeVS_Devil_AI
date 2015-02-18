package sego0301.Tester;

import java.applet.*; // おまじない
import java.awt.*; // おまじない
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sego0301.main.Point;


// sample01はプログラム名(正確にはクラス名)で,
// プログラマが任意に設定する(ただしファイル名と一致させておく)
public class PainterOfSeen extends Applet {

	// 一般に描画命令は paint メソッド内に定義する
	public void paint(Graphics g) {
		// 描画色と背景色の設定
		setForeground(Color.black);
		String readFileName = "C:/Users/baba/Desktop/codeVS/codeVSData/turn190seen.txt";
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(readFileName));
			List<Point> seenList = new ArrayList<Point>();
			String line;
			try {
				while ((line = br.readLine()) != null) {
					String[] l = line.split("	");
					seenList.add(new Point(Integer.parseInt(l[0]), Integer
							.parseInt(l[1])));
				}
			} catch (NumberFormatException | IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			int nPoints=seenList.size();
			int[] xPoints=new int[nPoints];
			int[] yPoints=new int[nPoints];
			int i=0;
			for (Point point : seenList) {
				xPoints[i]=point.getX();
				yPoints[i]=point.getY();
			}
		for (Point point : seenList) {
			g.drawString(".", point.getX(), point.getY());
		}
		g.drawPolygon(xPoints, yPoints, nPoints);

		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		// 座標原点に矢印を描き，座標値を表示
	}

}