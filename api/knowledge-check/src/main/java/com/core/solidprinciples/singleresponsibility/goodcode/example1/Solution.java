package com.core.solidprinciples.singleresponsibility.goodcode.example1;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Solution {
    public static void main(String[] args) {
        Course course = new Course("History 101");
        course.addStudent("John Doe");
        course.addStudent("Jane Smith");
        GradeManager gradeManager = new GradeManager();
        gradeManager.assignGrade(course, "John Doe", 85.5);
        gradeManager.assignGrade(course, "Jane Smith", 92.0);

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
        return this.name;
    }

    public Map<String, Double> getGrades() {
        return this.grades;
    }


    public void addStudent(String studentName) {
        students.add(studentName);
    }

    public void printStudentGrades() {
        for (String student : students) {
            System.out.println("Student: " + student + ", Grade: " + grades.get(student));
        }
    }
}

class GradeManager {
    public void assignGrade(Course course, String studentName, Double grade) {
        course.getGrades().put(studentName, grade);
    }

}