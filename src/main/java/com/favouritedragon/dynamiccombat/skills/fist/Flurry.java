package com.favouritedragon.dynamiccombat.skills.fist;

import dynamicswordskills.client.DSSKeyHandler;
import dynamicswordskills.network.PacketDispatcher;
import dynamicswordskills.network.bidirectional.ActivateSkillPacket;
import dynamicswordskills.ref.Config;
import dynamicswordskills.skills.SkillActive;
import dynamicswordskills.skills.SkillBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Flurry extends SkillFists {

	private int numberOfBlows;
	private int blowCoolDown;
	private boolean isActive = false;

	private Flurry(Flurry skill) {
		super(skill);
	}

	public Flurry(String name) {
		super(name);
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public boolean hasAnimation() {
		return true;
	}


	@Override
	protected float getExhaustion() {
		return 5.0F - (0.2F * getLevel());
	}

	@Override
	protected boolean onActivated(World world, EntityPlayer player) {
		return false;
	}

	@Override
	protected void onDeactivated(World world, EntityPlayer player) {

	}

	@Override
	public SkillBase newInstance() {
		return null;
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
}