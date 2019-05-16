package com.favouritedragon.dynamiccombat.skills.fist.active;

import com.favouritedragon.dynamiccombat.skills.AttackSkill;
import com.favouritedragon.dynamiccombat.skills.fist.SkillMainFist;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import zdoctor.skilltree.api.skills.SkillAttributeModifier;
import zdoctor.skilltree.api.skills.interfaces.ISkillToggle;
import zdoctor.skilltree.skills.SkillBase;

import javax.annotation.Nullable;

public class PowerStrike extends SkillMainFist implements ISkillToggle {


	protected PowerStrike(String name) {
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
	public dynamicswordskills.skills.SkillBase newInstance() {
		return null;
	}
}
