package com.favouritedragon.dynamiccombat.skills.fist.passive;

import com.favouritedragon.dynamiccombat.skills.AttackSkill;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import zdoctor.skilltree.api.SkillTreeApi;
import zdoctor.skilltree.api.enums.SkillFrameType;
import zdoctor.skilltree.api.skills.SkillAttributeModifier;
import zdoctor.skilltree.api.skills.interfaces.ISkillRequirment;
import zdoctor.skilltree.api.skills.interfaces.ISkillStackable;
import zdoctor.skilltree.api.skills.interfaces.ISkillTickable;
import zdoctor.skilltree.api.skills.interfaces.ISkillToggle;
import zdoctor.skilltree.api.skills.requirements.DescriptionRequirment;
import zdoctor.skilltree.api.skills.requirements.LevelRequirement;
import zdoctor.skilltree.api.skills.requirements.SkillPointRequirement;
import zdoctor.skilltree.skills.SkillBase;
import zdoctor.skilltree.skills.SkillSlot;
import zdoctor.skilltree.tabs.SkillTabs;

import java.util.*;

public class UnarmedCombat extends AttackSkill implements ISkillToggle, ISkillTickable, ISkillStackable {
	public final SkillAttributeModifier UNARMED_COMBAT;
	public static final String ATTRIBUTE_NAME = "attackSkill.unarmedCombat";
	private static int TIER;
	private List<ISkillRequirment> requirments;

	public UnarmedCombat() {
		super("UnarmedCombat", SkillTabs.enchantItem(Items.WOODEN_SWORD));
		UNARMED_COMBAT = new SkillAttributeModifier(ATTRIBUTE_NAME, 0, 0);
		setFrameType(SkillFrameType.NORMAL);

	}

	@Override
	public int getMaxTier(EntityLivingBase entity) {
		return 10;
	}


	@Override
	public boolean hasRequirments(EntityLivingBase entity) {
		return super.hasRequirments(entity);
	}

	@Override
	public void onSkillRePurchase(EntityLivingBase entity) {
		removeEntityModifier(entity, this);
	}

	@Override
	public void removeEntityModifier(EntityLivingBase entity, SkillBase skill) {
		for (AttributeModifier mod : entity.getEntityAttribute(getAttribute(entity, this)).getModifiers()) {
			if (mod.getName().equalsIgnoreCase(ATTRIBUTE_NAME)) {
				entity.getEntityAttribute(getAttribute(entity, skill)).removeModifier(mod);
			}
		}
	}

	@Override
	public void modifyEntity(EntityLivingBase entity, SkillBase skill) {
		if (!entity.getEntityAttribute(getAttribute(entity, skill)).hasModifier(getModifier(entity, skill))) {
			if (entity.getHeldItemMainhand() == ItemStack.EMPTY) {
				int tier = SkillTreeApi.getSkillTier(entity, skill);
				double modifier = Math.pow(1.16, tier);
				SkillAttributeModifier unarmedCombat = new SkillAttributeModifier(ATTRIBUTE_NAME, modifier, 0);
				entity.getEntityAttribute(getAttribute(entity, skill)).applyModifier(unarmedCombat);
				entity.getEntityAttribute(getAttribute(entity, skill)).applyModifier(getModifier(entity, skill));
			}
		}
	}

	@Override
	public void onActiveTick(EntityLivingBase entity, SkillBase skill, SkillSlot skillSlot) {
		if (entity.getHeldItemMainhand() != ItemStack.EMPTY) {
			if (entity.getEntityAttribute(getAttribute(entity, skill)).hasModifier(getModifier(entity, skill)))
				removeEntityModifier(entity, skill);
		} else if (!entity.getEntityAttribute(getAttribute(entity, skill)).hasModifier(getModifier(entity, skill)))
			modifyEntity(entity, skill);
	}

	// First one is free, but each level requires more xp
	@Override
	public List<ISkillRequirment> getRequirments(EntityLivingBase entity, boolean hasSkill) {
		if (hasSkill) {
			requirments.add(new LevelRequirement(SkillTreeApi.getSkillTier(entity, this) - 1));
			requirments.add(new SkillPointRequirement(SkillTreeApi.getSkillTier(entity, this) - 1));
			return requirments;
		} else {
			return Collections.EMPTY_LIST;
		}
	}

	@Override
	public SkillAttributeModifier getModifier(EntityLivingBase entity, SkillBase skill) {
		return UNARMED_COMBAT;
	}

}

