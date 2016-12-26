package edu.ccu.mario;

import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

public class Mario implements Runnable {
	// ����
	private int x;
	private int y;
	
	//��Ϸ�����ı��
	private boolean isClear;
	
	//��Ծʱ��
	private int upTime = 0;
	
	// �����߳�
	private Thread t = null;

	// �ٶ�
	private int xmove = 0;
	private int ymove = 0;

	// ״̬
	private String status = null;

	// ��ʾͼƬ
	private BufferedImage showImage = null;

	// �������ͷ���
	private int score = 0;
	private int life = 0;

	// �ƶ�ʽ��ǰ��ͼƬ
	private int moving = 0;

	// Ϊ�˼����ײҪһ��BackGround����,���ڵõ�
	// ���е��ϰ���,Ϊ�˽�ʡ��Դ����û�д����Լ����ϰ����б�
	private BackGround bg;

	//�������
	private boolean isDead = false;
	// Mario�Ĺ��췽��
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

	// Mario���ƶ�
	public void leftMove() {
		this.xmove = -5;
		//�����ǰ����Ծ״̬,Ӧ�ñ�����Ծ״̬
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
	
	//����ķ���
	public void down() {
		if(this.status.indexOf("left") != -1) {
			this.status = "left--jumping";
		}else {
			this.status = "right--jumping";
		}
		this.ymove = 5;
	}
	
	public void dead() {
		 //JOptionPane.showMessageDialog(this, "Mario����");
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
				//�������һ�ű������ҵ��������
				this.bg.setOver(true);
				if(this.bg.isDown()) {
					//������ϣ�Mario��ʼ��Ǳ��ƶ�
					this.status = "right--moving";
					if(this.x < 780)  {
						 this.x += 5;
					}else{
						//��Ϸ����,�������ȫ������
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
				// �ж��Ƿ�����ƶ�
				boolean canLeft = true;
				boolean canRight = true;

				// ���Mario�Ƿ���һ���ϰ�����Ϸ����Ƿ������Ծ
				boolean onLand  = false;

				for (int i = 0; i < this.bg.getAllObstruction().size(); i++) {
					Obstruction ob = this.bg.getAllObstruction().get(i);

					// �����������ƶ�
					if (ob.getX() == this.x + 60
							&& (ob.getY() + 50 > this.y && ob.getY() - 50 < this.y)) {
						if(ob.getType() != 3) {
							canRight = false;
						}
					}

					// �����������ƶ�
					if (ob.getX() == this.x - 60
							&& (ob.getY() + 50 > this.y && ob.getY() - 50 < this.y)) {
						if(ob.getType() != 3) {
							canLeft = false;
						}
					}
					//�ж��Ƿ���һ���ϰ�������
					if (ob.getY() - 60 == this.y && ob.getX() + 60 > this.x
							&& ob.getX() - 60 < this.x) {
						if(ob.getType() != 3) {
							onLand = true;
						}
					}
					//�ж�Mario�Ƿ񶥵���һ��ש��
					if (ob.getY() + 60 == this.y && ob.getX() + 50 > this.x
							&& ob.getX() - 50 < this.x) {
						//����ש��Ĵ���
						if(ob.getType() == 0) {
							this.bg.getAllObstruction().remove(ob);
							//���������ש��,�ŵ����Ƴ����ϰ���
							this.bg.getRemovedObstruction().add(ob);
						}
						//����?�Ĵ���
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
				//����Ե��˵��ж�
				for(int i = 0; i<this.bg.getAllEnemy().size(); i++) {
					Enemy e = this.bg.getAllEnemy().get(i);
					
					//�������������
					if (e.getX() + 50 >this.x && e.getX() - 50 < this.x
							&& (e.getY() + 60 > this.y && e.getY() - 60 < this.y)) {
						//Mario����
						this.dead();
					}
						
					//���ڵ����Ϸ�
					if (e.getY() - 60 == this.y && e.getX() + 60 > this.x
							&& e.getX() - 60 < this.x) {
						
						if(e.getType() == 1){
							//ѹ��Ģ��
							e.dead();
							this.upTime = 6;
							this.ymove = -5;
						}else if(e.getType() == 2) {
							//ѹ��ʳ�˻�
							this.dead();
						}
					}
					
					//���ұ���������
					if (this.x > e.getX() && this.x < e.getX() + 60 
							&& (e.getY() + 60 > this.y && e.getY() - 60 < this.y)) {
						//Mario����
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
					//��ʾ��ǰλ������״̬
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
			// Ĭ������
			int temp = 0;
			// ����
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
			// �ı���ʾͼƬ
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
