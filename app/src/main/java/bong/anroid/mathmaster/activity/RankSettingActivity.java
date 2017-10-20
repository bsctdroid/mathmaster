package bong.anroid.mathmaster.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import bong.anroid.mathmaster.BaseActivity;
import bong.anroid.mathmaster.R;
import bong.anroid.mathmaster.utils.Constants;
import bong.anroid.mathmaster.utils.NetBaseAsyncTask;
import bong.anroid.mathmaster.utils.RankItem;

public class RankSettingActivity extends BaseActivity {
	String TAG = "MATHMASTER";

	TextView tvId;
	TextView tvLocal;

	private Dialog dialog;

	ArrayList<RankItem> aListRankItem = new ArrayList<RankItem>();

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
			parseXml(eMsg);
			onDrawList();
		}
	}

	public void parseXml(String strXml)
	{
		RankItem rankItem;

		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			String sTag;
			parser.setInput(new StringReader(strXml));

			int eventType = parser.getEventType();
			while(eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
					case XmlPullParser.START_DOCUMENT:            // 문서의 시작
						Log.d(TAG, "xmlparser START_DOCUMENT");
						break;

					case XmlPullParser.START_TAG:                 // 태그의 시작
						Log.d(TAG, "xmlparser START_TAG");
						String startTag = parser.getName();
						Log.d(TAG, "xmlparser START_TAG " + startTag);
						break;

					case XmlPullParser.END_TAG:                    //태그의 끝
						Log.d(TAG, "xmlparser END_TAG");
//						String endTag = parser.getName();
//						if(endTag.equals("student")) {
//							arrayList.add(student);`
//						}
						break;
					case XmlPullParser.END_DOCUMENT:        // 문서의 끝
						Log.d(TAG, "xmlparser END_DOCUMENT");
						break;
				}
				eventType = parser.next();
			}

		} catch(Exception ex) {

		}

	}

	public void onDrawList()
	{
		ViewGroup group = ViewGroup.class.cast(findViewById(android.R.id.list));
		LayoutInflater layoutInflater = getLayoutInflater();

		for (int i = 0; i < 30; i++) {
			View listItem = layoutInflater.inflate(R.layout.view_rank_listitem, group, false);//(R.layout.template_listitem_info, group, false);
			TextView.class.cast(listItem.findViewById(R.id.tvRank)).setText("" + i);
			TextView.class.cast(listItem.findViewById(R.id.textViewBtnHidden)).setText(""+i);

			TextView.class.cast(listItem.findViewById(R.id.tvId)).setText("test" + i);
			TextView.class.cast(listItem.findViewById(R.id.tvCountry)).setText("KOREA");
			TextView.class.cast(listItem.findViewById(R.id.tvScore)).setText("10" + i);


			listItem.setClickable(true);
			listItem.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.list_selector_background));
			//listItem.setOnClickListener(listClick);
			group.addView(listItem);
//			LinearLayout.class.cast(listItem.findViewById(R.id.linearBtn1)).setOnClickListener(new View.OnClickListener() {
//				public void onClick(View v) {
//					//String path = TextView.class.cast(v.findViewById(R.id.textView1)).getText().toString();
//					String path = TextView.class.cast(v.findViewById(R.id.textViewBtnHidden)).getText().toString();
//					Log.d(TAG, "button click " + path);
//
//				}
//			});
		}
	}







}
