package edu.ccu.mario;

import java.awt.EventQueue;

/**
 * 
 * @author ACE
 * Ϊ�˷�ֹ���������õ��̵߳��ȳ���
 */
public class Run {
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable(){
			public void run() {
				MyFrame MyMario = new MyFrame();
			}
		});
	}
}
