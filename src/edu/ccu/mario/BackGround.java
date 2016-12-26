package edu.ccu.mario;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BackGround {
	//����ͼƬ
	private BufferedImage bgImage  = null;
	//�������
	private int sort;
	//�Ƿ�Ϊ���һ������
	private boolean falg;
	//��Ϸ�Ƿ����,Mario�Ƿ񵽴����
	private boolean isOver;
	//����һ����������ı��
	private boolean isDown;
	//��List�������ϰ���͵���
	private List<Enemy> allEnemy = new ArrayList<Enemy>();
	private List<Obstruction> allObstruction  = new ArrayList<Obstruction>();
	private List<Enemy> removedEnemy = new ArrayList<Enemy>();
	private List<Obstruction> removedObstruction = new ArrayList<Obstruction>();
	
	//���췽��������ʼ��
	public BackGround(int sort, boolean flag) {
		this.sort = sort;
		this.falg = flag;
		//���Ϊ���ĳ���
		if(this.falg == true) {
			this.bgImage = StaticValue.endImage;
		}else {
			this.bgImage = StaticValue.bgImage;
		}
		if(this.sort == 1) {
			//������������
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
			//�ܵ�
			this.allObstruction.add(new Obstruction(660, 480, 8,this));
			this.allObstruction.add(new Obstruction(720, 480, 7,this));
			this.allObstruction.add(new Obstruction(660, 540, 6,this));
			this.allObstruction.add(new Obstruction(720, 540, 5,this));
			 
			//���Ƶ���
			this.allEnemy.add(new Enemy(600, 480, true, 1, this));
			this.allEnemy.add(new Enemy(690, 540, true, 2, 420, 540, this));
			
			//�������Ե�ש��
			this.allObstruction.add(new Obstruction(660, 240, 3,this));
		}
	
		if(this.sort == 2) {
			//������������
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
			//������������
			this.falg = true;
			for(int i = 0; i < 15; i++) {      
				this.allObstruction.add(new Obstruction(i * 60, 510, 9,this));
			}
			this.allObstruction.add(new Obstruction(553, 168, 11,this));
		}
	}
	
	//�����������Mario��һ�����õķ���,�����ϰ���͵��ˣ������״̬����
	public void reset() {
		//�������Ƴ��Ķ��������� 
		this.allEnemy.addAll( this.removedEnemy);
		this.allObstruction.addAll(this.removedObstruction);
		//�������еĵ��˺��ϰ��� 
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
