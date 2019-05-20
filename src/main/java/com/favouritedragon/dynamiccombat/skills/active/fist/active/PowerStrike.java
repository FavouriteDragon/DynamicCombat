package com.favouritedragon.dynamiccombat.skills.active.fist.active;

import com.favouritedragon.dynamiccombat.skills.active.fist.SkillMainFist;
import dynamicswordskills.client.DSSKeyHandler;
import dynamicswordskills.entity.DSSPlayerInfo;
import dynamicswordskills.entity.DirtyEntityAccessor;
import dynamicswordskills.network.PacketDispatcher;
import dynamicswordskills.network.bidirectional.ActivateSkillPacket;
import dynamicswordskills.ref.Config;
import dynamicswordskills.ref.ModSounds;
import dynamicswordskills.skills.ILockOnTarget;
import dynamicswordskills.skills.SwordBasic;
import dynamicswordskills.util.DamageUtils;
import dynamicswordskills.util.PlayerUtils;
import dynamicswordskills.util.TargetUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class PowerStrike extends SkillMainFist {

	/**
	 * Set to 1 when triggered; set to 0 when target struck in onImpact()
	 */
	private int activeTimer = 0;

	/**
	 * Current charge time
	 */
	private int charge = 0;

	/**
	 * Flags whether the vanilla keyBindAttack was used to trigger this skill, in which
	 * case the keybinding state must be manually set to false once the skill activates;
	 * this is because the key is still pressed, and vanilla behavior is to attack like
	 * crazy as long as the key is held, which is not very cool. For custom key bindings
	 * this is not an issue, as it only results in an attack when the key is first pressed.
	 * <p>
	 * Another issue: while mouse state is true, if the cursor moves over a block, the player
	 * will furiously swing his arm at it, as though trying to break it. Perhaps it is better
	 * to set the key state to false as before and track 'buttonstate' from within the skill,
	 * though in that case it needs to listen for key releases as well as presses.
	 * Note that this is the default vanilla behavior for holding down the attack key, so
	 * perhaps it is best to leave it as is.
	 */
	private boolean requiresReset;

	public PowerStrike(String name) {
		super(name);
	}

	private PowerStrike(PowerStrike skill) {
		super(skill);
	}

	@Override
	public PowerStrike newInstance() {
		return new PowerStrike(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(List<String> desc, EntityPlayer player) {
		desc.add(getChargeDisplay(getChargeTime(player)));
		desc.add(getExhaustionDisplay(getExhaustion()));
	}


	@Override
	protected boolean allowUserActivation() {
		return false;
	}

	@Override
	public boolean isActive() {
		return activeTimer > 0;
	}

	@Override
	protected float getExhaustion() {
		return 2.0F - (0.1F * level);
	}

	/**
	 * Returns number of ticks required before attack will execute: 20 - level
	 */
	private int getChargeTime(EntityPlayer player) {
		return 20 - level;
	}

	/**
	 * Returns true if the skill is still charging up; always false on the server, as charge is handled client-side
	 */
	public boolean isCharging(EntityPlayer player) {
		ILockOnTarget target = DSSPlayerInfo.get(player).getTargetingSkill();
		return charge > 0 && target != null && target.isLockedOn();
	}

	@Override
	public boolean canUse(EntityPlayer player) {
		return super.canUse(player) && !isActive() && PlayerUtils.isWeapon(player.getHeldItemMainhand());
	}

	/**
	 * ArmorBreak does not listen for any keys so that there is no chance it is bypassed by
	 * another skill processing first; instead, keyPressed must be called manually, both
	 * when the attack key is pressed (and, to handle mouse clicks, when released)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isKeyListener(Minecraft mc, KeyBinding key) {
		return false;
	}

	/**
	 * Must be called manually when the attack key is pressed (and, for the mouse, when released);
	 * this is necessary to allow charging to start from a single key press, when other skills
	 * might otherwise preclude ArmorBreak's keyPressed from being called.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean keyPressed(Minecraft mc, KeyBinding key, EntityPlayer player) {
		requiresReset = (key == mc.gameSettings.keyBindAttack);
		if (requiresReset || key == DSSKeyHandler.keys[DSSKeyHandler.KEY_ATTACK]) {
			charge = getChargeTime(player);
			if (requiresReset) {
				// manually set the keybind state, since it will not be set by the canceled mouse event
				// releasing the mouse unsets it normally, but it must be manually unset if the skill is triggered
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
			}
			return true; // doesn't matter, as ArmorBreak is handled outside the normal framework
		}
		return false;
	}

	/**
	 * Returns true if the attack key is still pressed (i.e. ArmorBreak should continue to charge)
	 */
	@SideOnly(Side.CLIENT)
	public boolean isKeyPressed() {
		return (DSSKeyHandler.keys[DSSKeyHandler.KEY_ATTACK].isKeyDown() || (Config.allowVanillaControls() && Minecraft.getMinecraft().gameSettings.keyBindAttack.isKeyDown()));
	}

	/**
	 * ArmorBreak's activation was triggered from the client side and it will be over
	 * on the server by the time the client receives the packet, so don't bother
	 */
	@Override
	protected boolean sendClientUpdate() {
		return true;
	}

	@Override
	protected boolean onActivated(World world, EntityPlayer player) {
		activeTimer = 1; // needs to be active for hurt event to process correctly
		if (world.isRemote) {
			player.swingArm(EnumHand.MAIN_HAND);
		} else {
			// Attack first so skill still active upon impact, then set timer to zero
			ILockOnTarget skill = DSSPlayerInfo.get(player).getTargetingSkill();
			if (skill != null && skill.isLockedOn() && TargetUtils.canReachTarget(player, skill.getCurrentTarget())) {
				player.attackTargetEntityWithCurrentItem(skill.getCurrentTarget());
			}
		}
		return false;
	}

	@Override
	protected void onDeactivated(World world, EntityPlayer player) {
		activeTimer = 0;
		charge = 0;
		DSSPlayerInfo.get(player).armSwing = 0.0F;
	}

	@Override
	public void onUpdate(EntityPlayer player) {
		if (isCharging(player)) {
			if (isKeyPressed() && PlayerUtils.isWeapon(player.getHeldItemMainhand())) {
				if (!player.isSwingInProgress) {
					int maxCharge = getChargeTime(player);
					if (charge < maxCharge - 1) {
						DSSPlayerInfo.get(player).armSwing = 0.25F + 0.75F * ((float) (maxCharge - charge) / (float) maxCharge);
					}
					--charge;
				}
				// ArmorBreak triggers here, on the client side first, so onActivated need not process on the client
				if (charge == 0) {
					// can't use the standard animation methods to prevent key/mouse input,
					// since Armor Break will not return true for isActive
					DSSPlayerInfo.get(player).setAttackTime(4); // flag for isAnimating? no player parameter
					if (requiresReset) { // activated by vanilla attack key: manually unset the key state (fix for mouse event issues)
						KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode(), false);
					}
					SwordBasic skill = (SwordBasic) DSSPlayerInfo.get(player).getPlayerSkill(swordBasic);
					if (skill != null && skill.onAttack(player)) {
						// don't swing arm here, it screws up the attack damage calculations
						PacketDispatcher.sendToServer(new ActivateSkillPacket(this, true));
					} else { // player missed - swing arm manually since no activation packet will be sent
						player.swingArm(EnumHand.MAIN_HAND);
					}
				}
			} else {
				DSSPlayerInfo.get(player).armSwing = 0.0F;
				charge = 0;
			}
		} else {
			DSSPlayerInfo.get(player).armSwing = 0.0F;
		}
		if (isActive()) {
			activeTimer = 0;
		}
	}

	/**
	 * Deactivates this skill and inflicts armor-ignoring damage directly to the
	 * target; note that this causes the LivingHurtEvent to repost, but since the
	 * skill is no longer active it will behave normally. The current event's
	 * damage is set to zero to avoid double damage.
	 */
	public void onImpact(EntityPlayer player, LivingHurtEvent event) {
		activeTimer = 0;
		PlayerUtils.playSoundAtEntity(player.getEntityWorld(), player, ModSounds.ARMOR_BREAK, SoundCategory.PLAYERS, 0.4F, 0.5F);
		DirtyEntityAccessor.damageEntity(event.getEntityLiving(), DamageUtils.causeArmorBreakDamage(player), event.getAmount());
		event.setAmount(0.0F);
	}
}
