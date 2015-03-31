package sjtu.promised.puzzle;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Random;

public class StartActivity extends Activity {
	
	public static boolean soundOn  ;
	
	private ImageButton mButton1;
	private ImageButton mButton2;
	private ImageButton mButton3;
	private ImageButton mSet;
	private ImageButton mHelp;
	OnClickListener btnClick;
	OnClickListener btnClick2;
	OnClickListener btnClick3;
	OnClickListener btnClick4;
	OnClickListener btnClick5;
	

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
                
        mButton1 = (ImageButton)findViewById(R.id.imageButton1 );
		mButton2 = (ImageButton)findViewById(R.id.imageButton2 );
		mButton3 = (ImageButton)findViewById(R.id.imageButton3 );
		mSet = (ImageButton)findViewById(R.id.set);
		mHelp = (ImageButton)findViewById(R.id.help);
		final Intent intent3 = new Intent(); 
		intent3.setClass(this, PlayActivitySingle.class);
		
		final Intent intent = new Intent(); 
		intent.setClass(this, PlayActivity.class);
		
		final Intent intent2 = new Intent();
		intent2.setClass(this, Survive.class);
		
		btnClick3 = new OnClickListener(){
			public void onClick(View v){
				startActivity(intent3);
			}
		};
	
		btnClick = new OnClickListener(){
			public void onClick(View v){
				startActivity(intent);
			}
		};	
		btnClick2 = new OnClickListener(){
			public void onClick(View v){
				startActivity(intent2);
			}
		};
		
		btnClick4 = new OnClickListener(){
			public void onClick(View v){
				
				new AlertDialog.Builder(StartActivity.this).setTitle("Sound Effect").setItems(
					     new String[] { "Open Sound Effect?"}, null)
					     .setPositiveButton("On", new DialogInterface.OnClickListener() {  
			    	           public void onClick(DialogInterface dialog, int id) {  
			     	        	  soundOn = true;
			     	           }  
			     	       })
					     .setNegativeButton("Off", new DialogInterface.OnClickListener() {  
			    	           public void onClick(DialogInterface dialog, int id) {  
			    	        	   soundOn = false;
			     	           }  
			     	       }).show();
			     	       
        		}
		};
		btnClick5 = new OnClickListener(){
			public void onClick(View v){
				AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
				builder.setTitle("Help and About") 
			       .setMessage("一、游戏规则：" + "\n" + "1.一共有九个饼干，每个饼干有一个值，称为‘饼干值’"
			    			+ "\n" + "2.点击‘开始’可以获得一个1-8的随机数。选取两个相邻的饼干，将这个数字加到饼干值上" + "\n"
			    			+ "3.当饼干值达到9时，当前玩家可以得1分，该饼干被吃掉。超过9时则取个位数（生存模式计分方式不同）" 
			    			+ "\n" + "4.被吃掉的饼干无法再加上数值，但是可以被选中。不允许选中两个被吃掉的饼干" + "\n"
			    			+ "5.当某玩家分数达到5时则该玩家胜利。（生存模式除外）" + "\n"
			    			+ "\n" +  "二、生存模式：" + "\n" + "1.给予一分钟时间，玩家在时间用完前尽可能多得分" + "\n"
			    			+ "2.饼干初始值为1-6的随机值" + "\n"
			    			+ "3.饼干值达到9，得2分，时间+2秒；饼干值达到16，得3分，时间+3秒；饼干值达到10或11，减1分，时间-3秒。" + "\n"
			    			+ "发生以上情况后饼干值变为1-6的随机数，其他情况则取饼干值的个位数。" + "\n"
			    			+ "3.时间用完后，游戏结束。" + "\n"
			    			+ "\n" + "三、设置中可以设置声音的有无" + "\n"
			    			+ "\n" + "四、关于饼干大作战" + "\n"
			    			+ "版本：v1.0" + "\n"
			    			+ "制作组：小安工作室" + "\n"
			    			+ "制作人员：侠客、一隹、氢氧化铜、promised\n"
			    			)  
			       .setNeutralButton("back", new DialogInterface.OnClickListener() {  
			           public void onClick(DialogInterface dialog, int id) {  
			           }  
			       });  
		    	AlertDialog alert = builder.create();
		        alert.show();
				
			}
		};		
		
		mButton1.setOnClickListener(btnClick3);
		mButton2.setOnClickListener(btnClick);
		mButton3.setOnClickListener(btnClick2);
		mSet.setOnClickListener(btnClick4);
		mHelp.setOnClickListener(btnClick5);

    }

}