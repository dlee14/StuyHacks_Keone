package grv.lapitchat;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView mDisplayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        // Typeface customFont1 = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue Medium.ttf");
        Toolbar mToolbar = (Toolbar) findViewById(R.id.mainPageToolbar);
        setSupportActionBar(mToolbar);
        // Sets up text in the toolbar
        getSupportActionBar().setTitle("LapitChat");
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null)
        FirebaseUser user = mAuth.getCurrentUser();

        // If the user is not signed in, go to StartActivity
        if (user == null) {
            sendToStart();
        } else {
            //Gets the name and displays the welcome text!
            mDisplayName = (TextView) findViewById(R.id.mainNameText);
            String welcomeText = "Welcome to Lapitchat, " + user.getDisplayName();
            mDisplayName.setText(welcomeText);
        }
    }

    private void sendToStart() {
        Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(startIntent);
        finish(); // So the user ever come back
    }

    // Creates the menu on the top left
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // If the one of the option item selected is logout, log the user out
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.mainLogoutBtn) {
            FirebaseAuth.getInstance().signOut();
            // Since the user is now logged out, send to StartActivity
            sendToStart();
        }

        return true;
    }
};

