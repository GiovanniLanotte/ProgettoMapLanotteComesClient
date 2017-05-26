package mining;

import data.ContinuousAttribute;

public class ContinuousItem extends Item {
	ContinuousItem(ContinuousAttribute attribute, Interval value){
		super(attribute,value);
	}
	@Override
	public boolean checkItemCondition(Object value){
		return ((Interval)getValue()).checkValueInclusion((Float) value);
	}
	
	@Override
	public String toString() {
		return getAttribute()+"="+((Interval)getValue()).toString();
	}

}
