package com.favouritedragon.dynamiccombat.skills.fist.active;

import com.favouritedragon.dynamiccombat.skills.AttackSkill;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.FMLClientHandler;
import zdoctor.skilltree.api.skills.SkillAttributeModifier;
import zdoctor.skilltree.api.skills.interfaces.ISkillStackable;
import zdoctor.skilltree.api.skills.interfaces.ISkillTickable;
import zdoctor.skilltree.api.skills.interfaces.ISkillToggle;
import zdoctor.skilltree.skills.SkillBase;
import zdoctor.skilltree.skills.SkillSlot;

import javax.annotation.Nullable;

public class PowerStrike extends AttackSkill implements ISkillToggle, ISkillTickable, ISkillStackable {
	public PowerStrike(String name, Item icon) {
		super(name, icon);
	}

	public PowerStrike(String name, ItemStack icon) {
		super(name, icon);
	}

	@Nullable
	@Override
	public SkillAttributeModifier getModifier(EntityLivingBase entity, SkillBase skill) {
		return null;
	}

	@Override
	public void onSkillRePurchase(EntityLivingBase entity) {

	}

	@Override
	public int getMaxTier(EntityLivingBase entity) {
		return 3;
	}

	@Override
	public void onActiveTick(EntityLivingBase entity, SkillBase skill, SkillSlot skillSlot) {

	}
}
