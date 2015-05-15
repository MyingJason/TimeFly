package GameInfo;

public interface GameInfo {
	
	
	final static int LAYERS = 3;
	final static int LAYER_TONGUE = 2;
	final static int LAYER_FROG = 3;
	final static int LAYER_STATE = 1;
	
	final static int FLY_MAX =5;

	
	final static int TOTAL_TIME = 1800;
	
	final static int FLY_NORMAL_SCORE = 10;
	final static int FLY_SUPER_SCORE = 50;
	final static int FLY_BAD_SCORE = -30;
	
	final static int FLY_NORMAL =  1;
	final static int FLY_SUPER = 2;
	final static int FLY_BAD = 3;
	
	final static int TONGUEBACK_NORMAL = 1;
	final static int TONGUEBACK_SUPER = 2;
	final static int TONGUEBACK_BAD = 3;	
	
	final static float CATCHFLYADDTIME = 2f;
	
	static final int MENU_RESET = 0;
	static final int MENU_QUIT = MENU_RESET + 1;
	
	
}
