package laboratory1.run;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class Letters {
    int AIndex = 65;
    int aIndex = 97;

    private final String text;
    private String alphabet;
    private Map<String, String> ratios;
    private final StringBuffer encodingString;
    private ArrayList<Float> quantitativeRatioMapAnalysis;
    private ArrayList<ArrayList<Float>> quantitativeRatioMapLocationAnalysis;
    private ArrayList<ArrayList<Float>> quantitativeRatioMapAnalysisOfWord;
    private ArrayList<ArrayList<Integer>> wordsInFile;

    private Float quantitativeAnalysisCount;

    Letters() {
        this.text = "I play computer game";
        this.alphabet = "abcdefghijklmnopqrstuvwxyz";
        this.encodingString = new StringBuffer();
        this.ratios = new HashMap<>();

        this.quantitativeRatioMapAnalysis = new ArrayList<>();
        this.quantitativeRatioMapLocationAnalysis = new ArrayList<>();
        this.quantitativeRatioMapAnalysisOfWord = new ArrayList<>();
        this.wordsInFile = new ArrayList<>();

        this.quantitativeAnalysisCount = 0F;
    }

    public void main() throws IOException {
        frequencyAnalysis();
        encode();

//        ArrayList<Integer> decodingString = new ArrayList<>();
//        ArrayList<List<List<Symbol>>> wordsWeights = new ArrayList<>();
//        Map<Integer, Integer> keys = new HashMap<>();
//        for (String word : encodingString.toString().split(" ")) {
//            int index = 0;
//            ArrayList<List<Symbol>> wordWeights = new ArrayList<>();
//            for (String letter : word.split("")) {
//                List<Symbol> letterWeights = new ArrayList<>();
//                for (int i = 0; i < quantitativeRatioMapAnalysis.size(); i++) {
//                    int location;
//                    if (i < 26) {
//                        location = AIndex + i;
//                    } else {
//                        location = aIndex - 26 + i;
//                    }
//                    letterWeights.add(new Symbol(location, quantitativeRatioMapAnalysis.get(i) *
//                    quantitativeRatioMapLocationAnalysis.get(i).get(index) *
//                    quantitativeRatioMapAnalysisOfWord.get(i).get(word.length())));
//                }
//                letterWeights = letterWeights.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
//                wordWeights.add(letterWeights);
//
//                for (Symbol letterSymbol : letterWeights) {
//                    int finalIndex = index;
//                    List<ArrayList<Integer>> fsf = wordsInFile.stream().filter(wordInFile -> {
//                        if (wordInFile.size() == word.length()) {
//                            return wordInFile.get(finalIndex).equals(letterSymbol.getLocation());
//                        }
//                        return false;
//                    }).collect(Collectors.toList());
//
//                    Set<ArrayList<Integer>> set = new HashSet<>(fsf);
//                    fsf.clear();
//                    fsf.addAll(set);
//                    System.out.println(fsf);
//
//                    Integer code = keys.get((int) letter.charAt(0));
//                    if (code != null) {
//                        decodingString.add(code);
//                        break;
//                    }
//
//                    if (decodingString.stream().noneMatch(symbol -> symbol.equals(letterSymbol.getLocation()))) {
//                        keys.put((int) letter.charAt(0), letterSymbol.getLocation());
//                        decodingString.add(letterSymbol.getLocation());
//                        break;
//                    }
//                }
//                ++index;
//            }
//            decodingString.add(32);
//            wordsWeights.add(wordWeights);
//        }

//        System.out.println(wordsWeights);
//        System.out.println(decodingString.stream().map(word -> (char) word.intValue()).collect(Collectors.toList()));
    }

    private void enumeration() {
        String[] splitEncodingString = encodingString.toString().split(" ");
        for (int i = 0; i < splitEncodingString.length; i++) {
            String[] word = splitEncodingString[i].split(" ");
            for (int j = 0; j < word.length; j++) {
                word[j];
            }
        }
    }

    private void frequencyAnalysis() throws IOException {
        getWordsInTxt();

        ArrayList<Integer> quantitativeRatioLetterAnalysis = new ArrayList<>(Collections.nCopies(52, 0));

        ArrayList<ArrayList<Integer>> quantitativeRatioLocationAnalysis = new ArrayList<>();
        ArrayList<ArrayList<Integer>> quantitativeRatioLengthOfWord = new ArrayList<>();
        for (int i = 0; i < this.alphabet.length() * 2; i++) {
            quantitativeRatioLocationAnalysis.add(new ArrayList<>(Collections.nCopies(45, 0)));
            quantitativeRatioLengthOfWord.add(new ArrayList<>(Collections.nCopies(45, 0)));
        }

        for (ArrayList<Integer> wordInFile : wordsInFile) {
            for (int i = 0; i < wordInFile.size(); i++) {
                Integer letterCode = wordInFile.get(i);

                if (65 <= letterCode && letterCode < 91) {
                    quantitativeRatioLetterAnalysis.set(letterCode - AIndex, quantitativeRatioLetterAnalysis.get(letterCode - AIndex) + 1);
                    quantitativeRatioLocationAnalysis.get(letterCode - AIndex).set(i, quantitativeRatioLocationAnalysis.get(letterCode - AIndex).get(i) + 1);
                    quantitativeRatioLengthOfWord.get(letterCode - AIndex).set(wordInFile.size(), quantitativeRatioLengthOfWord.get(letterCode - AIndex).get(wordInFile.size()) + 1);
                } else if (97 <= letterCode && letterCode < 123) {
                    quantitativeRatioLetterAnalysis.set(letterCode - AIndex - 6, quantitativeRatioLetterAnalysis.get(letterCode - AIndex - 6) + 1);
                    quantitativeRatioLocationAnalysis.get(letterCode - AIndex - 6).set(i, quantitativeRatioLocationAnalysis.get(letterCode - AIndex - 6).get(i) + 1);
                    quantitativeRatioLengthOfWord.get(letterCode - AIndex - 6).set(wordInFile.size(), quantitativeRatioLengthOfWord.get(letterCode - AIndex - 6).get(wordInFile.size()) + 1);
                }
                ++quantitativeAnalysisCount;
            }
        }

        for (Integer count : quantitativeRatioLetterAnalysis) {
            quantitativeRatioMapAnalysis.add(count / quantitativeAnalysisCount);
        }

        for (ArrayList<Integer> quantitativeRatio : quantitativeRatioLocationAnalysis) {
            ArrayList<Float> quantitativeRatioStorage = new ArrayList<>();
            for (Integer ratio : quantitativeRatio) {
                quantitativeRatioStorage.add(ratio / quantitativeAnalysisCount);
            }
            quantitativeRatioMapLocationAnalysis.add(quantitativeRatioStorage);
        }

        for (ArrayList<Integer> quantitativeRatio : quantitativeRatioLengthOfWord) {
            ArrayList<Float> quantitativeRatioStorage = new ArrayList<>();
            for (Integer ratio : quantitativeRatio) {
                quantitativeRatioStorage.add(ratio / quantitativeAnalysisCount);
            }
            quantitativeRatioMapAnalysisOfWord.add(quantitativeRatioStorage);
        }

//        wordsInFile.sort();

        System.out.println(quantitativeRatioMapAnalysis);
        System.out.println(quantitativeRatioMapLocationAnalysis);
        System.out.println(quantitativeRatioMapAnalysisOfWord);
    }

    private void encode() {
        String[] textSplit = this.text.split("");
        for (String symbol: textSplit) {
            if (symbol.equals(" ")) {
                this.encodingString.append(" ");
                continue;
            }

            String randomLetter = this.getRandomLetter(symbol);
            this.ratios.put(symbol, randomLetter);
            this.encodingString.append(randomLetter);
            this.alphabet = this.alphabet.replace(randomLetter, "");
        }
        System.out.println(this.encodingString);
    }

    private String getRandomLetter(String symbol) {
        for (Map.Entry<String, String> pair : this.ratios.entrySet()) {
            if (symbol.equals(pair.getKey())) {
                return pair.getValue();
            }
        }

        int randomLetterIndex = (int) (Math.random() * this.alphabet.length());
        return this.alphabet.substring(randomLetterIndex, randomLetterIndex + 1);
    }

    private void getWordsInTxt() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("laboratory1/run/ChristieAgatha.txt");
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
    }
}