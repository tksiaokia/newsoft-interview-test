package com.sean.newsoft.activity;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.sean.newsoft.R;
import com.sean.newsoft.databinding.LoginActivityBinding;
import com.sean.newsoft.enums.LocalData;
import com.sean.newsoft.local.SharedPreferenceHelper;
import com.sean.newsoft.model.response.GeneralResponse;
import com.sean.newsoft.model.UserToken;
import com.sean.newsoft.services.BaseService;
import com.sean.newsoft.services.MainService;


public class LoginActivity extends BaseActivity {
    static int LOGIN_SUCCESS = 100;
    LoginActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = DataBindingUtil.setContentView(this, R.layout.login_activity);

        addLoginClickListener();

        //testing purpose
        pumpTestValue();
    }

    private void addLoginClickListener(){
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateAndLogin();
            }
        });
    }

    private void validateAndLogin(){
        String email = binding.txtEmail.getText().toString();
        String password = binding.txtPassword.getText().toString();
        /*
        //Comment below checking since api will check for it
        if( email.isEmpty()) {
            simpleToast("Email is empty");
            return;
        }

        if( password.isEmpty()) {
            simpleToast("Password is empty");
            return;
        }*/

        apiLogin(email,password);
    }

    private void apiLogin(String email,String password){
        toggleLoading(true);

        MainService service = new MainService();
        service.login(email, password, new MainService.LoginResponseCallback() {
            @Override
            public void onLoginSuccess(UserToken userToken) {
                simpleToast(userToken.getToken());
                toggleLoading(false);

                //store it
                SharedPreferenceHelper.getInstance().putValue(LocalData.userToken,userToken);

                //back to main activity by kill login activity
                setResult(LOGIN_SUCCESS);
                finish();
            }

            @Override
            public void onError(GeneralResponse response) {
                simpleToast(response.getMessage());
                toggleLoading(false);
            }
        });
    }



    private void pumpTestValue(){
//        binding.txtEmail.setText("movida@advisoryapps.com");
//        binding.txtPassword.setText("movida123");

        binding.txtEmail.setText("aiman@advisoryapps.com");
        binding.txtPassword.setText("aiman123");
//
//        binding.txtEmail.setText("admin@advisoryapps.com");
//        binding.txtPassword.setText("advisoryapps123");

    }




}
