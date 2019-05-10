package com.sean.newsoft.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.sean.newsoft.interfaces.SimpleUIInterface;

public class BaseActivity extends AppCompatActivity implements SimpleUIInterface {
    ProgressDialog dialog;

    //simple toast
    public void simpleToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    //simple easy blocking loading dialog
    public void toggleLoading(boolean isShow){
        if(isShow){
            if(dialog == null){
                dialog = new ProgressDialog(this);
                dialog.setMessage("Connecting you. Please wait...");
                dialog.setIndeterminate(true);
            }
            dialog.show();
        }else{
            if(dialog != null){
                dialog.dismiss();
            }
        }
    }
}
