package fuzs.universalbonemeal.config;

import fuzs.puzzleslib.config.AbstractConfig;
import fuzs.puzzleslib.config.annotation.Config;
import fuzs.puzzleslib.config.serialization.EntryCollectionBuilder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServerConfig extends AbstractConfig {
    @Config(description = "Allow bone meal to work on cactus.")
    public boolean allowCactus = true;
    @Config(description = "Allow bone meal to work on sugar canes.")
    public boolean allowSugarCane = true;
    @Config(description = "Allow bone meal to work on vines.")
    public boolean allowVines = true;

    public ServerConfig() {
        super("");
    }
}
