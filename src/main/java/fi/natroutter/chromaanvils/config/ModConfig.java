package fi.natroutter.colorfulanvils.config;

import fi.natroutter.colorfulanvils.ColorfulAnvils;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.List;

@Config(name = ColorfulAnvils.MOD_ID)
public class ModConfig implements ConfigData {


    @ConfigEntry.Gui.Tooltip
    boolean BypassCharacterLimit = true;

    @ConfigEntry.Gui.Tooltip
    int CharacterLimit = 50;

    @ConfigEntry.Gui.Tooltip
    List<String> BlackListedItems = new ArrayList<>();


}