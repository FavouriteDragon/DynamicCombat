package com.favouritedragon.dynamiccombat.skills.fist;

import dynamicswordskills.skills.SkillActive;
import dynamicswordskills.skills.SkillBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class Flurry extends SkillActive {

	protected Flurry(String name) {
		super(name);
	}

	@Override
	public boolean isActive() {
		return false;
	}

	@Override
	protected float getExhaustion() {
		return 0;
	}

	@Override
	protected boolean onActivated(World world, EntityPlayer player) {
		return false;
	}

	@Override
	protected void onDeactivated(World world, EntityPlayer player) {

	}

	@Override
	public SkillBase newInstance() {
		return null;
	}
}