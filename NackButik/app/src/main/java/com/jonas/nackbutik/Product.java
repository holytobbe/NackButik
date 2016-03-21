package com.jonas.nackbutik;

/**
 * Created by jonas on 15/03/16.
 */
public class Product {
    int Id;
    String Name;
    String Description;
    double Price;

    @Override
    public String toString() {
        return Name;
    }
}
