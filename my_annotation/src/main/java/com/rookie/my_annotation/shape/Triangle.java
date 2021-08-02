package com.rookie.my_annotation.shape;

public class Triangle implements IShape {
    @Override
    public void draw() {
        System.out.println("Draw a Triangle");
    }
}