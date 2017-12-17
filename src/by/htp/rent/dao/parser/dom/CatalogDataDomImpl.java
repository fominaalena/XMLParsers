package by.htp.rent.dao.parser.dom;
import static by.htp.rent.dao.parser.DataTypeTransformUtil.*;
import by.htp.rent.dao.CatalogData;
import by.htp.rent.domain.Catalog;
import by.htp.rent.domain.Equipment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CatalogDataDomImpl implements CatalogData{

	private static final String XML_FILE_PATH = "resources/rent_station.xml";

	@Override
	public Catalog readCatalog() {
		Catalog catalog = new Catalog();
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(XML_FILE_PATH);
			List<Equipment> eq = parseDocument(document);
			catalog.setEquipments(eq);
			} catch (ParserConfigurationException | SAXException | IOException e) {
				e.printStackTrace();	
			}
		return catalog;
	}

	private List<Equipment> parseDocument(Document document){
		List<Equipment> list = new ArrayList<>();
		Element root = document.getDocumentElement();
		
		NodeList nodes = root.getElementsByTagName("equipment");
		Equipment equipment = null;
		for(int i=0; i<nodes.getLength(); i++) {
			equipment = new Equipment();
			Element currentNode = (Element)nodes.item(i);
			
			String id = currentNode.getAttribute("id");
			equipment.setId(convertId(id));
			Element element = getSingleChild(currentNode, "title");
			String title = element.getTextContent().trim();
			equipment.setTitle(title);
			
			element = getSingleChild(currentNode, "price");
			String price = element.getTextContent().trim();
			equipment.setPrice(convertPrice(price));
			
			element = getSingleChild(currentNode, "date");
			String date = element.getTextContent().trim();
			equipment.setDate(convertDate(date));
			
			list.add(equipment);
			System.out.println(equipment.getId());
		}
		return list;
	}
	
	private Element getSingleChild(Element node, String name) {
		NodeList nodeList = node.getElementsByTagName(name);
		Element childElement = (Element) nodeList.item(0);
		return childElement;
	}
}
