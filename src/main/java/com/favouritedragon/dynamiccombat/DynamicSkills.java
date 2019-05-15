package com.favouritedragon.dynamiccombat;

import com.favouritedragon.dynamiccombat.skills.fist.Deflect;
import dynamicswordskills.skills.SkillBase;
import dynamicswordskills.skills.SwordBeam;

public abstract class DynamicSkills extends SkillBase {
	public static final SkillBase deflect = new Deflect("deflect");

	protected DynamicSkills(String name) {
		super(name, true);
	}

	protected DynamicSkills(SkillBase skill) {
		super(skill);
	}
}
