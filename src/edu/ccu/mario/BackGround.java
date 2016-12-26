package edu.ccu.mario;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BackGround {
	//背景图片
	private BufferedImage bgImage  = null;
	//场景编号
	private int sort;
	//是否为最后一个场景
	private boolean falg;
	//游戏是否结束,Mario是否到达旗杆
	private boolean isOver;
	//定义一个降旗结束的标记
	private boolean isDown;
	//用List来保存障碍物和敌人
	private List<Enemy> allEnemy = new ArrayList<Enemy>();
	private List<Obstruction> allObstruction  = new ArrayList<Obstruction>();
	private List<Enemy> removedEnemy = new ArrayList<Enemy>();
	private List<Obstruction> removedObstruction = new ArrayList<Obstruction>();
	
	//构造方法用来初始化
	public BackGround(int sort, boolean flag) {
		this.sort = sort;
		this.falg = flag;
		//如果为最后的场景
		if(this.falg == true) {
			this.bgImage = StaticValue.endImage;
		}else {
			this.bgImage = StaticValue.bgImage;
		}
		if(this.sort == 1) {
			//建立整个地面
			this.falg = false;
			for(int i = 0; i < 15; i++) {
				this.allObstruction.add(new Obstruction(i * 60, 540, 9,this));
			}
			this.allObstruction.add(new Obstruction(120, 360, 4,this));
			this.allObstruction.add(new Obstruction(300, 360, 0,this));
			this.allObstruction.add(new Obstruction(360, 360, 4,this));
			this.allObstruction.add(new Obstruction(420, 360, 0,this));
			this.allObstruction.add(new Obstruction(480, 360, 4,this));
			this.allObstruction.add(new Obstruction(540, 360, 0,this));
			this.allObstruction.add(new Obstruction(420, 180, 4,this));
			//管道
			this.allObstruction.add(new Obstruction(660, 480, 8,this));
			this.allObstruction.add(new Obstruction(720, 480, 7,this));
			this.allObstruction.add(new Obstruction(660, 540, 6,this));
			this.allObstruction.add(new Obstruction(720, 540, 5,this));
			 
			//绘制敌人
			this.allEnemy.add(new Enemy(600, 480, true, 1, this));
			this.allEnemy.add(new Enemy(690, 540, true, 2, 420, 540, this));
			
			//加入隐性的砖块
			this.allObstruction.add(new Obstruction(660, 240, 3,this));
		}
	
		if(this.sort == 2) {
			//建立整个地面
			this.falg = false;
			for(int i = 0; i < 15; i++) {
				if(i == 10) {
					continue;
				}
				this.allObstruction.add(new Obstruction(i * 60, 540, 9,this));
			}
			
			this.allObstruction.add(new Obstruction(60, 420, 8,this));
			this.allObstruction.add(new Obstruction(120, 420, 7,this));
			this.allObstruction.add(new Obstruction(60, 480, 6,this));
			this.allObstruction.add(new Obstruction(120, 480, 5,this));
			this.allObstruction.add(new Obstruction(60, 540, 6,this));
			this.allObstruction.add(new Obstruction(120, 540, 5,this));
			
			this.allObstruction.add(new Obstruction(300, 360, 8,this));
			this.allObstruction.add(new Obstruction(360, 360, 7,this));
			this.allObstruction.add(new Obstruction(300, 420, 6,this));
			this.allObstruction.add(new Obstruction(360, 420, 5,this));
			this.allObstruction.add(new Obstruction(300, 480, 6,this));
			this.allObstruction.add(new Obstruction(360, 480, 5,this));
			this.allObstruction.add(new Obstruction(300, 540, 6,this));
			this.allObstruction.add(new Obstruction(360, 540, 5,this));
		}
		if(sort == 3) {
			//建立整个地面
			this.falg = true;
			for(int i = 0; i < 15; i++) {      
				this.allObstruction.add(new Obstruction(i * 60, 510, 9,this));
			}
			this.allObstruction.add(new Obstruction(553, 168, 11,this));
		}
	}
	
	//如果是重生的Mario用一个重置的方法,重置障碍物和敌人，坐标和状态重置
	public void reset() {
		//将所有移出的东西还会来 
		this.allEnemy.addAll( this.removedEnemy);
		this.allObstruction.addAll(this.removedObstruction);
		//重置所有的敌人和障碍物 
		for(int i = 0; i < this.allEnemy.size(); i++) {
			this.allEnemy.get(i).reset();
		}
		for(int i = 0; i < this.allObstruction.size(); i++) {
			this.allObstruction.get(i).reset();
		}
		
	}
	
	public void enemyStartMove() {
		for(int i = 0; i < this.allEnemy.size(); i++) {
			this.allEnemy.get(i).startMove();
		}
	}
	
	public BufferedImage getBgImage() {
		return bgImage;
	}

	public List<Obstruction> getAllObstruction() {
		return allObstruction;
	}

	public List<Obstruction> getRemovedObstruction() {
		return removedObstruction;
	}

	public int getSort() {
		return sort;
	}

	public List<Enemy> getAllEnemy() {
		return allEnemy;
	}

	public List getRemovedEnemy() {
		return removedEnemy;
	}

	public boolean isFalg() {
		return falg;
	}

	public void setFalg(boolean falg) {
		this.falg = falg;
	}

	public boolean isOver() {
		return isOver;
	}

	public void setOver(boolean isOver) {
		this.isOver = isOver;
	}

	public boolean isDown() {
		return isDown;
	}

	public void setDown(boolean isDown) {
		this.isDown = isDown;
	}
}
