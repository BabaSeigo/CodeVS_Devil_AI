package sego0301.main;

import java.util.HashMap;
import java.util.Map;

import sego0301.RuleData.BasicAction;
import sego0301.RuleData.JobType;
import sego0301.RuleData.TypeOfBasicAction;
import sego0301.RuleData.TypeOfUnit;

public class Unit {
	// ユニットの現在のステータス。
	// 与えられる入力により input() で設定される。
	private int id;
	private Point point;
	private int hp;
	private TypeOfUnit type;

	// ユニットに出す命令。
	// freeならば次のターンは何もしない。コンストラクトの時点ではフリー
	private BasicAction nextAction;
	private boolean free;
	private boolean sigen;
	private JobType jobtype;
	private boolean actionLock;

	public boolean isActionLock() {
		return actionLock;
	}

	public void setActionLock(boolean actionLock) {
		this.actionLock = actionLock;
	}

	// Unitは必ずこれでしかコンストラクトできんように。また毎回フリーにするように
	public Unit(int id, Point point, int hp, TypeOfUnit type) {
		if ((point.getX() < 0 || 99 < point.getX())
				|| (point.getY() < 0 || 99 < point.getY())) {
			System.err.println("不正な座標" + point.getX() + "	" + point.getY());
		}
		if (hp < 0 || type.getMAXHP() < hp) {
			System.err.println("不正な体力" + hp);
		}
		if (type == null) {
			System.err.println("不正なtype" + type);
		}
		// for(TypeOfUnit tyepe :TypeOfUnit){
		//
		// }

		this.id = id;
		this.point = point;
		this.hp = hp;
		this.type = type;
		jobtype = JobType.normal;
		free = true;
	}

	public JobType getJobtype() {
		return jobtype;
	}

	public void setJobtype(JobType jobtype) {
		this.jobtype = jobtype;
	}

	public Unit(Unit targetUnit) {

		// targetUnitから情報を取得
		int id = targetUnit.getId();
		Point point = targetUnit.getPoint();
		int hp = targetUnit.getHp();
		TypeOfUnit type = targetUnit.getType();

		if ((point.getX() < 0 || 99 < point.getX())
				|| (point.getY() < 0 || 99 < point.getY())) {
			System.err.println("不正な座標" + point.getX() + "	" + point.getY());
		}
		if (hp < 0 || type.getMAXHP() < hp) {
			System.err.println("不正な体力" + hp);
		}
		if (type == null) {
			System.err.println("不正なtype" + type);
		}
		// for(TypeOfUnit tyepe :TypeOfUnit){
		//
		// }

		this.id = id;
		this.point = point;
		this.hp = hp;
		this.type = type;
		free = true;

	}

	// 基本情報のセッターゲッター
	public int getId() {
		return id;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public TypeOfUnit getType() {
		return type;
	}

	// 委譲した、x,y,viewのゲッター
	public int getX() {
		return point.getX();
	}

	public int getY() {
		return point.getY();
	}

	public int getViewRange() {
		return type.getVIEWRANGE();
	}

	public int getAttackRange() {
		return type.getATTACKRANGE();
	}

	// moveToy、produceのゲッターセッター

	public boolean isFree() {
		return free;
	}

	public void setFree() {
		free = true;
		this.nextAction = null;
	}

	public boolean equalsType(TypeOfUnit type) {
		if (this.getType() == type) {
			return true;
		} else {
			return false;
		}
	}

	// 毎回、不正な行動でないかを判断してから次の行動を埋める
	// 既に行動が埋まっていたらエラーを出力→城の位置によって返還いるからやめた
	public void setNextAction(BasicAction nextAction) {

		if (isActionLock()) {
//			System.err.println("既に" + this.getId() + "の行動はロックされています");
		} else {
			if (nextAction.checkAction(this)) {
				this.nextAction = nextAction;
				free = false;
			}

			// this.nextAction=nextAction;
		}

	}

	// 現在設定されている行動を出力用の文字列に変換する。
	// 行動が設定されてない場合は長さ 0 の文字列を返す。
	String toOrderString() {
		if (!isFree()) {
			return id + " " + nextAction.getStringOfBasicAction();
		}
		return "";
	}

	public BasicAction getNextAction() {
		return nextAction;
	}

	public String brieafSelfIntro() {
		String s;
		s = Integer.toString(id) + " " + Integer.toString(getX()) + " "
				+ Integer.toString(getY());
		return s;
	}

	public boolean equalsJobType(JobType job) {
		if (jobtype == job) {
			return true;
		} else {
			return false;
		}
	}

}