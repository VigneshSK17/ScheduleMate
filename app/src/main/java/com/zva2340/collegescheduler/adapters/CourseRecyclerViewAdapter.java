package com.zva2340.collegescheduler.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zva2340.collegescheduler.R;
import com.zva2340.collegescheduler.databinding.RecyclerviewCourseCardBinding;
import com.zva2340.collegescheduler.models.Course;
import com.zva2340.collegescheduler.utils.StartEndTime;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class CourseRecyclerViewAdapter extends RecyclerView.Adapter<CourseRecyclerViewAdapter.CourseViewHolder> {

    private Context context;
    private List<Course> courses;

    public CourseRecyclerViewAdapter(Context context, List<Course> courses) {
        this.context = context;
        this.courses = courses;
    }


    @NonNull
    @Override
    public CourseRecyclerViewAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclerview_course_card, parent, false);

        return new CourseViewHolder(view);
    }

    // TODO: Do button later
    @Override
    public void onBindViewHolder(@NonNull CourseRecyclerViewAdapter.CourseViewHolder holder, int position) {
        Course course = courses.get(position);

        holder.courseTitle.setText(course.getName());
        holder.courseDates.setText(genCourseDatesStr(course));

        Log.d("ON_BIND_VIEW_HOLDER", holder.courseTitle.getText().toString());
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }


    /**
     * A helper method to generate the string containing course timings
     * @param  course the course to be used
     * @return string containing course timings for display
     */
    private String genCourseDatesStr(Course course) {
        StringBuilder courseDates = new StringBuilder();

        if (course.getLectureTimes().size() != 0) {
            courseDates.append(genEventTimeStr(course.getLectureTimes()));
        }
        if (course.getLabTimes().size() != 0) {
            if (course.getLectureTimes().size() != 0) {
                courseDates.append(" | ");
            }
            courseDates.append(genEventTimeStr(course.getLabTimes()));
        }
        if (course.getExamTime() != null) {
            if (course.getLectureTimes().size() != 0 || course.getLabTimes().size() != 0) {
                courseDates.append(" | ");
            }
            courseDates.append(genEventTimeStr(course.getExamTime()));
        }

        Log.d("COURSE_INFO", courseDates.toString());
        return courseDates.toString();
    }

    /**
     * Helper method to generate string for multiple StartEndTimes
     * @param eventTimes times to be added to string
     * @return           string containing given times
     */
    private String genEventTimeStr(List<StartEndTime> eventTimes) {
        StringBuilder eventDaysStr = new StringBuilder();
        for (StartEndTime startEndTime : eventTimes) {
            eventDaysStr.append(startEndTime.getDayOfWeek().getDisplayName(TextStyle.NARROW_STANDALONE, Locale.US));
        }
        String eventTimeStr = String.format(
                "%s - %s",
                eventTimes.get(0).getStartTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)),
                eventTimes.get(0).getEndTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
        );

        return eventDaysStr + " " + eventTimeStr;
    }

    /**
     *
     * Helper method to generate string for singular StartEndTime
     * @param eventTime  time to be added to string
     * @return           string containing given times
     */
    private String genEventTimeStr(StartEndTime eventTime) {
        String eventDaysStr = eventTime.getDayOfWeek().getDisplayName(TextStyle.NARROW_STANDALONE, Locale.US);
        String eventTimeStr = String.format(
                "%s %s",
                eventTime.getStartTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)),
                eventTime.getEndTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
        );

        return eventDaysStr + " " + eventTime;
    }


    public static class CourseViewHolder extends RecyclerView.ViewHolder {

        private TextView courseTitle, courseDates;
        private ImageButton editButton;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            courseTitle = itemView.findViewById(R.id.textview_coursetitle);
            courseDates = itemView.findViewById(R.id.textview_coursedates);
            editButton = itemView.findViewById(R.id.imagebutton_edit);

        }
    }

}