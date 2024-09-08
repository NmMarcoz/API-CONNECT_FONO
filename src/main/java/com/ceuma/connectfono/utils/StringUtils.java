package com.ceuma.connectfono.utils;

public class StringUtils {

    public boolean isNullOrEmpty(String string){
        return string == null || string.isEmpty() || string.isBlank();
    }
}
