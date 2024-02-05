package com.zva2340.collegescheduler.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.zva2340.collegescheduler.R;
import com.zva2340.collegescheduler.databinding.ActivityMainBinding;
import com.zva2340.collegescheduler.fragments.CoursesFragment;
import com.zva2340.collegescheduler.fragments.TodosFragment;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.zva2340.collegescheduler.models.Course;
import com.zva2340.collegescheduler.models.TodoItem;

/**
 * Main activity for the app, holds the navigation bar and fragments within
 */
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private ActivityResultLauncher<Intent> arlTodo;
    private ActivityResultLauncher<Intent> arlCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(navView, navController);

        arlTodo = getArlTodo();
        arlCourse = getArlCourse();

        binding.fab.setOnClickListener((view) -> {
                fabFragmentNav();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_graph);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


    /**
     * Launches the appropriate edit activity for the current fragment
     */
    private void fabFragmentNav() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            Fragment currentFragment = navHostFragment.getChildFragmentManager().getFragments().get(0);
            if (currentFragment instanceof CoursesFragment) {
                Intent intent = new Intent(this, EditCourseActivity.class);
                intent.putExtra("TITLE", "Add");
                arlCourse.launch(intent);
            } else if (currentFragment instanceof TodosFragment) {
                Intent intent = new Intent(this, EditTodoActivity.class);
                intent.putExtra("TITLE", "Add");
                arlTodo.launch(intent);
            }
        }

    }

    /**
     * Generates the ActivityResultLauncher for the TodoItem, allowing for the adding of a todo
     * @return  ActivityResultLauncher for the TodoItem
     */
    public ActivityResultLauncher<Intent> getArlTodo() {
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    TodoItem todo = (TodoItem) data.getSerializableExtra("TODO");

                    Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    TodosFragment currFragment = (TodosFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
                    currFragment.adapter.update(todo, -1);
                }
            }
        });
    }

    /**
     * Generates the ActivityResultLauncher for the Course, allowing for the adding of a course
     * @return  ActivityResultLauncher for the Course
     */
    public ActivityResultLauncher<Intent> getArlCourse() {
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Course course = (Course) data.getSerializableExtra("COURSE");

                    Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    CoursesFragment currFragment = (CoursesFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
                    currFragment.adapter.update(course, -1);
                }
            }
        });
    }

}