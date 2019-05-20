package com.favouritedragon.dynamiccombat.skills.active.fist.active;

import com.favouritedragon.dynamiccombat.skills.active.fist.SkillFists;
import dynamicswordskills.client.DSSKeyHandler;
import dynamicswordskills.entity.DSSPlayerInfo;
import dynamicswordskills.network.PacketDispatcher;
import dynamicswordskills.network.bidirectional.ActivateSkillPacket;
import dynamicswordskills.ref.Config;
import dynamicswordskills.ref.ModSounds;
import dynamicswordskills.skills.SkillBase;
import dynamicswordskills.util.PlayerUtils;
import dynamicswordskills.util.TargetUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class Deflect extends SkillFists {

	/**
	 * Timer during which player is considered actively deflecting
	 */
	private int deflectTimer;

	/**
	 * Number of attacks deflected this activation cycle
	 */
	private int attacksDeflected;

	/**
	 * Only for double-tap activation: Current number of ticks remaining before skill will not activate
	 */
	@SideOnly(Side.CLIENT)
	private int ticksTilFail;

	/**
	 * Notification to play miss sound; set to true when activated and false when attack deflected
	 */
	private boolean playMissSound;

	public Deflect(String name) {
		super(name);
	}

	protected Deflect(SkillFists skill) {
		super(skill);
	}

	@Override
	public SkillBase newInstance() {
		return new Deflect(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(List<String> desc, EntityPlayer player) {
		desc.add(new TextComponentTranslation(getInfoString("info", 1), (int) (getDisarmChance(player, null) * 100)).getUnformattedText());
		desc.add(new TextComponentTranslation(getInfoString("info", 2), (int) (2.5F * (getActiveTime() - getDeflectDelay()))).getUnformattedText());
		desc.add(new TextComponentTranslation(getInfoString("info", 3), getMaxDeflects()).getUnformattedText());
		desc.add(getTimeLimitDisplay(getActiveTime() - getDeflectDelay()));
		desc.add(getExhaustionDisplay(getExhaustion()));
	}

	@Override
	public boolean isActive() {
		return (deflectTimer > 0);
	}

	@Override
	protected float getExhaustion() {
		return 0.7F - (0.02F * level);
	}

	/**
	 * Number of ticks that skill will be considered active
	 */
	private int getActiveTime() {
		return 8 + level;
	}

	/**
	 * Number of ticks before player may attempt to use this skill again
	 */
	private int getDeflectDelay() {
		return (7 - (level / 2));
	}

	/**
	 * The maximum number of attacks that may be deflected per use of the skill
	 */
	private int getMaxDeflects() {
		return (1 + level) / 2;
	}

	/**
	 * Returns player's chance to disarm an attacker
	 *
	 * @param attacker if the attacker is an EntityPlayer, their Deflect score will decrease their chance
	 *                 of being disarmed
	 */
	private float getDisarmChance(EntityPlayer player, EntityLivingBase attacker) {
		float penalty = 0.0F;
		float bonus = Config.getDisarmTimingBonus() * (deflectTimer > 0 ? (deflectTimer - getDeflectDelay()) : 0);
		if (attacker instanceof EntityPlayer) {
			penalty = Config.getDisarmPenalty() * DSSPlayerInfo.get((EntityPlayer) attacker).getSkillLevel(this);
		}
		return ((level * 0.1F) - penalty + bonus);
	}



	@Override
	@SideOnly(Side.CLIENT)
	public boolean isKeyListener(Minecraft mc, KeyBinding key) {
		return (key == DSSKeyHandler.keys[DSSKeyHandler.KEY_DOWN] || (Config.allowVanillaControls() && key == mc.gameSettings.keyBindBack));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean keyPressed(Minecraft mc, KeyBinding key, EntityPlayer player) {
		if (canExecute(player)) {
			if (Config.requiresDoubleTap()) {
				if (ticksTilFail > 0) {
					PacketDispatcher.sendToServer(new ActivateSkillPacket(this));
					ticksTilFail = 0;
					player.swingArm(EnumHand.MAIN_HAND);
					return true;
				} else {
					ticksTilFail = 6;
				}
			} else if (key != mc.gameSettings.keyBindBack) { // activate on first press, but not for vanilla key!
				PacketDispatcher.sendToServer(new ActivateSkillPacket(this));
				player.swingArm(EnumHand.MAIN_HAND);
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean onActivated(World world, EntityPlayer player) {
		deflectTimer = getActiveTime();
		attacksDeflected = 0;
		playMissSound = true;
		player.swingArm(EnumHand.MAIN_HAND);
		return isActive();
	}

	@Override
	protected void onDeactivated(World world, EntityPlayer player) {
		deflectTimer = 0;
	}

	@Override
	public void onUpdate(EntityPlayer player) {
		if (isActive()) {
			if (--deflectTimer <= getDeflectDelay() && playMissSound) {
				playMissSound = false;
				PlayerUtils.playSoundAtEntity(player.getEntityWorld(), player, ModSounds.SWORD_MISS, SoundCategory.PLAYERS, 0.4F, 0.5F);
			}
		} else if (player.getEntityWorld().isRemote && ticksTilFail > 0) {
			--ticksTilFail;
		}
	}

	@Override
	public boolean onBeingAttacked(EntityPlayer player, DamageSource source) {
		if (source.getTrueSource() instanceof EntityLivingBase) {
			EntityLivingBase attacker = (EntityLivingBase) source.getTrueSource();
			if (attacksDeflected < getMaxDeflects() && deflectTimer > getDeflectDelay() && !attacker.getHeldItemMainhand().isEmpty() && PlayerUtils.isWeapon(player.getHeldItemMainhand())) {
				if (player.getEntityWorld().rand.nextFloat() < getDisarmChance(player, attacker)) {
					PlayerUtils.dropHeldItem(attacker);
				}
				++attacksDeflected; // increment after disarm
				player.swingArm(EnumHand.MAIN_HAND);
				PlayerUtils.playSoundAtEntity(player.getEntityWorld(), player, ModSounds.SWORD_STRIKE, SoundCategory.PLAYERS, 0.4F, 0.5F);
				playMissSound = false;
				TargetUtils.knockTargetBack(attacker, player);
				return true;
			} // don't deactivate early, as there is a delay between uses
		}
		return false;
	}

}
