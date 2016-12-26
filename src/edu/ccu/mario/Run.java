package edu.ccu.mario;

import java.awt.EventQueue;

/**
 * 
 * @author ACE
 * 为了防止意外所以用的线程调度程序
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
