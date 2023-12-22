package net.bunnly.caf.item;

import net.bunnly.caf.CooksAndFarmers;
import net.bunnly.caf.block.CafBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CafCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CooksAndFarmers.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CAF_TAB = CREATIVE_MODE_TABS.register("caf",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.WHEAT)).
                    title(Component.translatable("creativetab.caf")).
                    displayItems((pParameters, pOutput) -> {
                        pOutput.accept(CafBlocks.OAK_FOOD_CRATE.get());
                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
