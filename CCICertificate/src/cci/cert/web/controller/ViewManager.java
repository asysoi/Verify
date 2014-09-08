package cci.cert.web.controller;

import java.util.ArrayList;
import java.util.List;

import cci.purchase.web.controller.HeaderTableView;

public class ViewManager {
	
	
	public List<Integer> getSizesList() {
		List<Integer> sizes = new ArrayList<Integer>();
        sizes.add(new Integer(5));
        sizes.add(new Integer(10));
        sizes.add(new Integer(15));
        sizes.add(new Integer(20));
        sizes.add(new Integer(50));
		return sizes;
	}


	public List<Integer> getPagesList(int page, int page_size, int prcount) {
		List<Integer> pages = new ArrayList<Integer>();
		
		int pagelast = (prcount + (page_size -1))/page_size;
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


	public String getPrevPageLink(int page_index, int page_size, int prcount, String orderby, String order) {
		String link = "#";
		
		if (page_index > 1) {
		   link  = "purchaselist.do?page=" + (page_index -1)+"&pagesize="+ page_size + "&orderby="+orderby+"&order="+order;
		} 
 
		return link;
	}
	
	private String getNextPageLink(int page_index, int page_size, int prcount, String orderby, String order) {
		String link = "#";
		
	    if ((page_size * page_index) < prcount) {
		   link  = "purchaselist.do?page=" + (page_index + 1)+"&pagesize="+ page_size + "&orderby="+orderby+"&order="+order;
	    }
 
		return link;
	}


	private HeaderTableView makeHeaderTableView(int width, String name, int page, int pagesize,
			String orderby, String order, boolean selected) {
		
        HeaderTableView header = new HeaderTableView();
        header.setWidth(width);
        header.setName(name);
        header.setDbfield(orderby);
        header.setLink("purchaselist.do?pagesize=" + pagesize + "&orderby=" + orderby + "&order=" + order);
        header.setSelected(selected);
        header.setSelection(selected ? (order.equals("asc") ?  "▲" : "▼") : "");
		return header;
	}

}
