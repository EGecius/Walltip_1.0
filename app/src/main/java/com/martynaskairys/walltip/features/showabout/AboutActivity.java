package com.martynaskairys.walltip.features.showabout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.martynaskairys.walltip.features.selectcategory.MainActivity;
import com.martynaskairys.walltip.R;

/** Tells user about the app and how to use it */
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
	    setScreenTitle();
    }

	private void setScreenTitle() {
		setTitle(getString(R.string.about_screen_title));
	}

	public void selectCategory(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
            case android.R.id.home:
	            //up buttons behave like back button
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
