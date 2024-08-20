package com.example.driveservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserContext {
    public static ThreadLocal<String> contextHolder = new ThreadLocal<>();
    public static String getUsername(){ return contextHolder.get(); }
    public static void setUsername(String username){ contextHolder.set(username); }
    public static void clear() { contextHolder.remove(); }
}
