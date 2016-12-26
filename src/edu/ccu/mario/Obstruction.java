package edu.ccu.mario;

import java.awt.image.BufferedImage;

public class Obstruction implements Runnable{
	//����
	private int x;
	private int y;
	
	//�̣߳�������Ĳ���
	private Thread t = new Thread(this);
	
	//Ҫ�õ�Mario�Ƿ񵽴���ˣ��ȵõ�����
	private BackGround bg;
	
	//��ǰ�����ͣ�ש�顢���顢�ʺš�ˮ��......
	private int type;
	
	//��ʼ�����ͣ���ʼΪ�ʺţ����ڿ���������
	private int startType;
	
	//ͼ��
	private BufferedImage showImage = null;
	
	//���õķ���
	public void reset() {
		this.type = this.startType;
		this.setImage();
	}
	
	//����״̬���͸ı���ʾͼƬ
	public void setImage() {
		this.showImage = StaticValue.allObstructionImage.get(this.type);
	}
	//���췽��
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
