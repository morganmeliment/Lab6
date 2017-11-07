import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

/**
 * Implement front compression.
 * <p>
 * Front compression (also called, strangely, back compression, and, less strangely, front coding)
 * is a compression algorithm used for reducing the size of certain kinds of textual structured
 * data. Instead of storing an entire string value, we use a prefix from the previous value in a
 * list.
 * <p>
 * Front compression is particularly useful when compressing lists of words where each successive
 * element has a great deal of similarity with the previous. One example is a search (or book)
 * index. Another example is a dictionary.
 * <p>
 * This starter code will help walk you through the process of implementing front compression.
 *
 * @see <a href="https://cs125.cs.illinois.edu/lab/6/">Lab 6 Description</a>
 * @see <a href="https://en.wikipedia.org/wiki/Incremental_encoding"> Incremental Encoding on
 *      Wikipedia </a>
 */

public class FrontCompression {

    /**
     * Compress a newline-separated list of words using simple front compression.
     *
     * @param corpus2 the newline-separated list of words to compress
     * @return the input compressed using front encoding
     */
    public static String compress(final String corpus2) {
        /*
         * Defend against bad inputs.
         */


        if (corpus2 == null) {
            return null;
        } else if (corpus2.length() == 0) {
            return "";
        }
        String[] corpusy = corpus2.split("\\R");
        String[] finalComp = new String[corpusy.length];
        for (int qwerty = 0; qwerty < corpusy.length; qwerty++) {
        /*
         * Complete this function.
         */
        String corpus = corpusy[qwerty];
        if (corpus.length() == 0) {
            System.out.println(corpus + "   12fh4");
            finalComp[qwerty] = "";
            continue;
        }

        String finished = "";
        int spaces = 0;
        int[] bin = new int[corpus.length()];
        int j;
        for (int t = 0; t < corpus.length(); t++) {
            if (corpus.charAt(t) == ' ') {
                spaces++;
            } else {
                bin[t] = 1;
            }
        }
        int[] spac = new int[spaces + 1];
        int len = 0;
        int yu = 0;
        for (int t = 0; t < corpus.length(); t++) {
            if (corpus.charAt(t) == ' ') {
                spac[yu] = len;
                yu++;
                len = 0;
            } else if (t == corpus.length() - 1) {
                spac[yu] = len + 1;
            } else {
                len++;
            }
        }
        int[][] cars = new int[spaces + 1][];
        for (int t = 0; t < cars.length; t++) {
            cars[t] = new int[spac[t]];
        }
        int y = 0;
        int diff = 0;
        for (int t = 0; t < corpus.length(); t++) {
            if (corpus.charAt(t) == ' ') {
                y++;
                diff = 0;
            } else {
                cars[y][diff] = (int) corpus.charAt(t);
            }
            diff++;
        }

        String preFin = "";
        String byt = "";
        String temp;
        for (int t = 0; t < cars.length; t++) {
            byt = "";
            //System.out.println(cars[t].length);
            for (int iu = 0; iu < cars[t].length; iu++) {
                temp = Integer.toBinaryString(cars[t][iu]);
                if (temp.length() > 8) {
                    temp = temp.substring(temp.length() - 8);
                } else if (temp.length() < 8) {
                    temp = String.format("%8s", temp).replace(' ', '0');
                }
                byt = byt + temp;
                //System.out.println(byt.length());
            }
            preFin = preFin + byt + " ";
        }
        //System.out.print(preFin);
        String[] bytes = preFin.split("\\s+");
        String you;
        String preFin2 = "";
        for (int g = 0; g < bytes.length; g++) {
            you = "";
            for (int ui = 0; ui < ((int) ((bytes[g].length() - 1) - ((bytes[g].length() - 1) % 32)) / 32) + 1; ui++) {
                //System.out.println((bytes[g].length() % 32));
                if ((ui * 32) + 32 > bytes[g].length()) {
                    you = you + (char) Integer.parseInt(bytes[g].substring(ui * 32, bytes[g].length()), 2);
                    //System.out.println(bytes[g].substring(ui * 32, bytes[g].length()));
                } else {
                    you = you + (char) Integer.parseInt(bytes[g].substring(ui * 32, (ui * 32) + 32), 2);
                    //System.out.println(bytes[g].substring(ui * 32, (ui * 32) + 32));
                }
                //System.out.println(corpus);
            }
            preFin2 = preFin2 + you + "\n";
        }
        finalComp[qwerty] = preFin2;
        }
        String finalString = "";
        for (String yui: finalComp) {
            finalString += yui;
        }
        //System.out.println(finalString);
        /*System.out.println("Original length: " + corpus2.length());
        System.out.println("Compressed length: " + finalString.length());
        System.out.println(((float) finalString.length()) / corpus2.length());*/
        return finalString;
    }

