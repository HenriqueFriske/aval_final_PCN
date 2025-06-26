package app.utils.xml_handler;

import app.model.Address;
import app.model.Customer;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Classe utilitária para manipulação de arquivos e documentos XML.
 * @author Eldair
 */
public class XmlHandler {

    /**
     * Questão 1a: Lê um arquivo XML do caminho especificado e o converte para um objeto Document.
     * @param filePath O caminho do arquivo XML.
     * @return Um objeto Document representando o XML, ou null se ocorrer um erro.
     */
    public static Document xmlFileToXmlDoc(String filePath) {
        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            return doc;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.err.println("Erro ao ler o arquivo XML: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Questão 1b: Converte um objeto Document para uma representação em String formatada.
     * @param xmlDoc O documento XML a ser convertido.
     * @return Uma String com o conteúdo do XML, ou null em caso de erro.
     */
    public static String xmlDocumentToString(Document xmlDoc) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            // Configurações para uma saída de XML bem formatada
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            StringWriter sw = new StringWriter();
            transformer.transform(new DOMSource(xmlDoc), new StreamResult(sw));
            return sw.toString();
        } catch (TransformerException e) {
            System.err.println("Erro ao converter Document para String: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Imprime a representação em String de um documento XML no console.
     * @param xmlDoc O documento a ser impresso.
     */
    public static void printXmlDocument(Document xmlDoc){
        String xmlString = xmlDocumentToString(xmlDoc);
        if (xmlString != null) {
            System.out.println(xmlString);
        }
    }
    
    /**
     * Questão 5: Salva um objeto Document em um arquivo XML no caminho especificado.
     * @param xmlDoc O documento a ser salvo.
     * @param filePath O caminho do arquivo de destino.
     */
    public static void saveXmlDocumentInFile(Document xmlDoc, String filePath) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            
            DOMSource source = new DOMSource(xmlDoc);
            StreamResult result = new StreamResult(new File(filePath));
            
            transformer.transform(source, result);
        } catch (TransformerException e) {
            System.err.println("Erro ao salvar o documento XML no arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Questão 2: Converte os nós 'customer' de um Document em uma lista de objetos Customer.
     * @param xmlDoc O documento XML contendo os dados dos clientes.
     * @return Uma lista de objetos Customer.
     */
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

    /**
     * Questão 4: Popula um Document com dados de um Map de contagem de clientes por estado.
     * @param doc O documento XML a ser populado.
     * @param stateCounts Um Map contendo a contagem de clientes por estado.
     */
    public static void populateDocumentWithStates(Document doc, Map<String, Integer> stateCounts) {
        Element root = doc.createElement("states");
        doc.appendChild(root);

        // Ordena as entradas do mapa pela chave (sigla do estado) para um XML mais organizado
        stateCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    Element stateElement = doc.createElement("state");
                    root.appendChild(stateElement);

                    Element stateCodeElement = doc.createElement("stateCode");
                    stateCodeElement.appendChild(doc.createTextNode(entry.getKey()));
                    stateElement.appendChild(stateCodeElement);

                    Element customersElement = doc.createElement("customers");
                    customersElement.appendChild(doc.createTextNode(entry.getValue().toString()));
                    stateElement.appendChild(customersElement);
                });
    }
    
     /**
      * Cria e retorna um novo objeto Document vazio.
      * @return Um novo Document, ou null se ocorrer um erro.
      */
     public static Document newXmlDocument() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            return db.newDocument();
        } catch (ParserConfigurationException e) {
            System.err.println("Erro na configuração do parser ao criar novo XML: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}