import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

import keyboardinput.Keyboard;
import mining.AssociationRule;
import mining.AssociationRuleArchive;
import mining.AssociationRuleMiner;
import mining.FrequentPattern;
import mining.FrequentPatternMiner;
import mining.OneLevelPatternException;

import data.Data;
import data.EmptySetException;





public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		InetAddress ip=  InetAddress.getByName("127.0.0.1");
		Socket s= new Socket(ip,8080);
		DataInputStream in= new DataInputStream(s.getInputStream());
		DataOutputStream out= new DataOutputStream(s.getOutputStream());
		
		AssociationRuleArchive archive=null;
		System.out.println("Carica archivio/Crea archivio? (s/n)");
		char r = Keyboard.readChar();
		
		if(r=='n'){
		
			Data data= new Data();
			archive=new AssociationRuleArchive();
			float minSup=(float)0.0,minConf=(float)0.0;
			do{
				System.out.println("Inserisci minsup (in [0,1])");
				minSup=Keyboard.readFloat();
			}while (minSup<0 || minSup>1);
			
			do{
				System.out.println("Inserisci minconf (in [0,1])");
				minConf=Keyboard.readFloat();
			}while (minConf<0 || minConf>1);
			
			
			
			try{
				List<FrequentPattern> outputFP=FrequentPatternMiner.frequentPatternDiscovery(data,minSup);
				
				
				
				
				Iterator<FrequentPattern> it=outputFP.iterator();
				while(it.hasNext()){
					FrequentPattern FP=it.next();
					archive.put(FP);
									
					List<AssociationRule> outputAR=null;
					try {
						outputAR = AssociationRuleMiner.confidentAssociationRuleDiscovery(data,FP,minConf);
						Iterator<AssociationRule> itRule=outputAR.iterator();
						while(itRule.hasNext()){
							archive.put(FP,itRule.next());
						}
										
					
					} catch (OneLevelPatternException e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());
					}
					
				}
				System.out.println("Nome file per backup:");
				String nomeFile=Keyboard.readString();
				try {
					archive.salva(nomeFile);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			catch(EmptySetException e){
				System.out.println(e.getMessage());
			}
		}
		else{
			System.out.println("Nome file di restore:");
			String nomeFile=Keyboard.readString();
			try {
				archive=AssociationRuleArchive.carica(nomeFile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.print(archive);
		
		
	}

}
