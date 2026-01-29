package com.java8.methodreference;

import java.util.Comparator;

public class BicycleComparator implements Comparator {

    @Override
    public int compare(Object a, Object b) {
        Bicycle bicycle1 = (Bicycle) a;
        Bicycle bicycle2 = (Bicycle) b;
        return bicycle1.getFrameSize().compareTo(bicycle2.getFrameSize());
    }

}
