package net.bunnly.caf;

import com.mojang.logging.LogUtils;
import net.bunnly.caf.block.CafBlocks;
import net.bunnly.caf.block.entity.CafBlockEntities;
import net.bunnly.caf.item.CafCreativeTabs;
import net.bunnly.caf.item.CafItems;
import net.bunnly.caf.screen.CafMenuTypes;
import net.bunnly.caf.screen.CuttingBoardScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CooksAndFarmers.MOD_ID)
public class CooksAndFarmers
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "caf";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public CooksAndFarmers()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CafCreativeTabs.register(modEventBus);

        CafItems.register(modEventBus);
        CafBlocks.register(modEventBus);

        CafBlockEntities.register(modEventBus);
        CafMenuTypes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            MenuScreens.register(CafMenuTypes.CUTTING_BOARD_MENU.get(), CuttingBoardScreen::new);
        }
    }
}
