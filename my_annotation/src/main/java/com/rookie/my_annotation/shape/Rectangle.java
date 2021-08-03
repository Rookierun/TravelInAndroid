package com.rookie.my_annotation.shape;

public class Rectangle implements IShape {
    @Override
    public void draw() {
        System.out.println("Draw a Rectangle");
    }
}