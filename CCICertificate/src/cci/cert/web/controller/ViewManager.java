package cci.cert.web.controller;

import java.util.ArrayList;
import java.util.List;

import cci.purchase.web.controller.HeaderTableView;

public class ViewManager {
	private String[] hnames; 
	private String[] ordnames;
	private int[] widths;
	private String orderby;
	private String order;
	private List<Object> elements;
	private int pagecount;
	private int pagesize;
	private int page;
	private String filterfield;
	private String filteroperator;
	private String filtervalue;
	private Boolean filter;
	private String  url;
	

	public String[] getHnames() {
		return hnames;
	}


	public void setHnames(String[] hnames) {
		this.hnames = hnames;
	}


	public String[] getOrdnames() {
		return ordnames;
	}


	public void setOrdnames(String[] ordnames) {
		this.ordnames = ordnames;
	}


	public int[] getWidths() {
		return widths;
	}


	public void setWidths(int[] widths) {
		this.widths = widths;
	}


	public String getOrderby() {
		return orderby;
	}


	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}


	public String getOrder() {
		return order;
	}


	public void setOrder(String order) {
		this.order = order;
	}


	public List<Object> getElements() {
		return elements;
	}


	public void setElements(List<Object> elements) {
		this.elements = elements;
	}


	public int getPagecount() {
		return pagecount;
	}


	public void setPagecount(int pagecount) {
		this.pagecount = pagecount;
	}


	public int getPage() {
		return page;
	}


	public void setPage(int page) {
		this.page = page;
	}


	public int getPagesize() {
		return pagesize;
	}


	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}


	public String getFilterfield() {
		return filterfield;
	}


	public void setFilterfield(String filterfield) {
		this.filterfield = filterfield;
	}


	public String getFilteroperator() {
		return filteroperator;
	}


	public void setFilteroperator(String filteroperator) {
		this.filteroperator = filteroperator;
	}


	public String getFiltervalue() {
		return filtervalue;
	}


	public void setFiltervalue(String filtervalue) {
		this.filtervalue = filtervalue;
	}


	public Boolean getFilter() {
		return filter;
	}


	public void setFilter(Boolean filter) {
		this.filter = filter;
	}
	
	public List<Integer> getSizesList() {
		List<Integer> sizes = new ArrayList<Integer>();
        sizes.add(new Integer(5));
        sizes.add(new Integer(10));
        sizes.add(new Integer(15));
        sizes.add(new Integer(20));
        sizes.add(new Integer(50));
		return sizes;
	}


	public List<Integer> getPagesList() {
		List<Integer> pages = new ArrayList<Integer>();
		
		int pagelast = (pagecount + (pagesize -1))/pagesize;
		int pagestart = page - 5; 
		if (pagestart < 1) pagestart = 1;
		
		int pageend = pagestart + 9;
		if (pageend > pagelast) pageend = pagelast;
		if (pagelast - 9 < pagestart && pagelast > 10) pagestart = pagelast - 9; 
		
		for (int i = pagestart; i <= pageend; i++) {
			pages.add(new Integer(i));
		}
        return pages;
	}


	public String getPrevPageLink() {
		String link = "#";
		
		if (page_index > 1) {
		   link  = pagename + "?page=" + (page_index -1)+"&pagesize="+ page_size + "&orderby="+orderby+"&order="+order;
		} 
 
		return link;
	}
	
	public String getNextPageLink(String pagename, int page_index, int page_size, int prcount, String orderby, String order) {
		String link = "#";
		
	    if ((page_size * page_index) < prcount) {
		   link  = pagename + "?page=" + (page_index + 1)+"&pagesize="+ page_size + "&orderby="+orderby+"&order="+order;
	    }
 
		return link;
	}


	private HeaderTableView makeHeaderTableView(String pagename, int width, String name, int page, int pagesize,
			String orderby, String order, boolean selected) {
		
        HeaderTableView header = new HeaderTableView();
        header.setWidth(width);
        header.setName(name);
        header.setDbfield(orderby);
        header.setLink(pagename + "?pagesize=" + pagesize + "&orderby=" + orderby + "&order=" + order);
        header.setSelected(selected);
        header.setSelection(selected ? (order.equals("asc") ?  "▲" : "▼") : "");
		return header;
	}
	
	
	public initHeaders() {
		List<HeaderTableView> headers = new ArrayList<HeaderTableView>(); 
        for (int i = 0; i < widths.length ; i++) {
        	if (ordnames[i].equals(orderby)) {
   	           headers.add(makeHeaderTableView(widths[i], hnames[i], page_index, page_size, ordnames[i], order.equals(ordasc) ? orddesc : ordasc, true));
        	} else {
        	   headers.add(makeHeaderTableView(widths[i], hnames[i], page_index, page_size, ordnames[i], ordasc, false)); 	
        	}
        }
	}


	public String getPagename() {
		return pagename;
	}


	public void setPagename(String pagename) {
		this.pagename = pagename;
	}
}
