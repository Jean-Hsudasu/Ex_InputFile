package com.example.user.ex_inputfile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText edtID,edtPW,edtcontent;
    private Button btnadd,btnclear,btnend;
    private static final String FILENAME = "login.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtID = findViewById(R.id.edtID);
        edtPW = findViewById(R.id.edtPW);
        edtcontent = findViewById(R.id.edtcontent);
        btnadd = findViewById(R.id.btnadd);
        btnclear = findViewById(R.id.btnclear);
        btnend = findViewById(R.id.btnend);

        edtID.setOnClickListener(listener);
        edtPW.setOnClickListener(listener);
        edtcontent.setOnClickListener(listener);
        btnadd.setOnClickListener(listener);
       btnclear.setOnClickListener(listener);
        btnend.setOnClickListener(listener);

        try {
            DisplayFile(FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Button.OnClickListener listener = new Button.OnClickListener()
    {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnadd:
                    if(edtID.getText().toString().equals("")||edtPW.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(),"帳號及密碼都必須輸入!",Toast.LENGTH_LONG).show();
                        break;
                    }
                    FileOutputStream fout = null;
                    BufferedOutputStream buffout = null;
                    try {
                        fout = openFileOutput(FILENAME,MODE_PRIVATE);
                        buffout = new BufferedOutputStream(fout);
                        buffout.write(edtID.getText().toString().getBytes());
                        buffout.write("\n".getBytes());
                        buffout.write(edtPW.getText().toString().getBytes());
                        buffout.write("\n".getBytes());
                        buffout.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        DisplayFile(FILENAME);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    edtID.setText("");
                    edtPW.setText("");
                    break;

                case R.id.btnclear:
                    try {
                        fout = openFileOutput(FILENAME,MODE_PRIVATE);
                        fout.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        DisplayFile(FILENAME);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case R.id.btnend:
                    finish();
            }
        }
    };

    private void DisplayFile(String filename) throws IOException {
        FileInputStream fin = null;
        BufferedInputStream buffin = null;
        try {
            fin = openFileInput(filename);
            buffin = new BufferedInputStream(fin);
            byte[] buffbyte = new byte[20];
            edtcontent.setText("");
            do {
                int flag = buffin.read(buffbyte);
                if (flag == -1) break;
                else
                    edtcontent.append(new String(buffbyte),0,flag);
            }while (true);
            buffin.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
