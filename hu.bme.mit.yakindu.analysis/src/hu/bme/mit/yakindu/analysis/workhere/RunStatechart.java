package hu.bme.mit.yakindu.analysis.workhere;

import java.io.IOException;
import java.util.Scanner;

import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;



public class RunStatechart {
	
	static ExampleStatemachine s;
	
	public static void main(String[] args) throws IOException {
		s = new ExampleStatemachine();
		s.setTimer(new TimerService());
		RuntimeService.getInstance().registerStatemachine(s, 200);
		s.init();
		s.enter();
		
		s.runCycle();
		
		Scanner sc = new Scanner(System.in);
		while(true) {
			String command = sc.nextLine();
			raiseEvent(command);
			s.runCycle();
			print(s);
			if(command.equals("exit")) {
					sc.close();
					System.exit(0);
			}
		}
	}
	
	private static void raiseEvent(String event) {
		switch(event) {
			case "start": s.raiseStart(); break;
			case "white": s.raiseWhite(); break;
			case "black": s.raiseBlack(); break;
		}
	}

	public static void print(IExampleStatemachine s) {
		System.out.println("W = " + s.getSCInterface().getWhiteTime());
		System.out.println("B = " + s.getSCInterface().getBlackTime());
	}
	
	
}
