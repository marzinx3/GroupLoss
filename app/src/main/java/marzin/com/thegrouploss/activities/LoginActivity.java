package marzin.com.thegrouploss.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import marzin.com.thegrouploss.R;
import marzin.com.thegrouploss.databinding.ActivityLoginBinding;

/**
 * A login screen that offers login via email/password and Google Sign in.
 */
public class LoginActivity extends FragmentActivity implements View.OnClickListener{

    //Vars
    private Button mGoogleButton;
    private Button mSignInButton;
    private TextView mSignUpTextView;
    private EditText mEmail;
    private EditText mPassword;
    private TextView mForgotPasswordTextView;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN=1;
    private static final String TAG="Login Activity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    // Data binding
    ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Start binding for activity login
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);

        // initialize variables
        mEmail = mBinding.email;
        mPassword= mBinding.password;
        mGoogleButton = mBinding.googleSigninButton;
        mSignInButton = mBinding.signinButton;
        mSignUpTextView=mBinding.actionSignup;
        mSignUpTextView.setPaintFlags(mSignUpTextView.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        mForgotPasswordTextView=mBinding.actionForgotPassword;
        mForgotPasswordTextView.setPaintFlags(mForgotPasswordTextView.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        // set onclick and call setupFirebase
        setupFirebase();
        mGoogleButton.setOnClickListener(this);
        mSignInButton.setOnClickListener(this);
        mSignUpTextView.setOnClickListener(this);
        mForgotPasswordTextView.setOnClickListener(this);
    }

    /**
     *  Home page button presses
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google_signin_button:
                googleSignIn();
                break;
            case R.id.signin_button:
                signInWithEmailPassword(mEmail,mPassword);
                break;
            case R.id.action_forgot_password:
                forgotPasswordIntent();
                break;
            case R.id.action_signup:
                signUpIntent();
                break;
        }

    }

    /**
     *  Sign in with just email and password
     * @param logOnEmail
     * @param logOnPassword
     */
    private void signInWithEmailPassword(EditText logOnEmail, EditText logOnPassword){
        String email= logOnEmail.getText().toString();
        String password = logOnPassword.getText().toString();

      if (email.isEmpty()||password.isEmpty()){
          Toast.makeText(LoginActivity.this, "All Fields Must Be Filled In",
                  Toast.LENGTH_SHORT).show();
      }else {
          mAuth.signInWithEmailAndPassword(email, password)
                  .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          if (task.isSuccessful()) {
                              // Sign in success, update UI with the signed-in user's information
                              Log.d(TAG, "signInWithEmail:success");
                              FirebaseUser user = mAuth.getCurrentUser();
                              updateUI(user);
                          } else {
                              // If sign in fails, display a message to the user.
                              Log.w(TAG, "signInWithEmail:failure", task.getException());
                              Toast.makeText(LoginActivity.this, "No User Found.",
                                      Toast.LENGTH_SHORT).show();
                              updateUI(null);
                          }

                          // ...
                      }
                  });
      }
    }

    /*
    ------------------------------------Google Button SignIn ------------------------------------
     */
    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Communication Issue",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }



    /*
    ------------------------------- Intents --------------------------------------------------------
     */
    public void updateUI(FirebaseUser user) {

        if (user != null) {
         startActivity(new Intent(LoginActivity.this,MainActivity.class));
         finish();
        } else {
          // Log how many times user has failed login. and lock account after 4 times.
        }
    }


    public void signUpIntent(){
        startActivity(new Intent(LoginActivity.this,OnBoardingActivity.class));
    }

    public void forgotPasswordIntent(){
        startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
    }


    /*
    ------------------------------- Firebase Setup -----------------------------
     */

    private void setupFirebase(){
        Log.d(TAG,"Setup Firebase:Setting up firebase");
        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()!=null){
                    Log.d(TAG,"Setup Firebase:User already logged in");
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }
            }
        };

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) {
            updateUI(currentUser);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthStateListener != null){
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
}

