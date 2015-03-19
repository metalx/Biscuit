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
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class BoardView extends View {

	private static final int DEFAULT_BOARD_LENGTH = 100;
	private static final int DEFAULT_BOARD_SIZE = 3;
	
	private static int mSize = DEFAULT_BOARD_SIZE;
	
	private boolean mActive;
	private boolean mSecond;
	
	public Cell mClickedCell;
	private Cell mSelectedCell;
	public Cell mSecondCell = null;
	
	private CellCollection mCells;
	
	private int mScoreFirst = 0;
	private int mScoreSecond = 0;
	private int mPlayer = 1;

	
	private float mCellWidth;
	private float mCellHeight;
	private float mNumberLeft;
	private float mNumberTop;
	private Canvas mCanvas;
	
	private Paint mBackgroundColorClicked;
	private Paint mBackgroundColorClickedSelected;
	private Paint mBackgroundColorSelected;
	private Paint mCellValueColor;
	private Paint mCellValueColorReadonly;
	private Paint mLineColor;
	
	private PlayActivity mPlayActivity;
	
	private Random mGenerator;
	
	public void setActive(boolean active){
		mActive = active;
	}
	
	public BoardView(Context context) {
		this(context, null);
	}
	
	public BoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setFocusable(true);
		setFocusableInTouchMode(true);
		
		mCells = new CellCollection(mSize);
		
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
		mLineColor.setColor(Color.WHITE);
		mCellValueColor.setColor(Color.WHITE);
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
	
	public int getPlayer() {
		return mPlayer;
	}
	
	public int getScoreFirst() {
		return mScoreFirst;
	}
	
	public int getScoreSecond() {
		return mScoreSecond;
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
							mPlayActivity.getHint(1);
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
		mCanvas=canvas;
		super.onDraw(mCanvas);
		
		
//		int width = getWidth() - getPaddingRight();
//		int height = getHeight() - getPaddingBottom();

		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
			
		float numberAscent = mCellValueColor.ascent();
		
	    
		for (int row = 0; row < mSize; row++) {
			for (int col = 0; col < mSize; col++) {
				Cell cell = mCells.getCell(row, col);
				
				float cellLeft = (col * mCellWidth) + paddingLeft;
				float cellTop = (row * mCellHeight) + paddingTop;

						onDrawCell(mCanvas, cell, cellLeft, cellTop);



				onDrawCellValue(mCanvas, 
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
		    InputStream is = getResources().openRawResource(R.drawable.picicon_1) ;
			Bitmap mBitmap = BitmapFactory.decodeStream(is);
	            canvas.drawBitmap(mBitmap, left+ mCellWidth/4, top+ mCellHeight/4, p);
	    }
			
		
		
		 else  if(cell == mSecondCell){
		        	if(mSecondCell.isAdjacent(mClickedCell)){
		        	 InputStream is = getResources().openRawResource(R.drawable.picicon_7) ;
						Bitmap mBitmap = BitmapFactory.decodeStream(is);

				            canvas.drawBitmap(mBitmap, left+ mCellWidth/4, top+ mCellHeight/4, p);
		        	}else{
		        		 InputStream is = getResources().openRawResource(R.drawable.picicon_5) ;
							Bitmap mBitmap = BitmapFactory.decodeStream(is);

					            canvas.drawBitmap(mBitmap, left+ mCellWidth/4, top+ mCellHeight/4, p);
		        		
		        	}
					
	            }
		 else {
	           if(cell.getValue() !=9){
	      			
	   				InputStream is4 = getResources().openRawResource(R.drawable.picicon_3) ;
	   				Bitmap mBitmap = BitmapFactory.decodeStream(is4);
	   				canvas.drawBitmap(mBitmap, left+ mCellWidth/4, top+ mCellHeight/4, p);
	   		} 
	           else{
	             	InputStream is5 = getResources().openRawResource(R.drawable.picicon_4) ;
	      			Bitmap mBitmap = BitmapFactory.decodeStream(is5);

	      			canvas.drawBitmap(mBitmap, left+ mCellWidth/4, top+ mCellHeight/4, p);
	             }
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
		if (mScoreFirst>=5 || mScoreSecond>=5)
		{
			android.os.Process.killProcess(android.os.Process.myPid()) ;
			}
		if(mActive){
			float x = event.getX();
			float y = event.getY();
			
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_MOVE:
					mSelectedCell = getCellAtPoint(x, y);
				//	invalidate();
					return true;
				case MotionEvent.ACTION_UP:
					mSelectedCell = getCellAtPoint(x, y);
					onClickCell();
				//	invalidate();
					return true;
			}
		}
		return false;
		
	}
	
	public void setPlayActivity(PlayActivity activity) {
		mPlayActivity = activity;
	}
	
	public static void setSize(int size) {
		mSize = size;
	}
	
	private void updateScore() {

		int valid_clicked = mClickedCell.isValid();
		int valid_selected = mSelectedCell.isValid(); 
		int valid_second = mSecondCell.isValid(); 
		
		mClickedCell.updateValue(mPlayActivity.getRandom());
		mSelectedCell.updateValue(mPlayActivity.getRandom());
		
		if (mPlayer == 1 ) mPlayer = 2;
		else mPlayer = 1;
		
		
		
		if (!(mClickedCell.isValid()==0) && (valid_clicked == 0)) {
			if (mSecond) {
				mScoreSecond++;
			} else {
				mScoreFirst++;
			}
		}
		
		if (!(mSecondCell.isValid()==0) && (valid_second==0)) {
			if (mSecond) {
				mScoreSecond++;
			} else {
				mScoreFirst++;
			}
		} 
		
		
		
		/*
		else if (!mSelectedCell.isValid() && valid_selected) {
			if (mSecond) {
				mScoreSecond++;
				mScoreFirst++;
			}
		}  */
		
		mActive = false;
		mSecond = !mSecond;	

		

		mPlayActivity.updateViews();
		

		
		if (mScoreFirst>=5 || mScoreSecond>=5)
		{
			mPlayActivity.getHint(2);
	//		android.os.Process.killProcess(android.os.Process.myPid()) ;
			}
	}


	
}
