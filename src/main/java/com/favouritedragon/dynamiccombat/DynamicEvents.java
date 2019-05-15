package com.favouritedragon.dynamiccombat;

import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zdoctor.skilltree.api.SkillTreeApi;

@Mod.EventBusSubscriber(modid = DynamicCombat.MODID)
public class DynamicEvents {


	@SubscribeEvent
	public static void playerAdvancementEarned(AdvancementEvent event) {
		debug(event);
	}

	public static void debug(AdvancementEvent event) {
		if (event.getAdvancement().getDisplay() != null) {
			System.out.println("Advancement: " + event.getAdvancement());
			SkillTreeApi.addSkillPoints(event.getEntityPlayer(), 10);
		}
	}

}
