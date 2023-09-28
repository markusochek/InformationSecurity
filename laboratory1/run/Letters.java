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

        ArrayList<Integer> decodingString = new ArrayList<>();
        ArrayList<List<List<Symbol>>> wordsWeights = new ArrayList<>();
        for (String word : encodingString.toString().split(" ")) {
            int index = 0;
            ArrayList<List<Symbol>> wordWeights = new ArrayList<>();
            for (String letter : word.split("")) {
                List<Symbol> letterWeights = new ArrayList<>();
                for (int i = 0; i < quantitativeRatioMapAnalysis.size(); i++) {
                    int location;
                    if (i < 26) {
                        location = AIndex + i;
                    } else {
                        location = aIndex - 26 + i;
                    }
                    letterWeights.add(new Symbol(location, quantitativeRatioMapAnalysis.get(i) *
                    quantitativeRatioMapLocationAnalysis.get(i).get(index) *
                    quantitativeRatioMapAnalysisOfWord.get(i).get(word.length())));
                }
                letterWeights = letterWeights.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
                wordWeights.add(letterWeights);
                ++index;

                for (Symbol letterSymbol : letterWeights) {
                    Optional<Integer> bukva = decodingString.stream().filter(symbol -> symbol.equals(letter.codePointAt(0))).findFirst();
                        if (bukva.isPresent()) {
                            decodingString.add(bukva.get());
                            break;
                        }

                    if (decodingString.stream().noneMatch(symbol -> symbol.equals(letterSymbol.getLocation()))) {
                        decodingString.add(letterSymbol.getLocation());
                        break;
                    }
                }
            }
            decodingString.add(32);
            wordsWeights.add(wordWeights);
        }

        System.out.println(wordsWeights);
        System.out.println(decodingString.stream().map(word -> (char) word.intValue()).collect(Collectors.toList()));
    }

    private void frequencyAnalysis() throws IOException {
        getWordsInTxt();
        quantitativeAnalysis();
        locationAnalysis();
        quantitativeAnalysisOfWord();

        System.out.println(quantitativeRatioMapAnalysis);
        System.out.println(quantitativeRatioMapLocationAnalysis);
        System.out.println(quantitativeRatioMapAnalysisOfWord);
    }

    private void quantitativeAnalysis() throws IOException {
        ArrayList<Integer> quantitativeRatioUpperCaseLetter = new ArrayList<>(Collections.nCopies(26, 0));
        ArrayList<Integer> quantitativeRatioLowerCaseLetter = new ArrayList<>(Collections.nCopies(26, 0));

        FileInputStream fileInputStream = new FileInputStream("laboratory1/run/ChristieAgatha.txt");
        int i;
        while((i = fileInputStream.read()) != -1) {
            if (65 <= i && i < 91) {
                quantitativeRatioUpperCaseLetter.set(i - AIndex, quantitativeRatioUpperCaseLetter.get(i - AIndex) + 1);
                ++quantitativeAnalysisCount;
            } else if (97 <= i && i < 123) {
                quantitativeRatioLowerCaseLetter.set(i - aIndex, quantitativeRatioLowerCaseLetter.get(i - aIndex) + 1);
                ++quantitativeAnalysisCount;
            }
        }
        fileInputStream.close();

        for (Integer count : quantitativeRatioUpperCaseLetter) {
            quantitativeRatioMapAnalysis.add(count / quantitativeAnalysisCount);
        }
        for (Integer count : quantitativeRatioLowerCaseLetter) {
            quantitativeRatioMapAnalysis.add(count / quantitativeAnalysisCount);
        }
    }

    private void locationAnalysis() throws IOException {
        ArrayList<ArrayList<Integer>> quantitativeRatioUpperCaseLetter = new ArrayList<>();
        ArrayList<ArrayList<Integer>> quantitativeRatioLowerCaseLetter = new ArrayList<>();
        for (int i = 0; i < this.alphabet.length(); i++) {
            quantitativeRatioUpperCaseLetter.add(new ArrayList<>(Collections.nCopies(45, 0)));
            quantitativeRatioLowerCaseLetter.add(new ArrayList<>(Collections.nCopies(45, 0)));
        }

        FileInputStream fileInputStream = new FileInputStream("laboratory1/run/ChristieAgatha.txt");
        int i;
        int wordLength = 0;
        while((i = fileInputStream.read()) != -1) {
            if (i == 32) {
                wordLength = 0;
                continue;
            }

            if (65 <= i && i < 91) {
                quantitativeRatioUpperCaseLetter.get(i - AIndex).set(wordLength, quantitativeRatioUpperCaseLetter.get(i - AIndex).get(wordLength) + 1);
                ++wordLength;
            } else if (97 <= i && i < 123) {
                quantitativeRatioLowerCaseLetter.get(i - aIndex).set(wordLength, quantitativeRatioLowerCaseLetter.get(i - aIndex).get(wordLength) + 1);
                ++wordLength;
            }
        }
        fileInputStream.close();

        for (ArrayList<Integer> quantitativeRatio : quantitativeRatioUpperCaseLetter) {
            ArrayList<Float> quantitativeRatioStorage = new ArrayList<>();
            for (Integer ratio : quantitativeRatio) {
                quantitativeRatioStorage.add(ratio / quantitativeAnalysisCount);
            }
            quantitativeRatioMapLocationAnalysis.add(quantitativeRatioStorage);
        }
        for (ArrayList<Integer> quantitativeRatio : quantitativeRatioLowerCaseLetter) {
            ArrayList<Float> quantitativeRatioStorage = new ArrayList<>();
            for (Integer ratio : quantitativeRatio) {
                quantitativeRatioStorage.add(ratio / quantitativeAnalysisCount);
            }
            quantitativeRatioMapLocationAnalysis.add(quantitativeRatioStorage);
        }
    }

    private void quantitativeAnalysisOfWord() throws IOException {
        ArrayList<ArrayList<Integer>> quantitativeRatioUpperCaseLetter = new ArrayList<>();
        ArrayList<ArrayList<Integer>> quantitativeRatioLowerCaseLetter = new ArrayList<>();
        for (int i = 0; i < this.alphabet.length(); i++) {
            quantitativeRatioUpperCaseLetter.add(new ArrayList<>(Collections.nCopies(45, 0)));
            quantitativeRatioLowerCaseLetter.add(new ArrayList<>(Collections.nCopies(45, 0)));
        }

        FileInputStream fileInputStream = new FileInputStream("laboratory1/run/ChristieAgatha.txt");
        int i;
        ArrayList<Integer> wordUpperCaseLetter = new ArrayList<>();
        ArrayList<Integer> wordLowerCaseLetter = new ArrayList<>();
        while((i = fileInputStream.read()) != -1) {
            if (i == 32) {
                int wordLength = wordUpperCaseLetter.size() + wordLowerCaseLetter.size();
                for (Integer letter : wordUpperCaseLetter) {
                    quantitativeRatioUpperCaseLetter.get(letter - AIndex).set(wordLength, quantitativeRatioUpperCaseLetter.get(letter - AIndex).get(wordLength) + 1);
                }
                for (Integer letter : wordLowerCaseLetter) {
                    quantitativeRatioLowerCaseLetter.get(letter - aIndex).set(wordLength, quantitativeRatioLowerCaseLetter.get(letter - aIndex).get(wordLength) + 1);
                }
                wordUpperCaseLetter.clear();
                wordLowerCaseLetter.clear();
                continue;
            }

            if (65 <= i && i < 91) {
                wordUpperCaseLetter.add(i);
            } else if (97 <= i && i < 123) {
                wordLowerCaseLetter.add(i);
            }
        }
        fileInputStream.close();

        for (int j = 0; j < quantitativeRatioUpperCaseLetter.size(); j++) {
            ArrayList<Integer> quantitativeRatio = quantitativeRatioUpperCaseLetter.get(j);
            ArrayList<Float> quantitativeRatioStorage = new ArrayList<>();
            for (Integer ratio : quantitativeRatio) {
                quantitativeRatioStorage.add(ratio / quantitativeAnalysisCount);
            }
            quantitativeRatioMapAnalysisOfWord.add(quantitativeRatioStorage);
        }
        for (int j = 0; j < quantitativeRatioLowerCaseLetter.size(); j++) {
            ArrayList<Integer> quantitativeRatio = quantitativeRatioLowerCaseLetter.get(j);
            ArrayList<Float> quantitativeRatioStorage = new ArrayList<>();
            for (Integer ratio : quantitativeRatio) {
                quantitativeRatioStorage.add(ratio / quantitativeAnalysisCount);
            }
            quantitativeRatioMapAnalysisOfWord.add(quantitativeRatioStorage);
        }
    }

    private void encode() throws IOException {
        String[] textSplit = this.text.split("");
        for (String symbol: textSplit) {
            if (symbol.equals(" ")) {
                this.encodingString.append(" ");
                continue;
            }

            String randomLetter = this.getRandomLetter(symbol);
            this.ratios = Collections.singletonMap(symbol, randomLetter);
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