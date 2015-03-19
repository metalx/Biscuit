package sjtu.promised.puzzle;

import java.io.InputStream;
import java.util.Random;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class SurviveView extends View {

	private static final int DEFAULT_BOARD_LENGTH = 100;
	private static final int DEFAULT_BOARD_SIZE = 3;
	
	private static int mSize = DEFAULT_BOARD_SIZE;
	
	private boolean mActive;
	
	private AlertDialog.Builder builder;
	
	public Cell mClickedCell;
	public Cell mSelectedCell;
	
	private CellCollection mCells;
	
	private int mScore;
	public Cell mSecondCell = null;
	
	private float mCellWidth;
	private float mCellHeight;
	private float mNumberLeft;
	private float mNumberTop;
	
	private Paint mBackgroundColorClicked;
	private Paint mBackgroundColorClickedSelected;
	private Paint mBackgroundColorSelected;
	private Paint mCellValueColor;
	private Paint mCellValueColorReadonly;
	private Paint mLineColor;
	
	private Survive mSurvive;
	
	private Random mGenerator;
	
	public void setActive(boolean active){
		mActive = active;
	}
	
	public SurviveView(Context context) {
		this(context, null);
	}
	
	public SurviveView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setFocusable(true);
		setFocusableInTouchMode(true);
		
		mCells = new CellCollection(mSize);
		mCells.Randomize(mSize);
		
		mBackgroundColorClicked = new Paint();
		mBackgroundColorSelected = new Paint();
		mBackgroundColorClickedSelected = new Paint();
		mCellValueColor = new Paint();
		mCellValueColorReadonly = new Paint();
		mLineColor = new Paint();
		
		mCellValueColor.setAntiAlias(true);
		mCellValueColorReadonly.setAntiAlias(true);
		
		setBackgroundColor(Color.TRANSPARENT );
		mBackgroundColorClicked.setColor(Color.BLUE);
		mBackgroundColorClickedSelected.setColor(Color.GREEN);
		mBackgroundColorSelected.setColor(Color.YELLOW);
		mLineColor.setColor(Color.BLACK);
		mCellValueColor.setColor(Color.BLACK);
		mCellValueColorReadonly.setColor(Color.RED);
        
		mGenerator = new Random();
	}
	
	private Cell getCellAtPoint(float x, float y) {
		float lx = x - getPaddingLeft();
		float ly = y - getPaddingTop();
		
		int row = (int)(ly / mCellHeight);
		int col = (int)(lx / mCellWidth);
		
		if(col >= 0 && col < mSize 
				&& row >= 0 && row < mSize) {
			return mCells.getCell(row, col);
		} else {
			return null;
		}
	}
	
	public int getScore() {
		return mScore;
	}
	
	
	private void moveCellSelection(int vx, int vy) {
		int row = 0;
		int col = 0;
		
		if (mSelectedCell != null) {
			row = mSelectedCell.getRowIndex() + vy;
			col = mSelectedCell.getColumnIndex() + vx;
		}
		
		if(col >= 0 && col < mSize 
				&& row >= 0 && row < mSize) {
			mSelectedCell = mCells.getCell(row, col);
			postInvalidate();
		}
	}

