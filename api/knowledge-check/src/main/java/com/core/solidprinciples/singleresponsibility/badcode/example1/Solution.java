package com.core.solidprinciples.singleresponsibility.badcode.example1;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The current code manages a university system where the Course class has multiple responsibilities, including managing course details and grading students.
 * We need to refactor the code so that each class has a single responsibility, leading to more maintainable and understandable code.
 *
 * Your task is to refactor the code to ensure that it adheres to the Single Responsibility Principle by distributing responsibilities across multiple classes.
 */
class Solution {
    public static void main(String[] args) {
        Course course = new Course("History 101");
        course.addStudent("John Doe");
        course.addStudent("Jane Smith");
        course.assignGrade("John Doe", 85.5);
        course.assignGrade("Jane Smith", 92.0);

        System.out.println("Course: " + course.getName());
        course.printStudentGrades();
    }
}

class Course {
    private String name;
    private List<String> students;
    private Map<String, Double> grades;

    public Course(String name) {
        this.name = name;
        this.students = new ArrayList<>();
        this.grades = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void addStudent(String studentName) {
        students.add(studentName);
    }

    public void assignGrade(String studentName, Double grade) {
        grades.put(studentName, grade);
    }

    public void printStudentGrades() {
        for (String student : students) {
            System.out.println("Student: " + student + ", Grade: " + grades.get(student));
        }
    }
}