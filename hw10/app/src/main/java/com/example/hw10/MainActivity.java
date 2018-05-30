package com.example.hw10;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String DB_FILE = "contact.db", DB_TABLE = "contact";
    public static SQLiteDatabase sdbContact;

    private AddNewContactFragment addNewContactFragment;
    private SearchContactFragment searchContactFragment;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);

        addNewContactFragment = new AddNewContactFragment();
        searchContactFragment = new SearchContactFragment();

        FriendDbOpenHelper friendDbOpenHelper = new FriendDbOpenHelper(getApplicationContext(),DB_FILE,null,1);
        sdbContact = friendDbOpenHelper.getWritableDatabase();

        Cursor cursor = sdbContact.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + DB_TABLE + "'",null);

        if(cursor != null){
            if(cursor.getCount() == 0)
                sdbContact.execSQL("CREATE TABLE " + DB_TABLE + " (" + "_id INTEGER PRIMARY KEY," + "name TEXT NOT NULL," + "phoneNumber TEXT," + "phoneType TEXT);");

            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search_item).getActionView();
        searchView.setOnQueryTextListener(searchView_OnQuery);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.add_item){
            ContentValues data = addNewContactFragment.getContentValues();
            sdbContact.insert(DB_TABLE,null,data);
            searchContactFragment.addDataToList("Name: " + data.getAsString("name") + "\nPhoneNumber: " + data.getAsString("phoneNumber") + "\nPhoneType: " + data.getAsString("phoneType"));
            Toast.makeText(this,"資料已加入資料庫",Toast.LENGTH_SHORT);
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sdbContact.close();
    }

    private final SearchView.OnQueryTextListener searchView_OnQuery = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            Cursor cursor = null;
            if (!query.equals("")) {
                cursor = sdbContact.query(true,
                        DB_TABLE,
                        new String[]{"name", "phoneNumber", "phoneType"},
                        "name=" + "\"" + query + "\"",
                        null, null, null, null, null);
            }

            if (cursor == null)
                return false;

            // If couldn't find data, then show the message
            if (cursor.getCount() == 0) {
                Toast.makeText(MainActivity.this, "找不到目標資料！", Toast.LENGTH_LONG).show();
                searchContactFragment.setListHighlight();
            }
            else {
                ArrayList<String> dataList = new ArrayList<>();
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    dataList.add(
                            "Name: " + cursor.getString(0) +
                                    "\nPhoneNumber: " + cursor.getString(1) +
                                    "\nPhoneType: " + cursor.getString(2));
                    cursor.moveToNext();
                }
                searchContactFragment.setListHighlight(dataList);
            }

            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) { return false; }
    };

    public class SectionsPagerAdapter extends FragmentPagerAdapter{

        public SectionsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            switch (position){
                case 0:
                    fragment = addNewContactFragment;
                    break;
                case 1:
                    fragment = searchContactFragment;
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "Add New Contact";
                case 1:
                    return "Search Contact";
                default:
                    return null;
            }
        }
    }
}
