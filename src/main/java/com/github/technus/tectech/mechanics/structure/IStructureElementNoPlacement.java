package com.github.technus.tectech.mechanics.structure;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Deprecated
public interface IStructureElementNoPlacement<T> extends IStructureElement<T> {
    @Override
    default boolean placeBlock(T t, World world, int x, int y, int z, ItemStack trigger){
        return false;
    }
}
