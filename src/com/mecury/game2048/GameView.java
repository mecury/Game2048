package com.mecury.game2048;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

public class GameView extends GridLayout{

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initGameView();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGameView();
	}

	public GameView(Context context) {
		super(context);
		initGameView();
	}
	public void initGameView(){
		
		setColumnCount(4);                  //���ù涨����
		setBackgroundColor(0XffF4A460);     //���ñ�����ɫ
		
		//������������
		setOnTouchListener(new View.OnTouchListener() {
			
			private float startX,startY,offsetX,offsetY;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					offsetX = event.getX() - startX;
					offsetY = event.getY() - startY;
					
					if (Math.abs(offsetX)>Math.abs(offsetY)) {
					//��X���ϵ�ƫ����������Y���ƫ��������ָ�����һ���
						if (offsetX < -5) {
							//���󻬶�
							swipeLeft();
						}else if (offsetX > 5) {
							//���һ���
							swipeRight();
						}
					}else if (Math.abs(offsetX)<Math.abs(offsetY)) {
						if (offsetY < -5) {
							//���ϻ���
							swipeUp();
						}else if (offsetY > 5) {
							//���»���
							swipeDown();
						}
					}
					break;
				default:
					break;
				}
				return true;
			}
		});
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		int cardWidth = Math.min(w, h)/4;
		
		addcard(cardWidth, cardWidth);
		startGame();
	}
	
	//��ӿ�Ƭ
	private void addcard(int cardwidth, int cardheight){
		Card c;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				c = new Card(getContext());
				c.setNum(0);
				addView(c, cardwidth, cardheight);
				
				cardMap[x][y] = c;
			}
		}
	}
	//��ʼ��Ϸ����
	public void startGame(){
		MainActivity.getMainActivity().clearScore();
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				cardMap[x][y].setNum(0);
			}
		}
		addRandomNum();
		addRandomNum();
	}
	
	//Ϊ��Ƭ��������
	private void addRandomNum(){
		emptyPoint.clear();
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (cardMap[x][y].getNum()<=0) {
					emptyPoint.add(new Point(x,y));
				}
			}
		}
		Point p = emptyPoint.remove((int)(Math.random()*emptyPoint.size()));
		cardMap[p.x][p.y].setNum(Math.random()>0.1?2:4);
	}
	//�й�2048��Ϸ�߼����㷨
	private void swipeLeft(){
		boolean judge = false;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				//���ұ���
				for (int x1 = x+1; x1 < 4; x1++) {
					
					if (cardMap[x1][y].getNum()>0) {
						//��ǰλ�õ������������
						if (cardMap[x][y].getNum()<=0) {
							//��ǰλ��û������
							cardMap[x][y].setNum(cardMap[x1][y].getNum());
							cardMap[x1][y].setNum(0);
							//���x--�Ҷ��ˣ����������ѽ���
							x--;
							judge = true;
						}else if (cardMap[x][y].getNum()==cardMap[x1][y].getNum()) {
							//��ǰλ�ô�����������������������
							cardMap[x][y].setNum(cardMap[x][y].getNum()*2);
							cardMap[x1][y].setNum(0);
							MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
							judge = true;
						}
						break;
					}
				}
				
			}
		}
		if (judge) {
			addRandomNum();
			checkGame();
		}
	}
	private void swipeRight(){
		boolean judge = false;
		for (int y = 0; y < 4; y++) {
			for (int x = 3; x >= 0; x--) {
				//�����������
				for (int x1 = x-1; x1 >=0; x1--) {
					if (cardMap[x1][y].getNum()>0) {
						//��ǰ����x��y�������λ�ô�������
						if (cardMap[x][y].getNum()<=0) {
							cardMap[x][y].setNum(cardMap[x1][y].getNum());
							cardMap[x1][y].setNum(0);
							
							x++;
							judge = true;
						}else if (cardMap[x][y].getNum()==cardMap[x1][y].getNum()) {
							cardMap[x][y].setNum(cardMap[x][y].getNum()*2);
							cardMap[x1][y].setNum(0);
							MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
							judge = true;
						}
						break;
					}
				}
			}
		}
		if (judge) {
			addRandomNum();
			checkGame();
		}
	}
	private void swipeUp(){
		boolean judge = false;
		for (int x = 0; x<4; x++) {
			for (int y = 0; y < 4; y++) {
				//�������±���
				for (int y1 = y+1; y1 <4; y1++) {
					if (cardMap[x][y1].getNum()>0) {
						if (cardMap[x][y].getNum()<=0) {
							cardMap[x][y].setNum(cardMap[x][y1].getNum());
							cardMap[x][y1].setNum(0);
							
							y--;
							judge = true;
						}else if (cardMap[x][y].getNum()==cardMap[x][y1].getNum()) {
							cardMap[x][y].setNum(cardMap[x][y].getNum()*2);
							cardMap[x][y1].setNum(0);
							MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
							judge = true;
						}
						break;
					}
				}
			}
		}
		if (judge) {
			addRandomNum();
			checkGame();
		}
	}
	private void swipeDown(){
		boolean judge = false;
		for (int x = 0; x < 4; x++) {
			for (int y = 3; y >= 0; y--) {
				
				for (int y1 = y-1; y1 >= 0; y1--) {
					if (cardMap[x][y1].getNum()>0) {
						if (cardMap[x][y].getNum()<=0) {
							cardMap[x][y].setNum(cardMap[x][y1].getNum());
							cardMap[x][y1].setNum(0);
							
							y++;
							judge = true;
						}else if (cardMap[x][y].getNum()==cardMap[x][y1].getNum()) {
							cardMap[x][y].setNum(cardMap[x][y].getNum()*2);
							cardMap[x][y1].setNum(0);
							MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
							judge = true;
						}
						break;
					}
				}
			}
		}
		if (judge) {
			addRandomNum();
			checkGame();
		}
	}
	
	//������Ϸ����
	private void checkGame(){
		boolean check = true;
		ALL:
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (cardMap[x][y].getNum()==0||
						x>0&&cardMap[x][y].equals(cardMap[x-1][y])||
						x<3&&cardMap[x][y].equals(cardMap[x+1][y])||
						y>0&&cardMap[x][y].equals(cardMap[x][y-1])||
						y<3&&cardMap[x][y].equals(cardMap[x][y+1])) {
					check = false;
					break ALL;
				}
			}
		}
		//�����cvscore��ֵ��bestscore
		if (MainActivity.getMainActivity().getcvscore()>MainActivity.getMainActivity().getbestscore()) {
			MainActivity.getMainActivity().showBestScore(MainActivity.getMainActivity().getcvscore());
			MainActivity.getMainActivity().save(MainActivity.getMainActivity().getcvscore()+"");
		}
		//��ʾ��Ϸʧ��
		if (check) {
			new AlertDialog.Builder(getContext()).setTitle("��ʾ").setMessage("��Ϸʧ��").setPositiveButton("����һ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					startGame();
				}
			}).show();
		}
		//����ͬ��ֵ����ͬ����ɫ
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
			
				if (cardMap[x][y].getNum() == 2) {
					cardMap[x][y].changeColor(1);
				}else if (cardMap[x][y].getNum() == 4) {
					cardMap[x][y].changeColor(2);
				}else if (cardMap[x][y].getNum() == 8) {
					cardMap[x][y].changeColor(3);
				}else if (cardMap[x][y].getNum() == 16) {
					cardMap[x][y].changeColor(4);
				}else if (cardMap[x][y].getNum() == 32) {
					cardMap[x][y].changeColor(5);
				}else if (cardMap[x][y].getNum() == 64) {
					cardMap[x][y].changeColor(6);
				}else if (cardMap[x][y].getNum() == 128) {
					cardMap[x][y].changeColor(7);
				}else if (cardMap[x][y].getNum() == 256) {
					cardMap[x][y].changeColor(8);
				}else if (cardMap[x][y].getNum() == 512) {
					cardMap[x][y].changeColor(9);
				}else if (cardMap[x][y].getNum() == 1024) {
					cardMap[x][y].changeColor(10);
				}else if (cardMap[x][y].getNum() == 2048) {
					cardMap[x][y].changeColor(11);
				}else if (cardMap[x][y].getNum() == 4096) {
					cardMap[x][y].changeColor(12);
				}else{
					cardMap[x][y].changeColor(13);
				}
			}
		}
	}
	
	//�����������洢16��card
	private Card[][] cardMap =  new Card[4][4];
	//�洢û�����ֵ�cardMap
	private List<Point> emptyPoint = new ArrayList<Point>();
	
	
}
