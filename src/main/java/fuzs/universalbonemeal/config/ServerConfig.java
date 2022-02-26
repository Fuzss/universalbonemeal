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
    @Config(description = "Allow bone meal to work on one block tall flowers making them spread around.")
    public boolean allowFlowers = true;
    @Config(description = "Allow bone meal to work on coral turning them into reef structures.")
    public boolean allowCorals = true;
    @Config(description = {"Allow bone meal to work on chorus plants and flowers.", "The algorithm has a range limit of 64 blocks when searching for connected chorus blocks."})
    public boolean allowChorus = true;

    public ServerConfig() {
        super("");
    }
}
