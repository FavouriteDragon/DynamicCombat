package com.favouritedragon.dynamiccombat.skills.fist;

import dynamicswordskills.skills.SkillActive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public abstract class SkillMainFist extends SkillActive {
	protected SkillMainFist(String name) {
		super(name);
	}


	@Override
	public boolean canUse(EntityPlayer player) {
		return super.canUse(player) && player.getHeldItem(EnumHand.MAIN_HAND) == ItemStack.EMPTY;
	}
}
