package GameInfo;

import java.text.DecimalFormat;

public class Timer implements GameInfo {
	
	public static String setTimeFormat(float time)
	{
		DecimalFormat df = new DecimalFormat("00"); 
		String text = "" + df.format((int)time/60) + 
				":" + df.format((int)time%60); 
		return text;
	}

}
