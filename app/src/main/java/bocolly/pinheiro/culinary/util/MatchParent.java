package bocolly.pinheiro.culinary.util;

public class MatchParent {

    public static boolean validateNameAuthor(String v){
        return v.matches("^[a-zA-Z]{2,30}$");
    }

    public static boolean validateIgredientsPreparation(String v){
        return v.matches("^[a-zÁ-ùA-Z 1-9]{2,500}$");
    }

}
