package grv.lapitchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText mDisplayName;
    private EditText mEmail;
    private EditText mPassword;

    // Firebase stuff
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Sets up the toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.registerToolbar);
        setSupportActionBar(mToolbar);
        // Put text in the toolbar
        getSupportActionBar().setTitle("Create Account");
        // Put the arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Android Layout
        mDisplayName = (EditText) findViewById(R.id.regDisplayName);
        mEmail = (EditText) findViewById(R.id.regEmail);
        mPassword = (EditText) findViewById(R.id.regPassword);
        Button mCreateBtn = (Button) findViewById(R.id.regCreateBtn);

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String displayName = mDisplayName.getEditableText().toString();
                String email = mEmail.getEditableText().toString();
                String password = mPassword.getEditableText().toString();

                registerUser(displayName, email, password);

            }
        });
    }

    // Registers the user on the server
    private void registerUser(String displayName, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "You got some error.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
