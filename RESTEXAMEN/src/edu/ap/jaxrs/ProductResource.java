package edu.ap.jaxrs;

import java.io.*;
import java.util.*;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.xml.bind.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
 
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
 
 
@RequestScoped
@Path("/products")
public class ProductResource {
	
	@GET
	@Produces({"text/html"})
	@Consumes("application/json")
	public String getProductsHTML() {
		String htmlString = "<html><body>";
		try {
			File JSONfile = new File("/Users/Sander Peeters/Desktop/Product.json");
			InputStream fis = new FileInputStream(JSONfile);
	         
	        //create JsonReader object
	        JsonReader jsonReader = Json.createReader(fis);
	         
	        //get JsonObject from JsonReader
	        JsonObject jsonObject = jsonReader.readObject();
	         
	        //we can close IO resource and JsonReader now
	        jsonReader.close();
	        fis.close();
	         
	        //Retrieve data from JsonObject and create Product bean
	        Product product = new Product();
	         
	        product.setId(jsonObject.getInt("id"));
	        product.setName(jsonObject.getString("name"));
	        product.setBrand(jsonObject.getString("brand"));
	        product.setDescription(jsonObject.getString("description"));
	        product.setPrice(jsonObject.getDouble("price"));

			ArrayList<Product> listOfProducts = jsonObject.getProducts();
			
			for(Product productAfdruk : listOfProducts) {
				htmlString += "<b>Name : " + product.getName() + "</b><br>";
				htmlString += "Id : " + product.getId() + "<br>";
				htmlString += "Brand : " + product.getBrand() + "<br>";
				htmlString += "Description : " + product.getDescription() + "<br>";
				htmlString += "Price : " + product.getPrice() + "<br>";
				htmlString += "<br><br>";
			}
		} 
		catch (JAXBException e) {
		   e.printStackTrace();
		}
		return htmlString;
	}
	
	@GET
	@Produces({"application/json"})
	public String getProductsJSON() {
		String jsonString = "{\"products\" : [";
		try {
			File JSONfile = new File("/Users/Sander Peeters/Desktop/Product.json");
			InputStream fis = new FileInputStream(JSONfile);
	         
	        //create JsonReader object
	        JsonReader jsonReader = Json.createReader(fis);
	         
	        //get JsonObject from JsonReader
	        JsonObject jsonObject = jsonReader.readObject();
	         
	        //we can close IO resource and JsonReader now
	        jsonReader.close();
	        fis.close();
	         
	        //Retrieve data from JsonObject and create Product bean
	        Product product = new Product();
	         
	        product.setId(jsonObject.getInt("id"));
	        product.setName(jsonObject.getString("name"));
	        product.setBrand(jsonObject.getString("brand"));
	        product.setDescription(jsonObject.getString("description"));
	        product.setPrice(jsonObject.getDouble("price"));

			ArrayList<Product> listOfProducts = jsonObject.getProducts();
			
			for(Product productAfdruk : listOfProducts) {
				jsonString += "{\"name\" : \"" + product.getName() + "\",";
				jsonString += "\"id\" : " + product.getId() + ",";
				jsonString += "\"brand\" : \"" + product.getBrand() + "\",";
				jsonString += "\"description\" : \"" + product.getDescription() + "\",";
				jsonString += "\"price\" : " + product.getPrice() + "},";
			}
			jsonString = jsonString.substring(0, jsonString.length()-1);
			jsonString += "]}";
		} 
		catch (JAXBException e) {
		   e.printStackTrace();
		}
		return jsonString;
	}
	
