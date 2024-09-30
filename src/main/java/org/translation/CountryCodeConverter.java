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
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {
    // pick appropriate instance variable(s) to store the data necessary for this class
    private Map<String, String> countryCodeMap;

    /**
     * Default constructor which will load the country codes from "country-codes.txt".
     * in the resources folder.
     */
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {
        // 初始化 HashMap
        countryCodeMap = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));
            String regex = "^(.*?)\\s+[A-Z]{2}\\s+([A-Z]{3})\\s+\\d+$";
            Pattern pattern = Pattern.compile(regex);
            // use lines to populate the instance variable(s)
            for (String line : lines.subList(1, lines.size())) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String countryName = matcher.group(1).trim();
                    String alpha3Code = matcher.group(2);
                    // 实现 Map alpha3code --> countryName
                    countryCodeMap.put(alpha3Code, countryName);
                }
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country                输入3位code 返回国家名称
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        // update this code to use an instance variable to return the correct value
        return countryCodeMap.getOrDefault(code.toUpperCase(), "Country code not found");
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country                      输入国家名称 返回3位code
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        // update this code to use an instance variable to return the correct value
        for (Map.Entry<String, String> entry : countryCodeMap.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(country)) {
                return entry.getKey();
            }
        }
        return "Country not found";
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        // update this code to use an instance variable to return the correct value
        return countryCodeMap.size();
    }
}
