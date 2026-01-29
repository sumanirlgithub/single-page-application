package com.java8.filtermapsort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Given a list of users with age and name, return names of users over 18, sorted alphabetically.
 */
public class FilterMapSortingExample {

    public static void main(String[] args) {
        List<User> users = Arrays.asList(new User("James", 18), new User("Conor", 20), new User("Ciara", 21));
        List<String> names = users.stream()
                .filter(user -> user.getAge() > 18)
                .sorted(Comparator.comparing(User::getAge))
                .map(User::getName)
                .collect(Collectors.toList());

        names.forEach(System.out::println);

    }
}

class User {

    private String name;
    private int age;
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    @Override
    public String toString() {
        return name + " , " + age;
    }

}

