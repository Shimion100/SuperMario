package edu.ccu.mario;

import java.awt.image.BufferedImage;

public class Obstruction implements Runnable{
	//坐标
	private int x;
	private int y;
	
	//线程，处理降旗的操作
	private Thread t = new Thread(this);
	
	//要得到Mario是否到达旗杆，先得到背景
	private BackGround bg;
	
	//当前的类型：砖块、铁块、问号、水管......
	private int type;
	
	//初始的类型：初始为问号，现在可能是铁块
	private int startType;
	
	//图像
	private BufferedImage showImage = null;
	
	//重置的方法
	public void reset() {
		this.type = this.startType;
		this.setImage();
	}
	
	//更具状态类型改变显示图片
	public void setImage() {
		this.showImage = StaticValue.allObstructionImage.get(this.type);
	}
	//构造方法
	public Obstruction(int x, int y, int type, BackGround bg) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.startType = type;
		this.bg = bg;
		this.setImage();
		if(this.type == 11) {
			this.t.start();
		}
	}

	public BufferedImage getShowImage() {
		return showImage;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void run() {
		// TODO Auto-generated method stub
		while(true){
			if(this.bg.isOver()) {
				if(this.y < 425)  {
					this.y += 10;
				}else{
					this.bg.setDown(true);
				}
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
