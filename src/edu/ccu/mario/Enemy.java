package edu.ccu.mario;

import java.awt.image.BufferedImage;

public class Enemy implements Runnable{
	//����
	private int x;
	private int y;
	
	//��ʼ����
	private int startX;
	private int startY;
	
	//����
	private int type;
	
	//�ƶ�����
	private boolean isLeftOrUp = true;
	
	//��ʾͼƬ
	private BufferedImage showImage = null;
	
	//���ϵ���������
	private int upMax = 0;
	
	//���µ���С����
	private int downMax = 0;
	
	//����һ���߳�
	Thread t = new Thread(this);
	
	//��ǰ��ʾ��ͼƬ
	private int imageType = 0;
	
	//���뱳��Ϊ���ж�������������ֹͣ
	private BackGround bg;
	
	//Ģ������
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
	
	//����ʳ�˻����췽��
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
		//�޸���ʾΪ������ͼƬ
		this.showImage = StaticValue.allTriangleImage.get(2);
		this.bg.getAllEnemy().remove(this);
		this.bg.getRemovedEnemy().add(this);
	}
	
	public void reset() {
		//�Ƚ����긴λ
		this.x = this.startX;
		this.y = this.startY;
		//������ʾͼƬ
		if(this.type == 1 ){
			this.showImage = StaticValue.allTriangleImage.get(0);
		}else if(this.type == 2) {
			this.showImage = StaticValue.allFlowerImage.get(0);
		}
	
	}
	
	public void run() {
		while(true) {
			// ���Mario�Ƿ���һ���ϰ�����Ϸ����Ƿ������Ծ
			boolean canLeft = true;
			boolean canRight = true;

			
			//��ʾ��Ģ���Ĵ���
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

					// �����������ƶ�
					if (ob.getX() == this.x + 60
							&& (ob.getY() + 50 > this.y && ob.getY() - 50 < this.y)) {
						canRight = false;
					}

					// �����������ƶ�
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
			

			
			//��ʳ�˻��Ĵ���
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
