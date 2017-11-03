package bong.anroid.mathmaster.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.navercorp.volleyextensions.volleyer.Volleyer;
import com.navercorp.volleyextensions.volleyer.factory.DefaultRequestQueueFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import bong.anroid.mathmaster.R;
import bong.anroid.mathmaster.utils.Constants;
import bong.anroid.mathmaster.utils.NetBaseAsyncTask;
import bong.anroid.mathmaster.utils.RankItem;

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

       // Volleyer.volleyer().get(Constants.URL_RANK_ID);
        RequestQueue rq = DefaultRequestQueueFactory.create(this);
        rq.start();
        Volleyer.volleyer(rq).settings().setAsDefault().done();
    }

    public void mOnClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_confirm:
                //finish();
                if(etId.getText().toString() != null && etId.getText().toString().length() > 0)
                {
                    //new AsyncTaskURegId().execute();
                    requestRegidVolley();
                }

                break;
        }
    }






    private void requestRegidVolley()
    {
        String url = Constants.URL_RANK_ID;

        String requestUrl = "";
        String enCodingId = "vo4테스트";//etId.getText().toString();
        String local = "3";

        try {
            //enCodingUrl = URLEncoder.encode(CDefine.id, "UTF-8");//test
            enCodingId = URLEncoder.encode(enCodingId, "EUC-KR");	//test
            //enCodingId = new String(enCodingId.getBytes("EUC-KR"));
//
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        requestUrl = url + "?id=" + enCodingId + "&local=" + local;



        Log.d("BONGTEST", " url " + requestUrl);
        try{
            Volleyer.volleyer().get(requestUrl)
                    .addHeader("Content-type", "application/json; charset=euc-kr")
                    //.withTargetClass(String)
                    .withListener(listener2)
                    .execute();
        }catch(Exception e)
        {
            Log.d("BONGTEST", " " + e);
        }


    }

    private Response.Listener<String> listener2 = new Response.Listener<String>()
    {
        @Override
        public void onResponse(String item)
        {
            Log.d("BONGTEST", "item " + item );
//            textResult.setText("==== jackson1 ====\n");
//            textResult.append(item.name + ", " + item.url);
        }
    };

    private Response.Listener<RankItem> listener1 = new Response.Listener<RankItem>()
    {
        @Override
        public void onResponse(RankItem item)
        {

//            textResult.setText("==== jackson1 ====\n");
//            textResult.append(item.name + ", " + item.url);
        }
    };

    /////////////////////////////////////////////////////////////////////////////////
    //                          일반 요청 함수                                     //
    /////////////////////////////////////////////////////////////////////////////////
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
