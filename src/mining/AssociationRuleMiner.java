package mining;

import java.util.LinkedList;

import data.Data;
public class AssociationRuleMiner {
	
	
	public static LinkedList<AssociationRule> confidentAssociationRuleDiscovery(Data data,FrequentPattern fp,float minConf)throws OneLevelPatternException{
//		TO DO
		
		LinkedList<AssociationRule> outputAR=new LinkedList<AssociationRule>();
		if(fp.getPatternLength()==1)
			throw new OneLevelPatternException(fp.toString());
		for(int i=1;i<fp.getPatternLength();i++){
			AssociationRule ar= confidentAssociationRuleDiscovery(data, fp, minConf, i);
			if(ar.getConfidence()>minConf){
				outputAR.add(ar);
			}
			
		}
		return outputAR;
	}


private static AssociationRule confidentAssociationRuleDiscovery(Data data,FrequentPattern fp,float minConf, int iCut){
	AssociationRule AR=new AssociationRule(fp.getSupport());
	
	//to generate the antecedent of the association rule
	for(int j=0;j<iCut;j++){
		AR.addAntecedentItem(fp.getItem(j));		
	}
	//to generate the consequent of the association rule
	for(int j=iCut;j<fp.getPatternLength();j++){
		AR.addConsequentItem(fp.getItem(j));
	}	
	AR.setConfidence(AssociationRuleMiner.computeConfidence(data,AR));
	return AR;
}

//Aggiorna il supporto
static float  computeConfidence(Data data, AssociationRule AR){
	// TO DO
	int numAntecedente=0;
	int numConseguente=0;
	for(int i=0;i<data.getNumberOfExamples();i++){
		int antecedenteEsatto=0;
		for(int j=0;j<AR.getAntecedentLength();j++){
			if(AR.getAntecedentItem(j).getValue().equals(data.getAttributeValue(i, AR.getAntecedentItem(j).getAttribute().getIndex()))){
				antecedenteEsatto++;
			}
		}
		if(antecedenteEsatto==AR.getAntecedentLength()){
			numAntecedente++;
			int conseguenteEsatto=0;
			for(int j=0;j<AR.getConsequentLength();j++){
				if(AR.getConsequentItem(j).getValue().equals(data.getAttributeValue(i, AR.getConsequentItem(j).getAttribute().getIndex()))){
					conseguenteEsatto++;
				}
			}
			if(conseguenteEsatto==AR.getConsequentLength()){
				numConseguente++;
			}
		}
	}
	return ((float) numConseguente)/ ((float) numAntecedente);
}

}