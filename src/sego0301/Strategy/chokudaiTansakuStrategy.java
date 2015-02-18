package sego0301.Strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sego0301.Actions.Actions;
import sego0301.Actions.MakeMuraInSigen;
import sego0301.Actions.MakeWokerFromCastle;
import sego0301.Actions.RandomWalk;
import sego0301.Alert.OpMuraOnSigen;
import sego0301.Alert.OpWokerOnSigen;
import sego0301.Alert.TypeOfAlert;
import sego0301.Alert.WokerMovingToSigen;
import sego0301.RuleData.BasicAction;
import sego0301.RuleData.JobType;
import sego0301.RuleData.TypeOfUnit;
import sego0301.function.CalculatorOfSeen;
import sego0301.function.GeneralFunction;
import sego0301.function.FunctionAboutScore;
import sego0301.main.Devil;
import sego0301.main.GeneralDirector;
import sego0301.main.OuterDirector;
import sego0301.main.Unit;
import sego0301.main.Point;

public class chokudaiTansakuStrategy extends Strategy {

	// chokudai縺ｯ讎りｦ√→縺励※縺ｯ莉･荳九�謌ｦ逡･
	// 蝓弱�莠ｺ繧剃ｽ懊ｉ縺ｪ縺�
	// 譚台ｺｺ縺瑚ｳ�ｺ舌ｒ隕九▽縺代◆繧峨�√◎縺薙↓蜷代°縺���
	// 譚台ｺｺ縺瑚ｳ�ｺ舌�荳翫〒雉�ｺ舌′貅懊∪縺｣縺ｦ縺�◆繧画搗繧貞ｻｺ縺ｦ繧�
	public chokudaiTansakuStrategy(Devil devil) {
		super(devil);
	}

