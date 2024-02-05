package com.zva2340.collegescheduler.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.zva2340.collegescheduler.R;
import com.zva2340.collegescheduler.activities.EditCourseActivity;
import com.zva2340.collegescheduler.models.Course;
import com.zva2340.collegescheduler.utils.StartEndTime;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

/**
 * Adapter for the recyclerview in the courses fragment
 */
public class CourseRecyclerViewAdapter extends RecyclerView.Adapter<CourseRecyclerViewAdapter.CourseViewHolder> {

    private Context context;
    private List<Course> courses;
    private ActivityResultLauncher<Intent> fragmentLauncher;


    /**
     * Constructor for the adapter
     * @param context          the context of the adapter
     * @param courses          the list of courses to be displayed
     * @param fragmentLauncher the launcher for the fragment
     */
    public CourseRecyclerViewAdapter(Context context, List<Course> courses, ActivityResultLauncher<Intent> fragmentLauncher) {
        this.context = context;
        this.courses = courses;
        this.fragmentLauncher = fragmentLauncher;
    }


    @NonNull
    @Override
    public CourseRecyclerViewAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclerview_course_card, parent, false);

        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseRecyclerViewAdapter.CourseViewHolder holder, int position) {
        Course course = courses.get(position);

        holder.courseTitle.setText(course.getName());
        holder.courseDates.setText(genCourseDatesStr(course));

        holder.deleteButton.setOnClickListener(l -> delete(position));

        holder.cardView.setOnClickListener(l -> onClick(course, position));
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
            courseDates.append(" | ");
            courseDates.append(genEventTimeStr(course.getLabTimes()));
        }

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

    /**
     * Helper method to delete a course from the recyclerview
     * @param position the position of the course to be deleted
     */
    private void delete(int position) {
        courses.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * Helper method to launch the edit course activity
     * @param course    the course to be edited
     * @param i         the position of the course in the recyclerview
     */
    private void onClick(Course course, int i) {
        Intent intent = new Intent(context, EditCourseActivity.class);
        intent.putExtra("COURSE", course);
        intent.putExtra("POSITION", i);
        intent.putExtra("TITLE", "Edit");
        fragmentLauncher.launch(intent);
    }

    /** Helper method to update the recyclerview with a new course
     * @param course    the course to be updated
     * @param position  the position of the course in the recyclerview
     */
    public void update(Course course, int position) {
        if (position == -1) {
            courses.add(course);
            notifyItemChanged(courses.size() - 1, course);
        } else {
            courses.set(position, course);
            notifyItemChanged(position, course);
        }
    }


    /** Viewholder for the recyclerview, allows for access to the views in each recyclerview card
     */
    public static class CourseViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView courseTitle, courseDates;
        private ImageButton deleteButton;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardview_course);
            courseTitle = itemView.findViewById(R.id.textview_coursetitle);
            courseDates = itemView.findViewById(R.id.textview_coursedates);
            deleteButton = itemView.findViewById(R.id.imagebutton_delete_course);

        }
    }

}
