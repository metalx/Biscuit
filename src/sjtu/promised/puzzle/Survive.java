package sjtu.promised.puzzle;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Survive extends Activity {
	
	private SurviveView mBoard;
	
	private int mRandom;
	
	//private int time_history;
	private int time_total = 60000;
	private long while_counting = time_total;
	//private Handler msghandler;
	private CountDownTimer cdt;
	private boolean IsTimeOut = false;
	private int maxTime = 66000;
		
	private TextView mTextRandom;
	private TextView mTextScoreFirst;
	private Button mStart;
	private Button mPause;
	private ProgressBar pb1;
	//private Toast toast;
	private MediaPlayer mp;
	//private AlertDialog.Builder builder;
	
	public int getRandom() {
		return mRandom;
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playsurvive);
        
     
        mBoard = (SurviveView)findViewById(R.id.puzzle_board);
        mTextRandom = (TextView)findViewById(R.id.text_random);
        mTextScoreFirst = (TextView)findViewById(R.id.text_score_first);
        mStart = (Button)findViewById(R.id.start_button);
        mPause = (Button)findViewById(R.id.pause);
        pb1 =(ProgressBar)findViewById(R.id.pb1);
    	//pb1.setHorizontalScrollBarEnabled(true);
    	pb1.setIndeterminate(false);
    	pb1.setMax(maxTime);
    	//change: time limit to time_total
    	pb1.setProgress((int)while_counting);
    	
    	progressbar_timing();
        
		
        OnClickListener btnClick,btnClick2;
    	btnClick = new OnClickListener(){
			public void onClick(View v){
			//	Toast.makeText(   PlayActivity.this, "ÄãµãÁËÎÒ", Toast.LENGTH_LONG).show();
				getHint();
			}
		};		
		btnClick2 = new OnClickListener(){
			public void onClick(View v){
					cdt.cancel();
					mBoard.Alert();
        		}
					//mStart.setClickable(false);
					//mPause.
					//mBoard.setActive(false);
		};
		mStart.setOnClickListener(btnClick);
		mPause.setOnClickListener(btnClick2);
        mBoard.setSurvive(this);
        updateViews();
	}
	
	public void updateViews() {
     	mTextRandom.setText("");
		mTextScoreFirst.setText("Score One: " + Integer.toString(mBoard.getScore()));
		mStart.setEnabled(true);
	}
	
	public boolean getIsTimeOut()
	{
		return IsTimeOut;
	}
	public void finished() {
		mBoard.Alert2(mBoard.getScore());
		//Toast.makeText( this, "game over,the score is£¬"+ mBoard.getScore(), Toast.LENGTH_LONG).show();
		//Intent intent = new Intent(); 
		//intent.setClass(this, Survive.class);
		//startActivity(intent);
 		this.finish();
	}
	
	public void progressbar_timing(){
		cdt = new CountDownTimer(while_counting, 1000) {

		     public void onTick(long millisUntilFinished) {
		    	 //Message msg_2= new Message();
		    	// msg_2.what = TIME_NOTIFICATION;
		    	 while_counting = millisUntilFinished;
		    	 pb1.setProgress((int)while_counting);
				 //msghandler.sendMessage(msg_2);
		     }
		     public void onFinish() {
		    	//IsTimeOut = true;
		    	 mBoard.Alert2(mBoard.getScore());
		    	 //this.cancel();
		     }
		  }.start();
		  
    }
	
	public void change_timing(int tm){
		while_counting = while_counting + (tm * 1000);
		if (while_counting > 0)
		{
			pb1.setProgress((int) while_counting);
			cdt.cancel();
			cdt = new CountDownTimer(while_counting, 1000) {

				public void onTick(long millisUntilFinished) {
		    	 //Message msg_2= new Message();
		    	// msg_2.what = TIME_NOTIFICATION;
					while_counting = millisUntilFinished;
					pb1.setProgress((int)while_counting);
				 //msghandler.sendMessage(msg_2);
				}
				public void onFinish() {
					//IsTimeOut = true;
					 mBoard.Alert2(mBoard.getScore());
					//this.cancel();
				}
			}.start();
			Toast.makeText(Survive.this, tm + "seconds", Toast.LENGTH_SHORT).show();
		}
		else {
			while_counting = 0;
		}
    }

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		cdt.cancel();
		super.finish();
	}
	
	public void timeCancel() {
		cdt.cancel();
	}
	
	public void getHint(){
		mBoard.mSecondCell = null;
		mBoard.mClickedCell = null;
		mBoard.postInvalidate();
		Random mGenerator = new Random();
		mRandom = mGenerator.nextInt(8) + 1;
		mTextRandom.setText("Number: " + Integer.toString(mRandom));
		mBoard.setActive(true);
		mStart.setEnabled(false);

	
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
		}
 }

	
	

