package makers.ar_d.firebaseauthentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //TODO: 4 Global Variable
    Button btnLogin;
    EditText input_email, input_password;
    TextView btnSignUp, btnForgotPass;

    RelativeLayout activity_main;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //TODO: 5 View
        btnLogin = (Button) findViewById(R.id.btn_login);
        input_email = (EditText) findViewById(R.id.login_email);
        input_password = (EditText) findViewById(R.id.login_password);
        btnSignUp = (TextView) findViewById(R.id.login_btn_signup);
        btnForgotPass = (TextView) findViewById(R.id.login_btn_forgot_password);
        activity_main = (RelativeLayout) findViewById(R.id.activity_main);

        btnLogin.setOnClickListener(this);
        btnForgotPass.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        //TODO: 6 init Firebase Auth
        auth = FirebaseAuth.getInstance();

        //TODO: 7 check already session, if ok->> Dashboard
        if (auth.getCurrentUser() != null)
            startActivity(new Intent(MainActivity.this, DashBoard.class));

    }

    @Override
    public void onClick(View view) {

        //TODO: 8 intent
        if (view.getId() == R.id.login_btn_forgot_password) {
            startActivity(new Intent(MainActivity.this, ForgotPassword.class));
            finish();
        } else if (view.getId() == R.id.login_btn_signup) {
            startActivity(new Intent(MainActivity.this, SignUp.class));
            finish();
        } else if (view.getId() == R.id.btn_login) {
            loginUser(input_email.getText().toString(), input_password.getText().toString());
        }
    }

    private void loginUser(String email, final String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (password.length() < 6) {
                                Snackbar snackBar = Snackbar.make
                                        (activity_main, "Panjang password harus lebih dari 6 karakter", Snackbar.LENGTH_SHORT);
                            }
                        } else {
                            startActivity(new Intent(MainActivity.this, DashBoard.class));
                        }
                    }
                });
    }

}
