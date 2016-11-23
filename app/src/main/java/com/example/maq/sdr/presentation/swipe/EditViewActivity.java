package com.example.maq.sdr.presentation.swipe;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.maq.sdr.R;

public class EditViewActivity extends AppCompatActivity {

    private int selectedBullet;

    private Button button;

    private EditText editText;

    private ImageView arrowBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_view_activity_layout);
        selectedBullet = getIntent().getExtras().getInt("selected_bullet", 0);
        editText = (EditText) findViewById(R.id.edit_text);
        button = (Button) findViewById(R.id.save_edit_text_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp =
                        getSharedPreferences(EditViewPager.SHARED_PREFS_NAME, MODE_PRIVATE);
                sp.edit()
                        .putString(EditViewPager.CONTENT_PREFS[selectedBullet],
                        editText.getText().toString())
                        .commit();
                finish();
            }
        });
        arrowBack = (ImageView) findViewById(R.id.arrow_back);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
