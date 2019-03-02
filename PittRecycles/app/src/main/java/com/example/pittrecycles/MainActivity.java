package com.example.pittrecycles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button myButton = (Button) findViewById(R.id.my_button);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdit = findViewById(R.id.bar_code);
                System.out.print(mEdit.getText().toString());
                Toast toast = Toast.makeText( getApplicationContext(), mEdit.getText().toString(), Toast.LENGTH_LONG);
                toast.show();
            }
        });

    }
}
