package com.favouritedragon.dynamiccombat;

import dynamicswordskills.network.PacketDispatcher;
import dynamicswordskills.network.bidirectional.ActivateSkillPacket;
import dynamicswordskills.skills.SkillActive;
import dynamicswordskills.skills.SkillBase;
import dynamicswordskills.util.PlayerUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public abstract class DynamicActiveSkills extends SkillActive {
	private static String ModID = DynamicCombat.MODID;
	protected DynamicActiveSkills(String name) {
		super(name);
	}

	protected DynamicActiveSkills(SkillActive skill) {
		super(skill);
	}



	public static void setModID(String modID) {
		ModID = modID;
	}

	public static String getModID() {
		return ModID;
	}




}
