package bong.anroid.mathmaster.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import bong.anroid.mathmaster.BaseActivity;
import bong.anroid.mathmaster.R;
import bong.anroid.mathmaster.utils.Constants;
import bong.anroid.mathmaster.utils.NetBaseAsyncTask;

public class RankSettingActivity extends BaseActivity {
	String TAG = "MATHMASTER";

	TextView tvId;
	TextView tvLocal;

	private Dialog dialog;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_setting);
        
        initData();
    }

    public void initData()
	{
		tvId = (TextView)findViewById(R.id.tv_id);
		tvLocal = (TextView)findViewById(R.id.tv_local);

		if(mDataMgrApp.userId.length() > 0)
			tvId.setText(mDataMgrApp.userId);
		if(mDataMgrApp.userLocal.length() > 0)
			tvLocal.setText(mDataMgrApp.userLocal);


	}

	public void dialogInput()
	{
		dialog = new Dialog(this, R.style.Theme_Dialog);
		dialog.setContentView(R.layout.view_input_userinfo_dialog);

		/*LinearLayout.class.cast(dialog.findViewById(R.id.linear_listpopup_5)).setVisibility(View.GONE);

		TextView.class.cast(dialog.findViewById(R.id.tv_listdialog_title)).setText(_strTitle);//.setVisibility(View.GONE);
		TextView.class.cast(dialog.findViewById(R.id.tv_listpopup_1)).setText(_strList1);

		Button btnCloseBtn = (Button)dialog.findViewById(R.id.btn_listpopup_close);
		LinearLayout linear1 = (LinearLayout)dialog.findViewById(R.id.linear_listpopup_1);
		btnCloseBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		//그룹 삭제
		linear4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				onDrawGroupDeletePopup(getString(R.string.group_delete), getString(R.string.group_delete_alert), _strTitle);
			}
		});
*/
		dialog.show();


	}
	
	public void mOnClick(View view)
	{
		Intent intent;
		
		switch(view.getId())
		{
		case R.id.btn_play:
			if(mDataMgrApp.userId.length() == 0)
            {
                intent = new Intent(RankSettingActivity.this, RegisterUserInfoActivity.class);
                startActivity(intent);
            }
			else {
				intent = new Intent(RankSettingActivity.this, PlayMathActivity.class);
				intent.putExtra("gm_menu", 1);
				startActivity(intent);
			}
			break;
		
		}
	}
	
	
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		
		new AsyncTaskUserArea().execute();
		super.onStart();
	}




	private class AsyncTaskUserArea extends NetBaseAsyncTask{
		private String eMsg = "";
		
		String strId = "";
		String strPw = "";
		
		@Override
		protected Boolean doInBackground(String... params) {
			boolean result = true;
			try {
				String url = Constants.URL_RANK_SCORE;
				
				HashMap<String, String> hashParams = new HashMap<String, String>();
				
				String enCodingId = "테스트3";
				
				try {
					//enCodingUrl = URLEncoder.encode(CDefine.id, "UTF-8");//test
					enCodingId = URLEncoder.encode(enCodingId, "EUC-KR");	//test
//					
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
				hashParams.put("id", enCodingId);
				hashParams.put("local", "1");
				hashParams.put("score", "16");
							
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
			
		}
	};
	
	
	public void parseXml(String strXml)
	{
		
	}

}
