package bong.anroid.mathmaster;

import android.util.Log;


public class MathCalcHelper {
	
	private static MathCalcHelper instance = null;
	
	//9, 19, 99, 199, 999, 1999, 9999, 19999, 99999
	
	int level = 0;	//0:min, 1:very low, 2:low, 3:medium, 4:high, 5:very high 
	int operationType = 0; //+, -, X, /
	String strResultValue = "";
							// 0  1	   2	3	4	5		6	    7	   8		9		10				
	int levelValueMinNum[]  = {1,  3,  5,  11,  21,  60,   199,  1999,   9999,   99999,  999999};	//???�� ?? ????? ???
	int levelValueMaxNum[]  = {8, 16, 45,  90, 180, 950,  1909,  8999,  89999,  899999, 8999999};	//???�� ?? ????? ???
	int levelResultNum[] 	= {10,20, 50, 109, 199, 999,  2000, 10000, 100000, 1000000,10000000};	//????? ??? 
							// 0   1   2    3    4    5     6    7      8      9        10	
	int levelValueMinNumX[] = {1,  2,  3,   5,  10,  11,   19,   29,    99,   399,     999};	//????, ?????? min
	int levelValueMaxNumX[] = {9,  9, 15,  19,  19,  89,   99,  299,   999,   1999,    9999};	
	int levelResultNumX[]   = {10,80, 99, 109, 199, 999, 2000,10000,100000,1000000,10000000};	//???? ?????? ?????
	
	int mPrevResultValue = 0;	//?????? ????? ???? ??????? ??? ???? 
	int mResultValue = 0;
	
	int mPrevValue = 0;
	int mFirstValue = 0;
	int mSecondValue = 0;
	
	
	public MathCalcHelper()
	{
		//level = _level;
	}

	public static MathCalcHelper getInstance()
	{
		if(instance == null)
		{
			instance = new MathCalcHelper();
		}
		return instance;
	}
	
	
	//?????? ????? ???
	public void setInitData(int _level, int _operationType)
	{
		level = _level;
		operationType = _operationType;
		strResultValue = "";
		mResultValue = 0;
		
		switch(_operationType)
		{
		case 0:
			setAddtionValue();
			break;
		case 1:
			setSubtractionValue();
			break;
		case 2:
			setMultiplicationValue();
			break;
		case 3:
			setDivisionValue();
			break;
		}
		
	}	
	
	/**
	 * ????? ?? ????
	 */
	public void setAddtionValue()
	{
		
		int resultMax = levelResultNum[level];
		int resultMin = 2;
		if(level > 0)
			resultMin = levelResultNum[level-1];
		
		int resultValue = randomNoRepeat(resultMin, resultMax, mPrevResultValue);
		
		int valueMax = levelValueMaxNum[level];
		int valueMin = levelValueMinNum[level];				
		//int firstValue = randomNoRepeat(valueMin, valueMax, mPrevValue);
		int firstValue = randomNoRepeat(valueMin, resultValue-1, mPrevValue);
			
		int secondValue = resultValue - firstValue;
		
		mPrevResultValue = mResultValue = resultValue;  
		mPrevValue = mFirstValue = firstValue;
		mSecondValue = secondValue;  
		
		
		Log.d("MATH", " mResultValue " + mResultValue + " mFirstValue " + mFirstValue + " mSecondValue " + mSecondValue);
		
	}
	
