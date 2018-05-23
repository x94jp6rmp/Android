package com.example.hw9;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.ServiceState;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int MENU_MUSIC = Menu.FIRST,
            MENU_PLAY_MUSIC = Menu.FIRST + 1,
            MENU_STOP_MUSIC = Menu.FIRST + 2,
            MENU_ABOUT = Menu.FIRST + 3,
            MENU_EXIT = Menu.FIRST + 4;

    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRelativeLayout = findViewById(R.id.relative);
        registerForContextMenu(mRelativeLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SubMenu subMenu = menu.addSubMenu(0, MENU_MUSIC, 0, "背景音樂");
        subMenu.add(0, MENU_PLAY_MUSIC, 0, "播放背景音樂");
        subMenu.add(0, MENU_STOP_MUSIC, 1, "停止播放背景音樂");
        menu.add(0, MENU_ABOUT, 1, "關於這個程式...");
        menu.add(0, MENU_EXIT, 2, "結束");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_PLAY_MUSIC:
                Intent intent = new Intent(MainActivity.this, MediaPlayService.class);
                startService(intent);
                return true;
            case MENU_STOP_MUSIC:
                intent = new Intent(MainActivity.this,MediaPlayService.class);
                stopService(intent);
                return true;
            case MENU_ABOUT:
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("關於這個程式")
                        .setMessage("選單範例程式")
                        .setCancelable(false)
                        .setPositiveButton("確定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                        .show();
                return true;
            case MENU_EXIT:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        onOptionsItemSelected(item);
        return super.onContextItemSelected(item);
    }
}
