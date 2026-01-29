    package com.core.sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

    public class SortingCustomObjectsOfListUsingComparable {

        public static void main(String[] args) {
            // create a list of Fruit objects:
            List<Fruit> fruitList=new ArrayList<Fruit>();
            Fruit apple=new Fruit(1, "Apple", "Sweet");
            Fruit orange=new Fruit(2, "Orange", "Sour");
            Fruit banana=new Fruit(4, "Banana", "Sweet");
            Fruit grape=new Fruit(3, "Grape", "Sweet and Sour");

            fruitList.add(apple);
            fruitList.add(orange);
            fruitList.add(banana);
            fruitList.add(grape);

            Collections.sort(fruitList);
            fruitList.forEach(System.out::println);
        }

    }
