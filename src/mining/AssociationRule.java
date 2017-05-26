package mining;

import java.io.Serializable;

public class AssociationRule implements Comparable<AssociationRule>, Serializable{
private Item antecedent[]=new Item[0];
private Item consequent[]=new Item[0];
private float support;
private float confidence;

public AssociationRule(float support){
	this.support=support;
}

public float getSupport(){
	return support;
}

public float getConfidence(){
	return confidence;
}

public int getAntecedentLength(){
	return antecedent.length;
}

public int getConsequentLength(){
	return consequent.length;
}

public void addAntecedentItem(Item item){
	Item temp[]= new Item[antecedent.length+1];
	System.arraycopy(antecedent, 0, temp, 0, getAntecedentLength());
	temp[getAntecedentLength()]=item;
	antecedent=temp;
}

public void addConsequentItem(Item item){
	Item temp[]= new Item[getConsequentLength()+1];
	System.arraycopy(consequent, 0, temp, 0, getConsequentLength());
	temp[getConsequentLength()]=item;
	consequent=temp;
}

public Item getAntecedentItem(int index){
	return antecedent[index];
}

public Item getConsequentItem(int index){
	return consequent[index];
}

public void setConfidence(float confidence){
	this.confidence=confidence;
}

public String toString(){
	String stringa="";
	int i=0;
	for(;i<getAntecedentLength()-1;i++){
		stringa+=getAntecedentItem(i)+" AND ";
	}
	stringa+=getAntecedentItem(i);
	stringa+="=>";
	for(i=0;i<getConsequentLength()-1;i++){
		stringa+=getConsequentItem(i)+" AND ";
	}
	stringa+=getConsequentItem(i);
	stringa+=" ("+getSupport()+","+getConfidence()+")";
	return stringa;
}

@Override
public int compareTo(AssociationRule arg0) {
	if(this.confidence==arg0.confidence)
		return 0;
	if(this.confidence<arg0.confidence)
		return -1;
	else
		return 1;
}
}
	
	