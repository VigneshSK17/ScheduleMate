package com.zva2340.collegescheduler.models;

import com.zva2340.collegescheduler.utils.StartEndTime;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Represents a course.
 * @author Vignesh Suresh Kumar
 */
public class Course implements Comparable<Course>, Serializable {

    // name, time, instructor
    // list of assignments ?
    // list of exams ?

    /** Title name of the course */
    private String name;
    /** The times this class's lectures meets */
    private List<StartEndTime> lectureTimes;

    /** The times this class's lectures meets (can be empty) */
    private List<StartEndTime> labTimes;
    /** Course instructor name */
    private String instructor;
    /** The location of the course */
    private String location;
    /** The room number of the course */
    private String room;


    /**
     * Creates course object with lab times
     * @param name          name of course
     * @param lectureTimes  lectures times for course
     * @param labTimes      lab times for course
     * @param instructor    instructor name
     * @param location      location of course
     * @param room          room number of course
     */
    public Course(String name, List<StartEndTime> lectureTimes, List<StartEndTime> labTimes, String instructor, String location, String room) {
        this.name = name;
        this.lectureTimes = lectureTimes;
        this.labTimes = labTimes;
        this.instructor = instructor;
        this.location = location;
        this.room = room;
    }

    /**
     *
     */
    public Course() {
        this.name = null;
        this.lectureTimes = null;
        this.labTimes = null;
        this.instructor = null;
        this.location = null;
        this.room = null;
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
     * Get the location of the course
     * @return location of course
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set the location of the course
     * @param location new location of course
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Get the room number of the course
     * @return room number of course
     */
    public String getRoom() {
        return room;
    }

    /**
     * Set the room number of the course
     * @param room new room number of course
     */
    public void setRoom(String room) {
        this.room = room;
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