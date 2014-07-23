package cci.cert.certificate;

public class Config {
	public static String XMLPATH = "D:\\oraclexe\\loaders\\data\\xml\\minsk\\";
	public static String REPPATH = "D:\\oraclexe\\loaders\\data\\xml\\repo\\";
	public static String ftpseparator = "/";
	
	public static boolean isdelete = true;
	public static String[] ftpdirs = {"minsk", "vitebsk", "grodno", "gomel", "brest", "mogilev"};
	public static String ftpserver = "212.98.164.233";
	public static String username = "cci_ca";
	public static String password = "MoonLight_2014";
	public static int filelimit = 1000;
	public static int processdelay = 30000;
	
	public static String M_START= "Старт";
	public static String M_STOP = "Стоп";
	public static String M_PROPERTY = "Праметры";
	public static String M_EXIT = "Выход";
}