	/**
	 * ?????? ?? ????
	 */
	public void setSubtractionValue()
	{
		int resultMax = levelResultNum[level];
		int resultMin = 2;
		if(level > 0)
			resultMin = levelResultNum[level-1];
		
		int resultValue = randomNoRepeat(resultMin, resultMax, mPrevResultValue);
		
		int valueMax = levelValueMaxNum[level];
		int valueMin = levelValueMinNum[level];				
		//int firstValue = randomNoRepeat(valueMin, valueMax, mPrevValue);
		int firstValue = randomNoRepeat(valueMin, resultValue-1, mPrevValue);
			
		int secondValue = resultValue - firstValue;
		
		//2+4 = 6
		mPrevResultValue = mFirstValue = resultValue;  
		mPrevValue = mResultValue = firstValue;
		mSecondValue = secondValue;  
        //firstValue -
       Log. d("MATH", " mResultValue " + mResultValue + " mFirstValue " + mFirstValue + " mSecondValue " + mSecondValue );
       
        
	}
	
	
	/**
	 * ????? ?? ????
	 */
	public void setMultiplicationValue()
	{
		int valueMax = levelValueMaxNumX[level];
		int valueMin = levelValueMinNumX[level];				
		
		int firstValue = randomNoRepeat(valueMin, valueMax, mPrevResultValue);
		int secondValue = valueMin;
		int resultValue = 0;
		boolean isSuccess = false;
		
		int resultMax = levelResultNumX[level];
		int resultMin = 2;
		if(level > 0)
			resultMin = levelResultNum[level-1];
		
		
		//Log. d("MATH", "--- firstValue " + firstValue + " levelResultNumX[level] " + levelResultNumX[level]);
		while(!isSuccess) 
		{
			secondValue = randomNoRepeat(valueMin, valueMax, mPrevValue);
			//secondValue = random(valueMin, valueMax);
			if(resultMax >= firstValue * secondValue )
			{
				isSuccess = true;				 
			}
			else
			{
				valueMax = secondValue;
				
			}
		}  
		
		resultValue = firstValue * secondValue;
		
		mPrevResultValue = mFirstValue = firstValue;
		mPrevValue = mSecondValue = secondValue;       
        mResultValue = resultValue;
        
        Log. d("MATH", " mResultValue " + mResultValue + " mFirstValue " + mFirstValue + " mSecondValue " + mSecondValue );
	}
	
	/**
	 * ?????? ?? ????
	 */
	public void setDivisionValue()
	{
		int valueMax = levelValueMaxNumX[level];
		int valueMin = levelValueMinNumX[level];				
		
		int firstValue = randomNoRepeat(valueMin, valueMax, mPrevResultValue);
		int secondValue = valueMin;
		int resultValue = 0;
		boolean isSuccess = false;
		
		int resultMax = levelResultNumX[level];
		int resultMin = 2;
		if(level > 0)
			resultMin = levelResultNum[level-1];
		
		
		//Log. d("MATH", "--- firstValue " + firstValue + " levelResultNumX[level] " + levelResultNumX[level]);
		while(!isSuccess) 
		{
			secondValue = randomNoRepeat(valueMin, valueMax, mPrevValue);
			//secondValue = random(valueMin, valueMax);
			if(resultMax >= firstValue * secondValue )
			{
				isSuccess = true;				 
			}
			else
			{
				valueMax = secondValue;
				
			}
		}  
		
		resultValue = firstValue * secondValue;
		
		mPrevResultValue = mFirstValue = resultValue;  
		mPrevValue = mResultValue = firstValue;
		mSecondValue = secondValue;  
        
        Log. d("MATH", " mResultValue " + mResultValue + " mFirstValue " + mFirstValue + " mSecondValue " + mSecondValue );
	}
	
	
	public int getFirstValue()
	{
		return mFirstValue;
	}
	
	public int getSecondValue()
	{
		return mSecondValue;
	}
	
	public int getResultValue()
	{
		return mResultValue;
	}
	
	
	
	
	//???? ????? ?????? ??�S ??????? ??????? ????
	public int random(int _min, int _max)
	{
		int randMinute = 1;
		double temp = Math.random()*(_max-_min);
		randMinute = (int)temp+_min;
		
		  
		return randMinute;
	}
	
	public int randomNoRepeat(int _min, int _max, int repeatValue)
	{
		int randMinute = 1;
		boolean isRepeat = true;
		int failCnt = 0; //3?? ??? ??????? ??? ????.
		
		while(isRepeat)
		{
			double temp = Math.random()*(_max-_min);
			randMinute = (int)temp+_min;
			if(repeatValue != randMinute)
				isRepeat = false;
			failCnt++;
			if(failCnt > 5)
				isRepeat = false;
		}
		
		return randMinute;
		
	}
	
}
