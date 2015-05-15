package scoreFactory;


import init.WelcomeScreen;

import com.example.fly.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ShowResult extends Activity {

	private Button cancel;
	private Button upload;
	private TextView tv;
	private TextView notice;
	private EditText edit;
	private String name = "default";
	private int score;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_result);
		score=this.getIntent().getExtras().getInt("score");
		/*getscore from param pass*/
		cancel =(Button)findViewById(R.id.cancel);
		upload = (Button)findViewById(R.id.upload);
		
		this.edit = (EditText)findViewById(R.id.editText1);
		tv = (TextView)findViewById(R.id.resultInfo);
		String res = "Score: "+Integer.toString(score);
		tv.setText(res);
		notice = (TextView)findViewById(R.id.notice);
		notice.setVisibility(View.INVISIBLE);
		if(score<10){
			upload.setVisibility(View.GONE);
		}
	}
	
	public void retry(View view){
	    Intent intent = new Intent(ShowResult.this, WelcomeScreen.class);
	    startActivity(intent);
	    this.finish();
	}

	public void upload(View view){
		name = this.edit.getText().toString();
		try{
		ScoreFactory sf = new ScoreFactory();
		//cancel.setText(name);
//		sf.saveInLocal(name, score);
		boolean succ = sf.saveToServer(name, score);
		if(!succ){
			notice.setText("Connection failed");
		}
		}catch(Exception e){
			notice.setText("Connection failed");
		}
		notice.setVisibility(View.VISIBLE);
		
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_result, menu);
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
