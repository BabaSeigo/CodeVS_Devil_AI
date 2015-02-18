package sego0301.RuleData;

public enum TypeOfUnit {

			WOKER(2000, 4, 2, 40, 0),
			KNIGHT(5000, 4, 2, 20, 1),
			FIGHTER(5000, 4, 2,40, 2),
			ASSASSIN(5000, 4, 2, 60, 3),
			CASTLE(50000, 10, 10,999999999, 4),
			MURA(20000, 10, 2, 100, 5),
			KYOTEN(20000, 4, 2, 500, 6);

	// 変数
	private final int MAXHP;
	private final int VIEWRANGE;
	private final int ATTACKRANGE;
	private final int COST;
	private final int TYPE;

	private final Integer[][] damegeTable = { {100,100,100,100,100,100,100},
										{100,500,200,200,200,200,200},
										{500,1600,500,200,200,200,200},
										{1000,500,1000,500,200,200,200},
										{100,100,100,100,100,100,100},
										{100,100,100,100,100,100,100},
										{100,100,100,100,100,100,100}
	};


	TypeOfUnit(int maxHP, int viewRange, int attackRange, int cost, int type) {
		this.MAXHP = maxHP;
		this.VIEWRANGE = viewRange;
		this.ATTACKRANGE = attackRange;
		this.COST = cost;
		this.TYPE = type;
	}

	public int getMAXHP() {
		return MAXHP;
	}

	public int getVIEWRANGE() {
		return VIEWRANGE;
	}

	public int getATTACKRANGE() {
		return ATTACKRANGE;
	}

	public int getCOST() {
		return COST;
	}

	public int getTYPE() {
		return TYPE;
	}

	public static int getNUMBEROFUNITS() {
		final int NUMBEROFUNITS=7;
		return NUMBEROFUNITS;
	}

	public Integer[][] getDamegeTable() {
		return damegeTable;
	}
	public int attackDamage(TypeOfUnit enemy){
		return damegeTable[this.TYPE][enemy.TYPE];
	}

	public int receiveDamage(TypeOfUnit enemy){
		return damegeTable[enemy.TYPE][this.TYPE];
	}

	public static int convertTypeToNum(TypeOfUnit type){
		if(type == WOKER){
			return 0;
		}if(type == KNIGHT){
			return 1;
		}if(type == FIGHTER){
			return 2;
		}if(type == ASSASSIN){
			return 3;
		}if(type == CASTLE){
			return 4;
		}if(type == MURA){
			return 5;
		}if(type == KYOTEN){
			return 6;
		}
		else{
			System.err.println("不正なタイプ");
			return -100;
		}
	}

	public static TypeOfUnit convertNumToType(int num){
		if(num == 0){
			return WOKER;
		}if(num == 1){
			return KNIGHT;
		}if(num == 2){
			return FIGHTER;
		}if(num == 3){
			return ASSASSIN;
		}if(num == 4){
			return CASTLE;
		}if(num == 5){
			return MURA;
		}if(num == 6){
			return KYOTEN;
		}else{
			System.err.println("不正なナンバータイプ");
			return null;
		}
	}



}
