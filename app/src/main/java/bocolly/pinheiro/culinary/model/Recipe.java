package bocolly.pinheiro.culinary.model;

import java.io.Serializable;

public class Recipe implements Serializable {

    private String key;
    private String name;
    private String author;
    private String type;
    private String ingredients;
    private String methodOfPreparation;

    public Recipe() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getMethodOfPreparation() {
        return methodOfPreparation;
    }

    public void setMethodOfPreparation(String methodOfPreparation) {
        this.methodOfPreparation = methodOfPreparation;
    }

    @Override
    public String toString() {
        return  "\nName: " + name +
                "\nAuthor: " + author +
                "\nType: " + type +
                "\nIngredients: " + ingredients +
                "\nMethod of Preparation: " + methodOfPreparation;
    }
}
