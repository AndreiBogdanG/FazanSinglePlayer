package com.abg.FazanSP;

import java.util.Scanner;

import static com.abg.FazanSP.Sounds.Sounds;

public class Main extends FileStuff {
    public static void main(String[] args) {
        FileStuff fileStuff = new FileStuff();
        Dialogue dialogue = new Dialogue();
        String oldWord;
        String newWord;
        String readWord = null;
        String xx;
        String yy;
        String oldEnding;
        String newWordEnding;
        int playerCounter = 0;
        int i;
        boolean endOfRound = false;
        Scanner scanner = new Scanner(System.in);

//enable ansi, clear the used words file before starting new game, then show the game intro:
        AnsiSupportChecker.enableAnsiSupportOnWindows();
        fileStuff.clearUsedWords();
        dialogue.introduction();


//the beginning of the game loop:
        do {
            oldWord = dialogue.startGame();
            xx = oldWord.substring(oldWord.length() - 2);
            oldEnding = oldWord.substring(0, 2);
            i = 0;
            do {
                newWord = fileStuff.findWord(xx, oldEnding);
                newWordEnding = newWord.substring(newWord.length() - 2);
                System.out.println("Hmm... ");
                i++;
            } while (!fileStuff.thereAreWordsStartingWithXX(newWordEnding) && i < 3);

            if (!fileStuff.thereAreWordsStartingWithXX(newWordEnding)) {
                System.out.println(newWord.substring(0, newWord.length() - 2) + Colors.YELLOW + newWord.substring(newWord.length() - 2) + Colors.RESET + ", na! Te-am blocat!");
                readWord = "fazan";
                playerCounter++;
            } else {
                if (newWord != null) {
                    System.out.println("Eu: " + newWord.substring(0, newWord.length() - 2) + Colors.YELLOW + newWord.substring(newWord.length() - 2) + Colors.RESET);
                    fileStuff.writeDownWord(newWord);
                    oldWord = newWord;
                    do {
                        System.out.print("Tu: ");
                        readWord = dialogue.readWord();
                        if (readWord.equalsIgnoreCase("fazan")) {
                            System.out.println("Ha, ha, te dai bătut?");
                            Sounds("laugh");
                            playerCounter++;
                            endOfRound = true;
                        }

                        if (!endOfRound && fileStuff.checkThe2Words(oldWord, readWord)) {

                            xx = readWord.substring(readWord.length() - 2);
                            fileStuff.writeDownWord(readWord);
                            oldEnding = readWord.substring(0, 2);
                            newWord = fileStuff.findWord(xx, oldEnding);

                            if (newWord != null) {
                                System.out.println("Eu: " + newWord.substring(0, newWord.length() - 2) + Colors.YELLOW + newWord.substring(newWord.length() - 2) + Colors.RESET);
                                fileStuff.writeDownWord(newWord);

                                oldWord = newWord;
                                yy = newWord.substring(newWord.length() - 2);
                                oldEnding = newWord.substring(0, 2);

                            } else {
                                yy = oldEnding;
                                endOfRound = true;
                            }

                            //check to see if the player has any available words for answer:
                            if ((!endOfRound && !fileStuff.thereAreWordsStartingWithXX(yy))) {
                                System.out.print("Te-am blocat! ");
                                endOfRound = true;
                                playerCounter++;
                                Sounds("laugh");

                                switch (playerCounter) {
                                    case 1:
                                        System.out.println("Ai " + Colors.RED_BRIGHT + "F" + Colors.RESET + "! " + Colors.YELLOW_BRIGHT + "☺" + Colors.RESET);
                                        break;
                                    case 2:
                                        System.out.println("Ai " + Colors.RED_BRIGHT + "FA" + Colors.RESET + "! " + Colors.YELLOW_BRIGHT + "☺" + Colors.RESET);
                                        break;
                                    case 3:
                                        System.out.println("Ai " + Colors.RED_BRIGHT + "FAZ" + Colors.RESET + "! " + Colors.YELLOW_BRIGHT + "☺" + Colors.RESET);
                                        break;
                                    case 4:
                                        System.out.println("Ai " + Colors.RED_BRIGHT + "FAZA" + Colors.RESET + "! " + Colors.YELLOW_BRIGHT + "☺" + Colors.RESET);
                                        break;
                                    case 5:
                                        System.out.println("Ai " + Colors.RED_BRIGHT + "FAZAN" + Colors.RESET + "! " + Colors.YELLOW_BRIGHT + "☺" + Colors.RESET);
                                        System.out.println("Ai pierdut, era de așteptat... Pe curând!");
                                        Sounds("cheering");
                                        System.exit(0);
                                }

                                //check to see if the losing end letters exist in the endings file; if not, they will be written:
                                if (!fileStuff.endingExists(oldEnding)) {
                                    fileStuff.avoidEnding(oldEnding);
                                }
                            }
                        }
                    } while (!endOfRound);
                }
            }
            System.out.print("S-a terminat o runda. ");

            if (readWord.equals("fazan")) {
                switch (playerCounter) {
                    case 1:
                        System.out.println("Ai " + Colors.RED_BRIGHT + "F" + Colors.RESET + "! " + Colors.YELLOW_BRIGHT + "☺" + Colors.RESET);
                        break;
                    case 2:
                        System.out.println("Ai " + Colors.RED_BRIGHT + "FA" + Colors.RESET + "! " + Colors.YELLOW_BRIGHT + "☺" + Colors.RESET);
                        break;
                    case 3:
                        System.out.println("Ai " + Colors.RED_BRIGHT + "FAZ" + Colors.RESET + "! " + Colors.YELLOW_BRIGHT + "☺" + Colors.RESET);
                        break;
                    case 4:
                        System.out.println("Ai " + Colors.RED_BRIGHT + "FAZA" + Colors.RESET + "! " + Colors.YELLOW_BRIGHT + "☺" + Colors.RESET);
                        break;
                    case 5:
                        System.out.println("Ai " + Colors.RED_BRIGHT + "FAZAN" + Colors.RESET + "! " + Colors.YELLOW_BRIGHT + "☺" + Colors.RESET);
                        System.out.println("Ai pierdut, era de așteptat... Pe curând!");
                        Sounds("cheering");
                        System.out.println("Apasa ENTER pentru a iesi din joc.");
                        scanner.nextLine();
                        System.exit(0);
                }
            }
            endOfRound = false;
            System.out.print("Zi un cuvânt: ");
        } while (true);
    }
}
