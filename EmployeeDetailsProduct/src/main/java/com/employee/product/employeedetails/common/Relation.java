package com.employee.product.employeedetails.common;

public enum Relation {
		SPOUSE(1),
		SON(2),
		DAUGHTER(3);
		private int value;
		private Relation(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
}
