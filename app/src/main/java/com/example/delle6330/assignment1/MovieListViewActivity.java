package com.example.delle6330.assignment1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.app.Dialog;
import android.view.View.OnClickListener;


public class MovieListViewActivity extends Activity {

    final Context context = this;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list_view);
        TextView tv = (TextView)findViewById(R.id.svInfo);
        tv.setText("Detailed info about movie");

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.activity_dialog);
                TextView txt = (TextView) dialog.findViewById(R.id.txt);
                txt.setText("Notification will be here");
                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButton);
                dialogButton.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
}
