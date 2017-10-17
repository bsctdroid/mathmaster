package bong.anroid.mathmaster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import bong.anroid.mathmaster.BaseActivity;
import bong.anroid.mathmaster.R;

public class WorkoutModeActivity extends BaseActivity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_mode);
    }
	
	public void mOnClick(View view)
	{
		Intent intent;
		
		switch(view.getId())
    	{
    	case R.id.button_addtion:
    		PlayMathActivity.mWorkoutType = 0;
    		intent = new Intent(WorkoutModeActivity.this, WorkoutLevelActivity.class);
    		startActivity(intent);
    		break;
    	case R.id.button_subtraction:
    		PlayMathActivity.mWorkoutType = 1;
    		intent = new Intent(WorkoutModeActivity.this, WorkoutLevelActivity.class);
    		startActivity(intent);
    		break;
    	case R.id.button_multiplication:
    		PlayMathActivity.mWorkoutType = 2;
    		intent = new Intent(WorkoutModeActivity.this, WorkoutLevelActivity.class);
    		startActivity(intent);
    		break;
    	case R.id.button_division:
    		PlayMathActivity.mWorkoutType = 3;
    		intent = new Intent(WorkoutModeActivity.this, WorkoutLevelActivity.class);
    		startActivity(intent);
    		break;
    	case R.id.button_mix:
    		PlayMathActivity.mWorkoutType = 4;
    		intent = new Intent(WorkoutModeActivity.this, WorkoutLevelActivity.class);
    		startActivity(intent);
    		break;
    	}
	}
}
