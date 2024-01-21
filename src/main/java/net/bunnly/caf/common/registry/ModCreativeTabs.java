package net.bunnly.caf.common.registry;

import net.bunnly.caf.CooksAndFarmers;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CooksAndFarmers.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CAF_TAB = CREATIVE_MODE_TABS.register("caf",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.WHEAT)).
                    title(Component.translatable("creativetab.caf")).
                    displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModBlocks.CUTTING_BOARD.get());
                        pOutput.accept(ModBlocks.OAK_FOOD_CRATE.get());
                        pOutput.accept(ModItems.WOODEN_KNIFE.get());
                        pOutput.accept(ModItems.STONE_KNIFE.get());
                        pOutput.accept(ModItems.IRON_KNIFE.get());
                        pOutput.accept(ModItems.GOLDEN_KNIFE.get());
                        pOutput.accept(ModItems.DIAMOND_KNIFE.get());
                        pOutput.accept(ModItems.NETHERITE_KNIFE.get());
                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
