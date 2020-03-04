package bocolly.pinheiro.culinary.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import bocolly.pinheiro.culinary.R;
import bocolly.pinheiro.culinary.model.MyApplication;
import bocolly.pinheiro.culinary.model.User;


public class Login extends AppCompatActivity {

    private EditText etUser;
    private EditText etPassword;
    private Button btLogin;
    private ProgressBar progress;

    private MyApplication myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etUser.getText().toString().isEmpty() ||
                        etPassword.getText().toString().isEmpty()){
                    toast("Enter username and/or password!");
                    return;
                }


                User u = new User();
                u.setLogin(etUser.getText().toString());
                u.setPassword(etPassword.getText().toString());

                progress.setVisibility(View.VISIBLE);

                myApp.getmAuth().signInWithEmailAndPassword(u.getLogin(),u.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progress.setVisibility(View.INVISIBLE);

                        if(!task.isSuccessful()){
                            toast("Invalid username/password!");
                            return;
                        }

                        toast("Login done successfully!");
                        Intent it = new Intent(Login.this,
                                MainActivity.class);
                        startActivity(it);
                        finish();
                    }
                });
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        myApp.getmAuth().addAuthStateListener(myApp.getmAuthStateListener());
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(myApp.getmAuthStateListener() != null){
            myApp.getmAuth().removeAuthStateListener(myApp.getmAuthStateListener());
        }
    }

    private void toast(String msg){
        Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();
    }

    private void init(){
        etUser = findViewById(R.id.lo_et_user);
        etPassword = findViewById(R.id.lo_et_password);
        btLogin = findViewById(R.id.lo_bt_login);
        progress = findViewById(R.id.lo_progress);

        progress.setVisibility(View.INVISIBLE);

        //Firebase
        myApp = new MyApplication();
        myApp.setmAuth(FirebaseAuth.getInstance());

        myApp.setFirebaseUser(myApp.getmAuth().getCurrentUser());

        myApp.setmAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(myApp.getFirebaseUser() != null){
                    Intent it = new Intent(
                            Login.this,
                            MainActivity.class);
                    startActivity(it);
                    finish();
                }
            }
        });
    }
}
