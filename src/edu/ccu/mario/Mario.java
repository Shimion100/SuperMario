package edu.ccu.mario;

import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

public class Mario implements Runnable {
	// 坐标
	private int x;
	private int y;
	
	//游戏结束的标记
	private boolean isClear;
	
	//跳跃时间
	private int upTime = 0;
	
	// 加入线程
	private Thread t = null;

	// 速度
	private int xmove = 0;
	private int ymove = 0;

	// 状态
	private String status = null;

	// 显示图片
	private BufferedImage showImage = null;

	// 生命数和分数
	private int score = 0;
	private int life = 0;

	// 移动式当前的图片
	private int moving = 0;

	// 为了检测碰撞要一个BackGround属性,用于得到
	// 所有的障碍物,为了节省资源所以没有创建自己的障碍物列表
	private BackGround bg;

	//死亡标记
	private boolean isDead = false;
	// Mario的构造方法
	public Mario(int x, int y) {
		this.x = x;
		this.y = y;
		this.showImage = StaticValue.allMarioImage.get(0);
		this.life = 3;
		this.score = 0;
		this.status = "right--standing";
		this.t = new Thread(this);
		this.t.start();
	}

	// Mario的移动
	public void leftMove() {
		this.xmove = -5;
		//如果当前是跳跃状态,应该保留跳跃状态
		if(this.status.indexOf("jumping") != -1) {
			this.status = "left--jumping";
		}else {
			this.status = "left--moving";
		}
	}

	public void rightMove() {
		this.xmove = 5;
		if(this.status.indexOf("jumping") != -1) {
			this.status = "right--jumping";
		}else {
			this.status = "right--moving";
		}
	}

	public void leftStop() {
		this.xmove = 0;
		if(this.status.indexOf("jumping") != -1) {
			this.status = "left--jumping";
		}else {
			this.status = "left--standing";
		}
	}

	public void rightStop() {
		this.xmove = 0;
		if(this.status.indexOf("jumping") != -1) {
			this.status = "right--jumping";
		}else {
			this.status = "right--standing";
		}
	}
	
	public void jump() {
		if(this.status.indexOf("jumping") == -1) {
			if(this.status.indexOf("left") != -1) {
				this.status = "left--jumping";
			}else {
				this.status = "right--jumping";
			}
			this.ymove = -5;
			this.upTime = 36;
		}
	}
	
	//下落的方法
	public void down() {
		if(this.status.indexOf("left") != -1) {
			this.status = "left--jumping";
		}else {
			this.status = "right--jumping";
		}
		this.ymove = 5;
	}
	
	public void dead() {
		 //JOptionPane.showMessageDialog(this, "Mario死亡");
		this.life--;
		if(this.life == 0) {
			this.isDead = true;
		}else {
			this.bg.reset();
			this.x = 0;
			this.y = 430;
		}
	}
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public BufferedImage getShowImage() {
		return showImage;
	}

	public void run() {
		while (true) {
			if(this.bg.isFalg()&&this.x>=500) {
				//处于最后一张背景，且到达了旗杆
				this.bg.setOver(true);
				if(this.bg.isDown()) {
					//降旗完毕，Mario开始向城堡移动
					this.status = "right--moving";
					if(this.x < 780)  {
						 this.x += 5;
					}else{
						//游戏结束,已完成了全部场景
						this.isClear = true;
					}
					
				}else{
					if(this.y < 450){
						this.y += 5;
					}else {
						this.status = "right--standing";
					}
				}
				
			}else {
				// 判断是否可以移动
				boolean canLeft = true;
				boolean canRight = true;

				// 标记Mario是否处于一个障碍物的上方，是否可以跳跃
				boolean onLand  = false;

				for (int i = 0; i < this.bg.getAllObstruction().size(); i++) {
					Obstruction ob = this.bg.getAllObstruction().get(i);

					// 不允许向右移动
					if (ob.getX() == this.x + 60
							&& (ob.getY() + 50 > this.y && ob.getY() - 50 < this.y)) {
						if(ob.getType() != 3) {
							canRight = false;
						}
					}

					// 不允许向左移动
					if (ob.getX() == this.x - 60
							&& (ob.getY() + 50 > this.y && ob.getY() - 50 < this.y)) {
						if(ob.getType() != 3) {
							canLeft = false;
						}
					}
					//判断是否处于一格障碍物上面
					if (ob.getY() - 60 == this.y && ob.getX() + 60 > this.x
							&& ob.getX() - 60 < this.x) {
						if(ob.getType() != 3) {
							onLand = true;
						}
					}
					//判断Mario是否顶到了一个砖块
					if (ob.getY() + 60 == this.y && ob.getX() + 50 > this.x
							&& ob.getX() - 50 < this.x) {
						//对于砖块的处理
						if(ob.getType() == 0) {
							this.bg.getAllObstruction().remove(ob);
							//将被溢出的砖块,放到被移出的障碍物
							this.bg.getRemovedObstruction().add(ob);
						}
						//对于?的处理
						if(ob.getType() == 4 || ob.getType() ==3 && upTime > 0) {
							ob.setType(2);
							ob.setImage();
						}
						upTime = 0;
					}
				}
				
				if(this.y > 600) {
					this.dead();
				}
				//加入对敌人的判断
				for(int i = 0; i<this.bg.getAllEnemy().size(); i++) {
					Enemy e = this.bg.getAllEnemy().get(i);
					
					//从左边碰到敌人
					if (e.getX() + 50 >this.x && e.getX() - 50 < this.x
							&& (e.getY() + 60 > this.y && e.getY() - 60 < this.y)) {
						//Mario死亡
						this.dead();
					}
						
					//处于敌人上方
					if (e.getY() - 60 == this.y && e.getX() + 60 > this.x
							&& e.getX() - 60 < this.x) {
						
						if(e.getType() == 1){
							//压到蘑菇
							e.dead();
							this.upTime = 6;
							this.ymove = -5;
						}else if(e.getType() == 2) {
							//压倒食人花
							this.dead();
						}
					}
					
					//从右边碰到敌人
					if (this.x > e.getX() && this.x < e.getX() + 60 
							&& (e.getY() + 60 > this.y && e.getY() - 60 < this.y)) {
						//Mario死亡
						this.dead();
					}
				}
				
				if(onLand && this.upTime == 0){
					if(this.status.indexOf("left") != -1) {
						if(this.xmove != 0) {
							this.status = "left--moving";
						}else{
							this.status = "left--standing";
						}
					}else {
						if(this.xmove != 0) {
							this.status = "right--moving";
						}else{
							this.status = "right--standing";
						}
					}
				}else {
					//表示当前位上升的状态
					if(upTime != 0) {
						upTime--;
					}else {
						this.down();
					}
					this.y += this.ymove;
				}
				if (canLeft && this.xmove < 0 || canRight && this.xmove > 0) {
					if(x < 0){
						this.x = 0;
					}
					this.x += this.xmove;
				}
				
			}
			// 默认向左
			int temp = 0;
			// 向右
			if (this.status.indexOf("left") != -1) {
				temp += 5;
			}
			if (this.status.indexOf("moving") != -1) {
				temp += this.moving;
				this.moving++;
				if (this.moving == 4) {
					this.moving = 0;
				}
			}
			// 改变显示图片
			if(this.status.indexOf("jumping") != -1) {
				temp += 4;
			}
			this.showImage = StaticValue.allMarioImage.get(temp);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setBg(BackGround bg) {
		this.bg = bg;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isDead() {
		return isDead;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public boolean isClear() {
		return isClear;
	}

}
