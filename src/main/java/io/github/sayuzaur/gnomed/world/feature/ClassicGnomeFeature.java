/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.gnomed.world.feature;

import io.github.sayuzaur.gnomed.event.init.BlockListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.GrassPatchFeature;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.tag.BlockTags;
import net.modificationstation.stationapi.api.util.math.Direction;

import java.util.Random;

import static io.github.sayuzaur.gnomed.block.Gnome.HORIZONTAL_FACING;
import static io.github.sayuzaur.gnomed.block.Gnome.PLACED_BY_PLAYER;

public class ClassicGnomeFeature extends Feature {
    private static final boolean DEBUG = false;

    public boolean generate(World world, Random random, int x, int y, int z) {
        Block gnome = BlockListener.CLASSIC_GNOME;
        int attempts = 256;
        int range = 16;

        for (int i = 0; i < attempts; i++) {
            int varX = x + random.nextInt(range) - random.nextInt(range);
            int varZ = z + random.nextInt(range) - random.nextInt(range);
            int varY = y + random.nextInt(range) - random.nextInt(range);

            if (isValidTarget(world, varX, varY, varZ) ) {
                world.setBlockWithoutNotifyingNeighbors(varX, varY, varZ, gnome.id);
                if (world.getBlockId(varX, varY, varZ) == gnome.id) {
                    BlockState state = world.getBlockState(varX, varY, varZ);
                    Direction facing = getFacingDirection(world, varX, varY, varZ);
                    world.setBlockStateWithoutNotifyingNeighbors(varX, varY, varZ, state.with(HORIZONTAL_FACING, facing).with(PLACED_BY_PLAYER, false));
                }

                Feature flowerPatch = new GrassPatchFeature(Block.GRASS.id, 1);
                flowerPatch.generate(world, random, varX, varY, varZ);

                if (DEBUG) {
                    for (int j = varY + 2; j < 128; j++) {
                        world.setBlockWithoutNotifyingNeighbors(varX, j, varZ, Block.GOLD_BLOCK.id);
                    }
                    System.out.println("X: " + varX + " Y: " + varY + " Z: " + varZ);
                }
                return true;
            }
        }
        return false;
    }

    private boolean isValidTarget(World world, int x, int y, int z) {
        return    (world.isAir(x, y, z)
                || world.getBlockState(x, y, z).getMaterial() == Material.PLANT)
                &&(world.getBlockState(x, y - 1, z).isIn(BlockTags.GRASS_BLOCKS)
                || world.getBlockState(x, y - 1, z).isIn(BlockTags.DIRTS));
    }

    private Direction getFacingDirection(World world, int x, int y, int z) {
        if        (world.getBlockState(x + 1, y, z).getMaterial().isSolid()
                &&(world.isAir(x - 1, y, z) || world.getBlockState(x - 1, y, z).getMaterial() == Material.PLANT)) {
            return Direction.EAST;
        } else if (world.getBlockState(x - 1, y, z).getMaterial().isSolid()
                &&(world.isAir(x + 1, y, z) || world.getBlockState(x + 1, y, z).getMaterial() == Material.PLANT)) {
            return Direction.WEST;
        } else if (world.getBlockState(x, y, z + 1).getMaterial().isSolid()
                &&(world.isAir(x, y, z - 1) || world.getBlockState(x, y, z - 1).getMaterial() == Material.PLANT)) {
            return Direction.SOUTH;
        } else if (world.getBlockState(x, y, z - 1).getMaterial().isSolid()
                &&(world.isAir(x, y, z + 1) || world.getBlockState(x, y, z + 1).getMaterial() == Material.PLANT)) {
            return Direction.NORTH;
        } else {
            int randDirection = world.random.nextInt(4);
            switch (randDirection) {
                case 0 -> {
                    return Direction.NORTH;
                }
                case 1 -> {
                    return Direction.SOUTH;
                }
                case 2 -> {
                    return Direction.EAST;
                }
                case 3 -> {
                    return Direction.WEST;
                }
            }
        }
        return Direction.NORTH;
    }
}
