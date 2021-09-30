package de.hswhameln.typetogether.client.gui.util;

import java.io.File;

public class FileHelper {

    public static File parseFile(File file) {
        if (file == null) {
            return new File(System.getProperty("user.home") + "/Desktop/ExportedText.txt");

        }
        if (!file.getName().endsWith(".txt")) {
            return new File(file.getAbsolutePath() + ".txt");
        }

        return file;
    }
}
