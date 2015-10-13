package com.mecury.game2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout{

	public Card(Context context) {
		super(context);
		
		lable = new TextView(getContext());
		lable.setTextSize(32);
		lable.setGravity(Gravity.CENTER);
		lable.setBackgroundColor(0X30FFFFFF);
		
		//下面设置了Layout_wight和Layout_height分别为match_parent（-1代表match_parent，-2代表wrap_content）
		LayoutParams lp = new LayoutParams(-1, -1);
		lp.setMargins(10, 10, 10, 10);       //设置了卡片间的间距
		addView(lable, lp);
		
		setNum(0);
	}

	private int num = 0;
	
	public int getNum(){
		return num;
	}
	public void setNum(int num){
		this.num = num;
		if (num<=0) {
			lable.setText("");
		}else {
			lable.setText(num+"");   //需要转换为字符串
		}
	}
	public void changeColor(int a){
		switch (a) {
		case 1:
			lable.setBackgroundColor(0X55FFFF00);
			break;
		case 2:
			lable.setBackgroundColor(0X55DAA520);
			break;
		case 3:
			lable.setBackgroundColor(0X50FF8C00);
			break;
		case 4:
			lable.setBackgroundColor(0X50D2691E);
			break;
		case 5:
			lable.setBackgroundColor(0X50A0522D);
			break;
		case 6:
			lable.setBackgroundColor(0X50FF7F50);
			break;
		case 7:
			lable.setBackgroundColor(0X50FF6347);
			break;
		case 8:
			lable.setBackgroundColor(0X50B22222);
			break;
		case 9:
			lable.setBackgroundColor(0X508B0000);
			break;
		case 10:
			lable.setBackgroundColor(0X50FF0000);
			break;
		case 11:
			lable.setBackgroundColor(0X5032CD21);
			break;
		case 12:
			lable.setBackgroundColor(0X5500FF00);
			break;
		case 13:
			lable.setBackgroundColor(0X30FFFFFF);
			break;
		
		default:
			break;
		}
	}
	public boolean equals(Card o) {
		return getNum()==o.getNum();
	}
	private TextView lable;
}


