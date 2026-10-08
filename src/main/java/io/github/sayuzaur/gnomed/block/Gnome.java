/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.gnomed.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.DirectionProperty;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;

import java.util.Random;

public class Gnome extends TemplateBlock {
    public static final DirectionProperty HORIZONTAL_FACING;
    public static final BooleanProperty PLACED_BY_PLAYER;

    static {
        HORIZONTAL_FACING = Properties.FACING;
        PLACED_BY_PLAYER = BooleanProperty.of("placed_by_player");
    }

    public Gnome(Identifier identifier) {
        super(identifier, Material.METAL);
        this.setSoundGroup(STONE_SOUND_GROUP);
        this.setTickRandomly(true);
        this.setHardness(1.0f);
        this.setBoundingBox(0.3125F, 0.0F, 0.3125F, 0.6875F, 0.625F, 0.6875F);
        setDefaultState(getStateManager().getDefaultState().with(HORIZONTAL_FACING, Direction.NORTH).with(PLACED_BY_PLAYER, false));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, PLACED_BY_PLAYER);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getStateManager().getDefaultState().with(HORIZONTAL_FACING,context.getHorizontalPlayerFacing()).with(PLACED_BY_PLAYER, true);
    }

    public boolean isOpaque() {
        return false;
    }

    public boolean isFullCube() {
        return false;
    }

    public boolean canPlaceOnTop(World world, int x, int y, int z) {
        return world.getBlockState(x, y, z).getBlock().isFullCube();
    }

    @Override
    public boolean canPlaceAt(World world, int x, int y, int z, int side) {
        return     world.isAir(x, y, z)
                && canPlaceOnTop(world, x, y - 1, z);
    }

    protected final void breakIfCannotStay(World world, int x, int y, int z) {
        if (!this.canPlaceOnTop(world, x, y - 1, z)) {
            this.dropStacks(world, x, y, z, world.getBlockMeta(x, y, z));
            world.setBlock(x, y, z, 0);
        }
    }

    public void neighborUpdate(World world, int x, int y, int z, int id) {
        super.neighborUpdate(world, x, y, z, id);
        this.breakIfCannotStay(world, x, y, z);
    }

    @Override
    public int getTickRate() {
        return 1;
    }

    public void doGnomishTricks(World world, int x, int y, int z, Random random) {
    }

    @Override
    public void onTick(World world, int x, int y, int z, Random random) {
        doGnomishTricks(world, x, y, z, random);
        world.playSound(x, y, z, "gnomed:gnome.wah", (random.nextFloat() * 0.5F + 2.5F), random.nextFloat() * 0.3F + 0.85F);
        //world.addParticle("smoke", (double) x+0.5F, y+0.5F, z+0.5F, (double) 0.0F, (double) 0.0F, (double) 0.0F);
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        if (random.nextInt(16) == 0) {
            world.playSound(x, y, z, "gnomed:gnome.wah", (random.nextFloat() * 0.3F + 0.85F), random.nextFloat() * 0.3F + 0.85F);
        }
        //world.addParticle("flame", (double) x, y, z, (double) 0.0F, (double) 0.0F, (double) 0.0F);
    }
}
