package bong.anroid.mathmaster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import bong.anroid.mathmaster.BaseActivity;
import bong.anroid.mathmaster.R;

public class WorkoutLevelActivity extends BaseActivity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worklevel);

		onDrawList();
    }

	public void onDrawList()
	{
		ViewGroup group = ViewGroup.class.cast(findViewById(android.R.id.list));
		LayoutInflater layoutInflater = getLayoutInflater();

		for (int i = 0; i < 10; i++) {
			View listItem = layoutInflater.inflate(R.layout.view_worklevel_listitem, group, false);//(R.layout.template_listitem_info, group, false);
			TextView.class.cast(listItem.findViewById(R.id.textView)).setText("test" + i);
			TextView.class.cast(listItem.findViewById(R.id.textView)).setTag(""+i);

			listItem.setClickable(true);
			ImageView.class.cast(listItem.findViewById(R.id.imgCheck)).setVisibility(View.GONE);

			if(i <= 2) {
				listItem.setBackgroundResource(R.drawable.ripple_workout_pass_item);//.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.rip));
				if(i < 2)
					ImageView.class.cast(listItem.findViewById(R.id.imgCheck)).setVisibility(View.VISIBLE);
			}
			else
				listItem.setBackgroundResource(R.drawable.ripple_workout_disable_item);



			//listItem.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.list_selector_background));
			listItem.setOnClickListener(listClick);
			group.addView(listItem);
//			LinearLayout.class.cast(listItem.findViewById(R.id.linearItemMain)).setOnClickListener(new View.OnClickListener() {
//				public void onClick(View v) {
//					//String path = TextView.class.cast(v.findViewById(R.id.textView1)).getText().toString();
//					String path = TextView.class.cast(v.findViewById(R.id.textViewBtnHidden)).getText().toString();
//					Log.d(TAG, "button click " + path);
//
//				}
//			});
		}
	}

	View.OnClickListener listClick = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			//String path = TextView.class.cast(v.findViewById(R.id.textViewBtnHidden)).getText().toString();
			//	Log.d(TAG, "touch " + path);

			String strIndex = (String)(v.findViewById(R.id.textView)).getTag();
			int nIndex = 0;
			try{
				nIndex = Integer.parseInt(strIndex);
			}catch (Exception e) {
				nIndex = -1;
			}

			Log.d("bongtest", "index " + nIndex);
			if(nIndex >= 0) {
				PlayMathActivity.mWorkoutLevel = nIndex;
				Intent intent = new Intent(WorkoutLevelActivity.this, PlayMathActivity.class);
				intent.putExtra("gm_menu", 0);
				intent.putExtra("workout_level", 0);
				startActivity(intent);
			}
		}
	};

	public void mOnClick(View view)
	{
		Intent intent;
		switch(view.getId())
		{
		case R.id.button1:
			PlayMathActivity.mWorkoutLevel = 0;
			intent = new Intent(WorkoutLevelActivity.this, PlayMathActivity.class);
			intent.putExtra("gm_menu", 0);
			intent.putExtra("workout_level", 0);
    		startActivity(intent);
			break;
		case R.id.button2:
			PlayMathActivity.mWorkoutLevel = 1;
			intent = new Intent(WorkoutLevelActivity.this, PlayMathActivity.class);
			intent.putExtra("gm_menu", 0);
			intent.putExtra("workout_level", 1);
    		startActivity(intent);
			break;
		case R.id.button3:
			PlayMathActivity.mWorkoutLevel = 2;
			intent = new Intent(WorkoutLevelActivity.this, PlayMathActivity.class);
			intent.putExtra("gm_menu", 0);
			intent.putExtra("workout_level", 2);
    		startActivity(intent);
			break;
		case R.id.button4:
			PlayMathActivity.mWorkoutLevel = 3;
			intent = new Intent(WorkoutLevelActivity.this, PlayMathActivity.class);
			intent.putExtra("gm_menu", 0);
			intent.putExtra("workout_level", 3);
    		startActivity(intent);
			break;
		case R.id.button5:
			PlayMathActivity.mWorkoutLevel = 4;
			intent = new Intent(WorkoutLevelActivity.this, PlayMathActivity.class);
			intent.putExtra("gm_menu", 0);
			intent.putExtra("workout_level", 4);
    		startActivity(intent);
			break;
		case R.id.button6:
			PlayMathActivity.mWorkoutLevel = 5;
			intent = new Intent(WorkoutLevelActivity.this, PlayMathActivity.class);
			intent.putExtra("gm_menu", 0);
			intent.putExtra("workout_level", 5);
    		startActivity(intent);
			break;
		case R.id.button7:
			PlayMathActivity.mWorkoutLevel = 6;
			intent = new Intent(WorkoutLevelActivity.this, PlayMathActivity.class);
			intent.putExtra("gm_menu", 0);
			intent.putExtra("workout_level", 6);
    		startActivity(intent);
			break;
		case R.id.button8:
			PlayMathActivity.mWorkoutLevel = 7;
			intent = new Intent(WorkoutLevelActivity.this, PlayMathActivity.class);
			intent.putExtra("gm_menu", 0);
			intent.putExtra("workout_level", 7);
    		startActivity(intent);
			break;
		case R.id.button9:
			PlayMathActivity.mWorkoutLevel = 8;
			intent = new Intent(WorkoutLevelActivity.this, PlayMathActivity.class);
			intent.putExtra("gm_menu", 0);
			intent.putExtra("workout_level", 8);
    		startActivity(intent);
			break;
		case R.id.button10:
			PlayMathActivity.mWorkoutLevel = 9;
			intent = new Intent(WorkoutLevelActivity.this, PlayMathActivity.class);
			intent.putExtra("gm_menu", 0);
			intent.putExtra("workout_level", 9);
    		startActivity(intent);
			break;
		}
	}

}
