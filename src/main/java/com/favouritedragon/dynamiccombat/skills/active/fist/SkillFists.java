package com.favouritedragon.dynamiccombat.skills.active.fist;

import dynamicswordskills.skills.SkillActive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public abstract class SkillFists extends SkillActive {

	protected SkillFists(String name) {
		super(name);
	}

	protected SkillFists(SkillFists skill) {
		super(skill);
	}

	@Override
	public boolean canUse(EntityPlayer player) {
		return super.canUse(player) && player.getHeldItem(EnumHand.MAIN_HAND) == ItemStack.EMPTY && player.getHeldItem(EnumHand.OFF_HAND) == ItemStack.EMPTY && !isActive();
	}
}
