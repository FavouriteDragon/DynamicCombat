package com.favouritedragon.dynamiccombat.skills.fist;

import dynamicswordskills.skills.SkillActive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public abstract class SkillOffFist extends SkillActive {
	protected SkillOffFist(String name) {
		super(name);
	}

	@Override
	public boolean canUse(EntityPlayer player) {
		return super.canUse(player) && player.getHeldItem(EnumHand.OFF_HAND) == ItemStack.EMPTY;
	}
}
