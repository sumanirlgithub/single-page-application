package com.java8.methodreference;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MethodReferencesExample2 {

    public static void main(String[] args) {

        List<String> bikeBrands = Arrays.asList("Giant", "Scott", "GT");
        Bicycle bicycle1 = new Bicycle();
        bicycle1.setBrand("Giant");
        bicycle1.setFrameSize(3);

        Bicycle bicycle2 = new Bicycle();
        bicycle2.setBrand("Scott");
        bicycle2.setFrameSize(0);

        Bicycle bicycle3 = new Bicycle();
        bicycle3.setBrand("GT");
        bicycle3.setFrameSize(1);

        List<Bicycle> createBikeList = Arrays.asList(bicycle1, bicycle2, bicycle3);

        // sort by bicycle frame size
        BicycleComparator bikeFrameSizeComparator = new BicycleComparator();
        createBikeList.stream().sorted(bikeFrameSizeComparator::compare)
                .collect(Collectors.toList()).forEach(bike -> System.out.println(bike.getBrand()));

    }
}
