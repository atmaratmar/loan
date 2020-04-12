package com.example.loanandrepay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void onClickSignupBtn(View view) {
new Register().execute();
    }

    public class Register extends AsyncTask<String, Void, Void> {
        private EditText editText;

        HttpConnection httpConnection = new HttpConnection();
        EditText enterFirstName = (EditText) findViewById(R.id.txtFirstName);
        EditText enterLastName = (EditText) findViewById(R.id.txtLastName);
        EditText enterPhone = (EditText) findViewById(R.id.txtPhone);
        EditText enterEmail = (EditText) findViewById(R.id.txtEmail);
        EditText enterPassword = (EditText) findViewById(R.id.txtSignupPassword);
        EditText confirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);

        @Override
        protected Void doInBackground(String... params) {
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("FirstName", enterFirstName.getText());
                postDataParams.put("LastName", enterLastName.getText());
                postDataParams.put("PhoneNumber", enterPhone.getText());
                postDataParams.put("Email", enterEmail.getText());
                postDataParams.put("Password", enterPassword.getText());
                postDataParams.put("ConfirmPassword", confirmPassword.getText());


                //url = new URL("http://25.95.117.73:7549/api/Account/Login");
                url = new URL("http://192.168.1.171:4567/api/Account/Register");


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);

                OutputStream os = urlConnection.getOutputStream();

                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(httpConnection.getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {

                    Intent goToAuthentication = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(goToAuthentication);
                    finish();


                } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST){
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Sign-up failed. Please try again",
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return null;
        }

        protected void onPostExecute(Void result){
            super.onPostExecute(result);

        }
}}
