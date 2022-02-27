package fuzs.universalbonemeal.config;

import fuzs.puzzleslib.config.AbstractConfig;
import fuzs.puzzleslib.config.annotation.Config;

public class ServerConfig extends AbstractConfig {
    @Config(description = "Allow bone meal to work on cactus.")
    public boolean allowCactus = true;
    @Config(description = "Allow bone meal to work on sugar canes.")
    public boolean allowSugarCane = true;
    @Config(description = "Allow bone meal to work on vines.")
    public boolean allowVines = true;
    @Config(description = "Allow bone meal to work on nether warts.")
    public boolean allowNetherWart = true;
    @Config(description = "Allow bone meal to work on fully grown melon stems to force producing a melon block.")
    public boolean allowMelonStem = true;
    @Config(description = "Allow bone meal to work on fully grown pumpkin stems to force producing a pumpkin block.")
    public boolean allowPumpkinStem = true;
    @Config(description = "Allow bone meal to work on lily pads making them spread around on water.")
    public boolean allowLilyPad = true;
    @Config(description = "Allow bone meal to work on dead bushes making them spread around.")
    public boolean allowDeadBush = true;
    @Config(description = "Allow bone meal to work on small flowers making them spread around.")
    public boolean allowSmallFlowers = true;
    @Config(description = {"Allow bone meal to work on normal coral turning them into coral reef tree structures for farming coral blocks.", "This does only work when inside of a warm ocean biome."})
    public boolean allowCorals = true;
    @Config(description = {"Allow bone meal to work on chorus plants and flowers.", "The algorithm has a range limit of 48 blocks when searching for connected chorus flowers."})
    public boolean allowChorus = true;
    @Config(description = "Allow bone meal to work on mycelium blocks, spawning various types of mushrooms in the vicinity.")
    public boolean allowMycelium = true;

    public ServerConfig() {
        super("");
    }
}
