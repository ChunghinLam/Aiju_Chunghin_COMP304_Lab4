package com.example.aiju_chunghin_comp304_lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class TicketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        Intent intent = getIntent();
        Integer movieId = intent.getIntExtra("movieId", 0);

        ImageView imageView = findViewById(R.id.ivMovieAvatar);
        // simply way
        switch (movieId) {
            case 0:
                imageView.setImageResource(R.mipmap.bullettrain_foreground);
                break;
            case 1:
                imageView.setImageResource(R.mipmap.topgun_foreground);
                break;
            case 2:
                imageView.setImageResource(R.mipmap.minions_foreground);
                break;
            case 3:
                imageView.setImageResource(R.mipmap.pearl_foreground);
                break;
            case 4:
                imageView.setImageResource(R.mipmap.womanking_foreground);
                break;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                RadioGroup radioGroup = findViewById(R.id.rgpShowtime);
                int showtimeId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(showtimeId);

                if (showtimeId == -1) {
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.msg_must_select_a_showtime), Toast.LENGTH_SHORT).show();
                    return;
                }

//                Intent intent = new Intent(TicketActivity.this, TickettypeActivity.class);
//                intent.putExtra("showtime", radioButton.getText());

//                myEdit.putString("showtime", radioButton.getText().toString());
//                myEdit.commit();
//                startActivity(intent);
            }
        });

        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(TicketActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}