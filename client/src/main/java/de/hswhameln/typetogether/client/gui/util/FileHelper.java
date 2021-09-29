package de.hswhameln.typetogether.client.gui.util;

import java.io.File;

public class FileHelper {
    
    public static File parseFile(File file) {
        if(file.getName() != null) {
            if(!file.getName().endsWith(".txt")) {
                return new File(file.getAbsolutePath() + ".txt");
            }
        } else {
            return new File(System.getProperty("user.home") + "/Desktop/ExportedText.txt");
        }
        return file;
    }
}
