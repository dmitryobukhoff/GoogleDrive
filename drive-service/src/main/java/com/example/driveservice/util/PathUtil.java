package com.example.driveservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PathUtil {
    public static String getName(String path){
        path = getPath(path);
        String[] route = path.split("/");
        return route[route.length - 1];
    }

    public static String getPath(String objectName){
        return objectName.substring(objectName.indexOf("/"), objectName.length() - 1);
    }

    public static String getExtension(String path){
        if(isFolder(path)) return "FOLDER";
        String[] route = path.split("\\.");
        return route[route.length - 1];
    }

    private boolean isFolder(String path){
        return path.charAt(path.length() - 1) == '/';
    }
}
