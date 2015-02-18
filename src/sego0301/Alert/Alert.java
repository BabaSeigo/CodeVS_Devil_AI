package sego0301.Alert;

import java.util.ArrayList;
import sego0301.function.GeneralFunction;
import sego0301.function.FunctionAboutScore;
import sego0301.main.Devil;
import sego0301.main.LogMaker;
import sego0301.main.Point;
import sego0301.main.Unit;

import java.util.List;

import sego0301.main.Point;

public abstract class Alert {

	private Point point;
	private TypeOfAlert type;

	public Alert(Point point, TypeOfAlert type) {
		this.point = point;
		this.type = type;
		LogMaker log=LogMaker.getInstance();
//		log.addLog(getAlertName()+"のアラートを("+point.getX()+","+point.getY()+")で検知");
		//selfIntro();
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public void selfIntro() {
		//System.err.println(getAlertName()+" @"+point.getX()+" "+point.getY());
	}

	public abstract String getAlertName();

	public TypeOfAlert getType() {
		return type;
	}

	public void setType(TypeOfAlert type) {
		this.type = type;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public static void removeAlert(List<Alert> alertList, TypeOfAlert type,Point point) {
		List<Alert> newAlertList=new ArrayList<Alert>();

		for (Alert alert : alertList) {
			//場所とタイプが同じならそのアラートを取り除きます。
			if (!(alert.getPoint().equalsPoint(point))
					&& (alert.getType() == type)) {
			newAlertList.add(alert);
			}
		}
		alertList=newAlertList;
	}

	public static List<Alert> abstractAlert(List<Alert> alertList, TypeOfAlert type){
		List<Alert> aList=new ArrayList<Alert>();
		for (Alert alert : alertList) {
			if(alert.getType()==type){
				aList.add(alert);
			}
		}
		return aList;
	}



}
