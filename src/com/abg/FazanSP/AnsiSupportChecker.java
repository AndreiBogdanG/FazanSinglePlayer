package com.abg.FazanSP;

import java.io.Console;

public class AnsiSupportChecker {
    // See if the system supports Ansi; if not - enable it
    public static void enableAnsiSupportOnWindows() {
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            try {
                // Enable ANSI support on Windows 10 (build 10586 or later)
                new ProcessBuilder("cmd", "/c", "echo", "\033[0m").inheritIO().start().waitFor();
            } catch (Exception e) {
                //   e.printStackTrace(); -> do not print any error.
            }
        }
    }
}