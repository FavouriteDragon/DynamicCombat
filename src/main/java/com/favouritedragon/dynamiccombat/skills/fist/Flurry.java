package com.favouritedragon.dynamiccombat.skills.fist;

import dynamicswordskills.client.DSSKeyHandler;
import dynamicswordskills.network.PacketDispatcher;
import dynamicswordskills.network.bidirectional.ActivateSkillPacket;
import dynamicswordskills.ref.Config;
import dynamicswordskills.skills.SkillActive;
import dynamicswordskills.skills.SkillBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class Flurry extends SkillFists {

	private int numberOfBlows;
	private int blowCoolDown;
	private int activeTimer;

	private Flurry(Flurry skill) {
		super(skill);
	}

	public Flurry(String name) {
		super(name);
	}


	@Override
	public boolean isActive() {
		return activeTimer > 0;
	}

	@Override
	public boolean hasAnimation() {
		return true;
	}


	@Override
	protected float getExhaustion() {
		return 4.0F - (0.2F * getLevel());
	}

	@Override
	protected boolean onActivated(World world, EntityPlayer player) {
		numberOfBlows = 3 + level;
		blowCoolDown = 6 - level;
		activeTimer = blowCoolDown * numberOfBlows + 2;
		return isActive();
	}

	@Override
	protected void onDeactivated(World world, EntityPlayer player) {
		activeTimer = 0;
	}

	@Override
	public SkillBase newInstance() {
		return new Flurry(this);
	}

	@Override
	protected void levelUp(EntityPlayer player) {
		super.levelUp(player);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isKeyListener(Minecraft mc, KeyBinding key) {
		return (key == DSSKeyHandler.keys[DSSKeyHandler.KEY_ATTACK] || (Config.allowVanillaControls() && key == mc.gameSettings.keyBindAttack));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean keyPressed(Minecraft mc, KeyBinding key, EntityPlayer player) {
		if (canExecute(player)) {
			PacketDispatcher.sendToServer(new ActivateSkillPacket(this));
			return true;
		}
		return false;
	}

	@Override
	public void onUpdate(EntityPlayer player) {
		super.onUpdate(player);
		if (isActive()) {
			--activeTimer;
			List<Entity> e = player.getEntityWorld().getEntitiesWithinAABB();
		}
	}
}