/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.gnomed.event.init;

import io.github.sayuzaur.gnomed.block.*;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;

import java.lang.invoke.MethodHandles;

import static io.github.sayuzaur.gnomed.GnomedMod.NAMESPACE;

public class BlockListener {
    static {
        EntrypointManager.registerLookup(MethodHandles.lookup());
    }

    public static Block CLASSIC_GNOME;
    public static Block BALD_GNOME;
    public static Block RGB_GNOME;
    public static Block GREEN_GNOME;
    public static Block TALL_GNOME;
    public static Block FLOWER_GNOME;

    @EventListener
    private static void registerBlocks(BlockRegistryEvent event) {
        CLASSIC_GNOME = new ClassicGnome(NAMESPACE.id("classic_gnome"));
        BALD_GNOME = new BaldGnome(NAMESPACE.id("bald_gnome"));
        RGB_GNOME = new RgbGnome(NAMESPACE.id("rgb_gnome"));
        GREEN_GNOME = new RgbGnome(NAMESPACE.id("green_gnome"));
        TALL_GNOME = new TallGnome(NAMESPACE.id("tall_gnome"));
        FLOWER_GNOME = new FlowerGnome(NAMESPACE.id("flower_gnome"));
    }
}
