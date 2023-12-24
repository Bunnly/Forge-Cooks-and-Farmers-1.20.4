package net.bunnly.caf.block.entity;

import net.bunnly.caf.screen.CuttingBoardMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CuttingBoardBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(5);

    private static final int OUTPUT_SLOT_1 = 0;
    private static final int OUTPUT_SLOT_2 = 1;
    private static final int OUTPUT_SLOT_3 = 2;
    private static final int OUTPUT_SLOT_4 = 3;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;

    public CuttingBoardBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(CafBlockEntities.CUTTING_BOARD_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex){
                    case 0 -> CuttingBoardBlockEntity.this.progress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex){
                    case 0 -> CuttingBoardBlockEntity.this.progress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 5;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.caf.cutting_board");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new CuttingBoardMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("progress", progress);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("progress");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if(hasRecipe()){
            if(hasProgressFinished()){
                CraftItem();
                ResetProgress();
            }
        }else{
            ResetProgress();
        }
    }

    private void ResetProgress() {
        progress = 0;
    }

    private void CraftItem() {
        ItemStack result_1 = new ItemStack(Items.WHEAT_SEEDS, 1);
        this.itemHandler.extractItem(5, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT_1, new ItemStack(result_1.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT_1).getCount() + result_1.getCount()));
    }

    private boolean hasProgressFinished() {
        return false;
    }

    private boolean hasRecipe() {
        boolean hasCraftingItem = this.itemHandler.getStackInSlot(4).getItem() == Items.WHEAT;
        ItemStack result = new ItemStack(Items.WHEAT_SEEDS);
        return hasCraftingItem && canInsertAmountIntoOutput(result.getCount()) && canInsertItemIntoOutput(result.getItem());
    }

    private boolean canInsertItemIntoOutput(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT_1).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT_1).is(item);
    }

    private boolean canInsertAmountIntoOutput(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT_1).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT_1).getMaxStackSize();
    }
}
