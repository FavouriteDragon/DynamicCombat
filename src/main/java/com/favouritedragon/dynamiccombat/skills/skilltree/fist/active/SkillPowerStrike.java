package com.favouritedragon.dynamiccombat.skills.skilltree.fist.active;

import com.favouritedragon.dynamiccombat.skills.skilltree.AttackSkill;
import com.favouritedragon.dynamiccombat.skills.skilltree.fist.passive.UnarmedCombat;
import dynamicswordskills.entity.DSSPlayerInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import zdoctor.skilltree.api.SkillTreeApi;
import zdoctor.skilltree.api.enums.SkillFrameType;
import zdoctor.skilltree.api.skills.SkillAttributeModifier;
import zdoctor.skilltree.api.skills.interfaces.ISkillStackable;
import zdoctor.skilltree.api.skills.interfaces.ISkillTickable;
import zdoctor.skilltree.api.skills.interfaces.ISkillToggle;
import zdoctor.skilltree.api.skills.requirements.LevelRequirement;
import zdoctor.skilltree.api.skills.requirements.PreviousSkillRequirement;
import zdoctor.skilltree.api.skills.requirements.SkillPointRequirement;
import zdoctor.skilltree.skills.SkillBase;
import zdoctor.skilltree.skills.SkillSlot;
import zdoctor.skilltree.tabs.SkillTabs;

import javax.annotation.Nullable;

public class SkillPowerStrike extends AttackSkill implements ISkillStackable {

	public SkillPowerStrike(String name, Item icon) {
		super(name, icon);
		setParent(new UnarmedCombat());
	}

	public SkillPowerStrike(String name, ItemStack icon) {
		super(name, icon);
	}

	@Nullable
	@Override
	public SkillAttributeModifier getModifier(EntityLivingBase entityLivingBase, SkillBase skillBase) {
		return null;
	}

	@Override
	public void onSkillRePurchase(EntityLivingBase entityLivingBase) {
		if (entityLivingBase instanceof EntityPlayer) {
			//DSSPlayerInfo.get((EntityPlayer) entityLivingBase).grantSkill(powerStrike);
		}
	}

	@Override
	public int getMaxTier(EntityLivingBase entity) {
		return 5;
	}



}