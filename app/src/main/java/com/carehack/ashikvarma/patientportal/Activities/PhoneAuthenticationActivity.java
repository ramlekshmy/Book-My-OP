package com.carehack.ashikvarma.patientportal.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.carehack.ashikvarma.patientportal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

import static com.carehack.ashikvarma.patientportal.Utilities.Constants.FIREBASE_TIMEOUT;

public class PhoneAuthenticationActivity extends BaseActivity {

    @BindView(R.id.authentication_root_layout)
    RelativeLayout rootView;
    @BindView(R.id.edittext_mobile_number)
    EditText phoneNumberField;
    @BindView(R.id.imageview_verify_phone)
    Button verifyPhoneNumber;
    @BindView(R.id.imageview_otp_submit)
    Button verifyOtp;
    @BindView(R.id.linearlayout_mobile_number)
    LinearLayout phoneLay;
    @BindView(R.id.linearlayout_otp)
    LinearLayout OTPLay;
    @BindViews({R.id.edt1, R.id.edt2, R.id.edt3, R.id.edt4, R.id.edt5, R.id.edt6})
    List<EditText> nameViews;

    private String mVerificationId;
    private FirebaseAuth mAuth;

    //    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Users");

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            signInWithPhoneAuthCredential(phoneAuthCredential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                displaySnackBar(rootView, getResources().getString(R.string.invalid_phone_number));
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                displaySnackBar(rootView, getResources().getString(R.string.quota_over));
            } else
                displaySnackBar(rootView, getResources().getString(R.string.verification_failure));
        }

        @Override
        public void onCodeSent(String verificationId,
                               PhoneAuthProvider.ForceResendingToken token) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            displaySnackBar(rootView, getResources().getString(R.string.verification_code_send));
            // Save verification ID and resending token so we can use them later
            mVerificationId = verificationId;

            phoneLay.setVisibility(View.GONE);
            OTPLay.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_authentication);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

        setOnClickListeners();
        setTextWatcher();
    }

    private void setTextWatcher() {
        for (EditText editText : nameViews) {
            editText.addTextChangedListener(new PhoneAuthenticationActivity.GenericTextWatcher(editText));
        }
    }

    private void setOnClickListeners() {

        verifyPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        getResources().getString(R.string.countrycode) + phoneNumberField.getText().toString(),        // Phone number to verify
                        FIREBASE_TIMEOUT,                                        // Timeout duration
                        TimeUnit.SECONDS,                                        // Unit of timeout
                        PhoneAuthenticationActivity.this,                // Activity (for callback binding)
                        mCallbacks);                                            // OnVerificationStateChangedCallbacks
            }
        });

        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String otp = "";
                for (EditText editText : nameViews) {
                    String text = editText.getText().toString().trim();
                    if (text.length() == 0) {
                        editText.setError("");
                    } else {
                        otp = otp.concat(text);
                    }
                }
                if (otp.length() == 6) {
                    PhoneAuthCredential credential = PhoneAuthProvider.
                            getCredential(mVerificationId, otp);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            displaySnackBar(rootView, getResources().getString(R.string.verification_complete));

                            finish();
                            startActivity(new Intent(PhoneAuthenticationActivity.this, DashboardActivity.class));
                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                displaySnackBar(rootView, getResources().getString(R.string.verification_invalid_code));
                            }
                        }
                    }
                });
    }

    private class GenericTextWatcher implements TextWatcher {

        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            switch (view.getId()) {

                case R.id.edt1:
                    if (text.length() == 1)
                        nameViews.get(1).requestFocus();
                    break;
                case R.id.edt2:
                    if (text.length() == 1)
                        nameViews.get(2).requestFocus();
                    break;
                case R.id.edt3:
                    if (text.length() == 1)
                        nameViews.get(3).requestFocus();
                    break;
                case R.id.edt4:
                    if (text.length() == 1)
                        nameViews.get(4).requestFocus();
                    break;
                case R.id.edt5:
                    if (text.length() == 1)
                        nameViews.get(5).requestFocus();
                    break;
                case R.id.edt6:
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }
    }
}