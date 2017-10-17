package bong.anroid.mathmaster.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import bong.anroid.mathmaster.BaseActivity;
import bong.anroid.mathmaster.MathCalcHelper;
import bong.anroid.mathmaster.R;
import bong.anroid.mathmaster.utils.SPManager;

@SuppressLint("NewApi")
public class PlayMathActivity extends BaseActivity {

	ProgressBar progressBarTimer;

	TextView tvCurrentQuiz;
	TextView tvAprvQuiz;

	TextView tvScore;
	TextView tvResultValue;

	public static int GM_MENU_WORKOUT = 0;
	public static int GM_MENU_RANKING = 1;


	public int gmMenu = 0;	//0:WorkOut 1:Ranking
	public int gmState = 0;
	public int gmLevel = 0;
	public int gmType = 0;
	public int gmTimer = 0;

	final int GM_READY 	= 0;
	final int GM_PLAY 	= 1;
	final int GM_PAUSE 	= 2;
	final int GM_OVER 	= 4;

	int mScore = 0;
	int mTimer = 150;
	int mCombo = 0;
	//int mQuestionState = 0; //문제가 나온상태

	public static int mWorkoutLevel = 0;
	public static int mWorkoutType = 0;		//0(plus), 1(minus), 2(gob), 3(devide), 4(mix)

	//LEVEL
	public static int WORKOUT_MAX_SCORE = 12;	//workout 처음 시작 스코어 (4로 나눠지는 수여야 함)
	public static int WORKOUT_MIN_TIME = 300;	//workout level 0일때 time
	public static int WORKOUT_TIME_GAP = 30;	//workout level 별 올라가는 time
	public static int WORKOUT_COLLECT_TIME = 5;	//workout 맞췄을때 증가하는 시간 수

	public static int RANK_TIMER = 600;			//rank timeㄱ
	public static int RANK_LEVEL_SIZE = 12;		//rank size별 문제 수
	public static int RANK_COLLECT_TIME = 8;	//workout 맞췄을때 증가하는 시간 수


	String strInput = "";
	//String strInputResult = "";

