package com.ceuma.connectfono.utils;

public class OsValidator {
    public static boolean isWindows(){
        String os = System.getProperty("os.name").toLowerCase();
        return (os.contains("win"));
    }

    public static String determineDbPath() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            // Windows: AppData/Local
            return System.getenv("LOCALAPPDATA") + "/fono-care/database";
        } else if (os.contains("mac")) {
            // macOS: Application Support
            return System.getProperty("user.home") + "/Library/Application Support/fono-care/database";
        } else {
            // Linux/Unix: .config ou diretório home
            return System.getProperty("user.home") + "/.local/share/fono-care/database";
        }
    }
}
