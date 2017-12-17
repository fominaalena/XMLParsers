package by.htp.rent.console.control;

import by.htp.rent.dao.CatalogData;
import by.htp.rent.dao.dlb.CatalogDataMySQLImpl;
import by.htp.rent.dao.parser.dom.CatalogDataDomImpl;
import by.htp.rent.dao.parser.sax.CatalogDataSaxImpl;
import by.htp.rent.domain.Catalog;

public class Main {

	public static void main(String[] args) {
		
		//CatalogData dao = new CatalogDataSaxImpl();
		//CatalogData dao = new CatalogDataDomImpl();
		CatalogData dao = new CatalogDataMySQLImpl();
		Catalog catalog = dao.readCatalog();
		
		System.out.println(catalog);

	}

}
