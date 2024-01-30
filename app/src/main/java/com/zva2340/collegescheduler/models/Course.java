package com.zva2340.collegescheduler.models;

import com.zva2340.collegescheduler.utils.StartEndTime;

import java.util.Collections;
import java.util.List;

/**
 * Represents a course.
 * @author Vignesh Suresh Kumar
 */
// TODO: Create methods to generate gson for course and convert set of gson to list of courses
public class Course implements Comparable<Course>{

    // name, time, instructor
    // list of assignments ?
    // list of exams ?

    /** Title name of the course */
    private String name;
    /** The times this class's lectures meets */
    private List<StartEndTime> lectureTimes;

    /** The times this class's lectures meets (can be empty) */
    private List<StartEndTime> labTimes;
    /** The time this class's exam sessions are (can be empty) */
    private StartEndTime examTime;
    /** Course instructor name */
    private String instructor;

    /**
     * Creates course object with lab times
     * @param name          name of course
     * @param lectureTimes  lectures times for course
     * @param labTimes      lab times for course
     * @param examTime      exam time for course
     * @param instructor    instructor name
     */
    public Course(String name, List<StartEndTime> lectureTimes, List<StartEndTime> labTimes, StartEndTime examTime, String instructor) {
        this.name = name;
        this.lectureTimes = lectureTimes;
        this.labTimes = labTimes;
        this.examTime = examTime;
        this.instructor = instructor;
    }

    /**
     * Creates course object without exam time
     * @param name          name of course
     * @param lectureTimes  lecture's times for course
     * @param labTimes      lab times for course
     * @param instructor    instructor name
     */
    public Course(String name, List<StartEndTime> lectureTimes, List<StartEndTime> labTimes, String instructor) {
        this(name, lectureTimes, labTimes, null, instructor);
    }

    /**
     * Creates course object without lab times
     * @param name          name of course
     * @param lectureTimes  lecture's times for course
     * @param examTime      exam time for course
     * @param instructor    instructor name
     */
    public Course(String name, List<StartEndTime> lectureTimes, StartEndTime examTime, String instructor) {
        this(name, lectureTimes, null, examTime, instructor);
    }

    /**
     * Creates course object without lab and exam times
     * @param name          name of course
     * @param lectureTimes  lectures times for course
     * @param instructor    instructor name
     */
    public Course(String name, List<StartEndTime> lectureTimes, String instructor) {
        this(name, lectureTimes, Collections.emptyList(), null, instructor);
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
    public List<StartEndTime> getLectureTimes() {
        return lectureTimes;
    }

    /**
     * Set the lecture times of the course
     * @param lectureTimes the new lecture times
     */
    public void setLectureTimes(List<StartEndTime> lectureTimes) {
        this.lectureTimes = lectureTimes;
    }

    /**
     * Get the lab times for the course
     * @return the lab times of the course
     */
    public List<StartEndTime> getLabTimes() {
        return labTimes;
    }

    /**
     * Set the lab times for the course
     * @param labTimes the new lab times
     */
    public void setLabTimes(List<StartEndTime> labTimes) {
        this.labTimes = labTimes;
    }

    /**
     * Get the exam time for the course
     * @return the exam time
     */
    public StartEndTime getExamTime() {
        return examTime;
    }

    /**
     * Set the exam time for course
     * @param examTime new exam time
     */
    public void setExamTime(StartEndTime examTime) {
        this.examTime = examTime;
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


    /**
     * Compares two courses by name
     * @param course course to be compared
     * @return 0 if equal, -1 if less than, 1 if greater than
     */
    @Override
    public int compareTo(Course course) {
        return name.compareTo(course.getName());
    }
}