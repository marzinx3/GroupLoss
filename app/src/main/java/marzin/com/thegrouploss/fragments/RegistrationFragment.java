package marzin.com.thegrouploss.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import marzin.com.thegrouploss.R;
import marzin.com.thegrouploss.activities.MainActivity;
import marzin.com.thegrouploss.databinding.FragmentRegistrationBinding;

public class RegistrationFragment extends Fragment implements View.OnClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private TextInputEditText mEmailTextbox;
    private TextInputEditText mUsernameTextbox;
    private TextInputEditText mNameTextbox;
    private TextInputEditText mPasswordTextbox;
    private RadioGroup mGenderRadiogroup;
    private RadioButton mRadioButton;
    private TextView mRegistrationText;
    private TextView mTermsText;
    private Button mSignupButton;
    private ProgressBar mProgress;
    private FragmentRegistrationBinding mBinding;
    private FirebaseAuth mAuth;
    private final String SCREENNAME="RegistrationFragment";
    public RegistrationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationOne.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Bind registration page
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_registration,container,false);
        View v = mBinding.getRoot();

        // init variables
        mEmailTextbox = mBinding.emailTextBox;
        mUsernameTextbox = mBinding.usernameTextBox;
        mNameTextbox= mBinding.nameTextbox;
        mPasswordTextbox = mBinding.passwordTextbox;
        mGenderRadiogroup = mBinding.genderRadioGroup;
        mSignupButton= mBinding.signUp;
        mTermsText=mBinding.terms;
        mRegistrationText=mBinding.userRegistration;
        mProgress=mBinding.progressBar;
        mAuth =FirebaseAuth.getInstance();

        // set var and methods
        mRegistrationText.setText(R.string.registration);
        mRegistrationText.setPaintFlags(mRegistrationText.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        mSignupButton.setOnClickListener(this);
        mTermsText.setOnClickListener(this);

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
                case R.id.signUp:
                checkRegistration(v);
                break;

                case R.id.terms:
                termsAndConditions();
                break;

                default:
                checkRegistration(v);
                break;
        }
    }

    public void checkRegistration(final View v){
        String email = mEmailTextbox.getText().toString();
        String username= mUsernameTextbox.getText().toString();
        String password= mPasswordTextbox.getText().toString();
        String name= mNameTextbox.getText().toString();
        int radioButtonID=mGenderRadiogroup.getCheckedRadioButtonId();
        if (radioButtonID== -1){
          Toast.makeText(getContext(),"Please select a Gender",Toast.LENGTH_SHORT).show();
        }else {
            // init radio button and gender
            mRadioButton = mGenderRadiogroup.findViewById(radioButtonID);
            final String gender = mRadioButton.getText().toString();

            if (!isEmptyField(email, username, password, name, gender)) {
                mProgress.setVisibility(v.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(getActivity(),"createUserWithEmail:onComplete:" + task.isSuccessful(),Toast.LENGTH_SHORT).show();
                                mProgress.setVisibility(v.GONE);
                                if(!task.isSuccessful()){
                                    Toast.makeText(getActivity(),"Authentification failed."+ task.getException(),Toast.LENGTH_SHORT).show();
                                }else{
                                    startActivity(new Intent(getActivity(),MainActivity.class));
                                    getActivity().finish();
                                }

                            }
                        });

            }
        }
    }


    // check email and username
    private boolean isEmptyField(String email,String username, String password, String name, String gender) {

        boolean noIssues=false;

    if(!(email.trim().length()>0)|| !(Patterns.EMAIL_ADDRESS.matcher(email).matches())){
        mEmailTextbox.setError("Email is not valid");
        mEmailTextbox.requestFocus();
        noIssues= true;
    }
    if  (!(username.trim().length() > 0)){
           mUsernameTextbox.setError("Username is Missing");
           mUsernameTextbox.requestFocus();
        noIssues= true;
        }
    if (!(name.trim().length() > 0)) {
             mNameTextbox.setError("Name is Missing");
             mNameTextbox.requestFocus();
        noIssues=true;
            }
    if(!(password.trim().length() > 6)) {
        //password length
        mPasswordTextbox.setError("Password is too short");
        mPasswordTextbox.requestFocus();
        noIssues= true;
    }

            return noIssues;

    }

    public void termsAndConditions(){
        Toast.makeText(getContext(),"TESTING",Toast.LENGTH_SHORT).show();
    }
}

