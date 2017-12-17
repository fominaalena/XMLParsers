package by.htp.rent.dao.parser.STax;

import by.htp.rent.domain.Equipment;

import static by.htp.rent.dao.parser.DataTypeTransformUtil.convertId;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import by.htp.rent.dao.CatalogData;
import by.htp.rent.dao.parser.CatalogTagName;
import by.htp.rent.domain.Catalog;

public class CatalogDataStaxImpl implements CatalogData {

	private static final String XML_FILE_PATH = "resources/rent_station.xml";

	private static final char UNDERSCORE = '_';
	private static final char DASH = '-';
	private static final String ID = "id";

	@Override
	public Catalog readCatalog() {
		Catalog catalog = new Catalog();

		XMLInputFactory xmlF = XMLInputFactory.newInstance();
		InputStream stream;
		try {
			stream = new FileInputStream(XML_FILE_PATH);
			XMLStreamReader reader = xmlF.createXMLStreamReader(stream);

			List<Equipment> equipment = processReader(reader);
			catalog.setEquipments(equipment);
		} catch (FileNotFoundException | XMLStreamException e) {
			e.printStackTrace();
		}
		return catalog;
	}

	private List<Equipment> processReader(XMLStreamReader reader) {
		List<Equipment> equipments = new ArrayList<>();
		Equipment equipment = null;
		CatalogTagName tag = null;
		try {
			while (reader.hasNext()) {
				int type = reader.next();
				switch (type) {
				case XMLStreamConstants.START_ELEMENT:
					tag = getTag(reader.getLocalName());
					switch (tag) {
					case EQUIPMENT:
						equipment = new Equipment();
						String id = reader.getAttributeValue(null, ID);
						equipment.setId(convertId(id));
						break;
					}
					break;
				case XMLStreamConstants.CHARACTERS:
					break;
				case XMLStreamConstants.END_ELEMENT:
				}
			}
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return equipments;
	}

	private CatalogTagName getTag(String localName) {
		String tag = localName.toUpperCase().replace(DASH, UNDERSCORE);
		CatalogTagName tagElement = CatalogTagName.valueOf(tag);
		return tagElement;
	}
}