	@Override
	public void doStrategy() {
		OuterDirector outerDirector = devil.getOuterDirector();
		// outerDirector縺檎ｮ｡逅�＠縺ｦ縺�ｋ繝ｯ繝ｼ繧ｫ繝ｪ繧ｹ繝医ｒ譖ｴ譁ｰ縲�
		// 謗｢邏｢荳ｭ縺ｮ繝ｯ繝ｼ繧ｫ
		Map<Integer, Unit> searchingWokerMap = outerDirector
				.renewSearchingWoker();
		// if (outerDirector.getSearchingWoker().isEmpty()) {
		// searchingWokerMap=Function.abstractTargetTypeUnits(devil.getMyCurrentUnits(),
		// TypeOfUnit.WOKER);
		// outerDirector.setSearchingWoker(searchingWokerMap);
		// }else {
		// searchingWokerMap = outerDirector.getSearchingWoker();
		// }
		// searchingWokerMap=Function.abstractTargetTypeUnits(devil.getMyCurrentUnits(),
		// TypeOfUnit.WOKER);

		outerDirector.renewAllWokerList();
		Map<Integer, Point> wokersMovingToSigen = outerDirector
				.getWokerMovingToSigen();
		Map<Integer, Point> wokersInSigen = outerDirector.getWokerInSigen();


		//System.err.println("s" + searchingWokerMap.size());
	//	System.err.println("m " + wokersMovingToSigen.size());
		System.err.println("I " + wokersInSigen.size());
		System.err.println("cyokudai");


		// 雉�ｺ千匱隕区凾縺ｫ縲『okerMovingToSigen縺ｫ蜉�縺医ｋ
		if (GeneralFunction.discoveredNewSigen(devil)) {
			System.err.println(devil.getCurrentTurn() + " 雉�ｺ千匱隕�");
			// movingTo縺ｫ譚台ｺｺ繧呈ｸ｡縺吶�ゅ◆縺�縺励�∵勸騾壹�譚台ｺｺ縺後＞縺溘ｉ
			if (searchingWokerMap.size() > 0) {
				// 雉�ｺ舌ｒ蜿悶ｊ蜃ｺ縺励※縲√Ρ繝ｼ繧ｫ繧貞牡繧雁ｽ薙※繧�
				for (Point sigen : GeneralFunction.getDiscoveredNewSigen(devil)) {
					if (!(OpMuraOnSigen.discoverdOpMuraOnSigen(sigen, devil))) {
						if (!(OpWokerOnSigen.discoverdOpWokerOnSigen(sigen,
								devil))) {

							// if (!(OpMuraOnSigen.discoverdOpMuraOnSigen(sigen,
							// devil)||OpWokerOnSigen.discoverdOpWokerOnSigen(sigen,
							// devil))) {
							// 隱ｰ縺九′縲∬ｳ�ｺ舌↓蜷代°縺｣縺ｦ縺�∪縺吶ｈ繧｢繝ｩ繝ｼ繝�
							devil.getAlertList().add(
									new WokerMovingToSigen(sigen,
											TypeOfAlert.wokerMovinToSigen));
							System.err.println("x:" + sigen.getX() + "	y:"
									+ sigen.getY());
							// 雉�ｺ舌�荳翫↓譚代ｒ蟒ｺ縺ｦ縺溷燕謠舌↓縺吶ｋ
							CalculatorOfSeen cal = new CalculatorOfSeen(devil);
							cal.calNewSeenIfUnitAdd(new Unit(0, new Point(sigen
									.getX(), sigen.getY()), 100,
									TypeOfUnit.MURA));
							// 雉�ｺ舌↓譛�繧りｿ代＞繝ｦ繝九ャ繝医ｒ蜿悶ｊ縺�縺�(蜈ｨ驛ｨ蜷ｫ繧√※)
							Map<Integer, Unit> nearSigenUnitMap = GeneralFunction
									.abstractNearestUnitFromTargetPoint(sigen,
											devil.getMyCurrentUnits());
							// 縺昴�荳ｭ縺ｧ譚台ｺｺ繧呈歓蜃ｺ
							Map<Integer, Unit> nearSigenWokerMap = GeneralFunction
									.abstractTargetTypeUnits(nearSigenUnitMap,
											TypeOfUnit.WOKER);
							List<Unit> nearSigenUnitList = FunctionAboutScore
									.convertUnitMapToUnitList(nearSigenWokerMap);
							if (nearSigenUnitList.size() > 0) {
								Unit wokerMovingToSigen = nearSigenUnitList
										.get(0);
								// 遘ｻ蜍穂ｸｭ縺ｮ繝ｯ繝ｼ繧ｫ縺ｨ遘ｻ蜍募�縺ｮ雉�ｺ舌ｒ髢｢騾｣莉倥¢繧�
								wokersMovingToSigen.put(
										wokerMovingToSigen.getId(), sigen);
								// 繝弱�繝槭ΝwokerMap縺九ｉ縺ｯ蜿悶ｊ髯､縺�
								searchingWokerMap.remove(wokerMovingToSigen
										.getId());
								wokerMovingToSigen
										.setJobtype(JobType.moveToSigen);
								// }
							}
						}
					}
				}
			}
		}

		// 騾壼ｸｸ縺ｮ繝ｯ繝ｼ繧ｫ縺ｫ蜻ｽ莉､
		if (searchingWokerMap.size() > 0) {
			for (Unit woker : searchingWokerMap.values()) {
				if (woker.equalsJobType(JobType.normal)) {
					FunctionAboutScore.setMaxScoreMoveDirection(devil.getSeen(),
							searchingWokerMap, woker);
					// }
					// UL縺梧が縺�％縺ｨ縺励↑縺�ｈ縺�↓(雉�ｺ占ｦ九▽縺代ｋ縺ｾ縺ｧ縺ｯ)
					if (devil.getSigenList().size() == 0) {
						devil.getOuterDirector().moveUorLwoker(
								searchingWokerMap);
					}
					// 驥阪↑縺｣縺溘ｉ迚�婿繧偵せ繝医ャ繝�
					devil.getOuterDirector().removeConcide(searchingWokerMap);
				}
			}
		}

		// outerDirector.renewAllWokerList();
		// 雉�ｺ舌∈遘ｻ蜍穂ｸｭ繝ｯ繝ｼ繧ｫ縺悟芦逹�縺励◆繧峨�∬ｳ�ｺ舌Ρ繝ｼ繧ｫ縺ｫ遘ｻ邀�
		outerDirector.transferWokerMovingToInSigen();
		// 繧上�縺九�縺瑚ｳ�ｺ蝉ｸ翫↓縺�ｋ縺ｪ繧峨�∵搗蟒ｺ險ｭ繧呈､懆ｨ�

		// for (Unit iterable_element : devil.getMyCurrentUnits().values()) {
		// System.err.println(iterable_element.getId());
		// }
		//

		if (wokersInSigen.size() > 0) {
			Map<Integer, Unit> wokerInSigenMap = GeneralFunction.convertIPMapToIUMap(
					wokersInSigen, devil.getMyCurrentUnits());
			Actions makeMura = new MakeMuraInSigen(devil);
			makeMura.doActions(wokerInSigenMap);
		}
		// if(searchingWokerMap.size()>0){
		outerDirector.renewAllWokerList();

		// for (Integer iterable_element : wokersMovingToSigen.keySet()) {
		// System.err.println(iterable_element);
		// }
		// for (Integer iterable_element : wokersInSigen.keySet()) {
		// System.err.println(iterable_element);
		// }
		outerDirector.moveWokerMovingToSigen();
		outerDirector.freeWokerInSigen();

		// }

		// 荳翫�縲√Θ繝九ャ繝医′蛟九��〒蛻､譁ｭ

		// 蜆ｪ蜈亥ｺｦ縺ｯ蟾ｦ縺ｮ陦悟虚
		// if(Function.isCastleAboveSlantingLine(devil)){
		// BasicAction[]
		// baActions={BasicAction.moveToRight,BasicAction.moveToDown,BasicAction.moveToLeft};
		// ScoreFunction.setMaxScoreMoveThinkingAllUnit(devil.getSeen(),
		// wokerMap, baActions);
		// }else {
		// BasicAction[]
		// baActions={BasicAction.moveToRight,BasicAction.moveToDown,BasicAction.moveToUp};
		// ScoreFunction.setMaxScoreMoveThinkingAllUnit(devil.getSeen(),
		// wokerMap, baActions);
		// }

		// BasicAction[]
		// baActions={BasicAction.moveToRight,BasicAction.moveToDown,BasicAction.moveToUp,BasicAction.moveToLeft};
		// ScoreFunction.setMaxScoreMoveThinkingAllUnit(devil.getSeen(),
		// wokerMap, baActions);

		// 荳翫ｂ蟾ｦ繧りｨｱ縺輔ｓ螂ｴ
		// BasicAction[]
		// baActions={BasicAction.moveToRight,BasicAction.moveToDown};
		// ScoreFunction.setMaxScoreMoveThinkingAllUnit(devil.getSeen(),
		// wokerMap, baActions);

		//
		// TODO 閾ｪ蜍慕函謌舌＆繧後◆繝｡繧ｽ繝�ラ繝ｻ繧ｹ繧ｿ繝�

	}

	@Override
	public String getName() {
		// TODO 閾ｪ蜍慕函謌舌＆繧後◆繝｡繧ｽ繝�ラ繝ｻ繧ｹ繧ｿ繝�
		return "chokudai雉�ｺ先爾邏｢謌ｦ逡･";
	}

}
