package com.example.signage.client;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import com.example.signage.client.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;


public class MainPage extends Activity implements AdapterView.OnItemSelectedListener {

    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Resources res = getResources();
        String[] options = res.getStringArray(R.array.awayOptions);

        setContentView(R.layout.activity_main_page);

        Spinner spinner = (Spinner) findViewById(R.id.awayOption);
        spinner.setOnItemSelectedListener(this);
        adapter = new ArrayAdapter(this, R.layout.list_item, new ArrayList(Arrays.asList(options)));
        adapter.setDropDownViewResource(R.layout.away_list_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final Spinner spinner = (Spinner) findViewById(R.id.awayOption);
        switch((int)id)
        {
            case 0:
            case 1:
                if(spinner.getChildCount() > 3) spinner.removeViewAt(spinner.getChildCount() - 1);
                break;
            case 2:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);

                alert.setTitle("Other");
                alert.setMessage("Please enter a message of where you will be...");

                // Set an EditText view to get user input
                final EditText input = new EditText(this);
                alert.setView(input);

                alert.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(spinner.getChildCount() > 3) spinner.removeViewAt(spinner.getChildCount() - 1);
                        String value = input.getText().toString();
                        adapter.add(value);
                        spinner.setSelection(spinner.getChildCount() - 1);
                        dialog.dismiss();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(spinner.getChildCount() > 3) spinner.removeViewAt(spinner.getChildCount() - 1);
                        adapter.add("Unspecified");
                        spinner.setSelection(spinner.getChildCount() - 1);
                        dialog.dismiss();
                    }
                });

                alert.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Spinner spinner = (Spinner) findViewById(R.id.awayOption);
        spinner.performClick();
    }
}
