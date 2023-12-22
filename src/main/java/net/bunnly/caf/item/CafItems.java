package net.bunnly.caf.item;

import net.bunnly.caf.CooksAndFarmers;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CafItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CooksAndFarmers.MOD_ID);



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}