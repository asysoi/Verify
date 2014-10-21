package cci.cert.service;

import java.util.Map;

public class CountryConverter  {
	   private static Map<String, String> countrymap;
	   
       public static Map<String, String> getCountrymap() {
		return countrymap;
	}

	public static void setCountrymap(Map<String, String> countrymap) {
		CountryConverter.countrymap = countrymap;
	}

	public static String getCountryNameByCode(String code) {
    	   String name = code;
    	   
		   if (code.trim().length() == 2 && countrymap != null) {
			   name = countrymap.get(code);
		   }
    	   return name;   
    }
} 
