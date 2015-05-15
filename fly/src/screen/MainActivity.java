package screen;


import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.scene.menu.item.TextMenuItem;
import org.anddev.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.MathUtils;


import scoreFactory.ScoreFactory;
import scoreFactory.ShowResult;
import tongue.TongueController;
import fly.FlyController;
import GameInfo.GameInfo;
import GameInfo.Timer;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;


public class MainActivity extends BaseGameActivity implements IOnSceneTouchListener, GameInfo, IOnMenuItemClickListener{


	private static int CAMERA_WIDTH;  
    private static int CAMERA_HEIGHT;  
    private Camera andCamera;  
	private Scene scene; 
	private MenuScene menuScene;
	
	private Texture timeBarBackgroundTexture;
	private Texture FontTexture;    
	private Texture tongueTexture;
	private Texture timeBarTexture;  
	private Texture flyTexture;
	private Texture FontTimeTexture;
	private Texture FontScoreTexture;
	private Texture FontRankTexture;
	private Texture menuFontTexture;
	
	private TextureRegion backgroudRegion;
	private TextureRegion timeBarBackgroundTextureRegion;	
	private TextureRegion timeBarTextureRegion;
	private TextureRegion offfrogTextureRegion;
	private TextureRegion onfrogTextureRegion;
	
	private TiledTextureRegion flytiledTextureRegion[] = new TiledTextureRegion[3];
	private TiledTextureRegion tonguetiledTextureRegion;
	
	private Sprite spriteoff;
	private Sprite spriteon;
	private Sprite timeBar;
	private Sprite timeBarBackground;
	
	private ChangeableText mCurrentRankText;
	private ChangeableText mCurrentScoreText;
	private ChangeableText mTimeText;

	private Font mTimeFont, mScoreFont;
	private Font menuFont;
	private Font mRankFont;
	
	private ScoreFactory sf = null;
	private TongueController tonguecontroller;
	private FlyController flycontroller;
	private ScreenMonitor monitor;

