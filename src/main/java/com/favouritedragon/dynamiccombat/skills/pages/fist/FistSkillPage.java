package com.favouritedragon.dynamiccombat.skills.pages.fist;

import com.favouritedragon.dynamiccombat.skills.fist.passive.UnarmedCombat;
import zdoctor.skilltree.skills.pages.SkillPageBase;

public class FistSkillPage extends SkillPageBase {

	private UnarmedCombat uC;
	public FistSkillPage() {
		super("FistPage");
	}

	@Override
	public void registerSkills() {
		uC = new UnarmedCombat();


	}

	@Override
	public void loadPage() {
		addSkill(uC, 0, 1);
	}

	@Override
	public BackgroundType getBackgroundType() {
		return BackgroundType.SANDSTONE;
	}


}
