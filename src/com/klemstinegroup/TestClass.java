package com.klemstinegroup;

public class TestClass{
    public static void main(String[] args){
        new TestClass();
    }
    public TestClass(){
        System.out.println("test instantiated");
    }
    public String toLowerCase(String s){
        return s.toLowerCase();
    }
    public void runCode() {
        System.out.println("code running");
    }
}
