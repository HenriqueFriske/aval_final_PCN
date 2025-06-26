package app;

import app.Threads.CityCustomerCounter;
import app.Threads.StateCustomerCounter;
import app.dao.CustomerDAO;
import app.model.Customer;
import app.utils.xml_handler.XmlHandler;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;


/**
 *
 * @author Eldair
 */
public class Main {

    public static void main(String[] args) {

        //Questão 1:  
        System.out.println("--- Questão 1 ---");
        Document xmlDoc = XmlHandler.xmlFileToXmlDoc("src/app/xml_files/CustormersSource.xml");
        XmlHandler.printXmlDocument(xmlDoc);

        //Questão 2:  
        System.out.println("\n--- Questão 2 ---");
        List<Customer> customers = XmlHandler.xmlToCustomer(xmlDoc);
        insertIntoTB_Customers(customers);
        System.out.println("Clientes inseridos no banco de dados.");

        //Questão 3:  
        System.out.println("\n--- Questão 3 ---");
        StateCustomerCounter stateCustomerCounter = new StateCustomerCounter(customers);
        CityCustomerCounter cityCustomerCounter = new CityCustomerCounter(customers);
        createAndExecuteTheThreads(stateCustomerCounter, cityCustomerCounter);
        
        System.out.println("Contagem de clientes por estado: " + stateCustomerCounter.getCustermersByState());
        System.out.println("Contagem de clientes por cidade: " + cityCustomerCounter.getCustomersByCity());


        // Questão 4
        System.out.println("\n--- Questão 4 ---");
        Map<String, Integer> customersByState = stateCustomerCounter.getCustermersByState();
        Document stateCustomerDocument = XmlHandler.newXmlDocument();
        XmlHandler.populateDocumentWithStates(stateCustomerDocument, customersByState);
        System.out.println("Documento XML com clientes por estado criado:");
        XmlHandler.printXmlDocument(stateCustomerDocument);


        //Questão 5
        System.out.println("\n--- Questão 5 ---");
        XmlHandler.saveXmlDocumentInFile(stateCustomerDocument, "src/app/xml_files/StateCustormers.xml");
        System.out.println("Documento salvo em src/app/xml_files/StateCustormers.xml");

    }


    private static void insertIntoTB_Customers(List<Customer> customers) {
      CustomerDAO customerDAO = new CustomerDAO();
      for(Customer customer : customers){
          customerDAO.insert(customer);
      }
    }

    private static void createAndExecuteTheThreads(StateCustomerCounter stateCustomerCounter, CityCustomerCounter cityCustomerCounter) {
        Thread threadState = new Thread(stateCustomerCounter);
        Thread threadCity = new Thread(cityCustomerCounter);

        threadState.start();
        threadCity.start();

        try {
            threadState.join();
            threadCity.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}