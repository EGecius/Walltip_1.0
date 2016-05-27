package com.martynaskairys.walltip;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;


//Shows a list of pictures
public class ThumbnailActivity extends AppCompatActivity {

    public static final String EXPLANATION = "explanation";
    public static final String TEXTS = "texts";
	public static final String IMAGES = "images";
	public static final String THUMB_IDS = "thumb_ids";

	final Context context = this;

    private int[] mThumbIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumbnail);

		mThumbIds = getIntent().getIntArrayExtra(THUMB_IDS);

        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.primary), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new ImageAdapter(this, mThumbIds));

        setTitle();

        setExplanationText();

        findViewById(R.id.RL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] images = getIntent().getStringArrayExtra(IMAGES);

                Intent intent = new Intent(ThumbnailActivity.this, ExitAppActivity.class);
                intent.putExtra("images", images);

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



