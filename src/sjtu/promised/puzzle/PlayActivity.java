package sjtu.promised.puzzle;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PlayActivity extends Activity {
	
	private BoardView mBoard;
	
	private int mRandom;
	private boolean warnFirst;
	
	private TextView mTextRandom;
	private TextView mTextScoreFirst;
	private TextView mTextScoreSecond;
	private TextView mTextWho;
	private Button mStart;
	private MediaPlayer mp;
	private Toast toast;
	
	public int getRandom() {
		return mRandom;
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        
     
        mBoard = (BoardView)findViewById(R.id.puzzle_board);
        mTextRandom = (TextView)findViewById(R.id.text_random);
        mTextScoreFirst = (TextView)findViewById(R.id.text_score_first);
        mTextScoreSecond = (TextView)findViewById(R.id.text_score_second);
        mTextWho = (TextView)findViewById(R.id.text_who);
        mStart = (Button)findViewById(R.id.start_button);
        
        mTextRandom.setTextColor(android.graphics.Color.WHITE);
        mTextScoreFirst.setTextColor(android.graphics.Color.WHITE);
        mTextScoreSecond.setTextColor(android.graphics.Color.WHITE);
        mTextWho.setTextColor(android.graphics.Color.WHITE);
       

        
        OnClickListener btnClick;
    	btnClick = new OnClickListener(){
			public void onClick(View v){
	        getHint(3);
			}
		};		
		mStart.setOnClickListener(btnClick);
        mBoard.setPlayActivity(this);
        updateViews();
	}
	


	
	public void updateViews() {
     	mTextRandom.setText("");
		mTextScoreFirst.setText("Score One: " + Integer.toString(mBoard.getScoreFirst()));
		mTextScoreSecond.setText("Score Two: " + Integer.toString(mBoard.getScoreSecond()));
		mTextWho.setText("Player : " + Integer.toString(mBoard.getPlayer()));
		mStart.setEnabled(true);
		warnFirst = true;
		
	}
	
	public void getHint(int i){
		switch(i){
		case 1:{
			
			if(warnFirst == true )
			{
			toast = Toast.makeText(getApplicationContext(),
					"Hey man£¡You can only select adjacent biscuits", Toast.LENGTH_LONG);
		    toast.setGravity(Gravity.BOTTOM, 0, 0);
		    toast.show();
		    warnFirst = false;
			}
			if(StartActivity.soundOn == true)
			{
		    mp = MediaPlayer.create(this, R.drawable.badsound);
			   try {
				   mp.stop();
				mp.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   mp.start();
			}
			   break;
		}
		case 2:	{
			int player = 3 - mBoard.getPlayer();
			toast = Toast.makeText(getApplicationContext(),
					"End! Player"+ player +"Win", Toast.LENGTH_LONG);
		    toast.setGravity(Gravity.CENTER, 0, 0);
		    toast.show();
		    if(StartActivity.soundOn == true)
			{
			mp = MediaPlayer.create(this, R.drawable.sound1);
						   try {
							   mp.stop();
							mp.prepare();
						} catch (IllegalStateException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			   mp.start();
			}
	
				   
			break;
		}
		case 3: {
			mBoard.mSecondCell = null;
			mBoard.mClickedCell = null;
			mBoard.postInvalidate();
			Random mGenerator = new Random();
			mRandom = mGenerator.nextInt(8) + 1;
			mTextRandom.setText("Number: " + Integer.toString(mRandom));
			mBoard.setActive(true);
			mStart.setEnabled(false);

			LayoutInflater inflater = getLayoutInflater();
			   View layout = inflater.inflate(R.layout.dialog,
			     (ViewGroup) findViewById(R.layout.dialog));



			   new AlertDialog.Builder(this).setTitle("The number is: "+mRandom ).setView(layout)
			     .setPositiveButton("Ok", null)
			     .show();

	
			   if(StartActivity.soundOn == true)
				{
		     mp = MediaPlayer.create(this, R.drawable.sound2);
					   try {
						   mp.stop();
						mp.prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		   mp.start();
				}
				   break;
		}
		default:;
		}
		
	}
	
	public void finished() {
		getHint(2);
		Intent intent = new Intent(); 
		intent.setClass(this, StartActivity.class);
		startActivity(intent);
		this.finish();
	}
	
}
