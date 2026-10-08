/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.gnomed.event.init;

import io.github.sayuzaur.gnomed.world.feature.*;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.world.gen.feature.Feature;
import net.modificationstation.stationapi.api.event.worldgen.biome.BiomeModificationEvent;
import net.modificationstation.stationapi.api.worldgen.feature.HeightScatterFeature;
import net.modificationstation.stationapi.api.worldgen.feature.VolumetricScatterFeature;
import net.modificationstation.stationapi.api.worldgen.feature.WeightedFeature;

import java.util.Objects;

public class BiomeModificationListener {
    private static final Feature CLASSIC_GNOME = new WeightedFeature(new HeightScatterFeature(new ClassicGnomeFeature(),1), 192);
    private static final Feature FLOWER_GNOME =  new WeightedFeature(new HeightScatterFeature(new FlowerGnomeFeature(),1), 128);
    private static final Feature WOODS_GNOME =   new WeightedFeature(new HeightScatterFeature(new GreenGnomeFeature(), 1), 80);
    private static final Feature BALD_GNOME =    new WeightedFeature(new HeightScatterFeature(new BaldGnomeFeature(), 1), 192);
    private static final Feature RGB_GNOME =     new WeightedFeature(new VolumetricScatterFeature(new RgbGnomeFeature(), 1, 8, 32), 192);
    private static final Feature TALL_GNOME =    new WeightedFeature(new VolumetricScatterFeature(new TallGnomeFeature(), 1, 96, 128), 64);

    @EventListener
    public void biomeModification(BiomeModificationEvent event) {
        if (event.world.dimension.id == 0) {
            if (Objects.equals(event.biome.name, "Shrubland") || Objects.equals(event.biome.name, "Savanna")) {
                event.biome.addFeature(CLASSIC_GNOME);
            }
            if (Objects.equals(event.biome.name, "Plains")) {
                event.biome.addFeature(FLOWER_GNOME);
            }
            if (Objects.equals(event.biome.name, "Forest") || Objects.equals(event.biome.name, "Seasonal Forest")) {
                event.biome.addFeature(WOODS_GNOME);
            }
            if (Objects.equals(event.biome.name, "Taiga") || Objects.equals(event.biome.name, "Tundra")) {
                event.biome.addFeature(BALD_GNOME);
            }
            event.biome.addFeature(RGB_GNOME);
            event.biome.addFeature(TALL_GNOME);
        }
    }
}
