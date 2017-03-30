package ap.cayenne.learning.functions.lang;

import org.apache.commons.lang.RandomStringUtils;

public class APRandomFunctions {

    /*
     * Generates random character sequence as a string in specified diapason
     * int minStringLength - minimum length of a string
     * int maxStringLength - maximum length of a string
     */
    public static String generateRandomString (int minStringLength, int maxStringLength){
        String generatedString = null;
        int stringLength = (int) (minStringLength + Math.random() * (maxStringLength - minStringLength));

        generatedString = RandomStringUtils.randomAlphabetic(stringLength);

        return generatedString;
    }
}
