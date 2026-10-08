/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.gnomed.block;

import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class BaldGnome extends Gnome {
    public BaldGnome(Identifier identifier) {
        super(identifier);
    }

    @Override
    public void doGnomishTricks(World world, int x, int y, int z, Random random) {
        world.playSound(x, y, z, "gnomed:gnome.yapp", (random.nextFloat() * 0.5F + 2.5F), random.nextFloat() * 0.3F + 0.8F);
    }
}
