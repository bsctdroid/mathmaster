package bong.anroid.mathmaster.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

public class SPManager {
	
	private static final String TAG = SPManager.class.getSimpleName();
	private static Context context;
	private static SharedPreferences spre = null;
	
	public static void init(Context ct){
		context = ct; 
		spre = getInstance();
	}
	
	public static SharedPreferences getInstance(){
		if (spre == null){
			return context.getSharedPreferences(Constants.PREF_STORAGE, Context.MODE_PRIVATE);
		}
		return spre;
	}


	/**
	 * 사용자의 ID
	 * @return
	 */
	public static String getUserId(){
		String userId = SPManager.getInstance().getString(Constants.PREF_KEY_USER_ID, "");
		return userId;
	}
	
	public static void setUserId(String userId){
		Editor spreEdit = SPManager.getInstance().edit();
		spreEdit.putString(Constants.PREF_KEY_USER_ID, userId);
		spreEdit.commit();		
	}
	
	/**
	 * 사용자의 위치 정보
	 * @return
	 */
	public static int getUserLocal(){
		int userLocal = SPManager.getInstance().getInt(Constants.PREF_KEY_USER_LOCAL, 0);
		return userLocal;
	}
	
	public static void setUserLocal(int userLocal){
		Editor spreEdit = SPManager.getInstance().edit();
		spreEdit.putInt(Constants.PREF_KEY_USER_LOCAL, userLocal);
		spreEdit.commit();		
	}
	
	/**
	 * 사용자의 연습 모드 레벨
	 * @param mode (1:plus, 2:minus, 3:mutiple 4:division)
	 * @return
	 */
	public static int getWorkLevel(int mode)
	{
		int level = SPManager.getInstance().getInt(Constants.PREF_KEY_WORK_LEVEL + mode, 0);
		return level;
	}
	
	public static void setWorkLevel(int mode, int level){
		Editor spreEdit = SPManager.getInstance().edit();
		spreEdit.putInt(Constants.PREF_KEY_WORK_LEVEL + mode, level);
		spreEdit.commit();		
	}
	
	/**
	 * 사용자 랭킹 점수 
	 * @param score
	 * @return
	 */
	public static int getRankScore()
	{
		int score = SPManager.getInstance().getInt(Constants.PREF_KEY_RANK_SCORE, 0);
		return score;
	}
	
	public static void setRankScore(int score){
		Editor spreEdit = SPManager.getInstance().edit();
		spreEdit.putInt(Constants.PREF_KEY_RANK_SCORE, score);
		spreEdit.commit();		
	}

}
