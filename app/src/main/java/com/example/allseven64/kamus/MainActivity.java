package com.example.allseven64.kamus;

import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.example.allseven64.kamus.db.KamusHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnQueryTextListener {
    RecyclerView recyclerView;
    SearchView searchView;
    KamusAdapter kamusAdapter;
    KamusHelper kamusHelper;
    ArrayList<KamusModel> kamusList = new ArrayList<>();
    String lang_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        searchView = (SearchView)findViewById(R.id.searchView);
        searchView.setQueryHint("Search View");
        searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(this);

        recyclerView = (RecyclerView)findViewById(R.id.rv_kamus);

        kamusHelper = new KamusHelper(this);
        kamusAdapter = new KamusAdapter(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(kamusAdapter);

        lang_select = "Eng";
        getData(lang_select, "");
    }

    private void getData(String lang_select, String query){
        try {
            kamusHelper.open();
            if (query.isEmpty()){
                kamusList = kamusHelper.getAllData(lang_select);
            }
            else {
                kamusList = kamusHelper.getDataByName(query, lang_select);
            }

            String title = null;
            String hint = null;

            if (lang_select == "Eng"){
                title = getResources().getString(R.string.eng_to_ind);
                hint = getResources().getString(R.string.search);
            } else {
                title = getResources().getString(R.string.ind_to_eng);
                hint = getResources().getString(R.string.cari);
            }
            getSupportActionBar().setSubtitle(title);
            searchView.setQueryHint(hint);

        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            kamusHelper.close();
        }
        kamusAdapter.replaceAll(kamusList);
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
        getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.nav_indo) {
            lang_select = "Ind";
            getData(lang_select, "");
        } else if (id == R.id.nav_ingg) {
            lang_select = "Eng";
            getData(lang_select,"");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        getData(lang_select, query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        getData(lang_select, query);
        return false;
    }
}
