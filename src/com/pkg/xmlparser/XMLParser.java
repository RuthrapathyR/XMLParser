/**
 * XMLParser
 */
package com.pkg.xmlparser;

import org.w3c.dom.*;

import src.com.pkg.fileOperations.FileCreate;

import java.io.*;

import javax.xml.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.*;

public class XMLParser {
    public static void main(String[] args) {
        Map<String,ArrayList<String>> dbSchema = new HashMap<>();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            documentBuilderFactory.setValidating(false);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.parse(new File("./DB.xml"));

            Element root = document.getDocumentElement();

            NodeList nList = root.getElementsByTagName("table");

             // Define the path to the directory
            String resultRootDirectory = "result"+File.separator+"src"+File.separator;
            String directoryPath = resultRootDirectory + "com" + File.separator + 
            "zlabs" + File.separator + 
            "distdb" + File.separator + 
            "ddtables" + File.separator + 
            "distdbmi";

            // Create a File object for the directory
            File directories = new File(directoryPath);

            if(!directories.exists()){
                System.out.println("Result Directory not existes so creating");
                directories.mkdirs();
            }

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);

                Element element = (Element) node;
                String tableName = element.getAttribute("name");
                ArrayList<String> columns = new ArrayList<>();
                NodeList clList = element.getElementsByTagName("column");
                for (int k = 0; k < clList.getLength(); k++) {
                    NamedNodeMap f = clList.item(k).getAttributes();
                    for (int a = 0; a < f.getLength(); a++) {
                        columns.add(f.item(a).getNodeValue().toUpperCase());
                    }
                }
                dbSchema.put(tableName.toUpperCase(), columns);
            }
                        
            List<String> keySet = new ArrayList<>(dbSchema.keySet());
            for(String key : keySet){
                FileCreate fileCreate = new FileCreate();
                fileCreate.setTemplate(key, dbSchema.get(key));
                fileCreate.createFile(key+".java");
            }

        } catch (Exception e) {
            System.out.println("Exception while creating file from dd.xml "+e);
        }
    }

    public static void readStudentFile() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            // Fetch XML File
            Document document = documentBuilder.parse(new File("./student.xml"));

            // Get root node
            Element root = document.getDocumentElement();
            System.out.println(root.getNodeName());

            // Get all students
            NodeList nList = document.getElementsByTagName("student");
            System.out.println(".................................");

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                System.out.println(); // Just a separator
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    // Print each student's detail
                    Element element = (Element) node;
                    System.out.println("Student id : " + element.getAttribute("id"));
                    System.out.println("Name : " + element.getElementsByTagName("Name").item(0).getTextContent());
                    System.out.println("Roll No : " + element.getElementsByTagName("id").item(0).getTextContent());
                    System.out
                            .println("Location : " + element.getElementsByTagName("location").item(0).getTextContent());
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}