	protected int currentrankscore;
	protected boolean mGameRunning = true;
	private boolean hasNet;
	private int currentTime;
	protected int score = 0;
	protected int newscore = 0;
	protected int rank = 11;
	private int level;
	protected int scorechange = 0;
	

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		level = this.getIntent().getExtras().getInt("level");
		hasNet = this.getIntent().getExtras().getBoolean("hasNet");
		

	}

	public Engine onLoadEngine() {
        
    	DisplayMetrics metrics=new DisplayMetrics();
    	getWindowManager().getDefaultDisplay().getMetrics(metrics);
    	
    	CAMERA_WIDTH = metrics.widthPixels;
    	CAMERA_HEIGHT = metrics.heightPixels;

        this.andCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT),
                this.andCamera));
        
    }
    public void onLoadResources() {
    	
    	//Time Font
    	this.FontTimeTexture = new Texture(256, 256, TextureOptions.DEFAULT);  
        this.mTimeFont = new Font(this.FontTimeTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 50, true, Color.BLACK);  
        this.mEngine.getTextureManager().loadTexture(this.FontTimeTexture);  
        this.mEngine.getFontManager().loadFont(this.mTimeFont);

        //Score Font
        this.FontScoreTexture = new Texture(256, 256, TextureOptions.DEFAULT);  
        this.mScoreFont = new Font(this.FontScoreTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 50, true, Color.MAGENTA);  
        this.mEngine.getTextureManager().loadTexture(this.FontScoreTexture);  
        this.mEngine.getFontManager().loadFont(this.mScoreFont);
        
        //Rank Font
        this.FontRankTexture = new Texture(256, 256, TextureOptions.DEFAULT);  
        this.mRankFont = new Font(this.FontRankTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 50, true, Color.RED);  
        this.mEngine.getTextureManager().loadTexture(this.FontRankTexture);  
        this.mEngine.getFontManager().loadFont(this.mRankFont);
        
        //Menu Font and View
  		this.menuFontTexture = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
  		this.menuFont = FontFactory.createFromAsset(this.menuFontTexture, this, "Plok.ttf", 48, true, Color.WHITE);
  		this.mEngine.getTextureManager().loadTexture(this.menuFontTexture);
  		this.mEngine.getFontManager().loadFont(this.menuFont);
        
    	//Close-mouth-frog
        Texture offfrog = new Texture(512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.offfrogTextureRegion = TextureRegionFactory.createFromAsset(offfrog,this, "offnew.png", 0, 0);
        this.getEngine().getTextureManager().loadTextures(offfrog);
        
        
        //Open-mouth-frog
        Texture onfrog = new Texture(512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.onfrogTextureRegion = TextureRegionFactory.createFromAsset(onfrog,this, "onnew.png", 0, 0);
        this.getEngine().getTextureManager().loadTextures(onfrog);
           
        //Fly
        for(int i = 0; i< 3; i++)
        		flytiledTextureRegion[i] = this.setupview(128, 256, "fly"+(i+1)+".png");

		//Tongue
		this.tongueTexture = new Texture(64, 512, TextureOptions.DEFAULT);
		this.tonguetiledTextureRegion = TextureRegionFactory.createTiledFromAsset(tongueTexture, MainActivity.this,"tongue0.png" , 0,0,1,1);
		this.getEngine().getTextureManager().loadTextures(tongueTexture);
		
        //Time Bar
		timeBarTexture = new Texture(512, 64, TextureOptions.DEFAULT);
		this.timeBarTextureRegion = TextureRegionFactory.createFromAsset(this.timeBarTexture, this, "timebar.png", 0, 0);
		this.timeBarBackgroundTexture = new Texture(512, 64, TextureOptions.DEFAULT);
		this.timeBarBackgroundTextureRegion = TextureRegionFactory.createFromAsset(this.timeBarBackgroundTexture, this, "timebarbackground.png", 0, 0);
		this.mEngine.getTextureManager().loadTextures(this.timeBarTexture, this.timeBarBackgroundTexture);
		
		//Screen Background
		Texture backgroud = new Texture(2048, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.backgroudRegion = TextureRegionFactory.createFromAsset(backgroud, this, "bknew.png", 0, 0);
        this.getEngine().getTextureManager().loadTextures(backgroud);
		
 
	}

	@Override
	public Scene onLoadScene() {
		
		getEngine().registerUpdateHandler(new FPSLogger());  
 
		menuScene = this.createMenuScene();
        scene = new Scene(1);
        
        //Background
        Sprite spritebackground = new Sprite(0, 0, this.backgroudRegion);
        scene.setBackground(new SpriteBackground(spritebackground));
        
        //Initial the Screen
        for(int i =0; i < LAYERS; i++)
        	scene.attachChild(new Entity());

        //Frogs
        spriteoff = new Sprite(0, 720 - this.offfrogTextureRegion.getHeight(), this.offfrogTextureRegion); 
        spriteon  = new Sprite(0, 720 - this.onfrogTextureRegion.getHeight(), this.onfrogTextureRegion);    
        scene.getChild(LAYER_FROG).attachChild(spriteon);
        scene.getChild(LAYER_FROG).attachChild(spriteoff); 
        spriteon.setVisible(false);        
        
        //Time Bar
        this.timeBar = new Sprite(0, 0, this.timeBarTextureRegion);
		this.timeBarBackground = new Sprite(0, 0, this.timeBarBackgroundTextureRegion);
		this.timeBar.setPosition(350,20);
		this.timeBarBackground.setPosition(350,20);
		scene.getChild(LAYER_STATE).attachChild(this.timeBarBackground);
		scene.getChild(LAYER_STATE).attachChild(this.timeBar);
        
        // Update Time
        this.currentTime = TOTAL_TIME;
		this.mTimeText = new ChangeableText(100, 20, this.mTimeFont, ":", "XX:XX:XX".length());
        scene.getChild(LAYER_STATE).attachChild(mTimeText);
        scene.registerUpdateHandler(new TimerHandler(1/100f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {				
				if (currentTime >= 0 && mGameRunning == true ){
					if(scorechange > 0){
						switch(scorechange){
						case FLY_NORMAL_SCORE:
							currentTime +=100;
							break;
						case FLY_SUPER_SCORE:
							currentTime +=100;
							break;
						}
						scorechange = 0;
					}
					mTimeText.setText(Timer.setTimeFormat(currentTime));
					currentTime -= 1f;
					timeBar.setWidth(timeBarBackground.getWidth()* currentTime / TOTAL_TIME);
				}
				else
				{
					//Game Ends
					mTimeText.setText("00:00");
					mGameRunning = false;
					Intent intent = new Intent(MainActivity.this, ShowResult.class);

					intent.putExtra("score", score);
					intent.putExtra("level", level);
					
				    startActivity(intent);
				    MainActivity.this.finish();
				}
			}
		}));
        
        // Update Score and Rank
        if(hasNet)
        {
    		sf = new ScoreFactory();
        	currentrankscore = sf.nextScore(11);
        }
        this.mCurrentScoreText = new ChangeableText(CAMERA_WIDTH*0.7f, 30, this.mScoreFont, this.score+"", "XXXX".length());
		scene.getChild(LAYER_STATE).attachChild(this.mCurrentScoreText);
		
		this.mCurrentRankText = new ChangeableText(CAMERA_WIDTH*0.7f, 100, this.mRankFont, this.rank+"", "Rank:XX NextGoal: XXXX".length());
		scene.getChild(LAYER_STATE).attachChild(this.mCurrentRankText);
		
		scene.registerUpdateHandler(new TimerHandler(1 / 20.0f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {				
				mCurrentScoreText.setText(score+"");
				if(hasNet){
					int currentrank = sf.getRank(score);
					if(currentrank != rank && rank >1)
					{
						rank = currentrank;
						currentrankscore = sf.nextScore(currentrank);
					}
					if(rank == 1)
						currentrankscore = score;
					
					mCurrentRankText.setText("Rank: "+rank+"\n"+"NextGoal: " + currentrankscore );
				}
				else
					mCurrentRankText.setText("Rank: No\nNextGoal: No" );

			}
		}));
		//Control Monitor
        monitor = new ScreenMonitor();
        
        //Controll Tongue
        tonguecontroller = new TongueController(CAMERA_WIDTH, CAMERA_HEIGHT, this.tonguetiledTextureRegion);
		scene.setOnSceneTouchListener(this);
        
		//Control Fly
        flycontroller = new FlyController(CAMERA_WIDTH, CAMERA_HEIGHT, this.flytiledTextureRegion);
		flycontroller.lauchfly(scene);
		//Update All status- scene, score, rank
        scene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() { }
			@Override
			public void onUpdate(final float pSecondsElapsed) {
				if(tonguecontroller.tongue != null){
					delay(10);
					newscore = monitor.Scoring(flycontroller.fList, tonguecontroller, scene, score);
					if(tonguecontroller.checktongue(scene)||newscore!=score){

						if(newscore!=score){
							scorechange = newscore - score;
							switch(scorechange){
								case FLY_NORMAL_SCORE:
									changetongue(1);
									break;
								case FLY_SUPER_SCORE:
									changetongue(2);
									break;
								case FLY_BAD_SCORE:
									changetongue(3);
									break;
							}
						}
						score = newscore;
						spriteon.setVisible(false);
						spriteoff.setVisible(true);
					}
				}
				flycontroller.checkfly(scene);
			}
		});
        return scene;
	}

	
	@Override
	public void onLoadComplete() {}

	
	//Listen to touch
	@Override
	public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
				
