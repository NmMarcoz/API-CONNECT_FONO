package com.ceuma.connectfono.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VerifyUtils {
    public static List<String> validNames = new ArrayList<String>(Arrays.asList("AUDIOMETRIA TONAL E VOCAL", "CONSULTA FONOAUDIOLOGIA", "IMITANCIOMETRIA", "PROCESSAMENTO AUDITIVO CENTRAL", "TESTE DE ORELHINHA", "VENG"));
    public static boolean isValidConsultName(String name){
        if(!validNames.contains(name)){
            return false;
        }
        return true;
    }
}
