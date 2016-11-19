package com.sunikita.bbapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunikita.bbapp.content.ExerciseActivityHistoryDataProvider;
import com.sunikita.bbapp.content.ExerciseNames;

import java.util.ArrayList;
import java.util.List;

public class BBActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener,
        AddExerciseDialogFragment.AddExerciseDialogListener {

    private RecyclerView recyclerView;
    private ExerciseActivityHistoryDataProvider mProvider;
    private List<String> mCurrentExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbmain);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mProvider = new ExerciseActivityHistoryDataProvider(getApplicationContext());
        mCurrentExercises = mProvider.getExercises();
        recyclerView = (RecyclerView) findViewById(R.id.exerciseNames);
        recyclerView.setAdapter(new ExerciseNameAdaptor(mCurrentExercises));
    }

    @Override
    public boolean onQueryTextChange(String query) {
        Log.d("TESTING", "Coming here");
        ExerciseNameAdaptor adaptor = (ExerciseNameAdaptor) recyclerView.getAdapter();
        if(query.isEmpty()) {
            adaptor.refresh(mCurrentExercises);
        } else {
            List<String> filteredList = new ArrayList<>();
            for(String name : mCurrentExercises) {
                if(name.toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(name);
                }
            }
            adaptor.refresh(filteredList);
        }

        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("TESTING", "Coming here1");
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bbactivity_main, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setOnQueryTextListener(this);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(false);
        searchView.setQueryHint("Search exercise");
        searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDialogPositiveClick(String exerciseName) {
        mProvider.addExercise(exerciseName);
        mCurrentExercises = mProvider.getExercises();
        ExerciseNameAdaptor adaptor = (ExerciseNameAdaptor) recyclerView.getAdapter();
        adaptor.refresh(mCurrentExercises);
    }

    public void addExercise(View view) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        AppCompatDialogFragment newFragment = new AddExerciseDialogFragment();
        newFragment.show(ft, "dialog");
    }

    public class ExerciseNameAdaptor extends RecyclerView.Adapter<ExerciseNameAdaptor.ExerciseActivityViewHolder>{

        private List<String> exerciseNames = new ArrayList<>();

        public ExerciseNameAdaptor(List<String> exercises) {
            this.exerciseNames.addAll(exercises);
        }

        public void refresh(List<String> exercises){
            this.exerciseNames.clear();
            this.exerciseNames.addAll(exercises);
            this.notifyDataSetChanged();
        }

        @Override
        public ExerciseActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.exercise_main_list_item, parent, false);
            return new ExerciseActivityViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ExerciseActivityViewHolder holder, int position) {
            final String exerciseName = exerciseNames.get(position);
            holder.exerciseName.setText(exerciseName);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ExerciseRecordActivity.class);
                    intent.putExtra("ExerciseName", exerciseName);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return exerciseNames.size();
        }

        public class ExerciseActivityViewHolder extends RecyclerView.ViewHolder {

            private TextView exerciseName;

            public ExerciseActivityViewHolder(View itemView) {
                super(itemView);
                exerciseName = (TextView) itemView.findViewById(R.id.exerciseNameRow);
            }
        }
    }
}
