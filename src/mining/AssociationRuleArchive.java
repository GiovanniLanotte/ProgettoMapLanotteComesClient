package mining;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class AssociationRuleArchive implements Serializable {

	
	private HashMap<FrequentPattern, TreeSet<AssociationRule>> archive;
	
	public AssociationRuleArchive() {
		archive= new HashMap<FrequentPattern, TreeSet<AssociationRule>>();
	}
	
	public void put(FrequentPattern fp){
		if(!archive.containsKey(fp)){
			TreeSet<AssociationRule> value= new  TreeSet<AssociationRule>();
			archive.put(fp,value);
		}
			
	}
	
	public void put(FrequentPattern fp, AssociationRule rule){
		if(!archive.containsKey(fp)){
			TreeSet<AssociationRule> value= new  TreeSet<AssociationRule>();
			value.add(rule);
			archive.put(fp, value);
		}
		else{
			TreeSet<AssociationRule> value= archive.get(fp);
			value.add(rule);
			archive.put(fp, value);
		}
	}
	
	public TreeSet<AssociationRule> getRules(FrequentPattern fp) throws NoPatternExcemption{
		if(fp.getPatternLength()==1)
			throw new NoPatternExcemption(fp);
		return archive.get(fp);
	}
	public String toString(){
		String stringa="";
		Set<FrequentPattern> keys=archive.keySet();
		int i=1;
		for(FrequentPattern fp: keys){
			
			try{
				stringa+= new Integer(i).toString()+"."+fp+"\n"+ getRules(fp)+"\n";
			}catch (NoPatternExcemption e) {
				System.out.println(e);
			}
			i++;
		}
		return stringa;
		
	}
	
	public void salva(String nomeFile) throws FileNotFoundException, IOException {
		ObjectOutputStream out= new ObjectOutputStream(new FileOutputStream(nomeFile));
		out.writeObject(this);
	}
	public static AssociationRuleArchive carica(String nomeFile) throws FileNotFoundException, IOException,ClassNotFoundException{
		ObjectInputStream in= new ObjectInputStream(new FileInputStream(nomeFile));
		return (AssociationRuleArchive) in.readObject();
	}
}