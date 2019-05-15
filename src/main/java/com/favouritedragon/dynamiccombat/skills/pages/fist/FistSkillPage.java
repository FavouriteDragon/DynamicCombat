package com.favouritedragon.dynamiccombat.skills.pages.fist;

import com.favouritedragon.dynamiccombat.skills.fist.passive.UnarmedCombat;
import zdoctor.skilltree.skills.pages.SkillPageBase;

public class FistSkillPage extends SkillPageBase {

	private UnarmedCombat uC1;
	private UnarmedCombat uC2;
	private UnarmedCombat uC3;
	private UnarmedCombat uC4;
	public FistSkillPage() {
		super("FistPage");
	}

	@Override
	public void registerSkills() {
		uC1 = new UnarmedCombat();
		uC2 = (UnarmedCombat) new UnarmedCombat().setParent(uC1);
		uC3 = (UnarmedCombat) new UnarmedCombat().setParent(uC2);;
		uC4 = (UnarmedCombat) new UnarmedCombat().setParent(uC3);;


	}

	@Override
	public void loadPage() {
		addSkill(uC1, 0, 1);
		addSkill(uC2, 1, 1);
		addSkill(uC3, 2, 1);
		addSkill(uC4, 3, 1);
	}

	@Override
	public BackgroundType getBackgroundType() {
		return BackgroundType.SANDSTONE;
	}
}
