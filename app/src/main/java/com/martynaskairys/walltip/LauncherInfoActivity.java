package com.martynaskairys.walltip;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class LauncherInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher_info);
    }

    public void downloadGoogleLauncher (View view){

        String url = "https://play.google.com/store/apps/details?id=com.google.android.launcher&hl=en";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void linkToPlayStore (View view){

        String url = "https://play.google.com/store/apps?hl=en";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void exitApp (View view){

        Intent intent1 = new Intent(this, ChooseCategoryActivity.class);
        Intent intent2 = new Intent(Intent.ACTION_MAIN);
        intent2.addCategory(Intent.CATEGORY_HOME);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Toast.makeText(this, R.string.exit_message, Toast.LENGTH_SHORT).show();

        startActivity(intent1);
        startActivity(intent2);
    }
}
