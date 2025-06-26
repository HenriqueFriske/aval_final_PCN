package app.utils.xml_handler;

import app.model.Address;
import app.model.Customer;
import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Eldair
 */
public class XmlHandler {

    //Questão 1a
    public static Document xmlFileToXmlDoc(String filePath) {
        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Questão 1b
    public static String xmlDocumentToString(Document xmlDoc) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            StringWriter sw = new StringWriter();
            transformer.transform(new DOMSource(xmlDoc), new StreamResult(sw));
            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void printXmlDocument(Document xmlDoc){
        System.out.println(xmlDocumentToString(xmlDoc));
    }
    
    //Questão 5
    public static void saveXmlDocumentInFile(Document xmlDoc, String filePath) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(xmlDoc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Questão 2
    public static List<Customer> xmlToCustomer(Document xmlDoc) {
        List<Customer> customers = new ArrayList<>();
        NodeList customerList = xmlDoc.getElementsByTagName("customer");

        for (int i = 0; i < customerList.getLength(); i++) {
            Node customerNode = customerList.item(i);
            if (customerNode.getNodeType() == Node.ELEMENT_NODE) {
                Element customerElement = (Element) customerNode;
                
                int id = Integer.parseInt(customerElement.getAttribute("id"));
                String name = customerElement.getElementsByTagName("name").item(0).getTextContent();
                String cpf = customerElement.getElementsByTagName("cpf").item(0).getTextContent();
                String email = customerElement.getElementsByTagName("email").item(0).getTextContent();
                String homePhone = customerElement.getElementsByTagName("homePhone").item(0).getTextContent();
                String cellPhone = customerElement.getElementsByTagName("cellPhone").item(0).getTextContent();

                Element addressElement = (Element) customerElement.getElementsByTagName("address").item(0);
                String street = addressElement.getElementsByTagName("street").item(0).getTextContent();
                String addressNumber = addressElement.getElementsByTagName("addressNumber").item(0).getTextContent();
                String neighborhood = addressElement.getElementsByTagName("neighborhood").item(0).getTextContent();
                String city = addressElement.getElementsByTagName("city").item(0).getTextContent();
                String zipCode = addressElement.getElementsByTagName("zipCode").item(0).getTextContent();
                String uf = addressElement.getElementsByTagName("uf").item(0).getTextContent();
                
                Address address = new Address(street, addressNumber, neighborhood, city, zipCode, uf);
                Customer customer = new Customer(id, name, cpf, email, homePhone, cellPhone, address);
                customers.add(customer);
            }
        }
        return customers;
    }

    //Questão 4
    public static void populateDocumentWithStates(Document doc, Map<String, Integer> stateCounts) {
        Element root = doc.createElement("states");
        doc.appendChild(root);

        for (Map.Entry<String, Integer> entry : stateCounts.entrySet()) {
            Element stateElement = doc.createElement("state");
            root.appendChild(stateElement);

            Element stateCodeElement = doc.createElement("stateCode");
            stateCodeElement.appendChild(doc.createTextNode(entry.getKey()));
            stateElement.appendChild(stateCodeElement);

            Element customersElement = doc.createElement("customers");
            customersElement.appendChild(doc.createTextNode(entry.getValue().toString()));
            stateElement.appendChild(customersElement);
        }
    }
    
     public static Document newXmlDocument() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            return db.newDocument();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}