//		this.runOnUpdateThread(new Runnable(){
//			@Override
//			public void run() {				
//		});
		
		int x = (int) pSceneTouchEvent.getX();
		int y = (int) pSceneTouchEvent.getY();		
		float angle = MathUtils.radToDeg((float)Math.atan2(x-150, CAMERA_HEIGHT-y-145));

		changetongue(0);
		Log.e("touch","HERHERHE");
		if(Math.abs(x-tonguecontroller.getlasttouchx())>10 && Math.abs(y-tonguecontroller.getlasttouchy())>10)
		{		
			spriteon.setVisible(true);
			spriteoff.setVisible(false);
			tonguecontroller.lauchtongue(this.level, x,y,angle,pScene);
		}
		return true;
	}
	
	public TiledTextureRegion setupview(int width, int height, String pngname){
		
		Texture Texture = new Texture(width, height, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		TiledTextureRegion tiledTextureRegion = TextureRegionFactory.createTiledFromAsset(Texture, this, pngname , 0, 128, 1,2);
		this.getEngine().getTextureManager().loadTextures(Texture);
		
		return tiledTextureRegion;
	}
	
	public void changetongue(int type)
	{
        this.tongueTexture.clearTextureSources();
		this.tonguetiledTextureRegion = TextureRegionFactory.createTiledFromAsset(this.tongueTexture, this,"tongue"+type+".png", 0, 0, 1,1);
	}
	
	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
		if(pKeyCode == KeyEvent.KEYCODE_MENU && pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			if(this.scene.hasChildScene()) {
				/* Remove the menu and reset it. */
				this.menuScene.back();
			} else {
				/* Attach the menu. */
				this.scene.setChildScene(this.menuScene, false, true, true);
			}
			return true;
		} else {
			return super.onKeyDown(pKeyCode, pEvent);
		}
	}

	@Override
	public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {
		switch(pMenuItem.getID()) {
			case MENU_RESET:
				/* Restart the animation. */
				this.currentTime = TOTAL_TIME;
				this.score = 0;
				this.rank = 11;				
//				this.scene.reset();
				/* Remove the menu and reset it. */
				
//				this.tonguecontroller.reset();
				for(int i = 2; i < 4; i++)
					this.scene.getChild(i).reset();
				
				this.scene.clearChildScene();
//				this.flycontroller.fList.clear();
				
				this.menuScene.reset();
				return true;
			case MENU_QUIT:
				/* End Activity. */
				this.finish();
				return true;
			default:
				return false;
		}
	}
	//Manage menu Button
	private MenuScene createMenuScene() {
		final MenuScene menuScene = new MenuScene(this.andCamera);
		final IMenuItem resetMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_RESET, this.menuFont, "RESET"), 1.0f,0.0f,0.0f, 0.0f,0.0f,0.0f);
		resetMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		menuScene.addMenuItem(resetMenuItem);

		final IMenuItem quitMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_QUIT, this.menuFont, "QUIT"), 1.0f,0.0f,0.0f, 0.0f,0.0f,0.0f);
		quitMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		menuScene.addMenuItem(quitMenuItem);
		menuScene.buildAnimations();
		menuScene.setBackgroundEnabled(false);
		menuScene.setOnMenuItemClickListener(this);
		return menuScene;
	}
	public void delay(int t){
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}