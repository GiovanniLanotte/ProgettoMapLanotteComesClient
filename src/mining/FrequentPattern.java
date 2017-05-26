package mining;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class FrequentPattern implements Cloneable,Serializable{

	private List<Item> fp;
	private float support;
	
	FrequentPattern(){ 
			fp=new LinkedList<Item>();
	}
	
	//aggiunge un nuovo item al pattern
	public void addItem(Item item){
		fp.add(item);
	}
	
	public Item getItem(int index){
		return fp.get(index);
	}
	
	public float getSupport(){
		return support;
	}
	
	public int getPatternLength(){
		return fp.size();
	}
	
	public void setSupport(float support){
		this.support=support;
	}
	
	public String toString(){
		String value="";
		
		for(int i=0;i<fp.size()-1;i++)
			value+=fp.get(i) +" AND ";
		if(fp.size()>0){
			value+=fp.get(fp.size()-1);
			value+="["+support+"]";
		}
		
		return value;
	}

	//AGGUNGI MEDOTO CLONEBOLE
	public Object clone(){
		Object o=null;
		try {
			o=super.clone();
			((FrequentPattern) o).listClone(fp);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	return o;
	}
	
	private void listClone(List<Item> list){
		fp= new LinkedList<Item>();
		for(Item i:list){
			fp.add(i);
		}
	}
	}
