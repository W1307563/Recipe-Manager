package com.example.cmis202project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Hashtable;

public class RecipeHashTable {

    private static Hashtable<String, String> ht1 = new Hashtable<>();

    public static void main(String args[]) throws FileNotFoundException {

        ht1.put("Ramen", "C'mon you don't need to store ramen as a recipe, everyone knows how to make these!");
        ht1.put("PBJ Sandwich", "Slap on PB and Jelly onto some bread, and there you have it!");
        ht1.put("Coffee", "Oh please, go to Starbucks instead...making coffee at home is too much work!");

        File example = new File("Example File");
        PrintWriter printWriter = new PrintWriter(example);
        printWriter.print(ht1);

        printWriter.close();

    }


}