	@GET
	@Produces({"application/json"})
	public String getProductsJSON() {
		String content = "";
		File JSONfile = new File("/Users/Sander Peeters/Desktop/Product.json");
		try {
			content = new Scanner(JSONfile).useDelimiter("\\Z").next(); 	//inlezen van json en inhoud teruggeven.
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return content; //teruggeven van inhoud van xml.
	}

	@GET
	@Path("/{name}") //voor één product (shortname) een JSON aanmaken.
	@Produces({"application/json"})
	public String getProductJSON(@PathParam("name") String name) {
		String jsonString = "";
		try {
			// get all products (We lezen alle producten in)
			File JSONfile = new File("/Users/Sander Peeters/Desktop/Product.json");
			InputStream fis = new FileInputStream(JSONfile);
	         
	        //create JsonReader object
	        JsonReader jsonReader = Json.createReader(fis);
	         
	        //get JsonObject from JsonReader
	        JsonObject jsonObject = jsonReader.readObject();
	         
	        //we can close IO resource and JsonReader now
	        jsonReader.close();
	        fis.close();
	         
	        //Retrieve data from JsonObject and create Product bean
	        Product product = new Product();
	         
	        product.setId(jsonObject.getInt("id"));
	        product.setName(jsonObject.getString("name"));
	        product.setBrand(jsonObject.getString("brand"));
	        product.setDescription(jsonObject.getString("description"));
	        product.setPrice(jsonObject.getDouble("price"));

			ArrayList<Product> listOfProducts = jsonObject.getProducts();
			
			
			// look for the product, using the shortname
			for(Product productAfdruk : listOfProducts) {
				if(name.equalsIgnoreCase(product.getName())) {
					jsonString += "{\"name\" : \"" + product.getName() + "\",";
					jsonString += "\"id\" : " + product.getId() + ",";
					jsonString += "\"brand\" : \"" + product.getBrand() + "\",";
					jsonString += "\"description\" : \"" + product.getDescription() + "\",";
					jsonString += "\"price\" : " + product.getPrice() + "}";
				}
			}
		} 
		catch (JAXBException e) {
		   e.printStackTrace();
		}
		return jsonString; //Eén JSON teruggeven.
	}
	
	@GET
	@Path("/{name}")
	@Produces({"text/xml"})
	public String getProductXML(@PathParam("name") String name) {
		String xmlString = "";
		try {
			// get all products (We lezen alle producten in)
			File JSONfile = new File("/Users/Sander Peeters/Desktop/Product.json");
			InputStream fis = new FileInputStream(JSONfile);
	         
	        //create JsonReader object
	        JsonReader jsonReader = Json.createReader(fis);
	         
	        //get JsonObject from JsonReader
	        JsonObject jsonObject = jsonReader.readObject();
	         
	        //we can close IO resource and JsonReader now
	        jsonReader.close();
	        fis.close();
	         
	        //Retrieve data from JsonObject and create Product bean
	        Product product = new Product();
	         
	        product.setId(jsonObject.getInt("id"));
	        product.setName(jsonObject.getString("name"));
	        product.setBrand(jsonObject.getString("brand"));
	        product.setDescription(jsonObject.getString("description"));
	        product.setPrice(jsonObject.getDouble("price"));

			ArrayList<Product> listOfProducts = jsonObject.getProducts();
			
			// look for the product, using the shortname
			for(Product productAfdruk : listOfProducts) {
				if(name.equalsIgnoreCase(product.getName())) {
					JAXBContext jaxbContext2 = JAXBContext.newInstance(Product.class);
					Marshaller jaxbMarshaller = jaxbContext2.createMarshaller();
					StringWriter sw = new StringWriter();
					jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
					jaxbMarshaller.marshal(product, sw);
					xmlString = sw.toString();
				}
			}
		} 
		catch (JAXBException e) {
		   e.printStackTrace();
		}
		return xmlString;
	}
	
	@POST //Methode waarbij we XML meegeven en een nieuw product toevoegen.
	@Consumes({"text/xml"})
	public void processFromXML(String productXML) {
		
		try {
			// get all products
			File JSONfile = new File("/Users/Sander Peeters/Desktop/Product.json");
			InputStream fis = new FileInputStream(JSONfile);
	         
	        //create JsonReader object
	        JsonReader jsonReader = Json.createReader(fis);
	         
	        //get JsonObject from JsonReader
	        JsonObject jsonObject = jsonReader.readObject();
	         
	        //we can close IO resource and JsonReader now
	        jsonReader.close();
	        fis.close();
	        
	        //Retrieve data from JsonObject and create Product bean
	        Product product = new Product();
	         
	        product.setId(jsonObject.getInt("id"));
	        product.setName(jsonObject.getString("name"));
	        product.setBrand(jsonObject.getString("brand"));
	        product.setDescription(jsonObject.getString("description"));
	        product.setPrice(jsonObject.getDouble("price"));

			ArrayList<Product> listOfProducts = jsonObject.getProducts();
	        
	        JsonArray jsonArray = jsonObject.getJsonArray("products");
	        long[] products = new long[jsonArray.size()];
	        int index = 0;
	        for(JsonValue value : jsonArray){
	            products[index++] = Long.parseLong(value.toString());
	        }
			
			// unmarshal new product (We gaan in de XML een product unmarshallen)
			JAXBContext jaxbContext2 = JAXBContext.newInstance(Product.class);
			Unmarshaller jaxbUnmarshaller2 = jaxbContext2.createUnmarshaller();
			StringReader reader = new StringReader(productXML);
			Product newProduct = (Product)jaxbUnmarshaller2.unmarshal(reader); //Vertaling XML naar nieuw product.
			
			// add product to existing product list 
			// and update list of products in  productsXML
			listOfProducts.add(newProduct);
			jsonObject.setProducts(listOfProducts);
			
			// marshal the updated productsXML object (Marshallen en terug naar schijf wegschrijven/naar XML).
			Marshaller jaxbMarshaller = jaxbContext1.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			jaxbMarshaller.marshal(jsonObject, XMLfile);
		} 
		catch (JAXBException e) {
		   e.printStackTrace();
		}
	}
}