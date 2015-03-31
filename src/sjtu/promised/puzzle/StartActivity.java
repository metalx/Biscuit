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
			       .setMessage("һ����Ϸ����" + "\n" + "1.һ���оŸ����ɣ�ÿ��������һ��ֵ����Ϊ������ֵ��"
			    			+ "\n" + "2.�������ʼ�����Ի��һ��1-8���������ѡȡ�������ڵı��ɣ���������ּӵ�����ֵ��" + "\n"
			    			+ "3.������ֵ�ﵽ9ʱ����ǰ��ҿ��Ե�1�֣��ñ��ɱ��Ե�������9ʱ��ȡ��λ��������ģʽ�Ʒַ�ʽ��ͬ��" 
			    			+ "\n" + "4.���Ե��ı����޷��ټ�����ֵ�����ǿ��Ա�ѡ�С�������ѡ���������Ե��ı���" + "\n"
			    			+ "5.��ĳ��ҷ����ﵽ5ʱ������ʤ����������ģʽ���⣩" + "\n"
			    			+ "\n" +  "��������ģʽ��" + "\n" + "1.����һ����ʱ�䣬�����ʱ������ǰ�����ܶ�÷�" + "\n"
			    			+ "2.���ɳ�ʼֵΪ1-6�����ֵ" + "\n"
			    			+ "3.����ֵ�ﵽ9����2�֣�ʱ��+2�룻����ֵ�ﵽ16����3�֣�ʱ��+3�룻����ֵ�ﵽ10��11����1�֣�ʱ��-3�롣" + "\n"
			    			+ "����������������ֵ��Ϊ1-6������������������ȡ����ֵ�ĸ�λ����" + "\n"
			    			+ "3.ʱ���������Ϸ������" + "\n"
			    			+ "\n" + "���������п�����������������" + "\n"
			    			+ "\n" + "�ġ����ڱ��ɴ���ս" + "\n"
			    			+ "�汾��v1.0" + "\n"
			    			+ "�����飺С��������" + "\n"
			    			+ "������Ա�����͡�һ����������ͭ��promised\n"
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