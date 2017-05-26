package mining;
import data.DiscreteAttribute;

public class DiscreteItem extends Item {

	public DiscreteItem(DiscreteAttribute attribute,String value) {
		super(attribute,value);
	}
	
	@Override
	public boolean checkItemCondition(Object value) {
		
		return ((String)value).equals(((String)this.getValue()));
	}
	
	

}