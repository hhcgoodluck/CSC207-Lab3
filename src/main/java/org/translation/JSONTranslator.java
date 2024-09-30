package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation.
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {
    // pick appropriate instance variables for this class
    // 存储 Map 键: alpha3 code -- 键值：2位code
    private Map<String, List<Map<String, String>>> countryLanguagesMap;

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        countryLanguagesMap = new HashMap<>();

        // read the file to get the data to populate things...
        try {
            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));
            JSONArray jsonArray = new JSONArray(jsonString);
            // use the data in the jsonArray to populate your instance variables
            // Note: this will likely be one of the most substantial pieces of code you write in this lab.

            // 使用 jsonArray 中的数据填充实例变量
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryObject = jsonArray.getJSONObject(i);
                String alpha3 = countryObject.getString("alpha3");
                // 创建某个 alpha3 对应的语言代码及其翻译映射的列表
                List<Map<String, String>> languages = new ArrayList<>();

                // 收集该国家的所有语言代码
                for (String langKey : countryObject.keySet()) {
                    if (!"id".equals(langKey) && !"alpha3".equals(langKey) && !"alpha2".equals(langKey)) {
                        Map<String, String> langMap = new HashMap<>();
                        langMap.put(langKey, countryObject.getString(langKey));
                        languages.add(langMap);
                    }
                }
                countryLanguagesMap.put(alpha3, languages);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // return an appropriate list of language codes, but make sure there is no aliasing to a mutable object
        // 根据给定国家的 alpha3 代码获取对应的语言代码列表
        List<Map<String, String>> languagesMaps = countryLanguagesMap.get(country);
        // 创建一个新的列表以保存语言代码
        List<String> languageCodes = new ArrayList<>();
        // 检查国家是否存在，且确保没有对可变对象的别名
        if (languagesMaps != null) {
            for (Map<String, String> langMap : languagesMaps) {
                // 将每个语言映射的键（语言代码）添加到列表中
                languageCodes.addAll(langMap.keySet());
            }
        }
        // 返回一个新的列表以避免对可变对象的别名
        return new ArrayList<>(languageCodes);
    }

    @Override
    public List<String> getCountries() {
        // return an appropriate list of country codes, but make sure there is no aliasing to a mutable object
        // 使用 keySet() 方法获取国家代码的集合，并返回一个新的列表以避免可变对象的别名
        return new ArrayList<>(countryLanguagesMap.keySet());
    }

    @Override
    public String translate(String country, String language) {
        // complete this method using your instance variables as needed
        // 根据给定的国家代码（country）和语言代码（language），返回该国家在指定语言下的翻译文本
        // 获取与给定国家 alpha3 代码相关联的语言翻译信息列表
        List<Map<String, String>> languageMaps = countryLanguagesMap.get(country);
        // 检查国家是否存在，并且语言翻译列表不为空
        if (languageMaps != null) {
            // 遍历每个语言映射，查找所需的语言代码
            for (Map<String, String> languageMap : languageMaps) {
                if (languageMap.containsKey(language)) {
                    // 返回对应的翻译文本
                    return languageMap.get(language);
                }
            }
        }
        // 如果未找到对应的翻译，返回自定义消息
        return "Translation not available for " + country + " in " + language;
    }
}
