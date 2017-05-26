package data;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import database.DatabaseConnectionException;
import database.DbAccess;
import database.NoValueException;
import database.QUERY_TYPE;
import database.TableData;
import database.TableSchema;
import database.TableData.TupleData;
import database.TableSchema.Column;

// TO DO : DECIDERE LE VISIBILITà DI ATTRIBUTI, METODI, CLASSI
/*Definiamo la classe Data per modellare un insieme di transizioni
 * (vettori attributo-valore)*/
public class Data {
	
	private Object data [][]; // una matrice di Object che ha numero di righe pari al numero di transazioni da memorizzare e numero di colonne pari al numero di attributi in ciascuna transazione
	private int numberOfExamples;//cardinalità dell’insieme di transazioni
	private List<Attribute> attributeSet;// un array di attributi, che sono avvalorati in ciascuna transazione
	
	/*La classe Data popola la matrice data[][] con le transizioni (14 transazioni per 5 attributi)
	 * Inoltre avvalora l'array attributeSet[] con cinque oggetti di DiscreteAttribute
	 * uno per ciascun attributo*/
	
	
	//TableData(per recuperare le tuple (cioè i dati)) e TableSchema (per recuperare le informazioni della tabella)
	public Data(){
		DbAccess db= new DbAccess();
		try {
			db.initConnection();
		} catch (DatabaseConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		attributeSet= new LinkedList<Attribute>();
		String table="playtennis";
		TableData td= new TableData();
		td.DbAccess(db);
		TableSchema ts = null;
		try {
			ts= new TableSchema(db, table);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//serve per leggere tutti gli stributi
		for(int i=0;i<ts.getNumberOfAttributes();i++){
			TableSchema.Column c=ts.getColumn(i);
			if(c.isNumber()){
				try {
					float max=(float)td.getAggregateColumnValue(table, c, QUERY_TYPE.MAX);
				
					float min=(float)td.getAggregateColumnValue(table, c, QUERY_TYPE.MIN);
					
					attributeSet.add(new ContinuousAttribute(c.getColumnName(), i, min, max));
					
				} catch (SQLException | NoValueException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				try {
					List<Object>elem= td.getDistinctColumnValues(table, c);
					String[] elementi= new String[elem.size()];
					int k=0;
					for(Object o: elem){
						elementi[k]=(String) o;
						k++;
					}
					attributeSet.add(new DiscreteAttribute(c.getColumnName(), i, elementi));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		//inseriamo gli elementi all'interno di data
		try {
			List<TupleData> listElem=td.getTransazioni(table);
			numberOfExamples=listElem.size();
			data= new Object[numberOfExamples][attributeSet.size()];
			Iterator<TupleData> iterator= listElem.iterator();
			for(int i=0;i<numberOfExamples;i++){
				TupleData dataTupla= iterator.next();
				int k=0;
				for(Object o: dataTupla.tuple){
					data[i][k]=o;
					k++;
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			db.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int getNumberOfExamples(){ //metodo che  restituisce il valore del membro  numberOfExamples 
		 
		 return numberOfExamples; //cardinalità dell'insieme delle transazioni
	}
	
	public int getNumberOfAttributes(){ //metodo che restituisce la cardinalità del membro  attributeSet 
		 
		 return attributeSet.size();  //cardinalità dell'nsieme degli attributi
	}
	
	public Object getAttributeValue(int exampleIndex, int attributeIndex){
		//metodo che  restituisce il valore dell' attributo attributeIndex  per la transazione exampleIndex meomorizzata in data 
		// assume come  indice di riga per la matrice data che corrisponde ad una specifica transazione, indice di colonna per un attributo
		return data[exampleIndex][attributeIndex];  // valore assunto dall’attributo identificato da attributeIndex nella transazione identificata da exampleIndex nel membro data. 
	}
	
	public Attribute getAttribute(int index){  //metodo che restituisce l’attributo in posizione attributeIndex di attributeSet 
		return attributeSet.get(index);  //attributo con indice attributeIndex
	}
	
	/*il metodo to String per ogni transazione memorizzata in data, concatena i valori assunti dagli attributi nella transazione 
	 * separati da virgole in una stringa. Le stringhe che rappresentano ogni transazione sono poi concatenate in un’unica stringa da 
	 *restituire in output. 
  */
	public String toString(){
		String stringa=new String();
		for(int i=0;i<data.length;i++){
			stringa+=(i+1)+":";
			for(int k=0;k<data[k].length;k++){
				stringa=stringa+data[i][k]+",";
			}
		stringa=stringa+"\n"; //concatenamento di stringhe
		}
		return stringa; //un unica stringa contente le stringhe concatenate
	}


	public static void main(String args[]){
		Data trainingSet=new Data();
		System.out.println(trainingSet);
	}
}
