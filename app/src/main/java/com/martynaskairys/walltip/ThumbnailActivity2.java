package com.martynaskairys.walltip;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

//Shows a list of pictures
public class ThumbnailActivity2 extends AppCompatActivity {

    public static final String EXPLANATION = "explanation";
    public static final String TEXTS = "texts";
    final Context context = this;

    public Integer[] mThumbIds = {
            R.drawable.b1,
            R.drawable.b2,
            R.drawable.b3,
            R.drawable.b4,
            R.drawable.b5,
            R.drawable.b6,
            R.drawable.b7,
            R.drawable.b8,
            R.drawable.b9,
            R.drawable.b10,
            R.drawable.b11


    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumbnail2);

        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.primary), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter2(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(context, PictureActivity2.class);
                intent.putExtra("Image Int", mThumbIds[position]);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //TODO (setFlags are not recommended?)
                context.startActivity(intent);

                // Toast.makeText(ThumbnailActivityTest1.this, "" + position,
                //       Toast.LENGTH_SHORT).show();
            }
        });

        setTitle();

        setExplanationText();

        findViewById(R.id.RL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] IMAGES = getIntent().getStringArrayExtra("images");

                Intent intent = new Intent(ThumbnailActivity2.this, ExitAppActivity.class);
                intent.putExtra("images", IMAGES);

                startActivity(intent);
            }
        });
    }



    private void setTitle(){

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b != null) {
            String j = (String) b.get(TEXTS);
            setTitle(j);
        }

    }
    private void setExplanationText() {
        TextView textExplainingFolderChoice = (TextView) findViewById(R.id.text_explaining_folder_content);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b != null) {
            String j = (String) b.get(EXPLANATION);
            textExplainingFolderChoice.setText(j);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {

            case R.id.action_favorite:
                displayPopupWindow();
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    public void displayPopupWindow() {

        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.popup);
        dialog.setTitle("Info");

        TextView txt = (TextView) dialog.findViewById(R.id.txt);

        txt.setText(R.string.information_message_window_popup);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButton);

        dialogButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                dialog.dismiss();

            }

        });



        dialog.show();

    }






}



