package com.favouritedragon.dynamiccombat.skills.skilltree.fist.passive;

import com.favouritedragon.dynamiccombat.skills.skilltree.AttackSkill;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import zdoctor.skilltree.api.SkillTreeApi;
import zdoctor.skilltree.api.enums.SkillFrameType;
import zdoctor.skilltree.api.skills.SkillAttributeModifier;
import zdoctor.skilltree.api.skills.interfaces.ISkillStackable;
import zdoctor.skilltree.api.skills.interfaces.ISkillTickable;
import zdoctor.skilltree.api.skills.interfaces.ISkillToggle;
import zdoctor.skilltree.api.skills.requirements.LevelRequirement;
import zdoctor.skilltree.api.skills.requirements.SkillPointRequirement;
import zdoctor.skilltree.skills.SkillBase;
import zdoctor.skilltree.skills.SkillSlot;
import zdoctor.skilltree.tabs.SkillTabs;

public class UnarmedCombat extends AttackSkill implements ISkillToggle, ISkillTickable, ISkillStackable {
	public static final String ATTRIBUTE_NAME = "attackSkill.unarmedCombat";
	private static int TIER;
	public final SkillAttributeModifier UNARMED_COMBAT;

	public UnarmedCombat() {
		super("UnarmedCombat", SkillTabs.enchantItem(Items.WOODEN_SWORD));
		UNARMED_COMBAT = new SkillAttributeModifier(ATTRIBUTE_NAME, 0, 0);
		setFrameType(SkillFrameType.NORMAL);
		addRequirement(new LevelRequirement(TIER - 1));
		addRequirement(new SkillPointRequirement(TIER - 1));

	}

	@Override
	public int getMaxTier(EntityLivingBase entity) {
		return 10;
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
				TIER = SkillTreeApi.getSkillTier(entity, skill);
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


	@Override
	public SkillAttributeModifier getModifier(EntityLivingBase entity, SkillBase skill) {
		return UNARMED_COMBAT;
	}

}

