package com.favouritedragon.dynamiccombat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zdoctor.skilltree.api.skills.Skill;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = DynamicCombat.MODID)
public class PlayerSkillData implements INBTSerializable<NBTTagCompound> {

	@CapabilityInject(PlayerSkillData.class)
	private static final Capability<PlayerSkillData> PLAYER_SKILL_DATA_CAPABILITY = null;

	private final EntityPlayer player;

	//Determines whether or not the player can use skills- true means they've pressed the keybind, false means they haven't. This stop
	//the player accidentally using skills and what not.
	public boolean skillsActive;

	//Stores the skills the player has and whether they're activated
	private HashMap<Skill, Boolean> activatedSkills;

	public PlayerSkillData() {
		this(null); // Constructor for the registration method factory parameter
	}

	public PlayerSkillData(EntityPlayer player) {
		this.player = player;
		this.skillsActive = false;
		this.activatedSkills = new HashMap<>();;
	}

	public static final PlayerSkillData get(EntityPlayer player) {
		return player.getCapability(PLAYER_SKILL_DATA_CAPABILITY, null);
	}

	public void addSkill(Skill skill){
		this.activatedSkills.put(skill, true);
	}

	public void setSkillActive(Skill skill, boolean active) {
		this.activatedSkills.put(skill, active);
	}

	public void setActivatedSkills(HashMap<Skill, Boolean> activatedSkills) {
		this.activatedSkills = activatedSkills;
	}

	public void sync() {
	}

	private void update() {
	}

	@SubscribeEvent
	// The type parameter here has to be Entity, not EntityPlayer, or the event won't get fired.
	public static void onCapabilityLoad(AttachCapabilitiesEvent<Entity> event) {

		if (event.getObject() instanceof EntityPlayer)
			event.addCapability(new ResourceLocation(DynamicCombat.MODID, "PlayerSkillData"),
					new PlayerSkillData.Provider((EntityPlayer) event.getObject()));

	}

	@SubscribeEvent
	public static void onPlayerCloneEvent(PlayerEvent.Clone event) {

		PlayerSkillData newData = PlayerSkillData.get(event.getEntityPlayer());
		PlayerSkillData oldData = PlayerSkillData.get(event.getOriginal());

		newData.copyFrom(oldData, event.isWasDeath());
	}

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (!event.getEntity().world.isRemote && event.getEntity() instanceof EntityPlayerMP) {
			// Synchronises data after loading.
			PlayerSkillData data = PlayerSkillData.get((EntityPlayer) event.getEntity());
			if (data != null) data.sync();
		}
	}

	@SubscribeEvent
	public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {

		if (event.getEntityLiving() instanceof EntityPlayer) {

			EntityPlayer player = (EntityPlayer) event.getEntityLiving();

			if (PlayerSkillData.get(player) != null) {
				PlayerSkillData.get(player).update();
			}
		}
	}

	public boolean areSkillsActive() {
		return skillsActive;
	}

	public void setSkillsActive(boolean active) {
		this.skillsActive = active;
	}

	public void copyFrom(PlayerSkillData data, boolean respawn) {
		this.skillsActive = data.skillsActive;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound properties = new NBTTagCompound();
		properties.setBoolean("skillsActive", this.skillsActive);
	//	properties.setBoolean("activatedSkills", );
		return properties;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if (nbt != null) {
			this.skillsActive = nbt.getBoolean("skillsActive");
		}

	}

	public static class Provider implements ICapabilitySerializable<NBTTagCompound> {

		private final PlayerSkillData data;

		public Provider(EntityPlayer player) {
			data = new PlayerSkillData(player);
		}

		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			return capability == PLAYER_SKILL_DATA_CAPABILITY;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {

			if (capability == PLAYER_SKILL_DATA_CAPABILITY) {
				return PLAYER_SKILL_DATA_CAPABILITY.cast(data);
			}

			return null;
		}

		@Override
		public NBTTagCompound serializeNBT() {
			return data.serializeNBT();
		}

		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			data.deserializeNBT(nbt);
		}

	}


}
