package laboratory1.run;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Run {
    private static HashMap<String, String> keys;
    private static ArrayList<ArrayList<Integer>> words;

    static {
        Run.keys = new HashMap<>();
        Run.words = new ArrayList<>();
    }

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

        System.out.println(hacking(text));
    }

    private static StringBuffer encode(String text) {
        StringBuffer encodingString = new StringBuffer();
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        ArrayList<Integer> word = new ArrayList<>();
        for (String symbol: text.split("")) {
            if (!(65 <= symbol.charAt(0) && symbol.charAt(0) < 91 || 97 <= symbol.charAt(0) && symbol.charAt(0) < 123)) {
                words.add(word);
                word = new ArrayList<>();
                encodingString.append(symbol);
                continue;
            }

            String randomLetter = getRandomLetter(symbol, keys, alphabet);
            keys.put(randomLetter, symbol);
            encodingString.append(randomLetter);
            word.add((int) randomLetter.charAt(0));
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
                decodingString.append(keys.get(encodingSymbol));
            } else {
                decodingString.append(encodingSymbol);
            }
        }
        return decodingString;
    }

    private static StringBuffer hacking(String text) throws IOException {
        int AIndex = 65;

        List<QuantitativeRatioMap> quantitativeAnalysisFile = quantitativeAnalysis(getWordsInTxt());
        List<QuantitativeRatioMap> quantitativeAnalysisInputText = quantitativeAnalysis(words);

        keys = new HashMap<>();
        for (int i = 0; i < quantitativeAnalysisFile.size(); i++) {
            keys.put(String.valueOf((char) (quantitativeAnalysisInputText.get(i).getKey().intValue())),
                    String.valueOf((char) (quantitativeAnalysisFile.get(i).getKey().intValue())));
        }

        return encode(text);
    }

    private static List<QuantitativeRatioMap> quantitativeAnalysis(ArrayList<ArrayList<Integer>> wordsInFile) {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Integer AIndex = 65;
        Float quantitativeAnalysisCount = 0F;

        ArrayList<Integer> quantitativeRatioLetterAnalysis = new ArrayList<>(Collections.nCopies(52, 0));

        for (ArrayList<Integer> wordInFile : wordsInFile) {
            for (int i = 0; i < wordInFile.size(); i++) {
                Integer letterCode = wordInFile.get(i);

                if (65 <= letterCode && letterCode < 91) {
                    quantitativeRatioLetterAnalysis.set(letterCode - AIndex, quantitativeRatioLetterAnalysis.get(letterCode - AIndex) + 1);
                } else if (97 <= letterCode && letterCode < 123) {
                    quantitativeRatioLetterAnalysis.set(letterCode - AIndex - 6, quantitativeRatioLetterAnalysis.get(letterCode - AIndex - 6) + 1);
                }
                ++quantitativeAnalysisCount;
            }
        }

        ArrayList<QuantitativeRatioMap> listQuantitativeRatioMap = new ArrayList<>();
        for (int i = 0; i < quantitativeRatioLetterAnalysis.size(); i++) {
            if (65 <= i && i < 91) {
                listQuantitativeRatioMap.add(new QuantitativeRatioMap(i + AIndex, quantitativeRatioLetterAnalysis.get(i) / quantitativeAnalysisCount));
            } else if (97 <= i && i < 123) {

            }
        }
        return listQuantitativeRatioMap.stream().sorted().collect(Collectors.toList());
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
