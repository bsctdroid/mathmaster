package bong.anroid.mathmaster;


import android.app.Application;

public class MainApplication  extends Application {
	private static final String TAG = MainApplication.class.getSimpleName();
	

	public String userId = "";
	public String userLocal = "";

	public int[] arWorkoutLevel = new int[5];
	public int maxRankScore = 0;



	@Override
	public void onCreate() {

        super.onCreate();
	}

}
