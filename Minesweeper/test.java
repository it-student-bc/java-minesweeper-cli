package Minesweeper;

import java.security.SecureRandom;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.time.StopWatch;

public class test {

	public static void main(String[] args) throws Exception {
		
		StopWatch a = new StopWatch();
		a.start();
		TimeUnit.SECONDS.sleep(2);
		a.stop();
		
		System.out.println(a);
		// TODO Auto-generated method stub

		/*
		 * StopWatch a = new StopWatch(); String x; Scanner input = new
		 * Scanner(System.in);
		 * 
		 * a.start(); System.out.println('\u25A0'); System.out.print("Enter String: ");
		 * x = input.nextLine(); System.out.println(x); a.stop();
		 * System.out.println(a.getTime());
		 */
		/*
		boolean lose = false;
		boolean win = false;
		Scanner input = new Scanner(System.in);

		MineField y = new MineField(8, 10, 10);
		System.out.println(y);
		System.out.println(y.reveal());
		
		while(!lose  && !win) {
			System.out.println(y);
			System.out.println("\nR: Reveal");
			System.out.println("F: Flag");
			System.out.println("U:unflag ");
			System.out.print("enter input:");
			String s = input.next().toLowerCase();
			
			switch(s) {
			
				case "r":
					lose = y.uncover();
				break;
				
				case "f":
					win = y.flag();
				break;
				
				case "u":
					y.unFlag();
				break;
			}
				
				
			
		}
		// 55 / 10 --> 10 is col so you get 5
		// 55 % 10 --> 10 is col so you get 5 so -1 both
		/*
		 if(row == 0 && col == 0)
		 x+1
		 x and y+1
		 y+1 
		  if(row == 10 && col == 0)
		  x+1
		  y-1
		  
		  
		  if col == 0
		  x+1
		  
		  if row==0
		  y+1
		  
		  if col == 10
		  x-1
		  
		  if row==10
		  y-1
		  
		 */

	}

	public static void clearScreen() {  
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	}  
	
}
