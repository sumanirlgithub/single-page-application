package com.core.sorting;

public class Fruit implements Comparable<Object> {
    private int id;
    private String name;
    private String taste;

    Fruit(int id, String name, String taste){
        this.id=id;
        this.name=name;
        this.taste=taste;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int compareTo(Object o) {
        Fruit f = (Fruit) o;
        //return this.id - f.id ;
        return this.name.compareTo(f.name);
    }

    @Override
    public String toString() {
        return this.id + ":"+ this.name;
    }


}