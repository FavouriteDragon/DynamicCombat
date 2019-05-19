package com.favouritedragon.dynamiccombat.skills.active.fist;

import dynamicswordskills.skills.SkillActive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public abstract class SkillMainFist extends SkillActive {


	protected SkillMainFist(String name) {
		super(name);
	}

	protected SkillMainFist(SkillActive skill) {
		super(skill);
	}

	@Override
	public boolean canUse(EntityPlayer player) {
		return super.canUse(player) && player.getHeldItem(EnumHand.MAIN_HAND) == ItemStack.EMPTY && !isActive();
	}
}
