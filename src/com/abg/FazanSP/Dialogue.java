package com.abg.FazanSP;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static com.abg.FazanSP.Sounds.Sounds;

public class Dialogue extends FileStuff {
    Scanner scanner = new Scanner(System.in);
    FileStuff fileStuff = new FileStuff();
    String option;

    // game intro:
    public void introduction() {
        System.out.println(" ");
        System.out.println("                        Salut! Cum te numești? ");
        System.out.print("                              ");
        String nume = scanner.nextLine();
        System.out.println("                  Bine ai venit, " + nume + "! Hai să jucăm  " + Colors.RED);
        System.out.println(" ");
        System.out.println("   ░▒▓████████▓▒░▒▓██████▓▒░░▒▓████████▓▒░░▒▓██████▓▒░░▒▓███████▓▒░  ");
        System.out.println("   ░▒▓█▓▒░     ░▒▓█▓▒░░▒▓█▓▒░      ░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░ ");
        System.out.println("   ░▒▓█▓▒░     ░▒▓█▓▒░░▒▓█▓▒░    ░▒▓██▓▒░░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░ ");
        System.out.println("   ░▒▓██████▓▒░░▒▓████████▓▒░  ░▒▓██▓▒░  ░▒▓████████▓▒░▒▓█▓▒░░▒▓█▓▒░ ");
        System.out.println("   ░▒▓█▓▒░     ░▒▓█▓▒░░▒▓█▓▒░░▒▓██▓▒░    ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░ ");
        System.out.println("   ░▒▓█▓▒░     ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░ ");
        System.out.println("   ░▒▓█▓▒░     ░▒▓█▓▒░░▒▓█▓▒░▒▓████████▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░");
        System.out.println(" " + Colors.RESET);
        System.out.println("╔═════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                     UITE CÂTEVA REGULI:                             ║");
        System.out.println("║    ▸ Ai 5 \"vieți\"; când pierzi una, \"câștigi\" o literă!             ║");
        System.out.println("║    ▸ Dacă nu știi un cuvânt, scrie \"fazan\"; vei pierde o viață!     ║");
        System.out.println("║    ▸ Dacă vrei să pleci, scrie \"stop\" pentru a termina jocul;       ║");
        System.out.println("║    ▸ Nu folosi la joc cele 2 cuvinte-cheie de mai sus;              ║");
        System.out.println("║    ▸ " + Colors.RED + "Nu ai voie " + Colors.RESET + "să închizi din prima;                               ║");
        System.out.println("║    ▸ Cine ajunge primul la \"" + Colors.YELLOW_BRIGHT + "FAZAN" + Colors.RESET + "\" pierde;                          ║");
        System.out.println("║    ▸ Nu știu chiar toate cuvintele, dar - după privire - nici tu.   ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════════╝");
        System.out.println(" ");
        Sounds("pacman_intro");
        System.out.print("Vrei un joc mai " + Colors.GREEN + "usor" + Colors.RESET +" sau mai" + Colors.GREEN + " greu" + Colors.RESET + "? ");
        do {
            option = scanner.nextLine();
        } while (!option.equalsIgnoreCase("usor") && !option.equalsIgnoreCase("greu"));
        if (option.equalsIgnoreCase("usor")){
            fileStuff.clearAvoidEndingsFile();
        }
        System.out.print("Atunci să-i dăm drumul. Scrie un cuvânt: ");
    }

    public String readWord() {
        int correct;
        String word;
        do {
            correct = 0;
            word = scanner.nextLine().toLowerCase();

            if (!word.equalsIgnoreCase("fazan")) {
                if (word.equalsIgnoreCase("stop")) {
                    System.out.println("Îmi pare rău că vrei să pleci, dar mă bucur că am câștigat. La revedere!");
                    Sounds("boo");

                    for (double stop = System.nanoTime() + (double) TimeUnit.SECONDS.toNanos(1); stop > System.nanoTime(); ) {
                        //makes the boo sound last longer;
                    }
                    System.exit(0);
                }

                //  check to see if it's in the dictionary:
                boolean exists = fileStuff.wordIsFoundInDictionary(word);

                if (!exists) {
                    correct = 1;
                } else {
                    correct = 0;
                }

                //  check to see if it contains only letters:
                for (int i = 0; i < word.length(); i++) {
                    if ((word.charAt(i) < 'a' && word.charAt(i) > 'z') ||
                            (word.charAt(i) < 'A' && word.charAt(i) < 'Z')) {
                        correct = 2;
                        break;
                    }
                }

                //  check to see if it has a minimum of 3 letters:
                if (word.length() < 3) {
                    correct = 3;
                }

                //check to see if it was previously used:
                if (fileStuff.previouslyUsed(word)) {
                    correct = 4;
                }

                switch (correct) {
                    case 1:
                        System.out.print("Cuvant inexistent, mai incearca: ");
                        Sounds("ahem");
                        break;
                    case 2:
                        System.out.print("Te rog sa folosesti doar litere: ");
                        Sounds("ahem");
                        break;
                    case 3:
                        System.out.print("Cuvântul trebuie să aibă minim 3 litere: ");
                        Sounds("ahem");
                        break;
                    case 4:
                        System.out.print("Cuvântul s-a mai zis, încearcă altul: ");
                        Sounds("ahem");
                        break;
                }
            }
        }
        while (correct != 0);
        return word;
    }

    //start game method:
    public String startGame() {
        FileStuff fileStuff = new FileStuff();
        boolean wiseGuy = true;
        String firstWord = null;
        while (wiseGuy) {
            firstWord = readWord();
            String firstWordEndingLetters = firstWord.substring(firstWord.length() - 2);
            if (!fileStuff.thereAreWordsStartingWithXX(firstWordEndingLetters)) {
                System.out.println("Nu ai voie sa inchizi din prima! ");
                Sounds("ahem");
                System.out.print("Tu: ");
                wiseGuy = true;
            } else {
                wiseGuy = false;
            }
        }
        fileStuff.writeDownWord(firstWord);
        return firstWord;
    }
}