protected void onClickCell() {

		
     	if (mSelectedCell != null) {
			if (mClickedCell != null) {
				
			
				if (mClickedCell != mSelectedCell) {
					if ((mSelectedCell.isValid() == 0) || (mClickedCell.isValid() == 0)) {
						if (mSelectedCell.isAdjacent(mClickedCell)) {
							mSecondCell = mSelectedCell;
							updateScore();					
							mSelectedCell = null;
							invalidate();
						}else{
							mSecondCell = mSelectedCell;
							mSelectedCell = null;
							//mPlayActivity.getHint(1);
							invalidate();
						}
					}
				} else {
					mClickedCell = null;
					mSecondCell = null; //ÐÂ¼Ó
					invalidate();
				}
				
			} else {
				mClickedCell = mSelectedCell;
				mSelectedCell = null;
				invalidate();
			}
		}
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		int width = getWidth() - getPaddingRight();
		int height = getHeight() - getPaddingBottom();
		
		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
			
		float numberAscent = mCellValueColor.ascent();
			
		for (int row = 0; row < mSize; row++) {
			for (int col = 0; col < mSize; col++) {
				Cell cell = mCells.getCell(row, col);
				//cell.RefreshRandomValue();
				
				float cellLeft = (col * mCellWidth) + paddingLeft;
				float cellTop = (row * mCellHeight) + paddingTop;
		
				onDrawCell(canvas, cell, cellLeft, cellTop);
				
				onDrawCellValue(canvas, 
						cell, 
						cellLeft + mNumberLeft, 
						cellTop + mNumberTop - numberAscent);
			}
		}
		
		// draw lines
		/*
		for (int c = 0; c <= mSize; c++) {
			float x = (c * mCellWidth) + paddingLeft;
			canvas.drawLine(x, paddingTop, x, height, mLineColor);
		}
		
		for (int r = 0; r <= mSize; r++) {
			float y = r * mCellHeight + paddingTop;
			canvas.drawLine(paddingLeft, y, width, y, mLineColor);
		}  */
		
	}
	
	protected void onDrawCell(Canvas canvas, Cell cell, float left, float top) {
		   Paint p = new Paint();
        p.setColor(Color.RED);

		
		if (cell == mClickedCell) {
		    InputStream is = getResources().openRawResource(R.drawable.bsct4) ;
			Bitmap mBitmap = BitmapFactory.decodeStream(is);
	            canvas.drawBitmap(mBitmap, left+ mCellWidth/4, top+ mCellHeight/4, p);
	    }
			
		
		
		 else  if(cell == mSecondCell){
		        	if(mSecondCell.isAdjacent(mClickedCell)){
		        	 InputStream is = getResources().openRawResource(R.drawable.bsct4) ;
						Bitmap mBitmap = BitmapFactory.decodeStream(is);

				            canvas.drawBitmap(mBitmap, left+ mCellWidth/4, top+ mCellHeight/4, p);
		        	}else{
		        		 InputStream is = getResources().openRawResource(R.drawable.bsct6) ;
							Bitmap mBitmap = BitmapFactory.decodeStream(is);

					            canvas.drawBitmap(mBitmap, left+ mCellWidth/4, top+ mCellHeight/4, p);
		        		
		        	}
					
	            }
		 else {
	           if(cell.getValue() !=9){
	      			
	   				InputStream is4 = getResources().openRawResource(R.drawable.bsct2) ;
	   				Bitmap mBitmap = BitmapFactory.decodeStream(is4);
	   				canvas.drawBitmap(mBitmap, left+ mCellWidth/4, top+ mCellHeight/4, p);
	   			} 
	           /*else{
	             	InputStream is5 = getResources().openRawResource(R.drawable.picicon_4) ;
	      			Bitmap mBitmap = BitmapFactory.decodeStream(is5);

	      			canvas.drawBitmap(mBitmap, left+ mCellWidth/4, top+ mCellHeight/4, p);
	             }*/
	      		}
	    }

	
	
	protected void onDrawCellValue(Canvas canvas, Cell cell, float left, float top) {
		canvas.drawText(Integer.toString(cell.getValue()), 
				left, 
				top, 
				(cell.isValid() == 0) ? mCellValueColor : mCellValueColorReadonly);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        
        int width, height;
        if (widthMode == MeasureSpec.EXACTLY) {
        	width = widthSize;
        } else {
        	width = DEFAULT_BOARD_LENGTH;
        	if (widthMode == MeasureSpec.AT_MOST && width > widthSize ) {
        		width = widthSize;
        	}
        }
        if (heightMode == MeasureSpec.EXACTLY) {
        	height = heightSize;
        } else {
        	height = DEFAULT_BOARD_LENGTH;
        	if (heightMode == MeasureSpec.AT_MOST && height > heightSize ) {
        		height = heightSize;
        	}
        }
        
        if (widthMode != MeasureSpec.EXACTLY) {
        	width = height;
        }
        
        if (heightMode != MeasureSpec.EXACTLY) {
        	height = width;
        }
        
    	if (widthMode == MeasureSpec.AT_MOST && width > widthSize ) {
    		width = widthSize;
    	}
    	if (heightMode == MeasureSpec.AT_MOST && height > heightSize ) {
    		height = heightSize;
    	}
        
    	mCellWidth = (float)(width - getPaddingLeft() - getPaddingRight()) / mSize;
        mCellHeight = (float)(height - getPaddingTop() - getPaddingBottom()) / mSize;

        setMeasuredDimension(width, height);
        
     //   float cellTextSize = mCellHeight * 0.75f;
        float cellTextSize = mCellHeight * 0.3f;
        mCellValueColor.setTextSize(cellTextSize);
        mCellValueColorReadonly.setTextSize(cellTextSize);
        
     //   mNumberLeft = (mCellWidth - mCellValueColor.measureText("0")) / 2;
     //   mNumberTop = (mCellHeight - mCellValueColor.getTextSize()) / 2;
        mNumberLeft = (mCellWidth - mCellValueColor.measureText("0"));
        mNumberTop = (mCellHeight - mCellValueColor.getTextSize());
        
	}

	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(mActive){
			float x = event.getX();
			float y = event.getY();
			
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_MOVE:
					mSelectedCell = getCellAtPoint(x, y);
					postInvalidate();
					return true;
				case MotionEvent.ACTION_UP:
					mSelectedCell = getCellAtPoint(x, y);
					onClickCell();
					postInvalidate();
					return true;
			}
		}
		return false;
		
	}
	
	public void setSurvive(Survive survive) {
		mSurvive = survive;
	}
	
	public static void setSize(int size) {
		mSize = size;
	}
	
	private void updateScore() {
		//int valid_clicked = mClickedCell.isValid();
		//int valid_selected = mSelectedCell.isValid(); 
		
		mClickedCell.updateValue2(mSurvive.getRandom());
		mSelectedCell.updateValue2(mSurvive.getRandom());
		//int tScore = mScore;
			
		//if (valid_clicked == 0) {
		mScore = mScore + mClickedCell.isValid();
		//}
		
		if(!(mClickedCell.isValid() == 0)) {
			if(mClickedCell.isValid() == 2)
				mSurvive.change_timing(2);
			else if(mClickedCell.isValid() == 3)
				mSurvive.change_timing(3);
			else if(mClickedCell.isValid() == -1)
				mSurvive.change_timing(-3);
			mClickedCell.RefreshRandomValue();					
		}
			

		mScore = mScore + mSelectedCell.isValid();
		
		if(!(mSelectedCell.isValid() == 0)) {
			if(mSelectedCell.isValid() == 2)
				mSurvive.change_timing(2);
			else if(mSelectedCell.isValid() == 3)
				mSurvive.change_timing(3);
			else if(mSelectedCell.isValid() == -1)
				mSurvive.change_timing(-3);
			mSelectedCell.RefreshRandomValue();
		}
		mActive = false;
		mSurvive.updateViews();
	}
	
	public void Alert()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setMessage("pause")  
	       .setCancelable(false)  
	       .setPositiveButton("resume", new DialogInterface.OnClickListener() {  
	           public void onClick(DialogInterface dialog, int id) {  
	        	   mSurvive.progressbar_timing();
	           }  
	       })  
	       .setNegativeButton("exit", new DialogInterface.OnClickListener() {  
	           public void onClick(DialogInterface dialog, int id) {  
	        	   mSurvive.finish(); 
	           }  
	       });  
    	AlertDialog alert = builder.create();
        alert.show();
	}
	
	public void Alert2(int score)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setMessage("game over,the score is£¬"+ score)  
	       .setCancelable(false)  
	       .setNeutralButton("ok", new DialogInterface.OnClickListener() {  
	           public void onClick(DialogInterface dialog, int id) {  
	        	   mSurvive.timeCancel();
	        	   mSurvive.finish();
	           }  
	       });  
    	AlertDialog alert = builder.create();
        alert.show();
	}
}