	MathCalcHelper mathCHelper;// = new MathCalcFunc(0);
	ArrayList<CalData> arrCalData = new ArrayList<CalData>();
	//CalData[] arrCalDatas= new CalData[2];
	int correctResult = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_playmath);

		SPManager.init(this);
		progressBarTimer = (ProgressBar)findViewById(R.id.progressTime);

		tvCurrentQuiz = (TextView)findViewById(R.id.tv_quiz);
		tvAprvQuiz = (TextView)findViewById(R.id.tv_quiz_aprv);

		tvScore = (TextView)findViewById(R.id.tv_score);
		tvResultValue = (TextView)findViewById(R.id.tv_result);

		gmMenu = getIntent().getIntExtra("gm_menu", 0);
		if(gmMenu == GM_MENU_WORKOUT)
		{
			mScore = WORKOUT_MAX_SCORE;
			gmLevel = mWorkoutLevel;
			gmType = mWorkoutType;
			mTimer = WORKOUT_MIN_TIME + (gmLevel*WORKOUT_TIME_GAP);
		}
		else
		{
			mScore = 0;
			mTimer = RANK_TIMER;
		}

		progressBarTimer.setMax(mTimer);
		progressBarTimer.setProgress(mTimer);

		//처음 2개 데이터 셋팅
		setCalData();
		setCalData();

		setTimer();
	}

	/**
	 * 새로운 데이터 입력
	 */
	public void setCalData()
	{
		CalData calData = new CalData();

		int level = gmLevel;
		int type = gmType;

		if(gmMenu == GM_MENU_WORKOUT)
		{
			level = gmLevel;
			type = gmType;

			if(type == 4)
			{
				int divide = WORKOUT_MAX_SCORE/4;
				type = (WORKOUT_MAX_SCORE - mScore + 1)/divide;
				if(type > 3)
					type = 3;
			}

			Log.d("MATH", "1 level " + level + " type " + type);
		}
		else
		{
			level = (mScore / RANK_LEVEL_SIZE);
			type = (mScore % RANK_LEVEL_SIZE);

			Log.d("MATH", "2 level " + level + " type " + type);

			type = type / (RANK_LEVEL_SIZE/4);
		}

		calData.setValue(level, type);
		arrCalData.add(calData);

		if(arrCalData.size() > 2)
			arrCalData.remove(0);

		if(arrCalData.size() > 0)
			tvCurrentQuiz.setText(""+arrCalData.get(0).getFirstValue() + arrCalData.get(0).getOperateKind() + arrCalData.get(0).getSecondValue());
		if(arrCalData.size() > 1)
			tvAprvQuiz.setText(""+arrCalData.get(1).getFirstValue() + arrCalData.get(1).getOperateKind() + arrCalData.get(1).getSecondValue());

		strInput = "";
		//tvResultValue.setText(strInput);

		if(gmMenu == GM_MENU_WORKOUT)
		{
			tvScore.setText("" + mScore + "/" + WORKOUT_MAX_SCORE);
		}
		else
		{
			tvScore.setText("" + mScore);
		}

	}


	public void mOnClick(View view)
	{
		if(strInput.equals("0"))
		{
			strInput = "";
		}
		switch(view.getId())
		{
			case R.id.btn_num1:
				strInput += "1";
				break;
			case R.id.btn_num2:
				strInput += "2";
				break;
			case R.id.btn_num3:
				strInput += "3";
				break;
			case R.id.btn_num4:
				strInput += "4";
				break;
			case R.id.btn_num5:
				strInput += "5";
				break;
			case R.id.btn_num6:
				strInput += "6";
				break;
			case R.id.btn_num7:
				strInput += "7";
				break;
			case R.id.btn_num8:
				strInput += "8";
				break;
			case R.id.btn_num9:
				strInput += "9";
				break;
			case R.id.btn_num0:
				if(strInput.length() > 0 )
					strInput += "0";
				break;
			case R.id.btn_num_clear:
				if(strInput.length() > 0 )
				{
					strInput = strInput.substring(0, strInput.length()-1);
				}
				else
				{
					strInput = "";
				}
				break;
			case R.id.btn_stop:
				finish();
				break;
		}

		if(strInput.length() > 0)
		{
			mHandler.removeMessages(100);
			//strInputResult = "";

			tvResultValue.setTextColor(Color.WHITE);
			tvResultValue.setText(strInput);
			correctResult = getCompareResultValue(strInput);


			if(correctResult == 1)
			{
				//strInputResult = strInput;
				tvResultValue.setText(strInput);
				strInput = "";
				//mHandlerPass.sendEmptyMessageDelayed(100, 100);
				if(gmMenu == GM_MENU_WORKOUT)
				{
					mTimer += WORKOUT_COLLECT_TIME;
					mScore -= 1;
					if(mScore <= 0)
						gmState = 1;
				}
				else
				{
					mTimer += RANK_COLLECT_TIME;
					mScore += 1;
				}


				setCalData();
				Toast.makeText(PlayMathActivity.this, "맞췄음", Toast.LENGTH_SHORT).show();
				mHandler.sendEmptyMessageDelayed(100, 1000);


			}
			else if(correctResult == 2)
			{
				//strInputResult = strInput;
				tvResultValue.setText(strInput);
				strInput = "";
				//mTimer -= 5;

				setCalData();
				Toast.makeText(PlayMathActivity.this, "틀렸음", Toast.LENGTH_SHORT).show();
				mHandler.sendEmptyMessageDelayed(100, 1000);

			}
		}
		else
		{
			tvResultValue.setText("0");
		}
	}

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			tvResultValue.setText("0");
			super.handleMessage(msg);
		}

	};

	/**
	 * 결과값을 맞춰본다.
	 * @param _strInput
	 * @return 0:아직 판단할 수 없음 1:맞췄음 2:틀렸음
	 */
	public int getCompareResultValue(String _strInput)
	{
		int mPass = 0;
		int mInputValue = 0;
		String strResult = Integer.toString(arrCalData.get(0).getResultValue());

		if(_strInput.length() >= strResult.length())
		{
			if(_strInput.equals(strResult))
			{
				mPass = 1;
			}
			else
			{
				mPass = 2;
			}
		}
		return mPass;
	}

	//랜덤 함수로 최소값과 최대값 사이에서 램덤하게 구함
	public int random(int _min, int _max)
	{
		int randMinute = 1;
		double temp = Math.random()*(_max-_min);
		randMinute = (int)temp+_min;

		return randMinute;

	}

	Handler mHandlerTimer = new Handler() {
		public void handleMessage(Message msg) {
			//6dismissProgressDialog();
			if(gmMenu == GM_MENU_WORKOUT)
			{
				if(mTimer <= 0)
					gmState = 2;
			}
			else
			{
				if(mTimer <= 0)
					gmState = 3;
			}
			setTimer();

		}
	};


	public Drawable getDrawableCustom(int drawableId)
	{
		Drawable drawable = null;
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			//buttons[i].setBackground(getDrawable(tilesList.get(i).getId()));
			drawable = getResources().getDrawable(drawableId);
		} else {
			drawable = getDrawable(drawableId);
		}
		return drawable;
	}

	public void setTimer()
	{
		if(gmState == 0)
		{
			mTimer--;
			Drawable drawable = getDrawableCustom(R.drawable.progressbar_progressbar1);
			progressBarTimer.setProgressDrawable(drawable);
			progressBarTimer.setProgress(mTimer);
			if(mTimer > 600)
			{
				Drawable draw1 = getDrawableCustom(R.drawable.progressbar_progressbar2);
				progressBarTimer.setProgressDrawable(draw1);
				progressBarTimer.setProgress(mTimer-300);
			}

			if(mTimer >= 0)
			{
				mHandlerTimer.sendEmptyMessageDelayed(100, 50);
			}
			else
			{
				//			gmState = GM_OVER;
				//			baseDialog();
			}
		}
		else if(gmState == 1)
		{
			//progressBarTimer.setProgress(mTimer);
			tvCurrentQuiz.setText("Success");
		}
		else if(gmState == 2)
		{
			//progressBarTimer.setProgress(mTimer);

			tvCurrentQuiz.setText("Fail");
		}
		else if(gmState == 3)
		{

			SPManager.setRankScore(mScore);
			tvCurrentQuiz.setText("GameOver");
		}
	}


	class CalData
	{
		int level = 0;	//1:매우낮음, 2:낮음, 3:보통, 4:높음, 5:매우높음
		int firstValue = 0;
		int secondValue = 0;
		int operateKind = 0;
		int resultValue = 0;

		String strOperateKind = "+";

		public CalData() {
			super();
			//setValue();
		}

		public void setValue(int level, int opreation)
		{
			this.operateKind = opreation;
			this.level = level;

			mathCHelper.getInstance().setInitData(level, operateKind);

			switch(operateKind)
			{
				case 0:	//add
					strOperateKind = "+";
					break;
				case 1://subtract
					strOperateKind = "－";
					break;
				case 2://multiple
					strOperateKind = "×";
					break;
				case 3://devide
					strOperateKind = "÷";
					break;
			}

			firstValue = mathCHelper.getInstance().getFirstValue();
			secondValue = mathCHelper.getInstance().getSecondValue();
			resultValue = mathCHelper.getInstance().getResultValue();

		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public int getFirstValue() {
			return firstValue;
		}

		public void setFirstValue(int firstValue) {
			this.firstValue = firstValue;
		}

		public int getSecondValue() {
			return secondValue;
		}

		public void setSecondValue(int sencdValue) {
			this.secondValue = sencdValue;
		}

		public String getOperateKind() {
			String operate = "";
			switch(operateKind)
			{
				case 0:
					operate = "+";
					break;
				case 1:
					operate = "-";
					break;
				case 2:
					operate = "*";
					break;
				case 3:
					operate = "/";
					break;
			}
			return operate;
		}

		public void setOperateKind(int operateKind) {
			this.operateKind = operateKind;
		}

		public int getResultValue() {
			return resultValue;
		}

		public void setResultValue(int resultValue) {
			this.resultValue = resultValue;
		}

	}


}
