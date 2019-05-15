package com.favouritedragon.dynamiccombat.skills.sword.passive.dss;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import zdoctor.skilltree.api.enums.SkillFrameType;
import zdoctor.skilltree.api.skills.Skill;
import zdoctor.skilltree.api.skills.interfaces.ISkillRequirment;
import zdoctor.skilltree.api.skills.interfaces.ISkillToggle;
import zdoctor.skilltree.skills.ISkillListener;

public class SwordBasic extends Skill implements ISkillToggle {
	public SwordBasic(String name, Item iconIn) {
		super(name, iconIn);
	}

	public SwordBasic(String name, SkillFrameType type, Item iconIn) {
		super(name, type, iconIn);
	}

	public SwordBasic(String name, ItemStack iconIn) {
		super(name, iconIn);
	}

	public SwordBasic(String name, SkillFrameType type, ItemStack iconIn) {
		super(name, type, iconIn);
	}

	public SwordBasic(String name, ItemStack iconIn, ISkillRequirment... requirements) {
		super(name, iconIn, requirements);
	}

	public SwordBasic(String name, SkillFrameType type, ItemStack iconIn, ISkillRequirment... requirements) {
		super(name, type, iconIn, requirements);
	}
}
