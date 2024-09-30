package org.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {

        // once you finish the JSONTranslator,
        // you can use it here instead of the InLabByHandTranslator
        // to try out the whole program!
        Translator translator = new JSONTranslator("sample.json");
        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program.
     */
    public static void runProgram(Translator translator) {
        // 将字符串 "quit" 定义为常量，遵守 CheckStyle 规则
        final String quit = "quit";

        while (true) {
            String country = promptForCountry(translator);
            // The String "quit" appears 3 times in the file.
            // String literal expressions should be on the left side of an equals comparison
            if (quit.equals(country)) {
                break;
            }
            // Once you switch promptForCountry so that it returns the country
            // name rather than the 3-letter country code, you will need to
            // convert it back to its 3-letter country code when calling promptForLanguage
            String language = promptForLanguage(translator, country);
            if (quit.equals(language)) {
                break;
            }
            // Once you switch promptForLanguage so that it returns the language
            // name rather than the 2-letter language code, you will need to
            // convert it back to its 2-letter language code when calling translate.
            // Note: you should use the actual names in the message printed below though,
            // since the user will see the displayed message.
            System.out.println(country + " in " + language + " is " + translator.translate(country, language));
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (quit.equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        // replace the following println call, sort the countries alphabetically,and print them out; one per line
        // hint: class Collections provides a static sort method
        // convert the country codes to the actual country names before sorting
        List<String> countryCodes = translator.getCountries();

        // 创建 CountryCodeConverter 实例
        CountryCodeConverter countryCodeConverter = new CountryCodeConverter();

        // 转换国家代码为国家名称并存储在列表中
        List<String> countryNames = new ArrayList<>();
        for (String code : countryCodes) {
            String countryName = countryCodeConverter.fromCountryCode(code);
            if (!"Country code not found".equals(countryName)) {
                countryNames.add(countryName);
            }
        }
        // 对国家名称进行字母顺序排序
        Collections.sort(countryNames);

        // 逐行打印排序后的国家名称
        for (String name : countryNames) {
            System.out.println(name);
        }

        System.out.println("select a country from above:");
        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {
        // replace the line below so that we sort the languages alphabetically and print them out; one per line
        // convert the language codes to the actual language names before sorting
        List<String> languageCodes = translator.getCountryLanguages(country);

        // 创建 LanguageCodeConverter 实例
        LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();

        // 转换语言代码为语言名称并存储在列表中
        List<String> languageNames = new ArrayList<>();
        for (String code : languageCodes) {
            String languageName = languageCodeConverter.fromLanguageCode(code);
            if (!"Language code not found".equals(languageName)) {
                languageNames.add(languageName);
            }
        }

        // 对语言名称进行字母顺序排序
        Collections.sort(languageNames);

        // 逐行打印排序后的语言名称
        for (String name : languageNames) {
            System.out.println(name);
        }

        System.out.println("select a language from above:");
        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}
