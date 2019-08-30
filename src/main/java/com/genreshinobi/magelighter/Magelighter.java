package com.genreshinobi.magelighter;

import com.genreshinobi.magelighter.setup.ClientProxy;
import com.genreshinobi.magelighter.setup.IProxy;
import com.genreshinobi.magelighter.setup.ServerProxy;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("magelighter")
public class Magelighter {
    // Directly reference log4j logger
    public static final Logger LOGGER = LogManager.getLogger();

    // Set the MODID
    public static final String MODID = "magelighter";

    // Make Creative Tab
    static final ItemGroup magelighter = new ModCreativeTab();

    // Declare the Proxies
    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public Magelighter() {
        // register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        proxy.init();
    }
}
