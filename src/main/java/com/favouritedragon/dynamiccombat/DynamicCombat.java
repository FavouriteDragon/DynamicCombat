package com.favouritedragon.dynamiccombat;

import com.favouritedragon.dynamiccombat.proxy.IProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;


@Mod(modid = DynamicCombat.MODID, name = DynamicCombat.NAME, version = DynamicCombat.VERSION, dependencies="required-after:dynamicswordskills")
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
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.Init(event);
    }

    @EventHandler
    public void postInit(FMLInitializationEvent event) {
        proxy.postInit(event);
    }

}
