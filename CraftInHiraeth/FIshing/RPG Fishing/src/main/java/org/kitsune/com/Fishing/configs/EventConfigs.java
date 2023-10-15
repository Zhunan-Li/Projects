package me.springbreezex.craftinhiraethcore.fishing.configs;

import me.springbreezex.craftinhiraethcore.fishing.utils.Event;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class EventConfigs {
    private Plugin plugin;
    public EventConfigs(Plugin plugin) throws IOException {
        this.plugin = plugin;
        this.load(this.plugin.getDataFolder(), this.plugin.getLogger());
    }

    public static Map<String, Event> eventMap = new HashMap<>();

    public void load(File dataFolder, Logger logger) throws IOException {
        File fishFolder = new File(dataFolder.toString().concat("/fishing/Events/"));
        fishFolder.mkdirs();
        if(fishFolder.listFiles().length!=0){
            genMap(dataFolder,logger);
        }else{
            generate(dataFolder, logger);
        }
    }

    private void generate(File dataFolder, Logger logger) throws IOException {
        logger.info("generating default event files...");
        List<String> eventFiles = new ArrayList<>(Arrays.asList("fishing/Events/Example"));
        for(String path: eventFiles){
            InputStream inputStream = this.plugin.getResource(path.concat(".yml"));
            byte[] eventBuffer = new byte[inputStream.available()];
            inputStream.read(eventBuffer);
            File file = new File(dataFolder.toString().concat("/").concat(path.concat(".yml")));
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(eventBuffer);
            outputStream.close();
        }
        genMap(dataFolder, logger);
    }
    private void genMap(File dataFolder, Logger logger){
        for(File file: new File(dataFolder.toString().concat("/fishing/Events/")).listFiles()){
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

            String type = configuration.getString("Type");
            String mob_name = configuration.getString("Mob_Name");
            int difficult_min = configuration.getInt("Difficult_min");
            int difficult_max = configuration.getInt("Difficult_max");
            int pullTimes_min = configuration.getInt("PullTimes_min");
            int pullTimes_max = configuration.getInt("PullTimes_max");
            int xp_min = configuration.getInt("Xp_min");
            int xp_max = configuration.getInt("Xp_max");
            int chance_min = configuration.getInt("Chance_min");
            int chance_max = configuration.getInt("Chance_max");
            int level_min = configuration.getInt("Level_min");
            int level_max = configuration.getInt("Level_max");
            List<String> region = configuration.getStringList("Region");

            eventMap.put(type, new Event(type,mob_name,difficult_min,difficult_max,pullTimes_min,pullTimes_max,xp_min,xp_max,chance_min,chance_max,level_min,level_max,region));
        }
        logger.info("Loaded ".concat(String.valueOf(eventMap.size())).concat(" types of event!"));
    }
}
