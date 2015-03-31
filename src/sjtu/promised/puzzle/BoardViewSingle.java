package sjtu.promised.puzzle;

import java.io.InputStream;
import java.util.Random;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class BoardViewSingle extends View {

	private static final int DEFAULT_BOARD_LENGTH = 100;
	private static final int DEFAULT_BOARD_SIZE = 3;
	
	private static int mSize = DEFAULT_BOARD_SIZE;//Row numbers
	
	private boolean mActive;//State of Activity
	private boolean mSecond;//Is the second player
	
	public Cell mClickedCell;//First selected biscuit
	public Cell mSelectedCell;//Second selected biscuit
	public Cell mSecondCell = null;//Legal second selected biscuit(Adjacent with the first selected biscuit)
	
	private CellCollection mCells;//Cell Object
	
	private int mScoreFirst;//Player's Score
	private int mScoreSecond;//CPU's Score
	
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
	
	public PlayActivitySingle mPlayActivity;
	
	private int rowA, colA, rowB, colB;
	private boolean isDisappear = false;

	
	//Set current state of Biscuit Cell
	public void setActive(boolean active){
		mActive = active;
	}
	
	//Construct method
	public BoardViewSingle(Context context) {
		this(context, null);
	}
	
	public BoardViewSingle(Context context, AttributeSet attrs) {
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
		mLineColor.setColor(Color.BLACK);
		mCellValueColor.setColor(Color.BLACK);
		mCellValueColorReadonly.setColor(Color.RED);
	}
	
	//返回一个饼干
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
	
	public float getCellHeight(){
		return mCellHeight;
	}
	
	public float getCellWidth(){
		return mCellWidth;
	}
	
	//Return current player's ID
	public int getPlayer() {
		if(mSecond) return 2;
		else return 1;
	}
	
	//Return Player's score
	public int getScoreFirst() {
		return mScoreFirst;
	}
	
	//return Bot's score
	public int getScoreSecond() {
		return mScoreSecond;
	}
	
	//This function is called when selecting the first biscuit or second biscuit.
	//Finally the two biscuit will be determined.
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
					mSecondCell = null; 
					invalidate();
				}
				
			} else {
				mClickedCell = mSelectedCell;
				mSelectedCell = null;
				invalidate();
			}
		}
		
	}
	
	//Overrride OnDraw. Draw biscuits and value of biscuits.
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
			
		float numberAscent = mCellValueColor.ascent();
			
		for (int row = 0; row < mSize; row++) {
			for (int col = 0; col < mSize; col++) {
				Cell cell = mCells.getCell(row, col);
				
				float cellLeft = (col * mCellWidth) + paddingLeft;
				float cellTop = (row * mCellHeight) + paddingTop;
				
				if(isDisappear == true){
					if((row == rowA && col == colA) || (row == rowB && col == colB) ) continue;
				}
		
				onDrawCell(canvas, cell, cellLeft, cellTop);
				onDrawCellValue(canvas, cell, cellLeft + mNumberLeft, cellTop + mNumberTop - numberAscent);
			}
		}
	}
	
	//Draw all biscuits.
	protected void onDrawCell(Canvas canvas, Cell cell, float left, float top) {
		   Paint p = new Paint();
        p.setColor(Color.RED);

		
		if (cell == mClickedCell) {
		    InputStream is = getResources().openRawResource(R.drawable.spicicon_1) ;
			Bitmap mBitmap = BitmapFactory.decodeStream(is);
	            canvas.drawBitmap(mBitmap, left+ mCellWidth/4, top+ mCellHeight/4, p);
	    }
			
		
		
		 else  if(cell == mSecondCell){
		        	if(mSecondCell.isAdjacent(mClickedCell)){
		        	 InputStream is = getResources().openRawResource(R.drawable.spicicon_1) ;
						Bitmap mBitmap = BitmapFactory.decodeStream(is);

				            canvas.drawBitmap(mBitmap, left+ mCellWidth/4, top+ mCellHeight/4, p);
		        	}else{
		        		 InputStream is = getResources().openRawResource(R.drawable.spicicon_5) ;
							Bitmap mBitmap = BitmapFactory.decodeStream(is);

					            canvas.drawBitmap(mBitmap, left+ mCellWidth/4, top+ mCellHeight/4, p);
		        		
		        	}
					
	            }
		 else {
	           if(cell.getValue() !=9){
	      			
	   				InputStream is4 = getResources().openRawResource(R.drawable.spicicon_3) ;
	   				Bitmap mBitmap = BitmapFactory.decodeStream(is4);
	   				canvas.drawBitmap(mBitmap, left+ mCellWidth/4, top+ mCellHeight/4, p);
	   		} 
	           else{
	             	InputStream is5 = getResources().openRawResource(R.drawable.spicicon_4) ;
	      			Bitmap mBitmap = BitmapFactory.decodeStream(is5);

	      			canvas.drawBitmap(mBitmap, left+ mCellWidth/4, top+ mCellHeight/4, p);
	             }
	      			}
	    }
	
	//Draw values of all biscuits
	protected void onDrawCellValue(Canvas canvas, Cell cell, float left, float top) {
		canvas.drawText(Integer.toString(cell.getValue()), left, top, (cell.isValid() == 0) ? mCellValueColor : mCellValueColorReadonly);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        
        int width, height;
        
        if(widthMode == MeasureSpec.EXACTLY){
        	width = widthSize;
        } 
        else{
        	width = DEFAULT_BOARD_LENGTH;
        	if(widthMode == MeasureSpec.AT_MOST && width > widthSize ){
        		width = widthSize;
        	}
        }
        
        if(heightMode == MeasureSpec.EXACTLY){
        	height = heightSize;
        } 
        else{
        	height = DEFAULT_BOARD_LENGTH;
        	if(heightMode == MeasureSpec.AT_MOST && height > heightSize ){
        		height = heightSize;
        	}
        }
        
        if(widthMode != MeasureSpec.EXACTLY){
        	width = height;
        }
        
        if(heightMode != MeasureSpec.EXACTLY){
        	height = width;
        }
        
    	if(widthMode == MeasureSpec.AT_MOST && width > widthSize ){
    		width = widthSize;
    	}
    	
    	if(heightMode == MeasureSpec.AT_MOST && height > heightSize ){
    		height = heightSize;
    	}
        
    	mCellWidth = (float)(width - getPaddingLeft() - getPaddingRight()) / mSize;
        mCellHeight = (float)(height - getPaddingTop() - getPaddingBottom()) / mSize;

        setMeasuredDimension(width, height);
        
        float cellTextSize = mCellHeight * 0.3f;
        mCellValueColor.setTextSize(cellTextSize);
        mCellValueColorReadonly.setTextSize(cellTextSize);
        
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
	
	public void setPlayActivity(PlayActivitySingle playActivitySingle) {
		mPlayActivity = playActivitySingle;
	}

	//Set Cell's Size and Number of Biscuits.
	public static void setSize(int size) {
		mSize = size;
	}
	
	private void updateScore() {
		int valid_clicked = mClickedCell.isValid();
		int valid_second = mSecondCell.isValid(); 
		
		mClickedCell.updateValue(mPlayActivity.getRandom());
		mSelectedCell.updateValue(mPlayActivity.getRandom());
		
		if (!(mClickedCell.isValid() == 0) && (valid_clicked == 0)) {
			if (mSecond) {
				mScoreSecond++;
			} else {
				mScoreFirst++;
			}
		}
		
		if (!(mSecondCell.isValid() == 0) && (valid_second == 0)) {
			if (mSecond) {
				mScoreSecond++;
			} else {
				mScoreFirst++;
			}
		}
		
		mActive = false;
		mSecond = !mSecond;	
		
		mPlayActivity.updateViews();
		
		if(mSecond == true && mScoreFirst<5 && mScoreSecond<5) {
			Random mGenerator = new Random();
			int mRandom = mGenerator.nextInt(8) + 1;
			Alert2(mGenerator, mRandom);
		}
		
		if (mScoreFirst>=5 || mScoreSecond>=5){
			Alert();
		}
	}
	
	//Simulate Bot's operations.
	private void robotFinger(Random mGenerator, int mRandom)
	{
		boolean isFound = false;
		toastAndTwinkle(mRandom);
		Cell checkCell;
		for(int row = 0; row < mSize; row++){
			for(int col = 0; col < mSize; col++){
				checkCell = mCells.getCell(row, col);
				if(checkCell.getValue() + mRandom == 9){
						isFound = true;
						checkCell.updateValue(mRandom);
						mScoreSecond++;
						Log.v("123", "err1");
						if(row >0){
							checkCell = mCells.getCell(row - 1, col);
							if(checkCell.getValue() + mRandom == 9){
								rowA = row;
								colA = col;
								rowB = row - 1;
								colB = col;
								Log.v("123", "err2");
								checkCell.updateValue(mRandom);
								mScoreSecond++;
								mSecond = !mSecond;
								mPlayActivity.updateViews();
								return;
							}
							checkCell = mCells.getCell(row, col);
						}
						if(row <(mSize - 1)){
							checkCell = mCells.getCell(row + 1, col);
							if(checkCell.getValue() + mRandom == 9){
								rowA = row;
								colA = col;
								rowB = row + 1;
								colB = col;
								Log.v("123", "err2");
								checkCell.updateValue(mRandom);
								mScoreSecond++;
								mSecond = !mSecond;
								mPlayActivity.updateViews();
								return;
							}
							checkCell = mCells.getCell(row, col);
						}
						if(col > 0){
							checkCell = mCells.getCell(row, col - 1);
							if(checkCell.getValue() + mRandom == 9){
								rowA = row;
								colA = col;
								rowB = row;
								colB = col - 1;
								Log.v("123", "err2");
								checkCell.updateValue(mRandom);
								mScoreSecond++;
								mSecond = !mSecond;
								mPlayActivity.updateViews();
								return;
							}
							checkCell = mCells.getCell(row, col);
						}
						if(col <(mSize - 1)){
							checkCell = mCells.getCell(row, col + 1);
							if(checkCell.getValue() + mRandom == 9){
								rowA = row;
								colA = col;
								rowB = row;
								colB = col + 1;
								Log.v("123", "err2");
								checkCell.updateValue(mRandom);
								mScoreSecond++;
								mSecond = !mSecond;
								mPlayActivity.updateViews();
								return;
							}
							checkCell = mCells.getCell(row, col);
						}
						if(row > 0) {
							checkCell = mCells.getCell(row - 1, col);
							rowA = row;
							colA = col;
							rowB = row - 1;
							colB = col;
							checkCell.updateValue(mRandom);
							mSecond = !mSecond;
							mPlayActivity.updateViews();
							return;
						}
						else
						{
							checkCell = mCells.getCell(row + 1, col);
							rowA = row;
							colA = col;
							rowB = row + 1;
							colB = col;
							checkCell.updateValue(mRandom);
							mSecond = !mSecond;
							mPlayActivity.updateViews();
							return;
						}
				}
			}
		}
		
		if(!isFound){
			int randomRow = mGenerator.nextInt(mSize);
			int randomCol = mGenerator.nextInt(mSize);
			checkCell = mCells.getCell(randomRow, randomCol);
			while(!(checkCell.isValid() == 0)){
				randomRow = mGenerator.nextInt(mSize);
				randomCol = mGenerator.nextInt(mSize);
				checkCell = mCells.getCell(randomRow, randomCol);
			}
			if(randomCol == mSize - 1){
				checkCell = mCells.getCell(randomRow, randomCol);
				checkCell.updateValue(mRandom);
				checkCell = mCells.getCell(randomRow, randomCol - 1);
				checkCell.updateValue(mRandom);
				rowA = randomRow;
				colA = randomCol;
				rowB = randomRow;
				colB = randomCol - 1;
			}
			else{
				checkCell = mCells.getCell(randomRow, randomCol);
				checkCell.updateValue(mRandom);
				checkCell = mCells.getCell(randomRow, randomCol + 1);
				checkCell.updateValue(mRandom);
				rowA = randomRow;
				colA = randomCol;
				rowB = randomRow;
				colB = randomCol + 1;
			}
		}
		isFound = false;
		mSecond = !mSecond;
		mPlayActivity.updateViews();
	}
	
	//Twinkle for Bot's dice number and selected biscuits.
	private void toastAndTwinkle(int mRandom){
		

		mPlayActivity.getHint(4);
	
	}
	
	public void setIsDisappear(boolean disappear){
		isDisappear = disappear;
	}
	
	public void Alert()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		if(mScoreFirst > mScoreSecond) {	
			builder.setMessage("End. You Win!")  
			.setCancelable(false)  
			.setNeutralButton("ok", new DialogInterface.OnClickListener() {  
				public void onClick(DialogInterface dialog, int id) {  
					mPlayActivity.finish();
				}  
			});  
			AlertDialog alert = builder.create();
			alert.show();
		}
		else {
			builder.setMessage("Sorry, You Lose")  
			.setCancelable(false)  
			.setNeutralButton("ok", new DialogInterface.OnClickListener() {  
				public void onClick(DialogInterface dialog, int id) {  
					mPlayActivity.finish();
				}  
			});  
			AlertDialog alert = builder.create();
			alert.show();
		}
	}
	public void Alert2(final Random mGenerator, final int mRandom)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());	
			builder.setMessage("The Bot roll out "+mRandom + "^-^")  
			.setCancelable(false)  
			.setNeutralButton("ok", new DialogInterface.OnClickListener() {  
				public void onClick(DialogInterface dialog, int id) {  
					mPlayActivity.Twinkle();
					robotFinger(mGenerator, mRandom);
					if (mScoreFirst>=5 || mScoreSecond>=5){
						Alert();
					}
				}  
			});  
			AlertDialog alert = builder.create();
			alert.show();
		
		
	}
}

