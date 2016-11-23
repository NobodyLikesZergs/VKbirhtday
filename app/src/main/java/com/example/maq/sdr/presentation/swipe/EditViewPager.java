package com.example.maq.sdr.presentation.swipe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.maq.sdr.R;

public class EditViewPager extends LinearLayout {

    public static final String[] CONTENT_PREFS = {
            "content0",
            "content1",
            "content2",
            "content3",
            "content4",
    };

    private static final String selectedPref = "selected_bullet";
    
    public static final String SHARED_PREFS_NAME = "EDIT_VIEW_CONTENT";

    private TextView textView;

    private ImageView[] bullets;

    private ImageView editButton;

    private String[] contentStrings;

    private SharedPreferences sharedPreferences;

    private int selectedBullet;

    public EditViewPager(Context context) {
        super(context);
        initializeView(context);
    }

    public EditViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }

    public EditViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context);
    }

    private void initializeView(Context context) {
        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.edit_view_pager, this);
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        configureContentStrings(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        textView = (TextView) this.findViewById(R.id.message_text);
        selectedBullet = sharedPreferences.getInt(selectedPref, 0);
        configureBullets();
        onSelectBullet(selectedBullet);
        editButton = (ImageView) this.findViewById(R.id.edit_button);
        editButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditActivity();
            }
        });
    }

    private void startEditActivity() {
        Intent intent = new Intent(getContext(), EditViewActivity.class);
        intent.putExtra("selected_bullet", selectedBullet);
        getContext().startActivity(intent);
    }

    private void configureBullets() {
        bullets = new ImageView[5];
        bullets[0] = (ImageView) this.findViewById(R.id.first_bullet);
        bullets[1] = (ImageView) this.findViewById(R.id.second_bullet);
        bullets[2] = (ImageView) this.findViewById(R.id.third_bullet);
        bullets[3] = (ImageView) this.findViewById(R.id.fourth_bullet);
        bullets[4] = (ImageView) this.findViewById(R.id.fifth_bullet);
        for (int i = 0; i < 5; i++) {
            bullets[i].setBackgroundResource(R.drawable.unselected_bullet);
            final int finalI = i;
            bullets[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelectBullet(finalI);
                }
            });
        }
    }

    private void configureContentStrings(Context context) {
        contentStrings = new String[5];
        contentStrings[0] = sharedPreferences.getString(CONTENT_PREFS[0],
                context.getString(R.string.def_congrats0));
        contentStrings[1] = sharedPreferences.getString(CONTENT_PREFS[1],
                context.getString(R.string.def_congrats1));
        contentStrings[2] = sharedPreferences.getString(CONTENT_PREFS[2],
                context.getString(R.string.def_congrats2));
        contentStrings[3] = sharedPreferences.getString(CONTENT_PREFS[3],
                context.getString(R.string.def_congrats3));
        contentStrings[4] = sharedPreferences.getString(CONTENT_PREFS[4],
                context.getString(R.string.def_congrats4));
    }

    public void refreshContent(){
        contentStrings[selectedBullet] = sharedPreferences
                .getString(CONTENT_PREFS[selectedBullet], "");
        onSelectBullet(selectedBullet);
    }

    private void setTextViewContent(String content) {
        textView.setText(content);
    }

    private void onSelectBullet(int newSelection) {
        bullets[selectedBullet].setBackgroundResource(R.drawable.unselected_bullet);
        selectedBullet = newSelection;
        bullets[selectedBullet].setBackgroundResource(R.drawable.selected_bullet);
        setTextViewContent(contentStrings[selectedBullet]);
        sharedPreferences.edit().putInt(selectedPref, selectedBullet).commit();
    }

    public void saveCurrentContent() {
        sharedPreferences.edit()
                .putString(CONTENT_PREFS[selectedBullet], textView.getText().toString())
                .commit();
    }

    public String getText() {
        return textView.getText().toString();
    }
}
