package edu.ap.jaxrs;

import java.io.*;
import java.util.*;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.xml.bind.*;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;


@RequestScoped
@Path("/products")
public class ProductResource {
	
	@GET
	@Produces({"text/html"})
	@Consumes("application/json")
	public String getProductsHTML() {
		String htmlString = "<html><body>";
		try {
			AXBContext jc = JAXBContext.newInstance(Product.class);
			        // Create the Unmarshaller Object using the JaxB Context
			        Unmarshaller unmarshaller = jc.createUnmarshaller();
			        // Set the Unmarshaller media type to JSON or XML
			        unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE,
			                "application/json");
			        // Set it to true if you need to include the JSON root element in the
			        // JSON input
			        unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true)
			        // Create the StreamSource by creating StringReader using the JSON input
			        File JSONfile = new File("/Users/Sander Peeters/Desktop/Product.json");
			        Employee employee = unmarshaller.unmarshal(JSONfile, Employee.class)
			                .getValue();

			ArrayList<Product> listOfProducts = productsJSON.getProducts();
			
			for(Product product : listOfProducts) {
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
			JAXBContext jaxbContext1 = JAXBContext.newInstance(ProductsJSON.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext1.createUnmarshaller();
			File JSONfile = new File("/Users/Sander Peeters/Desktop/Product.json");
			ProductsJSON productsJSON = (ProductsJSON)jaxbUnmarshaller.unmarshal(JSONfile);
			ArrayList<Product> listOfProducts = productsJSON.getProducts();
			
			for(Product product : listOfProducts) {
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
	@Produces({"text/xml"})
	public String getProductsXML() {
		String content = "";
		File XMLfile = new File("/Users/Sander Peeters/Desktop/Product.json");
		try {
			content = new Scanner(XMLfile).useDelimiter("\\Z").next(); 	//inlezen van xml en inhoud teruggeven.
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
			JAXBContext jaxbContext1 = JAXBContext.newInstance(ProductsJSON.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext1.createUnmarshaller();
			File XMLfile = new File("/Users/Sander Peeters/Desktop/Product.json");
			ProductsJSON productsXML = (ProductsJSON)jaxbUnmarshaller.unmarshal(XMLfile);
			ArrayList<Product> listOfProducts = productsXML.getProducts();
			
			// look for the product, using the shortname
			for(Product product : listOfProducts) {
				if(name.equalsIgnoreCase(product.getName())) {
					jsonString += "{\"shortname\" : \"" + product.getName() + "\",";
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
			JAXBContext jaxbContext1 = JAXBContext.newInstance(ProductsJSON.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext1.createUnmarshaller();
			File XMLfile = new File("/Users/Sander Peeters/Desktop/Product.json");
			ProductsJSON productsXML = (ProductsJSON)jaxbUnmarshaller.unmarshal(XMLfile);
			ArrayList<Product> listOfProducts = productsXML.getProducts();
			
			// look for the product, using the shortname
			for(Product product : listOfProducts) {
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
		
		/* newProductXML should look like this :
		 *  
		 <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
		 <product>
        	<brand>BRAND</brand>
        	<description>DESCRIPTION</description>
        	<id>123456</id>
        	<price>20.0</price>
        	<shortname>SHORTNAME</shortname>
        	<sku>SKU</sku>
		 </product>
		 */
		
		try {
			// get all products
			JAXBContext jaxbContext1 = JAXBContext.newInstance(ProductsJSON.class);
			Unmarshaller jaxbUnmarshaller1 = jaxbContext1.createUnmarshaller();
			File XMLfile = new File("/Users/Sander Peeters/Desktop/Product.json");
			ProductsJSON productsXML = (ProductsJSON)jaxbUnmarshaller1.unmarshal(XMLfile);
			ArrayList<Product> listOfProducts = productsXML.getProducts();
			
			// unmarshal new product (We gaan in de XML een product unmarshallen)
			JAXBContext jaxbContext2 = JAXBContext.newInstance(Product.class);
			Unmarshaller jaxbUnmarshaller2 = jaxbContext2.createUnmarshaller();
			StringReader reader = new StringReader(productXML);
			Product newProduct = (Product)jaxbUnmarshaller2.unmarshal(reader); //Vertaling XML naar nieuw product.
			
			// add product to existing product list 
			// and update list of products in  productsXML
			listOfProducts.add(newProduct);
			productsXML.setProducts(listOfProducts);
			
			// marshal the updated productsXML object (Marshallen en terug naar schijf wegschrijven/naar XML).
			Marshaller jaxbMarshaller = jaxbContext1.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			jaxbMarshaller.marshal(productsXML, XMLfile);
		} 
		catch (JAXBException e) {
		   e.printStackTrace();
		}
	}
}