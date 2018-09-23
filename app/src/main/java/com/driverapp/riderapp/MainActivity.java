package com.driverapp.riderapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.driverapp.riderapp.Common.Common;
import com.driverapp.riderapp.Model.Rider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn, btnRegister;
    RelativeLayout rootLayout;

    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    private final static int PERMISSION = 1000;

    TextView txt_forgot_pass;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().
                setDefaultFontPath("fonts/KaushanScript-Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_main);

        //Firebase Authentication
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users= db.getReference(Common.user_rider_tbl);


        //Button on click
        btnSignIn = (Button)findViewById(R.id.btnSignIn);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        rootLayout = (RelativeLayout)findViewById(R.id.rootLayout);
        txt_forgot_pass = (TextView)findViewById(R.id.txt_forgot_pass);
        txt_forgot_pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showDialogForgotPass();
                return false;
            }
        });

        //Event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterDialog();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoginDialog();
            }
        });
    }

    private void showDialogForgotPass() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("FORGOT PASSWORD");
        alertDialog.setMessage("Please enter your email Address");


        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View forgot_pass_layout = inflater.inflate(R.layout.layout_forgot_pass, null);


        final MaterialEditText edtEmail = (MaterialEditText)forgot_pass_layout.findViewById(R.id.EdtEmail);
        alertDialog.setView(forgot_pass_layout);

        //Set Button
        alertDialog.setPositiveButton("RESET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                if(TextUtils.isEmpty(edtEmail.getText().toString()))
                {
                    Snackbar.make(rootLayout, "Please Enter Email Address", Snackbar.LENGTH_SHORT).
                            show();
                    return;
                }
                final android.app.AlertDialog waitingDialog = new SpotsDialog(MainActivity.this);
                waitingDialog.show();

                auth.sendPasswordResetEmail(edtEmail.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                dialog.dismiss();
                                waitingDialog.dismiss();


                                Snackbar.make(rootLayout, "Reset password link has been sent", Snackbar.LENGTH_SHORT)
                                        .show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        waitingDialog.dismiss();


                        Snackbar.make(rootLayout, ""+e.getMessage(), Snackbar.LENGTH_SHORT)
                                .show();
                    }
                });
            }
        });
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        //Fix Dialog not show
        alertDialog.show();
    }

    private void showRegisterDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("REGISTER");
        dialog.setMessage("Use Email to Register");


        LayoutInflater inflater;
        inflater = new LayoutInflater(this) {
            @Override
            public LayoutInflater cloneInContext(Context context) {
                return null;
            }
        };
        View register_Layout =  inflater.inflate(R.layout.layout_register,null);


        final MaterialEditText edtEmail = register_Layout.findViewById(R.id.EdtEmail);
        final MaterialEditText edtPassword = register_Layout.findViewById(R.id.EdtPassword);
        final MaterialEditText edtName = register_Layout.findViewById(R.id.EdtName);
        final MaterialEditText edtPhone = register_Layout.findViewById(R.id.edtPhone);


        dialog.setView(register_Layout);


        //Set Button

        dialog.setPositiveButton("Register", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                //Check Validation
                if(TextUtils.isEmpty(edtEmail.getText().toString()))
                {
                    Snackbar.make(rootLayout, "Please Enter Email Address", Snackbar.LENGTH_LONG).
                            show();
                    return;
                }

                if(TextUtils.isEmpty(edtPassword.getText().toString()))
                {
                    Snackbar.make(rootLayout, "Please Enter Password", Snackbar.LENGTH_LONG).
                            show();
                    return;
                }
                if(edtPassword.getText().toString().length()<6)
                {
                    Snackbar.make(rootLayout, "Password too short!!!", Snackbar.LENGTH_LONG).
                            show();
                    return;
                }

                if(TextUtils.isEmpty(edtPhone.getText().toString()))
                {
                    Snackbar.make(rootLayout, "Please Enter Phone", Snackbar.LENGTH_LONG).
                            show();
                    return;
                }

                if(TextUtils.isEmpty(edtName.getText().toString()))
                {
                    Snackbar.make(rootLayout, "Please Enter Name", Snackbar.LENGTH_LONG).
                            show();
                    return;
                }
                final android.app.AlertDialog waitingdialog = new SpotsDialog(MainActivity.this);
                waitingdialog.show();
                //Register new user
                auth.createUserWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                //Save user to database
                                Rider user = new Rider();
                                user.setEmail(edtEmail.getText().toString());
                                user.setName(edtName.getText().toString());
                                user.setPassword(edtPassword.getText().toString());
                                user.setPhone(edtPhone.getText().toString());

                                //use Email to key
                                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                waitingdialog.dismiss();
                                                Snackbar.make(rootLayout, "Registration successful!!", Snackbar.LENGTH_LONG).
                                                        show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                waitingdialog.dismiss();
                                                Snackbar.make(rootLayout, " Failed" +e.getMessage(), Snackbar.LENGTH_LONG).
                                                        show();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                waitingdialog.dismiss();
                                Snackbar.make(rootLayout, " Failed" +e.getMessage(), Snackbar.LENGTH_LONG).
                                        show();
                            }
                        });
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }
    private void showLoginDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("LOGIN");
        dialog.setMessage("Use Email to Login");


        LayoutInflater inflater;
        inflater = new LayoutInflater(this) {
            @Override
            public LayoutInflater cloneInContext(Context context) {
                return null;
            }
        };
        View login_layout =  inflater.inflate(R.layout.layout_login,null);


        final MaterialEditText edtEmail = login_layout.findViewById(R.id.EdtEmail);
        final MaterialEditText edtPassword = login_layout.findViewById(R.id.EdtPassword);


        dialog.setView(login_layout);


        //Set Button

        dialog.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                //set disable button sign in if processing

                btnSignIn.setEnabled(false);



                //Check Validation
                if(TextUtils.isEmpty(edtEmail.getText().toString()))
                {
                    Snackbar.make(rootLayout, "Please Enter Email Address", Snackbar.LENGTH_LONG).
                            show();
                    btnSignIn.setEnabled(true);
                    return;
                }

                if(TextUtils.isEmpty(edtPassword.getText().toString()))
                {
                    Snackbar.make(rootLayout, "Please Enter Password", Snackbar.LENGTH_LONG).
                            show();
                    btnSignIn.setEnabled(true);
                    return;
                }
                //if(edtPassword.getText().toString().length()<6)
                //{
               //     Snackbar.make(rootLayout, "Password too short!!!", Snackbar.LENGTH_LONG).
                //            show();
               //     return;
               // }

                final android.app.AlertDialog waitingdialog = new SpotsDialog(MainActivity.this);
                waitingdialog.show();
                //Login user
                auth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                waitingdialog.dismiss();
                                startActivity(new Intent(MainActivity.this,Home.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        waitingdialog.dismiss();
                        Snackbar.make(rootLayout, "Failed"+ e.getMessage(), Snackbar.LENGTH_LONG)
                                .show();

                        //activate the sign in button

                        btnSignIn.setEnabled(true);
                    }
                });
            }
        });


        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.show();
    }
}
