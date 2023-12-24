package net.bunnly.caf.block.entity;

import net.bunnly.caf.CooksAndFarmers;
import net.bunnly.caf.block.CafBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CafBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CooksAndFarmers.MOD_ID);

public static final RegistryObject<BlockEntityType<CuttingBoardBlockEntity>> CUTTING_BOARD_BE =
        BLOCK_ENTITIES.register("cutting_board_be", () ->
                BlockEntityType.Builder.of(CuttingBoardBlockEntity::new,
                        CafBlocks.CUTTING_BOARD.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}