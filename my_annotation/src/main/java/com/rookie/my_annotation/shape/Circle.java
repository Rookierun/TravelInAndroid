package com.rookie.my_annotation.shape;



public class Circle implements IShape {
    @Override
    public void draw() {
        System.out.println("Draw a Circle");
    }
}
//package com.rookie.my_annotation.shape;  //    PackageElement
//
//public class Circle {  //  TypeElement
//
//    private int i; //   VariableElement
//    private Triangle triangle;  //  VariableElement
//
//    public Circle() {} //    ExecuteableElement
//
//    public void draw(   //  ExecuteableElement
//                        String s)   //  VariableElement
//    {
//        System.out.println(s);
//    }
//
//    @Override
//    public void draw() {    //  ExecuteableElement
//        System.out.println("Draw a circle");
//    }
//}