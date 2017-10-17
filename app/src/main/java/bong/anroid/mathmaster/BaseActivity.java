package bong.anroid.mathmaster;





import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {

	public MainApplication mDataMgrApp = null; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mDataMgrApp = (MainApplication) getApplicationContext();
	}
	

}
