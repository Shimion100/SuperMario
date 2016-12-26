package edu.ccu.mario;

import java.awt.image.BufferedImage;

public class Enemy implements Runnable{
	//坐标
	private int x;
	private int y;
	
	//初始坐标
	private int startX;
	private int startY;
	
	//类型
	private int type;
	
	//移动方向
	private boolean isLeftOrUp = true;
	
	//显示图片
	private BufferedImage showImage = null;
	
	//向上的最大的坐标
	private int upMax = 0;
	
	//向下的最小坐标
	private int downMax = 0;
	
	//定义一个线程
	Thread t = new Thread(this);
	
	//当前显示的图片
	private int imageType = 0;
	
	//加入背景为了判断其碰到背景后停止
	private BackGround bg;
	
	//蘑菇敌人
	public Enemy(int x, int y, boolean isLeft, int type, BackGround bg) {
		this.x = x;
		this.y = y;
		this.startX = x;
		this.startY = y;
		this.isLeftOrUp = isLeft;
		this.type = type;
		this.bg = bg;
		if(this.type == 1) {
			this.showImage = StaticValue.allTriangleImage.get(0);
		}
		t.start();
		t.suspend();
	}
	
	//创建食人花构造方法
	public Enemy(int x, int y, boolean isUp, int type, int upMax, int downMax, BackGround bd) {
		this.x = x;
		this.y = y;
		this.startX = x;
		this.startY = y;
		this.isLeftOrUp = isUp;
		this.type = type;
		this.upMax = upMax;
		this.downMax = downMax;
		this.bg = bg; 
		if(this.type == 2) {
			this.showImage = StaticValue.allFlowerImage.get(0);
		}
		t.start();
		t.suspend();
	}
	
	
	public void dead() {
		//修改显示为死亡的图片
		this.showImage = StaticValue.allTriangleImage.get(2);
		this.bg.getAllEnemy().remove(this);
		this.bg.getRemovedEnemy().add(this);
	}
	
	public void reset() {
		//先将坐标复位
		this.x = this.startX;
		this.y = this.startY;
		//重置显示图片
		if(this.type == 1 ){
			this.showImage = StaticValue.allTriangleImage.get(0);
		}else if(this.type == 2) {
			this.showImage = StaticValue.allFlowerImage.get(0);
		}
	
	}
	
	public void run() {
		while(true) {
			// 标记Mario是否处于一个障碍物的上方，是否可以跳跃
			boolean canLeft = true;
			boolean canRight = true;

			
			//表示对蘑菇的处理
			if(this.type == 1) {
				if(this.isLeftOrUp) {
					this.x -= 2;
				}else {
					this.x += 2;
				}
				if(this.imageType == 0) {
					this.imageType = 1;
				}else {
					this.imageType = 0;
				}
				for (int i = 0; i < this.bg.getAllObstruction().size(); i++) {
					Obstruction ob = this.bg.getAllObstruction().get(i);

					// 不允许向右移动
					if (ob.getX() == this.x + 60
							&& (ob.getY() + 50 > this.y && ob.getY() - 50 < this.y)) {
						canRight = false;
					}

					// 不允许向左移动
					if (ob.getX() == this.x - 60
							&& (ob.getY() + 50 > this.y && ob.getY() - 50 < this.y)) {
						canLeft = false;
					}
				}
				
				
				if(this.isLeftOrUp && !canLeft || this.x==0) {
					this.isLeftOrUp = false;
				}else if(!this.isLeftOrUp && !canRight || this.x == 840){
					this.isLeftOrUp = true;
				}
				this.showImage = StaticValue.allTriangleImage.get(this.imageType);
			}
			

			
			//对食人花的处理
			if(this.type == 2) {
				if(this.isLeftOrUp) {
					this.y -= 2;
				}else {
					this.y += 2;
				}
				if(this.imageType == 0) {
					this.imageType = 1;
				}else {
					this.imageType = 0;
				}
				if(this.isLeftOrUp && this.y == this.upMax) {
					this.isLeftOrUp = false;
				}
				if(!this.isLeftOrUp && this.y == this.downMax) {
					this.isLeftOrUp = true;
				}
				this.showImage = StaticValue.allFlowerImage.get(this.imageType);
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	public void startMove() {
		this.t.resume();
	}
	
}
