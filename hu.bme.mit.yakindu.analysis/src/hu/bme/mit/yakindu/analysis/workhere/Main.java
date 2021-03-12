package hu.bme.mit.yakindu.analysis.workhere;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.base.types.Direction;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;
import org.yakindu.sct.model.stext.stext.EventDefinition;
import org.yakindu.sct.model.stext.stext.VariableDefinition;


import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		
		// Reading model
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iteratorEvent = s.eAllContents();
		TreeIterator<EObject> iteratorVar = s.eAllContents();

		
		System.out.println("package hu.bme.mit.yakindu.analysis.workhere;\r\n" + 
				"import java.io.IOException;\r\n" + 
				"import java.util.Scanner;\r\n" + 
				"\r\n" + 
				"import hu.bme.mit.yakindu.analysis.RuntimeService;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.TimerService;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"public class RunStatechart {\r\n" + 
				"	\r\n" + 
				"	static ExampleStatemachine s;\r\n" + 
				"	\r\n" + 
				"	public static void main(String[] args) throws IOException {\r\n" + 
				"		s = new ExampleStatemachine();\r\n" + 
				"		s.setTimer(new TimerService());\r\n" + 
				"		RuntimeService.getInstance().registerStatemachine(s, 200);\r\n" + 
				"		s.init();\r\n" + 
				"		s.enter();\r\n" + 
				"		\r\n" + 
				"		s.runCycle();\r\n" + 
				"		\r\n" + 
				"		Scanner sc = new Scanner(System.in);\r\n" + 
				"		while(true) {\r\n" + 
				"			String command = sc.nextLine();\r\n" + 
				"			raiseEvent(command);\r\n" + 
				"			s.runCycle();\r\n" + 
				"			print(s);\r\n" + 
				"			if(command.equals(\"exit\")) {\r\n" + 
				"					sc.close();\r\n" + 
				"					System.exit(0);\r\n" + 
				"			}\r\n" + 
				"		}\r\n" + 
				"	}\r\n" + 
				"	\r\n");
		
		
		//////////////////////////////////////////////////////////////////////////

		System.out.println("	private static void raiseEvent(String event) {\r\n" + 
				"		switch(event) {");
		while (iteratorEvent.hasNext()) {
			EObject content = iteratorEvent.next();
						
			if(content instanceof EventDefinition) {
				EventDefinition e = (EventDefinition) content;
				if(e.getDirection() == Direction.IN) { 
				//System.out.println(e.getName());
					System.out.println("			case \""+e.getName()+"\": s.raise"+e.getName()+"(); break;");
				}	
			}
		}
		System.out.println("		}\r\n"+
					 		"	}");
		//////////////////////////////////////////////////////////////////////////
	
		System.out.println("	public static void print(IExampleStatemachine s) {");
		while (iteratorVar.hasNext()) {
			EObject content = iteratorVar.next();
			
			if(content instanceof VariableDefinition) {
				VariableDefinition v = (VariableDefinition) content;
				//System.out.println(v.getName());
				System.out.println("		System.out.println(\""+v.getName()+" = \" + s.getSCInterface().get"+v.getName()+"();");
			}
		}
		System.out.println("	}");
		//////////////////////////////////////////////////////////////////////////
		System.out.println("}");
		
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
	
	
}
