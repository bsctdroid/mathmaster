package bong.anroid.mathmaster.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import bong.anroid.mathmaster.R;

/**
 * Created by jubong on 2017-08-25.
 */

public class RegisterUserInfoActivity extends Activity {

    EditText etId = null;
    EditText etCountry = null;
    Button btnConfirm = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registeruserinfo);

        etId = (EditText)findViewById(R.id.et_id);
        etCountry = (EditText)findViewById(R.id.et_country);
        btnConfirm = (Button)findViewById(R.id.btn_confirm);
    }

    public void mOnClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_confirm:
                finish();
                break;
        }
    }
}
