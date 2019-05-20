package com.favouritedragon.dynamiccombat;

import dynamicswordskills.entity.DSSPlayerInfo;
import dynamicswordskills.skills.ArmorBreak;
import dynamicswordskills.skills.ICombo;
import dynamicswordskills.skills.MortalDraw;
import dynamicswordskills.skills.SkillBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
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

	@SubscribeEvent(priority= EventPriority.LOWEST)
	public void onHurt(LivingHurtEvent event) {
		if (event.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			DSSPlayerInfo skills = DSSPlayerInfo.get(player);
			if (skills.isSkillActive(DynamicSkills.powerStrike)) {
				((ArmorBreak) skills.getPlayerSkill(DynamicSkills.powerStrike)).onImpact(player, event);

			}
		}
	}

}
