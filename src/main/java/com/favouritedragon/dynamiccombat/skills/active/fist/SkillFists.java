package com.favouritedragon.dynamiccombat.skills.fist;

import dynamicswordskills.skills.SkillActive;
import dynamicswordskills.skills.SkillBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public abstract class SkillFists extends SkillActive {

	protected SkillFists(String name) {
		super(name);
	}

	protected SkillFists(SkillFists skill) {
		super(skill);
	}

	@Override
	public boolean canUse(EntityPlayer player) {
		return super.canUse(player) && player.getHeldItem(EnumHand.MAIN_HAND) == ItemStack.EMPTY && player.getHeldItem(EnumHand.OFF_HAND) == ItemStack.EMPTY;
	}
}
