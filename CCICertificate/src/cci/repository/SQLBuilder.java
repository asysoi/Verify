package cci.repository;

import cci.service.Filter;

public abstract class SQLBuilder {
	   private Filter filter = null;
	   
       public abstract String getWhereClause();
       
       public void setFilter(Filter filter) {
    	   this.filter = filter;
       }

       public Filter getFilter() {
    	   return filter;
       }
}
