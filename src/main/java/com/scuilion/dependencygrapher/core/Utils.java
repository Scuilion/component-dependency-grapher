package com.scuilion.dependencygrapher.core;

import java.io.File;

public class Utils{

    
    public static void deleteFileOrDirectory(String file) {
        deleteFileOrDirectory(new File(file));
    }
    public static void deleteFileOrDirectory(File file) {
        if ( file.exists() ) {
            if ( file.isDirectory() ) {
                for ( File child : file.listFiles() ) {
                    deleteFileOrDirectory( child );
                }
            }
            file.delete();
        }
    }
}
