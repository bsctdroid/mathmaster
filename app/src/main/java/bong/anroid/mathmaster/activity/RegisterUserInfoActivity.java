package bong.anroid.mathmaster.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import bong.anroid.mathmaster.R;
import bong.anroid.mathmaster.utils.Constants;
import bong.anroid.mathmaster.utils.NetBaseAsyncTask;

/**
 * Created by jubong on 2017-08-25.
 */

public class RegisterUserInfoActivity extends Activity {
    public static String TAG = RegisterUserInfoActivity.class.getName();

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
                //finish();
                if(etId.getText().toString() != null && etId.getText().toString().length() > 0)
                {

                    new AsyncTaskURegId().execute();
                }

                break;
        }
    }

    private class AsyncTaskURegId extends NetBaseAsyncTask {
        private String eMsg = "";

        String strId = "";
        String strPw = "";

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;
            try {
               // String = strId = params[0];

                String url = Constants.URL_RANK_ID;

                HashMap<String, String> hashParams = new HashMap<String, String>();

                String enCodingId = "registest1";
                String local = "3";

                try {
                    //enCodingUrl = URLEncoder.encode(CDefine.id, "UTF-8");//test
                    enCodingId = URLEncoder.encode(enCodingId, "EUC-KR");	//test
//
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                hashParams.put("id", enCodingId);
                hashParams.put("local", local);

                NetReturn data = getHTTPGETData(url,hashParams);

                if (data != null && data.mReturnStr != null && false == "".equals(data.mReturnStr)) {
                    Log.d(TAG, "data.mReturnStr (Logout) : " + data.mReturnStr);
                    eMsg = data.mReturnStr;
                }else{
                    int mesID = data.getErrString();

                    if (data.mErrNo != 0) {
                        eMsg = getString(mesID) + "(" + data.mErrNo + ")";
                    } else {
                        eMsg = getString(mesID);
                    }

                    if (data.mException != null) {
                        data.mException.printStackTrace();
                    }

                    return false;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }


            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            Log.d("BONGTEST", "eMsg " + eMsg);
            parseXml(eMsg);
        }
    }

    public void parseXml(String strXml)
    {
        if(strXml.contains("success")) {
            Toast.makeText(RegisterUserInfoActivity.this, "success", Toast.LENGTH_SHORT).show();
        }
        else if(strXml.contains("fail")){
            Toast.makeText(RegisterUserInfoActivity.this, "fail", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(RegisterUserInfoActivity.this, "network error", Toast.LENGTH_SHORT).show();
    }


}
