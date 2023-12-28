package net.bunnly.caf.block;

import net.bunnly.caf.CooksAndFarmers;
import net.bunnly.caf.block.custom.CuttingBoardBlock;
import net.bunnly.caf.block.custom.WoodenFoodCrate;
import net.bunnly.caf.item.CafItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class CafBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CooksAndFarmers.MOD_ID);

    public static final RegistryObject<Block> OAK_FOOD_CRATE = registerBlock("oak_food_crate",
            () -> new WoodenFoodCrate(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).sound(SoundType.WOOD)),
            true);

    public static final RegistryObject<Block> CUTTING_BOARD = registerBlock("cutting_board",
            () -> new CuttingBoardBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).
                    sound(SoundType.WOOD).explosionResistance(0.5f).
                    destroyTime(0.5f)),
            true);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, Boolean createItem) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        if(createItem){
            registerBlockItem(name, toReturn);
        }
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        return CafItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
