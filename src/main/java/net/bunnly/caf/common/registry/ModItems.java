package net.bunnly.caf.common.registry;

import net.bunnly.caf.CooksAndFarmers;
import net.bunnly.caf.common.item.KnifeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CooksAndFarmers.MOD_ID);

    public static final RegistryObject<Item> WOODEN_KNIFE = ITEMS.register("wooden_knife",
            () -> new KnifeItem(Tiers.WOOD, 1f, 5, new Item.Properties().durability(60)));

    public static final RegistryObject<Item> STONE_KNIFE = ITEMS.register("stone_knife",
            () -> new KnifeItem(Tiers.STONE, 1f, 5, new Item.Properties().durability(131)));

    public static final RegistryObject<Item> IRON_KNIFE = ITEMS.register("iron_knife",
            () -> new KnifeItem(Tiers.IRON, 1f, 5, new Item.Properties().durability(250)));

    public static final RegistryObject<Item> GOLDEN_KNIFE = ITEMS.register("golden_knife",
            () -> new KnifeItem(Tiers.GOLD, 1f, 5, new Item.Properties().durability(32)));

    public static final RegistryObject<Item> DIAMOND_KNIFE = ITEMS.register("diamond_knife",
            () -> new KnifeItem(Tiers.DIAMOND, 1f, 5, new Item.Properties().durability(1561)));
 
    public static final RegistryObject<Item> NETHERITE_KNIFE = ITEMS.register("netherite_knife",
            () -> new KnifeItem(Tiers.NETHERITE, 1f, 5, new Item.Properties().durability(2031)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}