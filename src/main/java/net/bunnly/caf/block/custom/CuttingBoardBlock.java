package net.bunnly.caf.block.custom;

import com.mojang.serialization.MapCodec;
import net.bunnly.caf.block.entity.CafBlockEntities;
import net.bunnly.caf.block.entity.CuttingBoardBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CuttingBoardBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 1, 15);

    public CuttingBoardBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        return canSupportRigidBlock(pLevel, blockpos) || canSupportCenter(pLevel, blockpos, Direction.UP);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        return pDirection == Direction.DOWN && !this.canSurvive(pState, pLevel, pPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof CuttingBoardBlockEntity) {
                ((CuttingBoardBlockEntity) blockEntity).drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pPlayer instanceof ServerPlayer player){
            if (!pLevel.isClientSide()) {
                BlockEntity entity = pLevel.getBlockEntity(pPos);
                ItemStack heldItem = player.getItemInHand(pHand);
                if(entity instanceof CuttingBoardBlockEntity) {
                    CuttingBoardBlockEntity blockEntity = (CuttingBoardBlockEntity) entity;
                    if(player.isCrouching()){
                        player.openMenu((CuttingBoardBlockEntity)entity, pPos);
                        return InteractionResult.SUCCESS;
                    }else if(!heldItem.isEmpty()){
                        if(blockEntity.isEmpty()){
                            blockEntity.setItem(heldItem);
                            pLevel.playSound(null, pPos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS);
                            return InteractionResult.SUCCESS;
                        }
                    }else{
                        if(!blockEntity.isEmpty()){
                            if(player.isCreative()){
                                blockEntity.removeItem();
                            }else{
                                Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), blockEntity.removeItem());
                            }
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CuttingBoardBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, CafBlockEntities.CUTTING_BOARD_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }
}
