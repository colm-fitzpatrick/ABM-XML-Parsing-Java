package abm_xml_parsing_java;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XML_Parsing {
	
	public void question1() {
		String edifact = "UNA:+.? '\n"
		         + "UNB+UNOC:3+2021000969+4441963198+180525:1225+3VAL2MJV6EH9IX+KMSV7HMD+CUSDECU-IE++1++1\n"
		         + "UNH+EDIFACT+CUSDEC:D:96B:UN:145050'\n"
		         + "BGM+ZEM:::EX+09SEE7JPUV5HC06IC6+Z'\n"
		         + "LOC+17+IT044100'\n"
		         + "LOC+18+SOL'\n"
		         + "LOC+35+SE'\n"
		         + "LOC+36+TZ'\n"
		         + "LOC+116+SE003033'\n"
		         + "DTM+9:20090527:102'\n"
		         + "DTM+268:20090626:102'\n"
		         + "DTM+182:20090527:102'";
		
		// Create linkedHashMap to store ordered results
		Map<String, String> results = new LinkedHashMap<String, String>();
		
		 // Create a pattern
		String pattern = "LOC\\+([A-Za-z0-9\\+]+)";

		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(edifact);
		
		 // Loop results, remove LOC+, split and store
		while (m.find()) {
		    String temp = m.group().replace("LOC+","");
		    String[] strList = temp.split("\\+");
		    results.put(strList[0],strList[1]);
		    strList = null;
		}
		// Print results
		for (Map.Entry<String, String> entry : results.entrySet()) {
		    System.out.println("2nd Element = " + entry.getKey() + " , " + "3rd Element = " + entry.getValue());
		}
	}
	
	public void question2() {
		
		// Initialize RefCode list and LinkedHashMap for ordered results
		List<String> RefCodes = Arrays.asList("MWB", "TRV", "CAR");
		Map<String, String> results = new LinkedHashMap<String, String>();
		
		try {
			// Get path to XML file
			Path currentRelativePath = Paths.get("");
			String path = currentRelativePath.toAbsolutePath().toString() + "\\data\\Q2.xml";
			File inputFile = new File(path);
			
			// Create a document builder
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			// Parsing XML file
			Document doc = dBuilder.parse(inputFile);
			
			// Create list of Reference node elements
			NodeList Reference = doc.getElementsByTagName("Reference");
			
			// Loop through each element in Reference list
			for (int temp = 0; temp < Reference.getLength(); temp++) {
	            Node nNode = Reference.item(temp);
	            
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	               Element eElement = (Element) nNode;
	               if (RefCodes.contains(eElement.getAttribute("RefCode"))) { // If a targeted RefCode
	            	   String RefCode = eElement.getAttribute("RefCode");
	            	   String RefText = eElement.getElementsByTagName("RefText").item(0).getTextContent();
	            	   results.put(RefCode,RefText); // Add Ref Code and Text to results
	               }
	            }
	         }
		}
		catch (Exception e) { // Catch exceptions
	         e.printStackTrace();
	      }
		
		for (Map.Entry<String, String> entry : results.entrySet()) { // Print results
		    System.out.println("Ref Code = " + entry.getKey() + " , " + "Ref Text = " + entry.getValue());
		}
	}
	
	public static void main(String[] args) {
		XML_Parsing xml = new XML_Parsing(); // Create instance of XML_Parsing class
		System.out.println("Question One Results:");
		xml.question1(); // Run methods
		System.out.println("");
		System.out.println("Question Two Results:");
		xml.question2();
	}
}
