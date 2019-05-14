package com.favouritedragon.dynamiccombat.skills.fist;

import dynamicswordskills.skills.SkillActive;
import dynamicswordskills.skills.SkillBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class Flurry extends SkillFists {

	private int numberOfBlows;
	private int blowCoolDown;
	private boolean isActive = false;

	private Flurry(Flurry skill) {
		super(skill);
	}

	public Flurry(String name) {
		super(name);
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public boolean hasAnimation() {
		return true;
	}


	@Override
	protected float getExhaustion() {
		return 5.0F - (0.2F * getLevel());
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