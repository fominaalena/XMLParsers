package by.htp.rent.dao.parser.sax;

import static by.htp.rent.dao.parser.DataTypeTransformUtil.*;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import by.htp.rent.dao.parser.CatalogTagName;
import by.htp.rent.domain.Equipment;

public final class CatalogDataHandler extends DefaultHandler {

	private StringBuilder text;
	private Equipment equipment;
	private List<Equipment> equipments = new ArrayList<>();

	private static final char UNDERSCORE = '_';
	private static final char DASH = '-';
	private static final String ID = "id";

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		CatalogTagName tag = getTag(localName);
		switch (tag) {
		case EQUIPMENT:
			equipment = new Equipment();
			String id = attributes.getValue(ID);
			equipment.setId(convertId(id));
			break;
		}
		text = new StringBuilder();
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		CatalogTagName tag = getTag(localName);
		switch (tag) {
		case EQUIPMENT:
			equipments.add(equipment);
			break;
		case TITLE:
			equipment.setTitle(text.toString().trim());
			break;
		case PRICE:
			String price = text.toString().trim();
			equipment.setPrice(convertPrice(price));
			break;
		case DATE:
			String date = text.toString().trim();
			equipment.setDate(convertDate(date));
			break;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		text.append(ch, start, length);
	}

	public List<Equipment> getEquipments() {
		return equipments;
	}

	private CatalogTagName getTag(String localName) {
		String tag = localName.toUpperCase().replace(DASH, UNDERSCORE);
		CatalogTagName tagElement = CatalogTagName.valueOf(tag);
		return tagElement;
	}
}