    /**
     * Decompress a newline-separated list of words using simple front compression.
     *
     * @param corpus the newline-separated list of words to decompress
     * @return the input decompressed using front encoding
     */
    public static String decompress(final String corpus) {
        /*
         * Defend against bad inputs.
         */
        if (corpus == null) {
            return null;
        } else if (corpus.length() == 0) {
            return "";
        }
        String[] corpusy = corpus.split("\\R");
        String[] finalComp = new String[corpusy.length];
        for (int qwerty = 0; qwerty < corpusy.length; qwerty++) {
            String you = "";
            String preFin2 = "";
            for (int g = 0; g < corpusy[qwerty].length(); g++) {
                you += String.format("%32s", Integer.toBinaryString(corpusy[qwerty].charAt(g))).replace(' ', '0');
            }
            int gu = 8;
            for (int ui = 0; ui < ((int) ((you.length() - 1) - ((you.length() - 1) % gu)) / gu) + 1; ui++) {
                if ((ui * gu) + gu > you.length()) {
                    preFin2 = preFin2 + (char) Integer.parseInt(you.substring(ui * gu, you.length()), 2);
                    //System.out.println(bytes[g].substring(ui * 32, bytes[g].length()));
                } else {
                    preFin2 = preFin2 + (char) Integer.parseInt(you.substring(ui * gu, (ui * gu) + gu), 2);
                    //System.out.println(bytes[g].substring(ui * 32, (ui * 32) + 32));
                }
            }
            finalComp[qwerty] = preFin2;
        }
        String finalString = "";
        for (String yui: finalComp) {
            finalString += yui + "\n";
            //System.out.println(yui);
        }
        //System.out.println(finalString);

        /*
         * Complete this function.
         */

        return finalString;
    }

    /**
     * Compute the length of the common prefix between two strings.
     *
     * @param firstString the first string
     * @param secondString the second string
     * @return the length of the common prefix between the two strings
     */
    private static int longestPrefix(final String firstString, final String secondString) {
        /*
         * Complete this function.
         */
        return 0;
    }

    /**
     * Test your compression and decompression algorithm.
     *
     * @param unused unused input arguments
     * @throws URISyntaxException thrown if the file URI is invalid
     * @throws FileNotFoundException thrown if the file cannot be found
     */
    public static void main(final String[] unused)
            throws URISyntaxException, FileNotFoundException {

        /*
         * The magic 6 lines that you need in Java to read stuff from a file.
         */
        String words = null;
        String wordsFilePath = FrontCompression.class.getClassLoader().getResource("words.txt")
                .getFile();
        wordsFilePath = new URI(wordsFilePath).getPath();
        File wordsFile = new File(wordsFilePath);
        Scanner wordsScanner = new Scanner(wordsFile, "UTF-8");
        words = wordsScanner.useDelimiter("\\A").next();
        wordsScanner.close();

        String originalWords = words;
        String compressedWords = compress(words);
        String decompressedWords = decompress(compressedWords);

        if (decompressedWords.equals(originalWords)) {
            System.out.println("Original length: " + originalWords.length());
            System.out.println("Compressed length: " + compressedWords.length());
        } else {
            System.out.println("Your compression or decompression is broken!");
            String[] originalWordsArray = originalWords.split("\\R");
            String[] decompressedWordsArray = decompressedWords.split("\\R");
            boolean foundMismatch = false;
            for (int stringIndex = 0; //
                    stringIndex < Math.min(originalWordsArray.length,
                            decompressedWordsArray.length); //
                    stringIndex++) {
                if (!(originalWordsArray[stringIndex]
                        .equals(decompressedWordsArray[stringIndex]))) {
                    System.out.println("Line " + stringIndex + ": " //
                            + originalWordsArray[stringIndex] //
                            + " != " + decompressedWordsArray[stringIndex]);
                    foundMismatch = true;
                    break;
                }
            }
            if (!foundMismatch) {
                if (originalWordsArray.length != decompressedWordsArray.length) {
                    System.out.println("Original and decompressed files have different lengths");
                } else {
                    System.out.println("Original and decompressed files " //
                            + "have different line endings.");
                }
            }
        }
    }
}
