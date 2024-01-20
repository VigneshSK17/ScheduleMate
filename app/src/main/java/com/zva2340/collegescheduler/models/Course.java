package com.zva2340.collegescheduler.models;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Represents a course.
 * @author Vignesh Suresh Kumar
 */
public class Course {

    // name, time, instructor
    // list of assignments ?
    // list of exams ?

    /** Title name of the course */
    private String name;
    /** The times this class's lectures meets */
    private List<LocalDateTime> lectureTimes;

    /** The times this class's lectures meets (can be empty) */
    private List<LocalDateTime> labTimes;
    /** Course instructor name */
    private String instructor;

    /**
     * Creates course object with lab times
     * @param name          name of course
     * @param lectureTimes  lectures times for course
     * @param labTimes      lab times for course
     * @param instructor    instructor name
     */
    public Course(String name, List<LocalDateTime> lectureTimes, List<LocalDateTime> labTimes, String instructor) {
        this.name = name;
        this.lectureTimes = lectureTimes;
        this.labTimes = labTimes;
        this.instructor = instructor;
    }

    /**
     * Creates course object without lab times
     * @param name          name of course
     * @param lectureTimes  lectures times for course
     * @param instructor    instructor name
     */
    public Course(String name, List<LocalDateTime> lectureTimes, String instructor) {
        this(name, lectureTimes, Collections.emptyList(), instructor);
    }

    /**
     * Get the name of the course
     * @return name of course
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the course
     * @param name the new name of the course
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the lecture times of the course
     * @return the lecture times of the course
     */
    public List<LocalDateTime> getLectureTimes() {
        return lectureTimes;
    }

    /**
     * Set the lecture times of the course
     * @param lectureTimes the new lecture times
     */
    public void setLectureTimes(List<LocalDateTime> lectureTimes) {
        this.lectureTimes = lectureTimes;
    }

    /**
     * Get the lab times for the course
     * @return the lab times of the course
     */
    public List<LocalDateTime> getLabTimes() {
        return labTimes;
    }

    /**
     * Set the lab times for the course
     * @param labTimes the new lab times
     */
    public void setLabTimes(List<LocalDateTime> labTimes) {
        this.labTimes = labTimes;
    }


    /**
     * Get the course instructor's name
     * @return course instructor's name
     */
    public String getInstructor() {
        return instructor;
    }

    /**
     * Set the course instructor's name
     * @param instructor the new course instructor's name
     */
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }


}