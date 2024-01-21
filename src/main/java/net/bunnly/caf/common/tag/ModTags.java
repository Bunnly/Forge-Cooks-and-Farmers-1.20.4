package net.bunnly.caf.common.tag;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static final TagKey<Block> MINEABLE_WITH_KNIFE = BlockTag("mineable/knife");

    public static final TagKey<Item> KNIVES = ItemTag("tools/knives");

    private static TagKey<Block> BlockTag(String path){
        return BlockTags.create(new ResourceLocation("mod", path));
    }

    private static TagKey<Item> ItemTag(String path){
        return ItemTags.create(new ResourceLocation("mod", path));
    }
}
