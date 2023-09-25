package laboratory1.run;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Letters {
    private final String text;
    private String alphabet;
    private Map<String, String> ratios;
    private final StringBuffer encodingString;
    private Map<String, Float> quantitativeRatioMapAnalysis;
    private Map<String, ArrayList<Float>> quantitativeRatioMapLocationAnalysis;
    private Map<String, ArrayList<Float>> quantitativeRatioMapAnalysisOfWord;

    private Float quantitativeAnalysisCount;

    Letters() {
        this.text = "i play computer game";
        this.alphabet = "abcdefghijklmnopqrstuvwxyz";
        this.encodingString = new StringBuffer();
        this.ratios = new HashMap<>();

        this.quantitativeRatioMapAnalysis = new HashMap<>();
        this.quantitativeRatioMapLocationAnalysis = new HashMap<>();
        this.quantitativeRatioMapAnalysisOfWord = new HashMap<>();

        this.quantitativeAnalysisCount = 0F;
    }

    public void main() throws IOException {
        frequencyAnalysis();
        encode();

        List<Map.Entry<String, Float>> sortedQuantitativeRatioMapAnalysis = quantitativeRatioMapAnalysis.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toList());
        System.out.println(sortedQuantitativeRatioMapAnalysis);


        StringBuffer decodingString = new StringBuffer();
        for (String word : encodingString.toString().split(" ")) {
            int index = 0;

            for (String letter : word.split("")) {
                quantitativeRatioMapAnalysis.get(letter);
//                decodingString.append();
                ++index;
            }
        }
        System.out.println(decodingString);
    }

    private void sort() {
        for (int i = 0; i < quantitativeRatioMapLocationAnalysis.entrySet().size(); i++) {
        }
    }

    private void frequencyAnalysis() throws IOException {
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
        int AIndex = 65;
        int aIndex = 97;
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

        for (int j = 0; j < quantitativeRatioUpperCaseLetter.size(); j++) {
            quantitativeRatioMapAnalysis.put(String.valueOf((char) (AIndex + j)), quantitativeRatioUpperCaseLetter.get(j) / quantitativeAnalysisCount);
        }
        for (int j = 0; j < quantitativeRatioLowerCaseLetter.size(); j++) {
            quantitativeRatioMapAnalysis.put(String.valueOf((char) (aIndex + j)), quantitativeRatioLowerCaseLetter.get(j) / quantitativeAnalysisCount);
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
        int AIndex = 65;
        int aIndex = 97;
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

        for (int j = 0; j < quantitativeRatioUpperCaseLetter.size(); j++) {
            ArrayList<Integer> quantitativeRatio = quantitativeRatioUpperCaseLetter.get(j);
            ArrayList<Float> quantitativeRatioStorage = new ArrayList<>();
            for (Integer ratio : quantitativeRatio) {
                quantitativeRatioStorage.add(ratio / quantitativeAnalysisCount);
            }
            quantitativeRatioMapLocationAnalysis.put(String.valueOf((char) (AIndex + j)), quantitativeRatioStorage);
        }
        for (int j = 0; j < quantitativeRatioLowerCaseLetter.size(); j++) {
            ArrayList<Integer> quantitativeRatio = quantitativeRatioLowerCaseLetter.get(j);
            ArrayList<Float> quantitativeRatioStorage = new ArrayList<>();
            for (Integer ratio : quantitativeRatio) {
                quantitativeRatioStorage.add(ratio / quantitativeAnalysisCount);
            }
            quantitativeRatioMapLocationAnalysis.put(String.valueOf((char) (aIndex + j)), quantitativeRatioStorage);
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
        int AIndex = 65;
        int aIndex = 97;
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
            quantitativeRatioMapAnalysisOfWord.put(String.valueOf((char) (AIndex + j)), quantitativeRatioStorage);
        }
        for (int j = 0; j < quantitativeRatioLowerCaseLetter.size(); j++) {
            ArrayList<Integer> quantitativeRatio = quantitativeRatioLowerCaseLetter.get(j);
            ArrayList<Float> quantitativeRatioStorage = new ArrayList<>();
            for (Integer ratio : quantitativeRatio) {
                quantitativeRatioStorage.add(ratio / quantitativeAnalysisCount);
            }
            quantitativeRatioMapAnalysisOfWord.put(String.valueOf((char) (aIndex + j)), quantitativeRatioStorage);
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
}