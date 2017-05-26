package mining;

import java.util.Iterator;
import java.util.LinkedList;

import data.Attribute;
import data.ContinuousAttribute;
import data.Data;
import data.DiscreteAttribute;
import data.EmptySetException;
import utility.*;
public class FrequentPatternMiner {

	public static LinkedList<FrequentPattern> frequentPatternDiscovery(Data data, float minSup) throws EmptySetException{
		Queue<FrequentPattern> fpQueue = new Queue<FrequentPattern>();
		LinkedList<FrequentPattern> outputFP = new LinkedList<FrequentPattern>();
		
		for (int i = 0; i < data.getNumberOfAttributes(); i++) {
			Attribute currentAttribute = data.getAttribute(i);
			if(currentAttribute instanceof DiscreteAttribute){
				//currentattribute genera item discreti
			
			
			for (int j = 0; j < ((DiscreteAttribute) currentAttribute).getNumberOfDistinctValues(); j++) {
				DiscreteItem item = new DiscreteItem((DiscreteAttribute) currentAttribute,
						((DiscreteAttribute) currentAttribute).getValue(j));
				FrequentPattern fp = new FrequentPattern();
				fp.addItem(item);
				fp.setSupport(FrequentPatternMiner.computeSupport(data, fp));
				if (fp.getSupport() >= minSup) { // 1-FP CANDIDATE
					fpQueue.enqueue(fp);
					// System.out.println(fp);
					outputFP.add(fp);
				}

			}
			}
			else{
				//continuous attribute
				//currenAttribute genera item continui
				ContinuousAttribute continuousAttribute=(ContinuousAttribute)currentAttribute;
				Iterator<Float>it=continuousAttribute.iterator();  
				if(it.hasNext()) {  
					float inf=it.next();   
					while(it.hasNext()){    
						float sup=it.next();    
						ContinuousItem item;    
						continuousAttribute=(ContinuousAttribute)currentAttribute;
						if(it.hasNext())  
							item=new ContinuousItem(continuousAttribute,new Interval(inf, sup));    
						else      
							item=new ContinuousItem(continuousAttribute, new Interval(inf, sup+0.01f*(sup-inf)));    
							inf=sup;    
							FrequentPattern fp=new FrequentPattern();
							fp.addItem(item);           
							fp.setSupport(FrequentPatternMiner.computeSupport(data,fp));    
							if(fp.getSupport()>=minSup){ // 1-FP CANDIDATE      
								fpQueue.enqueue(fp);      
								outputFP.add(fp);    
								}   
							}  
					} 
				} 
							
					
				
			

		}
		
		try{
		outputFP = expandFrequentPatterns(data, minSup, fpQueue, outputFP);
		}catch (EmptyQueueException e) {
			System.err.println(e.getMessage());
		}
		if (outputFP.isEmpty()){ //solleva l'eccezione se l'outputFP è vuoto
			throw new EmptySetException();
		}
		return outputFP;
	}

	private static LinkedList<FrequentPattern> expandFrequentPatterns(Data data, float minSup, Queue<FrequentPattern> fpQueue, LinkedList<FrequentPattern> outputFP) throws EmptyQueueException{
		// TO DO
		//in outputFP ci sono i pattern di lunghezza 1 frequenti
		//1) crearsi un array in cui copia i riferimenti agli item 
		int t=0;
		//for (Puntatore p = outputFP.firstList(); !outputFP.endList(p); p = outputFP.succ(p), t++);
		Item itemPerRaffinamento[]=new Item[outputFP.size()];
		t=0;
		Queue<FrequentPattern> tempQueue=(Queue<FrequentPattern>) fpQueue.clone();
		while (!tempQueue.isEmpty()) {
			FrequentPattern fp=(FrequentPattern) tempQueue.first();
			tempQueue.dequeue();
			itemPerRaffinamento[t]=((FrequentPattern)fp).getItem(0);
			t++;
		}
		
		
		// Per ogni pattern fp della coda
		// provo a cotruire i raffinamenti di fp e se sono frequenti li aggiungo sia alla coda sia alla lista
		// come trovo i raffinamenti di fp?
		// per ogni item contenuto nell'array itemPerRaffinamento, vedo se quell'item fa già parte di fp, se non dovesse far part, provo a costruire un nuovo
		// pattern newfp che colleziona tutti gli atem di fp + l'item che ha selazionato dall'array; si calcola supporto di newfp e se è maggiore di min sup aggiunge
		// newfp alla lista e alla coda
		
		while(!fpQueue.isEmpty()){
			FrequentPattern fp=(FrequentPattern) fpQueue.first();
			fpQueue.dequeue();
			for(int i=0;i<itemPerRaffinamento.length;i++){
				boolean elemItemUguali=false;
				for(int k=0;k<fp.getPatternLength();k++){
						if(itemPerRaffinamento[i] instanceof DiscreteItem && (fp.getItem(k)) instanceof DiscreteItem){
							if(itemPerRaffinamento[i].getAttribute().getName().equals((String)fp.getItem(k).getAttribute().getName())){
								elemItemUguali=true;
								break;
							}
						}
						if((itemPerRaffinamento[i] instanceof ContinuousItem) && (fp.getItem(k) instanceof ContinuousItem)){
							if(itemPerRaffinamento[i].getAttribute().getName().equals((String)fp.getItem(k).getAttribute().getName())){
								elemItemUguali=true;
								break;
							}
						}
				}
				if(!elemItemUguali){
						FrequentPattern fpNew= (FrequentPattern) fp.clone();
						fpNew.addItem(itemPerRaffinamento[i]);
						fpNew.setSupport(FrequentPatternMiner.computeSupport(data, fpNew));
						if(fpNew.getSupport()>minSup){
							fpQueue.enqueue(fpNew);
							outputFP.add(fpNew);
						}
				}
			}
		}
		return outputFP;
	}

	// Aggiorna il supporto
	public static float computeSupport(Data data, FrequentPattern FP) {
		int suppCount = 0;
		// indice esempio
		for (int i = 0; i < data.getNumberOfExamples(); i++) {
			// indice item
			boolean isSupporting = true;
			for (int j = 0; j < FP.getPatternLength(); j++) {
				// DiscreteItem
				Item item = (Item) FP.getItem(j);
				Attribute attribute = (Attribute) item.getAttribute();
				// Extract the example value
				Object valueInExample = data.getAttributeValue(i, attribute.getIndex());
				if (!item.checkItemCondition(valueInExample)) {
					isSupporting = false;
					break; // the ith example does not satisfy fp
				}

			}
			if (isSupporting)
				suppCount++;
		}
		return ((float) suppCount) / (data.getNumberOfExamples());

	}

	public static FrequentPattern refineFrequentPattern(FrequentPattern FP, Item item) {
		// TO DO
		FrequentPattern temp = (FrequentPattern) FP.clone();
		temp.addItem(item);
		return temp;
	}

}
