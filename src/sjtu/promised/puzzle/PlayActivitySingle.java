package sjtu.promised.puzzle;

import java.io.IOException;
import java.util.Random;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PlayActivitySingle extends Activity {	
	private BoardViewSingle mBoard;	
	private int mRandom;	
	private TextView mTextRandom;
	private TextView mTextScoreFirst;
	private TextView mTextScoreSecond;
	private TextView mTextWho;
	private Button mStart;
	private MediaPlayer mp;
	private Toast toast;
	private boolean warnFirst = true;

	public int getRandom() {
		return mRandom;
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playsingle);
        
        mBoard = (BoardViewSingle)findViewById(R.id.puzzle_board);
        mTextRandom = (TextView)findViewById(R.id.text_random);
        mTextScoreFirst = (TextView)findViewById(R.id.text_score_first);
        mTextScoreSecond = (TextView)findViewById(R.id.text_score_second);
        mTextWho = (TextView)findViewById(R.id.text_who);
        mStart = (Button)findViewById(R.id.start_button);
       
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
	
	//Refresh View
	public void updateViews() {
     	mTextRandom.setText("");
		mTextScoreFirst.setText("  Your Score: " + Integer.toString(mBoard.getScoreFirst()));
		mTextScoreSecond.setText("Robot Score: " + Integer.toString(mBoard.getScoreSecond()));
		if(mBoard.getPlayer() == 1)
			mTextWho.setText("Your Turn");
		else
			mTextWho.setText("Robot's Turn");	
		mStart.setEnabled(true);
		warnFirst = true;
	}
	
	//Ending Information
	public void finished() {
		mBoard.Alert();
		this.finish();
	}

	public void Twinkle() {	
		handler.post(disappear);
		handler.postDelayed(appear, 200);
		handler.postDelayed(disappear, 400);
		handler.postDelayed(appear, 600);
		handler.postDelayed(disappear, 800);
		handler.postDelayed(appear, 1000);



		

	}
	
	Handler handler = new Handler();
	Runnable disappear = new Runnable(){
		public void run() {
			mBoard.setIsDisappear(true);
			mBoard.postInvalidate();
		}	
	};
	
	Runnable appear = new Runnable(){
		public void run() {
			mBoard.setIsDisappear(false);
			mBoard.postInvalidate();
		}	
	};
	
	Runnable getToast = new Runnable(){
		public void run() {
			getHint(4);
		}	
	};
	
	
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
					"End. Player"+ player +"Win", Toast.LENGTH_LONG);
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
			   View layout = inflater.inflate(R.layout.dialog1,
			     (ViewGroup) findViewById(R.layout.dialog1));



			  new AlertDialog.Builder(this).setTitle("The number is"+mRandom ).setView(layout)
			     .setNeutralButton("ok",null).show();
	
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
		case 4: {
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
				   break;
		}
		default:;
		}
		
	}
}
