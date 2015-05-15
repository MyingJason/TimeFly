package init;

import java.util.ArrayList;

import com.example.fly.R;
import com.parse.Parse;

import scoreFactory.ScoreFactory;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class WelcomeScreen extends Activity {

	boolean hasNet = true;
	ArrayList<String> name;
	ArrayList<Integer> score;
	private boolean noConnetion;
	@Override
	public void onBackPressed(){
	   super.onBackPressed();
	   System.exit(0);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
//		hasNet = this.getIntent().getExtras().getBoolean("hasNet");
		
		setContentView(R.layout.welcome_screen);
		final TextView textView3 = (TextView)findViewById(R.id.textView3);
		textView3.setVisibility(View.INVISIBLE);//findViewById(R.id.horizontal_determinate);
		try{
			//Parse.enableLocalDatastore(this);
			Parse.initialize(this, "qkObiKYtLZdNKAPt7hPf6owzJYKBS2990gKIOwa5", "jgcdkOuDQynjFIGx4WPFAUHV3u5HkN8vaMx6pIco");
			ScoreFactory sf = new ScoreFactory();
			name = sf.getNameList();
			score= sf.getScoreList();
		}catch (Exception e) {
			
			textView3.setVisibility(View.VISIBLE);
			hasNet = false;
		}	
		
//		Button start = (Button)findViewById(R.id.button1);
//		Button help = (Button)findViewById(R.id.button2);
//		Button exit = (Button)findViewById(R.id.button3);
//		Button score = (Button)findViewById(R.id.button4);
	}

	public void sendMessage(View view) 
	{
	    Intent intent = new Intent(WelcomeScreen.this, Difficulty.class);
//	    if(hasNet){
	    intent.putExtra("hasNet", hasNet);//("score", score);
//	    }
	    startActivity(intent);

	}
	
	public void goHelp(View view){
	    Intent intent = new Intent(WelcomeScreen.this, HelpPage.class);
	    startActivity(intent);
	}

	public void finish(View view){
	    WelcomeScreen.this.finish();
	}
	
	public void scoreBoard(View view){
		Intent intent = new Intent(WelcomeScreen.this, ScoreBoard.class);
		intent.putExtra("hasNet", hasNet);
	    if(!noConnetion){
	    	intent.putIntegerArrayListExtra("score", score);
	    	intent.putStringArrayListExtra("name", name);
	    }

	    startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
