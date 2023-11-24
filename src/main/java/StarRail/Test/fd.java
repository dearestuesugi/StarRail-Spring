//package StarRail.Test;
//
//import org.apache.commons.io.IOUtils;
//import org.springframework.core.io.ClassPathResource;
//import org.yaml.snakeyaml.Yaml;
//
//import java.io.FileWriter;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
///**
// * @author : 真寻
// * 2023/11/24
// */
//public class fd {
//    public String getTotalYamlFileContent() throws Exception {
//        String fileName = "StarRailConfig.yaml";
//        return getYamlFileContent(fileName);
//    }
//    public String getYamlFileContent(String fileName) throws Exception {
//        ClassPathResource classPathResource = new ClassPathResource(fileName);
//        return convertStreamToString(classPathResource.getInputStream());
//    }
//    public static String convertStreamToString(InputStream inputStream) throws Exception{
//        return IOUtils.toString(inputStream, "utf-8");
//    }
//
//    public static void main(String[] args) {
//        fd fd = new fd();
//        try {
//            System.out.println(fd.getTotalYamlFileContent());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        String name = "applicationConfig: [classpath:/" + fileName + "]";
//        MapPropertySource propertySource = (MapPropertySource) getPropertySources().get(name);
//        Map<String, Object> source = propertySource.getSource();
//        Map<String, Object> map = new HashMap<>(source.size());
//        map.putAll(source);
//
//        Map<String, Object> propertyMap = convertYmlMapToPropertyMap(yamlMap);
//
//        for (String key : propertyMap.keySet()) {
//            Object value = propertyMap.get(key);
//            map.put(key, value);
//        }
//        environment.getPropertySources().replace(name, new MapPropertySource(name, map));
//    }
//
//
//    public void updateTotalYamlFileContent(String content) throws Exception {
//        String fileName = "StarRailConfig.yaml";
//        updateYamlFileContent(fileName, content);
//    }
//    public void updateYamlFileContent(String fileName, String content) throws Exception {
//        Yaml template = new Yaml();
//        Map<String, Object> yamlMap = template.load(content);
//
//        ClassPathResource classPathResource = new ClassPathResource(fileName);
//
//        Yaml yaml = new Yaml();
//        //字符输出
//        FileWriter fileWriter = new FileWriter(classPathResource.getFile());
//        //用yaml方法把map结构格式化为yaml文件结构
//        fileWriter.write(yaml.dumpAsMap(yamlMap));
//        //刷新
//        fileWriter.flush();
//        //关闭流
//        fileWriter.close();
//    }
//
//    public static HashMap<String, Object> convertYmlMapToPropertyMap(Map<String, Object> yamlMap) {
//        HashMap<String, Object> propertyMap = new HashMap<String, Object>();
//        for (String key : yamlMap.keySet()) {
//            String keyName = key;
//            Object value = yamlMap.get(key);
//            if (value != null && value.getClass() == LinkedHashMap.class) {
//                convertYmlMapToPropertyMapSub(keyName, ((LinkedHashMap<String, Object>) value), propertyMap);
//            } else {
//                propertyMap.put(keyName, value);
//            }
//        }
//        return propertyMap;
//    }
//
//    private static void convertYmlMapToPropertyMapSub(String keyName, LinkedHashMap<String, Object> submMap, Map<String, Object> propertyMap) {
//        for (String key : submMap.keySet()) {
//            String newKey = keyName + "." + key;
//            Object value = submMap.get(key);
//            if (value != null && value.getClass() == LinkedHashMap.class) {
//                convertYmlMapToPropertyMapSub(newKey, ((LinkedHashMap<String, Object>) value), propertyMap);
//            } else {
//                propertyMap.put(newKey, value);
//            }
//        }
//    }
//
//}
