package com.abg.FazanSP;

import com.abg.FazanSP.Colors;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import static com.abg.FazanSP.Sounds.Sounds;

public class FileStuff {

    public int computerFazanCounter;
    boolean exists = false;
    Path dictionaryFile = Paths.get("src/com/abg/resources/Dictionary.txt");
    Path usedWordsFile = Paths.get("src/com/abg/resources/Used.txt");
    Path avoidEndings = Paths.get("src/com/abg/resources/Avoid.txt");
    boolean roundEnd = false;

    // method to check if the first letters of the word are correct:
    public boolean checkThe2Words(String oldWord, String newWord) {
        boolean correct;
        String beginningLetters = newWord.substring(0, 2);
        String endingLetters = oldWord.substring(oldWord.length() - 2);

        if (oldWord.endsWith(beginningLetters) && !previouslyUsed(newWord)) {
            correct = true;
        } else if ((oldWord.endsWith(beginningLetters) && previouslyUsed(newWord))) {
            System.out.println("Cuvântul s-a mai zis, caută altul: ");
            Sounds("ahem");
            correct = false;
        } else {
            System.out.println("Cuvântul trebuie să înceapă cu " + endingLetters);
            Sounds("ahem");
            correct = false;
        }
        return correct;
    }

    //method to check if the word is found in the dictionary:
    public boolean wordIsFoundInDictionary(String wordToBeChecked) {
        exists = false;
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(dictionaryFile);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty()) {
                    if (line.equals(wordToBeChecked)) {
                        exists = true;
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Probleme cu fisierul-dictionar. Jocul nu poate continua :(");
            System.exit(0);
        }
        return exists;
    }

    // Method to write in the used words file:
    public void writeDownWord(String wordToBeWritten) {
        // create a new file if it doesn't exist:
        try {
            File myObj = new File("src/com/abg/resources/Used.txt");
            if (myObj.createNewFile()) {
                System.out.println("Fisier creat: " + myObj.getName());
            }
        } catch (IOException e) {
            System.out.println("A apărut o eroare. Jocul nu poate continua :(");
            System.exit(0);
            //   e.printStackTrace();
        }
        // write the word in the file:
        try {
            Files.writeString(usedWordsFile, wordToBeWritten + "\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Probleme fisier cuvinte folosite. Jocul nu poate continua :(");
            System.exit(0);
        }
    }

    // Method to check if the word was previously used:
    public boolean previouslyUsed(String wordToBeWritten) {
        boolean wordWasSaidBefore = false;
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(usedWordsFile);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty()) {
                    if (line.equals(wordToBeWritten)) {
                        wordWasSaidBefore = true;
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Probleme cu fisierul");
        }
        return wordWasSaidBefore;
    }

    // method to clear the used words file:
    public void clearUsedWords() {
        try {
            Files.writeString(usedWordsFile, "");
        } catch (IOException e) {
            System.out.println("Probleme fisier cuvinte folosite");
        }
    }

    // Method to write to the file of word endings to avoid:
    public void avoidEnding(String ending) {

        // writes the ending to avoid in the file:
        try {
            Files.writeString(avoidEndings, ending + "\n", StandardOpenOption.APPEND);

        } catch (IOException e) {
            System.out.println("Probleme fisier");
        }
    }


// method which makes the Computer search for a word starting with xx:
// I create an array list with all the possible values, then I randomly pick one
// Also check if the chosen word was used before

    public String findWord(String xx, String oldEnding) {
        ArrayList<String> possibleWords = new ArrayList<>();
        ArrayList<String> possibleWords1 = new ArrayList<>();
        String line;
        String word = null;
        String wordEnding;
        String dangerousEnding;

        try {
            BufferedReader bufferedReader = Files.newBufferedReader(dictionaryFile);

            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty()) {

                    if (line.substring(0, 2).equals(xx) && !previouslyUsed(line)) {
                        possibleWords.add(line);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Probleme cu fisierul-dictionar. Jocul nu poate rula :(");
            System.exit(0);
        }

        //pick a random word from the list:
        if (!possibleWords.isEmpty()) {

            // check all the possible words endings and put the words with dangerous endings in possibleWords1 list:
            for (int i = possibleWords.size() - 1; i >= 0; i--) {
                word = possibleWords.get(i);
                wordEnding = word.substring(word.length() - 2);

                // compare the ending to the list of endings to avoid:
                try {
                    BufferedReader bufferedReader = Files.newBufferedReader(avoidEndings);

                    while ((dangerousEnding = bufferedReader.readLine()) != null) {
                        if (!dangerousEnding.isEmpty()) {
                            if (dangerousEnding.equals(wordEnding)) {
                                possibleWords.remove(word);
                                possibleWords1.add(word);
                            }
                        }
                    }

                } catch (IOException e) {
                    System.out.println("Probleme cu fisierul de terminatii periculoase. Jocul nu poate rula :(");
                    System.exit(0);
                }
            }

            if (!possibleWords.isEmpty()) {

                int rnd = (int) (Math.random() * possibleWords.size());
                word = possibleWords.get(rnd);
            } else {
                int rnd = (int) (Math.random() * possibleWords1.size());
                word = possibleWords1.get(rnd);
            }

        } else {
            System.out.print("Nu găsesc cuvânt " + Colors.RED + "☹ " + Colors.RESET);
            roundEnd = true;
            Sounds("applause");
            if (!endingExists(oldEnding)) {
                avoidEnding(oldEnding);
            }

            //modify the computerFazanCounter:
            this.computerFazanCounter++;
            switch (computerFazanCounter) {
                case 0:
                    System.out.println("Contorul e 0");
                    break;
                case 1:
                    System.out.println("Am " + Colors.RED_BRIGHT + "F" + Colors.RESET + "!");
                    break;
                case 2:
                    System.out.println("Am " + Colors.RED_BRIGHT + "FA" + Colors.RESET + "!");
                    break;
                case 3:
                    System.out.println("Am " + Colors.RED_BRIGHT + "FAZ" + Colors.RESET + "!");
                    break;
                case 4:
                    System.out.println("Am " + Colors.RED_BRIGHT + "FAZA" + Colors.RESET + "!");
                    break;
                case 5:
                    System.out.println("Am " + Colors.RED_BRIGHT + "FAZAN" + Colors.RESET + ". Am pierdut jocul!");
                    Sounds("fanfare");
                    System.exit(0);
            }
        }
        return word;
    }

// Method to check if there is any word starting with xx:

    public boolean thereAreWordsStartingWithXX(String xx) {
        exists = false;
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(dictionaryFile);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty()) {
                    String lettersToBeChecked = line.substring(0, 2);
                    if (lettersToBeChecked.equals(xx)) {
                        exists = true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Probleme cu fisierul-dictionar. Jocul nu poate rula :(");
            System.exit(0);
        }
        return exists;
    }

    // method to check if the ending is already in the file before writing it:
    public boolean endingExists(String ending) {
        exists = false;
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(avoidEndings);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty()) {
                    if (line.equals(ending)) {
                        exists = true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Probleme cu fisierul de terminatii periculoase. Jocul nu poate rula :(");
            System.exit(0);
        }
        return exists;
    }

    // method to clear the "dangerous endings" file and only let a few, like "re" to avoid "restaurant".
    public void clearAvoidEndingsFile(){
        // Clear the endings file and only write "re" ending:
        try {
            Files.writeString(avoidEndings, "re" + "\n");
            Files.writeString(avoidEndings, "or" + "\n", StandardOpenOption.APPEND);

        } catch (IOException e) {
            System.out.println("Probleme fisier terminatii periculoase. Jocul nu poate rula :(");
            System.exit(0);
        }
    }
}
