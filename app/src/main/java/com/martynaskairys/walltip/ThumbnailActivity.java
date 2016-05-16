package com.martynaskairys.walltip;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//Shows a list of pictures
public class ThumbnailActivity extends AppCompatActivity {

    public static final String EXPLANATION = "explanation";
    public static final String TEXTS = "texts";
    final Context context = this;



    //Populates list items
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thumbnail_activity);

        setTitle();

        setExplanationText();

        initRecyclerView();


        String[] IMAGES = getIntent().getStringArrayExtra("images");
        showList(IMAGES);

        findViewById(R.id.RL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] IMAGES = getIntent().getStringArrayExtra("images");

                Intent intent = new Intent(ThumbnailActivity.this, ExitAppActivity.class);
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


    private void initRecyclerView() {
        adapter = new RecyclerAdapter(getApplicationContext());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(adapter);
    }


    //Shows list of images
    private void showList(String[] IMAGES) {
        adapter.setData(IMAGES);
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



