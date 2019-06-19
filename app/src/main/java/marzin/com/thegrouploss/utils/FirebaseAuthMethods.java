package marzin.com.thegrouploss.utils;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthMethods {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private Context mContext;
    private String mUserID;
    private static final int RC_SIGN_IN=1;
    private static final String TAG="FirebaseAuthMethods";



    public FirebaseAuthMethods(Context context){
        mAuth = FirebaseAuth.getInstance();
        mContext = context;
        if (mAuth.getCurrentUser()!= null){
            mUserID = mAuth.getCurrentUser().getUid();
        }
    }

}
