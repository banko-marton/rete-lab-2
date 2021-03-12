package hu.bme.mit.yakindu.analysis.workhere;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;

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
		TreeIterator<EObject> iterator = s.eAllContents();
	
		List<State> namelessStates = new ArrayList<>();
		List<String> statenames = new ArrayList<>();
		
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof State) {
				State state = (State) content;
				
				if(state.getName() == null) {
					namelessStates.add(state);
				} else {	
					statenames.add(state.getName());
					System.out.println(state.getName());
				}	
			}
		}
		//Nevtelen állapotok elnevezése és kiírása
		int idx = 0;
		for(State state: namelessStates) {
			boolean found = false;
			while(!found) {
				String newname = "Unnamed" + idx;
				if(!statenames.contains(newname)) {
					state.setName(newname);
					statenames.add(newname);
					System.out.println(state.getName());
					found = true;
				}
				idx++;
			}
		}
		
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
