package StarRail.config;//package StarRail.config;

import StarRail.general.StarRailProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.beans.PropertyDescriptor;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author : 真寻
 * 2023/11/13
 */
@Slf4j
@Component
public class StartRailProfiles {
    /**
     * 加载属性
     */
    @Autowired
    private StarRailProperties starRailProperties;

    /**
     * 配置文件路径
     */
    private static final String CONFIG_FILE_NAME = "data/config/StarRailConfig11111111.yaml";
    private static final Map<String, String> PROPERTY = new LinkedHashMap<>();

    /**
     * 内置配置文件模板
     */
    private static final String[] PROPERTY_NAMES = {
            "starRailPath", "usingSupportRoles",
            "appointedDaySign", "weeklyChallengeSelection", "weeklyChallengeCompletionTime",
            "exclusiveMaterials", "empiricalMaterials", "delegationStartTime",
            "empiricalMaterialSelection", "traceMaterialSelection",
            "breakthroughMaterialSelection",
            "legacySelection",
            "brushMaterial",

            "destiny", "LastBootTime",

            "account", "cipher",
    };
    private static final String[] PROPERTY_ANNOTATE = {
            "游戏路径", "是否使用支援角色",
            "历战余响标识", "历战余响选择", "历战余响完成时间",
            "专属材料委托数量", "经验材料/信用点委托数量", "每日委托开始时间",
            "拟造花萼(金)【经验材料】: 1 角色经验材料; 2 光锥经验材料【默认】; 3 信用点", "拟造花萼(赤)【行迹材料】: 1、毁灭; 2、存护; 3、巡猎; 4、丰饶; 5、智识; 6、同谐; 7、虚无",
            "凝滞虚影选择:1、空海(量子); 2、巽风(风); 3、鸣雷(雷); 4、炎华(火); 5、锋芒(物理); 6、霜晶(冰); 7、幻光(虚数); 8、冰棱(冰); 9、震厄(雷); 10、天人(风)",
            "遗器选择:1、霜风(冰、风); 2、讯拳(物理、击破); 3、漂泊(治疗、攻击); 4、睿治(减伤、量子); 5、圣颂(防御、雷); 6、野焰(火、虚数); 7、药使(生命、速度)",
            "体力刷取选择:1、拟造花萼(金)【经验材料】; 2、拟造花萼(赤)【行迹材料】; 3、凝滞虚影; 4、遗器",

            "模拟宇宙命途【destiny=虚无】","上次启动时间",

            "账号","密码"
    };

    /**
     * 根据内置模板创建新的配置文件
     */
    private static void createOrChangeProfiles() {
        Map<String,Map<String,String>> config = new LinkedHashMap<>();

        for (int i = 0; i < PROPERTY_NAMES.length; i++) {
            PROPERTY.put(PROPERTY_NAMES[i], PROPERTY_ANNOTATE[i]);
        }

        config.put("config", PROPERTY);

        DumperOptions options = new DumperOptions();
        // 格式化YAML文件
        options.setPrettyFlow(true);
        // 设置YAML文件会使用块状风格
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);

        try (FileWriter writer = new FileWriter(CONFIG_FILE_NAME)) {
            for (Map.Entry<String,Map<String,String>> name : config.entrySet()) {
                writer.write(name.getKey()+":\n");
                for (Map.Entry<String, String> entry : PROPERTY.entrySet()) {
                    writer.write("# " + entry.getValue() + "\n");
                    yaml.dump(Collections.singletonMap(entry.getKey(), ""), writer);
                }
            }
        } catch (IOException e) {
            log.error("创建配置文件失败\n", e);
            System.exit(-1);
        }
    }



    /**
     * 更新配置文件
     *
     * @param st1 属性
     * @param st2 值
     */
    public static void updateStartRailProfiles(String st1, String st2) {
//        DumperOptions options = new DumperOptions();
//        // 格式化YAML文件
//        options.setPrettyFlow(true);
//        // 设置YAML文件会使用块状风格
//        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
//        Yaml yaml = new Yaml(options);
        Yaml yaml = new Yaml();

        try (FileWriter writer = new FileWriter(CONFIG_FILE_NAME); InputStream fis = new FileInputStream(CONFIG_FILE_NAME)) {
            // 读取YAML文件
            Map<String, Object> data = yaml.load(fis);

            // 更新属性
            updateProperty(data, st1, st2);

            // 写入YAML文件
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                writer.write("# " + entry.getValue() + "\n");
                yaml.dump(Collections.singletonMap(entry.getKey(), ""), writer);
            }

//            yaml.dump(data, writer);
        } catch (IOException e) {
            log.error("更新配置文件失败: " + e);
            System.exit(-1);
        }
    }

    private static void updateProperty(Map<String, Object> data, String key, String newValue) {
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (entry.getKey().equals(key)) {
                entry.setValue(newValue);
                return;
            } else if (entry.getValue() instanceof Map) {
                updateProperty((Map<String, Object>) entry.getValue(), key, newValue);
            }
        }
    }


    public static void main(String[] args) {
        createOrChangeProfiles();
//        updateStartRailProfiles("cipher","213eee1");


    }

}
