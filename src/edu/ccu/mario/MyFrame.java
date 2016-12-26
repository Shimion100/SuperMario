package edu.ccu.mario;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyFrame extends JFrame implements KeyListener , Runnable{
	private int width = 900;
	private int height = 600;
	private List<BackGround> allBG = new ArrayList<BackGround>();
	private BackGround nowBG = null;
	private Mario mario = null;
	private Thread t = new Thread(this);
	private Font f = new Font("隶书", Font.ITALIC, 20);
	
	//判断游戏是否已经开始
	private boolean isStart = false;

	public MyFrame() {
		super("Mario");
		this.setSize(width, height);
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation((screenWidth - this.width) / 2,
				(screenHeight - this.height) / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		// 初始化静态量
		new StaticValue().init();
		// 创建所有的场景
		for (int i = 1; i <= 3; i++) {
			this.allBG.add(new BackGround(i, i == 3 ? true : false));
		}
		this.nowBG = this.allBG.get(0);
		this.mario = new Mario(0, 480);
		//调用方法设置Mario的BackGround;
		this.mario.setBg(nowBG);
		t.start();
		this.repaint();
		this.addKeyListener(this);
		this.setVisible(true);
	}

	public void keyPressed(KeyEvent key) {
		// TODO Auto-generated method stub
		//System.out.println(key.getKeyCode());
		/**
		 * up-->38 down-->40 left-->37 right-->39 space-->32
		 */
		if(this.isStart) {
			if(key.getKeyCode() == 37) {
				this.mario.leftMove();
			}
			if(key.getKeyCode() == 39) {
				this.mario.rightMove();
			}
			if(key.getKeyCode() == 32) {
				this.mario.jump();
			}
		}else {
			if(key.getKeyCode() == 32) {
				this.isStart = true;
				this.nowBG.enemyStartMove();
			}
		}
	}

	public void keyReleased(KeyEvent key) {
		// TODO Auto-generated method stub
		if(this.isStart) {
			if(key.getKeyCode() == 37) {
				this.mario.leftStop();
			}
			if(key.getKeyCode() == 39) {
				this.mario.rightStop();
			}
		}
	}

	public void keyTyped(KeyEvent key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		BufferedImage image = new BufferedImage(900, 600,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics g2 = image.getGraphics();
		
		// 绘制背景
		g2.drawImage(this.nowBG.getBgImage(), 0, 0, this);
		
		//显示生命 
		g2.drawString("生命：" + this.mario.getLife(), 750,60);
		
		//绘制敌人
		Iterator iterEnemy = this.nowBG.getAllEnemy().iterator();
		while(iterEnemy.hasNext()) {
			Enemy e = (Enemy) iterEnemy.next();
			g2.drawImage(e.getShowImage(), e.getX(), e.getY(), this);
		}  
		    
		// 绘制障碍物
		Iterator<Obstruction> iter = this.nowBG.getAllObstruction().iterator();
		while (iter.hasNext()) {
			Obstruction ob = iter.next();
			g2.drawImage(ob.getShowImage(), ob.getX(), ob.getY(), this);
		}
		
		//绘制Mario
		g2.drawImage(this.mario.getShowImage(), this.mario.getX(), this.mario
				.getY(), this);
		
		
		
		//二级缓存
		if(this.isStart) {
			g.drawImage(image, 0, 0, this);
		}else {
			g.drawImage(StaticValue.startImage, 0, 0, this);
		}
	}

	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			this.repaint();
			try {
				Thread.sleep(50);
				if(this.mario.getX() >= 840) {
					//切换场景
					this.nowBG = this.allBG.get(this.nowBG.getSort());
					this.mario.setBg(this.nowBG);
					this.mario.setX(0);
					this.nowBG.enemyStartMove();
					this.mario.setLife(3);
					this.mario.setScore(0);
				}
				if(this.mario.isDead()) {
					JOptionPane.showMessageDialog(this, "Mario死亡，游戏结束");
					System.exit(0);
				}
				if(this.mario.isClear()) {
					JOptionPane.showMessageDialog(this, "恭喜您，游戏通关！");
					System.exit(0);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
