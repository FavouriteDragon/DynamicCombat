package com.favouritedragon.dynamiccombat;

import com.favouritedragon.dynamiccombat.skills.active.fist.active.Deflect;
import com.favouritedragon.dynamiccombat.skills.active.fist.active.PowerStrike;
import dynamicswordskills.skills.SkillBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public abstract class DynamicSkills extends SkillBase {
	public static final SkillBase deflect = new Deflect("deflect");
	public static final SkillBase powerStrike = new PowerStrike("power_strike");

	protected DynamicSkills(String name, boolean register) {
		super(name, register);
	}

	protected DynamicSkills(SkillBase skill) {
		super(skill);
	}

	@Override
	public void addInformation(List<String> desc, EntityPlayer player) {
		super.addInformation(desc, player);
	}

}
