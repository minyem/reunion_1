package com.openclassrooms.reunion.ui.reunion_list;

import android.graphics.Color;

import java.util.Random;

public class Utils {

    public static int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
    // pour les méthodes static
    // filtre, tri ...
}
