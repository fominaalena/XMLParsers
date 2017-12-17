package by.htp.rent.dao.dlb;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import by.htp.rent.dao.CatalogData;
import by.htp.rent.domain.Catalog;
import by.htp.rent.domain.Equipment;

public class CatalogDataMySQLImpl implements CatalogData{

	@Override
	public Catalog readCatalog() {
		Catalog catalog = new Catalog();
		
		List<Equipment> equipments = selectEquipmentList();
		catalog.setEquipments(equipments);
		return catalog;
	}
	
	private List<Equipment> selectEquipmentList(){
		
		ResourceBundle rb = ResourceBundle.getBundle("db_config");
		String url = rb.getString("db.url");
		String user = rb.getString("db.login");
		String pass = rb.getString("db.pass");
		String driver = rb.getString("db.driver.name");
		
		List<Equipment> equipments = new ArrayList<>();
		Equipment equipment = new Equipment();
		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from equipment");
			
			while (rs.next()) {
				int id = rs.getInt(1);
				String title = rs.getString(2);
				double price = rs.getDouble(3);
				Date date = rs.getDate(4);
				//System.out.println(id + " " + name + " " + phone + " ");
				
			}
			PreparedStatement ps;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return equipments;
	}

}
