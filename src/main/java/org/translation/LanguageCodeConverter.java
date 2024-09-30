package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides the service of converting language codes to their names.
 */
public class LanguageCodeConverter {
    // pick appropriate instance variables to store the data necessary for this class
    private Map<String, String> languageCodeMap;

    /**
     * Default constructor which will load the language codes from "language-codes.txt"
     * in the resources folder.
     */
    public LanguageCodeConverter() {
        this("language-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the language code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public LanguageCodeConverter(String filename) {
        languageCodeMap = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            String regex = "^(.*?)\\s+([a-z]{2}(-[a-z]{2})?)$";
            Pattern pattern = Pattern.compile(regex);

            for (String line : lines.subList(1, lines.size())) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String languageName = matcher.group(1).trim();
                    String languageCode = matcher.group(2);
                    // 实现 Map 语言代码 --> 语言名称
                    languageCodeMap.put(languageCode, languageName);
                }
            }
            // use lines to populate the instance variable
            // tip: you might find it convenient to create an iterator using lines.iterator()
            // '}' on next line should be alone on a line.
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns the name of the language for the given language code.
     * @param code the language code。                                输入2位code 返回语言名称
     * @return the name of the language corresponding to the code
     */
    public String fromLanguageCode(String code) {
        // update this code to use your instance variable to return the correct value
        return languageCodeMap.getOrDefault(code.toLowerCase(), "Language code not found");
    }

    /**
     * Returns the code of the language for the given language name.
     * @param language the name of the language                    输入语言名称 返回2位code
     * @return the 2-letter code of the language
     */
    public String fromLanguage(String language) {
        // update this code to use your instance variable to return the correct value
        for (Map.Entry<String, String> entry : languageCodeMap.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(language)) {
                return entry.getKey();
            }
        }
        return "Language name not found";
    }

    /**
     * Returns how many languages are included in this code converter.
     * @return how many languages are included in this code converter.
     */
    public int getNumLanguages() {
        // update this code to use your instance variable to return the correct value
        return languageCodeMap.size();
    }
}
