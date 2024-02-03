package com.zva2340.collegescheduler.utils;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Holds an event's start and end time
 * @author Vignesh Suresh Kumar
 */
public class StartEndTime implements Serializable {

    /** The start time of the event */
    private LocalTime startTime;
    /** The endtime of the event */
    private LocalTime endTime;
    /** Day of the week of the event */
    private DayOfWeek dayOfWeek;

    /**
     * Initialize this with all properties
     * @param startTime start time of event
     * @param endTime   end time of event
     * @param dayOfWeek day of week of event
     */
    public StartEndTime(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Set up event based on start date & time and end date & time
     * @param startDateTime
     * @param endDateTime
     */
    public StartEndTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.startTime = startDateTime.toLocalTime();
        this.endTime = endDateTime.toLocalTime();
        this.dayOfWeek = startDateTime.getDayOfWeek();
    }

    /**
     * Get the start time of the event
     * @return start time of event
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Set the start time of event
     * @param startTime new start time of event
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Get the end time of the event
     * @return end time of event
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Set the end time of event
     * @param endTime new end time of event
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Get the day of week of event
     * @return day of week of event
     */
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Set the day of week of event
     * @param dayOfWeek new day of week of event
     */
    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Check if this event is the same as another event
     * @param obj the other event
     * @return true if this event is the same as other event, false otherwise
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof StartEndTime)) return false;
        StartEndTime startEndTime = (StartEndTime) obj;

        return startTime.equals(startEndTime.startTime) && endTime.equals(startEndTime.endTime);
    }
}
