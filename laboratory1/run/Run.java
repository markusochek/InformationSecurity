package laboratory1.run;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Run {
    private static HashMap<String, String> ratios = new HashMap<>();

    public static void main(String[] args) throws IOException {
        String text = "In religion and folklore, hell is a location or state in the afterlife" +
                " in which souls are subjected to punitive suffering, most often through torture," +
                " as eternal punishment after death. Religions with a linear divine history" +
                " often depict hells as eternal destinations, the biggest examples of which are Christianity" +
                " and Islam, whereas religions with reincarnation usually depict a hell as an intermediary period" +
                " between incarnations, as is the case in the dharmic religions." +
                " Religions typically locate hell in another dimension or under Earth's surface." +
                " Other afterlife destinations include heaven, paradise, purgatory, limbo, and the underworld." +
                "Other religions, which do not conceive of the afterlife as a place of punishment or reward," +
                " merely describe an abode of the dead, the grave," +
                " a neutral place that is located under the surface of Earth (for example, see Kur, Hades, and Sheol)." +
                " Such places are sometimes equated with the English word hell," +
                " though a more correct translation would be underworld or world of the dead." +
                " The ancient Mesopotamian, Greek, Roman," +
                " and Finnic religions include entrances to the underworld from the land of the living.";
        StringBuffer encodingString = encode(text);
        System.out.println(encodingString);
        System.out.println(decode(encodingString));
        hacking(encodingString);
        getWordsInTxt();
    }

    private static StringBuffer encode(String text) {
        StringBuffer encodingString = new StringBuffer();
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (String symbol: text.split("")) {
            if (!(65 <= symbol.charAt(0) && symbol.charAt(0) < 91 || 97 <= symbol.charAt(0) && symbol.charAt(0) < 123)) {
                encodingString.append(symbol);
                continue;
            }

            String randomLetter = getRandomLetter(symbol, ratios, alphabet);
            ratios.put(randomLetter, symbol);
            encodingString.append(randomLetter);
            alphabet = alphabet.replace(randomLetter, "");
        }
        return encodingString;
    }

    private static String getRandomLetter(String symbol, HashMap<String, String> ratios, String alphabet) {
        for (Map.Entry<String, String> pair : ratios.entrySet()) {
            if (symbol.equals(pair.getValue())) {
                return pair.getKey();
            }
        }

        int randomLetterIndex = (int) (Math.random() * alphabet.length());
        return alphabet.substring(randomLetterIndex, randomLetterIndex + 1);
    }

    private static StringBuffer decode(StringBuffer encodingString) {
        StringBuffer decodingString = new StringBuffer();
        for (String encodingSymbol : encodingString.toString().split("")) {
            if (65 <= encodingSymbol.charAt(0) && encodingSymbol.charAt(0) < 91 || 97 <= encodingSymbol.charAt(0) && encodingSymbol.charAt(0) < 123) {
                decodingString.append(ratios.get(encodingSymbol));
            } else {
                decodingString.append(encodingSymbol);
            }
        }
        return decodingString;
    }

    private static StringBuffer hacking(StringBuffer encodingString) throws IOException {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Integer AIndex = 65;
        Float quantitativeAnalysisCount = 0F;
        ArrayList<ArrayList<Integer>> wordsInFile = getWordsInTxt();

        ArrayList<Float> quantitativeRatioLetterAnalysis = new ArrayList<>(Collections.nCopies(52, 0F));

//        ArrayList<ArrayList<Integer>> quantitativeRatioLocationAnalysis = new ArrayList<>();
//        ArrayList<ArrayList<Integer>> quantitativeRatioLengthOfWord = new ArrayList<>();
//        for (int i = 0; i < alphabet.length() * 2; i++) {
//            quantitativeRatioLocationAnalysis.add(new ArrayList<>(Collections.nCopies(45, 0)));
//            quantitativeRatioLengthOfWord.add(new ArrayList<>(Collections.nCopies(45, 0)));
//        }

        for (ArrayList<Integer> wordInFile : wordsInFile) {
            for (int i = 0; i < wordInFile.size(); i++) {
                Integer letterCode = wordInFile.get(i);

                if (65 <= letterCode && letterCode < 91) {
                    quantitativeRatioLetterAnalysis.set(letterCode - AIndex, quantitativeRatioLetterAnalysis.get(letterCode - AIndex) + 1);
//                    quantitativeRatioLocationAnalysis.get(letterCode - AIndex).set(i, quantitativeRatioLocationAnalysis.get(letterCode - AIndex).get(i) + 1);
//                    quantitativeRatioLengthOfWord.get(letterCode - AIndex).set(wordInFile.size(), quantitativeRatioLengthOfWord.get(letterCode - AIndex).get(wordInFile.size()) + 1);
                } else if (97 <= letterCode && letterCode < 123) {
                    quantitativeRatioLetterAnalysis.set(letterCode - AIndex - 6, quantitativeRatioLetterAnalysis.get(letterCode - AIndex - 6) + 1);
//                    quantitativeRatioLocationAnalysis.get(letterCode - AIndex - 6).set(i, quantitativeRatioLocationAnalysis.get(letterCode - AIndex - 6).get(i) + 1);
//                    quantitativeRatioLengthOfWord.get(letterCode - AIndex - 6).set(wordInFile.size(), quantitativeRatioLengthOfWord.get(letterCode - AIndex - 6).get(wordInFile.size()) + 1);
                }
                ++quantitativeAnalysisCount;
            }
        }

        for (int i = 0; i < quantitativeRatioLetterAnalysis.size(); i++) {
            quantitativeRatioLetterAnalysis.set(i, quantitativeRatioLetterAnalysis.get(i) / quantitativeAnalysisCount);
        }

//        for (ArrayList<Integer> quantitativeRatio : quantitativeRatioLocationAnalysis) {
//            ArrayList<Float> quantitativeRatioStorage = new ArrayList<>();
//            for (Integer ratio : quantitativeRatio) {
//                quantitativeRatioStorage.add(ratio / quantitativeAnalysisCount);
//            }
//            quantitativeRatioMapLocationAnalysis.add(quantitativeRatioStorage);
//        }
//
//        for (ArrayList<Integer> quantitativeRatio : quantitativeRatioLengthOfWord) {
//            ArrayList<Float> quantitativeRatioStorage = new ArrayList<>();
//            for (Integer ratio : quantitativeRatio) {
//                quantitativeRatioStorage.add(ratio / quantitativeAnalysisCount);
//            }
//            quantitativeRatioMapAnalysisOfWord.add(quantitativeRatioStorage);
//        }

//        wordsInFile.sort();

        System.out.println(quantitativeRatioLetterAnalysis);


//        System.out.println(quantitativeRatioMapAnalysis);
//        System.out.println(quantitativeRatioMapLocationAnalysis);
//        System.out.println(quantitativeRatioMapAnalysisOfWord);
        return encodingString;
    }

    private static ArrayList<ArrayList<Integer>> getWordsInTxt() throws IOException {
        ArrayList<ArrayList<Integer>> wordsInFile = new ArrayList<>();

        FileInputStream fileInputStream = new FileInputStream("laboratory1/txt/ChristieAgatha.txt");
        int i;
        ArrayList<Integer> word = new ArrayList<>();
        while((i = fileInputStream.read()) != -1) {
            if (i == 32 || i == 10 || i == 13 || i == 46) {
                if (word.size() == 0) {
                    continue;
                }
                wordsInFile.add(word);
                word = new ArrayList<>();
                continue;
            }
            if (65 <= i && i < 91 || 97 <= i && i < 123) {
                word.add(i);
            }
        }
        fileInputStream.close();

        return wordsInFile;
    }
}
