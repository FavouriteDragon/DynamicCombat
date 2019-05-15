package com.favouritedragon.dynamiccombat.skills.tabs;

import com.favouritedragon.dynamiccombat.skills.pages.fist.FistSkillPage;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import zdoctor.skilltree.tabs.SkillTabs;

public class SkillTreeTabs {

	public static final SkillTabs ATTACK_TAB = new SkillTabs("FistTab", new
			FistSkillPage()) {

		@Override
		public ItemStack getTabIconItem() {
			return SkillTabs.enchantItem(Items.SKULL);
		}
	};

	public static void init() {
	};
}
