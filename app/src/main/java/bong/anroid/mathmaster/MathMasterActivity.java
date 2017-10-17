package bong.anroid.mathmaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import bong.anroid.mathmaster.activity.RankSettingActivity;
import bong.anroid.mathmaster.activity.WorkoutModeActivity;
import bong.anroid.mathmaster.utils.SPManager;


public class MathMasterActivity extends BaseActivity {

	MathCalcHelper mathCcHelper;// = new MathCalcFunc(0); 
	int level = 0;
	int type = 0;
	
	TextView tvView;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_master);
        tvView = (TextView)findViewById(R.id.textView1);
        tvView.setText("level : " + level  + " type " + type);
        mathCcHelper.getInstance().setInitData(0, 1);

		SPManager.init(MathMasterActivity.this);
		initData();
    }

    public void initData()
	{
		mDataMgrApp.userId = SPManager.getUserId();
		mDataMgrApp.userLocal = SPManager.getUserLocal();

		for(int i=0; i<5; i++)
			mDataMgrApp.arWorkoutLevel[i] = SPManager.getWorkLevel(i);

		mDataMgrApp.maxRankScore = SPManager.getRankScore();

	}

    public void mOnClick(View view)
    { 
    	Intent intent;
    	
    	switch(view.getId())
    	{
    	case R.id.btn_workout:
    		intent = new Intent(MathMasterActivity.this, WorkoutModeActivity.class);
    		startActivity(intent);
    		
//    		for(int i=0; i<10; i++)
//    		{
//    			mathCcHelper.getInstance().setInitData(level, type);
//    		}
    		
    		
    		break;
    	case R.id.btn_result:
    		intent = new Intent(MathMasterActivity.this, RankSettingActivity.class);
    		startActivity(intent);
    		
//    		intent = new Intent(MathMasterActivity.this, PlayMathActivity.class);
//    		startActivity(intent);
    		break;
    		
    	case R.id.btnLevel:
    		level++;
    		if(level > 9)
    			level = 0;
    		tvView.setText("level : " + level  + " type " + type);
    		break;
    	case R.id.btnType:
    		type++;
    		if(type > 3)
    			type = 0;
    		tvView.setText("level : " + level  + " type " + type);
    		break;
    		
    		
    	}
    	
    	
    	
    }

    
}
