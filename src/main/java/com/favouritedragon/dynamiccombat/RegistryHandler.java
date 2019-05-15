package com.favouritedragon.dynamiccombat;

import dynamicswordskills.DynamicSwordSkills;
import dynamicswordskills.skills.SkillBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = DynamicCombat.MODID)
public class RegistryHandler {
	private static final int LIVING_UPDATE_INTERVAL = 3;
	private static final int PROJECTILE_UPDATE_INTERVAL = 10;

	/**
	 * Private helper method for registering entities; keeps things neater. For some reason, Forge 1.11.2 wants a
	 * ResourceLocation and a string name... probably because it's transitioning to the registry system.
	 */
	private static void registerEntity(Class<? extends Entity> entityClass, String name, int id, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		ResourceLocation registryName = new ResourceLocation(DynamicCombat.MODID, name);
		EntityRegistry.registerModEntity(registryName, entityClass, registryName.toString(), id, DynamicCombat.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
	}

	/**
	 * Private helper method for registering entities with eggs; keeps things neater. For some reason, Forge 1.11.2
	 * wants a ResourceLocation and a string name... probably because it's transitioning to the registry system.
	 */
	private static void registerEntityAndEgg(Class<? extends Entity> entityClass, String name, int id, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggColour, int spotColour) {
		ResourceLocation registryName = new ResourceLocation(DynamicCombat.MODID, name);
		EntityRegistry.registerModEntity(registryName, entityClass, registryName.toString(), id, DynamicCombat.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
		EntityRegistry.registerEgg(registryName, eggColour, spotColour);
	}
	/** Mapping of mobs to skill orb drops */
	private static final Map<Class<? extends EntityLivingBase>, ItemStack> dropsList = new HashMap<Class<? extends EntityLivingBase>, ItemStack>();

	/** Adds a mob-class to skill orb mapping */
	private static void addDrop(Class<? extends EntityLivingBase> mobClass, SkillBase skill) {
		ItemStack stack = new ItemStack(DynamicSwordSkills.skillOrb, 1, skill.getId());
		dropsList.put(mobClass, stack);
	}

	public static void initializeDrops() {
		addDrop(EntityZombie.class, DynamicSkills.deflect);
	}

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {

	}

	public static void registerItems() {

	}

	public static void registerEntities() {
		int id = 0;


	}

	public static void registerLoot() {

	}

	public static void registerAll() {
		registerLoot();
		registerEntities();
		registerItems();
		initializeDrops();
	}

	//Register skills
	/*@SubscribeEvent
	public static void register(RegistryEvent.Register<SkillActive> event) {

	}**/
}
