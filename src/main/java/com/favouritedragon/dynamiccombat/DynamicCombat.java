package com.favouritedragon.dynamiccombat;

import com.favouritedragon.dynamiccombat.proxy.IProxy;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import zdoctor.skilltree.api.SkillTreeApi;


@Mod(modid = DynamicCombat.MODID, name = DynamicCombat.NAME, version = DynamicCombat.VERSION, dependencies="required-before:dynamicswordskills;required-after:skilltree")
public class DynamicCombat
{
    public static final String MODID = "dynamic_combat";
    public static final String NAME = "Dynamic Combat";
    public static final String VERSION = "1.0";

    public static final String CLIENT = "com.favouritedragon.dynamiccombat.proxy.ClientProxy";
    public static final String SERVER = "com.favouritedragon.dynamiccombat.proxy.CommonProxy";

    private static Logger logger;

    @Mod.Instance(DynamicCombat.MODID)
    public static DynamicCombat instance;

    @SidedProxy(clientSide = DynamicCombat.CLIENT, serverSide = DynamicCombat.SERVER)
    public static IProxy proxy;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
        proxy.registerRender();
        RegistryHandler.registerAll();
        /*CapabilityManager.INSTANCE.register(PlayerSkillData.class, new Capability.IStorage<PlayerSkillData>(){
            // These methods are only called by Capability.writeNBT() or Capability.readNBT(), which in turn are
            // NEVER CALLED. Unless I'm missing some reflective invocation, that means this entire class serves only
            // to allow capabilities to be saved and loaded manually. What that would be useful for I don't know.
            // (If an API forces most users to write redundant code for no reason, it's not user friendly, is it?)
            // ... well, that's my rant for today!
            @Override
            public NBTBase writeNBT(Capability<PlayerSkillData> capability, PlayerSkillData instance, EnumFacing side){
                return null;
            }

            @Override
            public void readNBT(Capability<PlayerSkillData> capability, PlayerSkillData instance, EnumFacing side, NBTBase nbt){
            }
        }, PlayerSkillData::new);**/
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.Init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

}
