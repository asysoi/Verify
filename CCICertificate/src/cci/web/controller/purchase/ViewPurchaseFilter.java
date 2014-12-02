package cci.web.controller.purchase;



public class ViewPurchaseFilter {
	private ViewPurchaseCondition condition = new ViewPurchaseCondition();
   	private ViewPurchase object = new ViewPurchase();
   
	public ViewPurchaseCondition getCondition() {
		return condition;
	}
	public void setCondition(ViewPurchaseCondition condition) {
		this.condition = condition;
	}

	public ViewPurchase getViewpurchase() {
		return object;
	}
	public void setViewpurchase(ViewPurchase object) {
		this.object = object;
	}
		
	public ViewPurchaseFilter() {
		super();		
	}

	public ViewPurchaseFilter(ViewPurchase object, ViewPurchaseCondition condition) {
		super();
		this.condition = condition;
		this.object = object;
	}
	
	@Override
	public String toString() {
		return "ViewFilter [viewPurchase=" + object + "] \n [condition="
				+ condition + "]";
	}

}
