package core;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.olap4j.OlapConnection;


public class Solap4py {

	
	
	private OlapConnection olapConnection;
	
	public Solap4py() {
		try {
			Class.forName("org.olap4j.driver.xmla.XmlaOlap4jDriver");
			Connection connection = DriverManager.getConnection("jdbc:xmla:Server=http://postgres:westcoast@192.168.1.1:8080/geomondrian/xmla");
			this.olapConnection = connection.unwrap(OlapConnection.class);
			
		} catch (ClassNotFoundException e) {
			System.err.println(e);
		} catch (SQLException e) {
			System.err.println(e);
		}
		
	}
	
	public String select(String input) {
		JsonObject inputJson = Json.createReader(new StringReader(input)).readObject();
		JsonObjectBuilder output = Json.createObjectBuilder();
		
		String schema = inputJson.getString("schema");
		JsonObject cubeJson = inputJson.getJsonObject("cube");
		String cubeName = cubeJson.getString("name");
		JsonArray measuresJson = cubeJson.getJsonArray("measures");
		//TODO		
		
		return "";
	}
	
	
	
	
	public String getMetadata(String param) throws Exception {

		JsonObject query = Json.createReader(new StringReader(param)).readObject();
		Metadata m = new Metadata(this.olapConnection);
		
		return m.query(query).toString();
	}

	
	
	
	
	public static void main(String[] args) throws Exception {
		
		String query = "{\"schema\" : [ \"Traffic\"],\"cube\" : [],\"dimension\" : [],\"measure\" : [],\"hierarchy\" : [],\"level\" : [],\"member\" : [],\"property\" : []}";
		
		Solap4py p = new Solap4py();
		String metadata = p.getMetadata(query);
	
		System.out.println(metadata);
	}
	
}
