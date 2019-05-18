package com.favouritedragon.dynamiccombat.proxy;

import com.favouritedragon.dynamiccombat.skills.skilltree.tabs.SkillTreeTabs;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zdoctor.skilltree.api.SkillTreeApi;
import zdoctor.skilltree.events.SkillHandlerEvent;

public class CommonProxy implements IProxy {
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		SkillTreeTabs.init();
	}

	@Override
	public void Init(FMLInitializationEvent event) {


	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {

	}

	@Override
	public void registerRender() {

	}

	@SubscribeEvent
	public static void firstLoad(SkillHandlerEvent.FirstLoadEvent e) {
		SkillTreeApi.addSkillPoints(e.owner, 5);
	}
}
