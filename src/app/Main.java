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

        System.out.println("--- Iniciando Avaliação II ---");

        // Questão 1: Carregar o arquivo XML
        System.out.println("\n[Passo 1/5] Lendo o arquivo XML de clientes...");
        Document xmlDoc = XmlHandler.xmlFileToXmlDoc("src/app/xml_files/CustormersSource.xml");
        System.out.println("Arquivo XML carregado com sucesso.");
        // XmlHandler.printXmlDocument(xmlDoc);

        // Questão 2: Converter XML para objetos e inserir no banco de dados
        System.out.println("\n[Passo 2/5] Convertendo XML para lista de clientes e inserindo no banco de dados...");
        List<Customer> customers = XmlHandler.xmlToCustomer(xmlDoc);
        insertIntoTB_Customers(customers);
        System.out.println("Clientes inseridos/atualizados no banco de dados.");

        // Questão 3: Contagem de clientes por estado e cidade com Threads
        System.out.println("\n[Passo 3/5] Processando contagem de clientes por estado e cidade com threads...");
        StateCustomerCounter stateCustomerCounter = new StateCustomerCounter(customers);
        CityCustomerCounter cityCustomerCounter = new CityCustomerCounter(customers);
        createAndExecuteTheThreads(stateCustomerCounter, cityCustomerCounter);
        
        System.out.println("Processamento de threads finalizado.");
        System.out.println("Contagem de clientes por estado: " + stateCustomerCounter.getCustermersByState());
        System.out.println("Contagem de clientes por cidade: " + cityCustomerCounter.getCustomersByCity());

        // Questão 4: Criar novo documento XML com os resultados
        System.out.println("\n[Passo 4/5] Criando novo documento XML com a contagem de clientes por estado...");
        Map<String, Integer> customersByState = stateCustomerCounter.getCustermersByState();
        Document stateCustomerDocument = XmlHandler.newXmlDocument();
        XmlHandler.populateDocumentWithStates(stateCustomerDocument, customersByState);
        System.out.println("Documento XML com o resultado criado em memória.");
        // XmlHandler.printXmlDocument(stateCustomerDocument);

        // Questão 5: Salvar o novo documento XML em um arquivo
        System.out.println("\n[Passo 5/5] Salvando o novo documento XML em arquivo...");
        XmlHandler.saveXmlDocumentInFile(stateCustomerDocument, "src/app/xml_files/StateCustormers.xml");
        System.out.println("Documento salvo com sucesso em: src/app/xml_files/StateCustormers.xml");

        System.out.println("\n--- Avaliação II Finalizada ---");
    }

    /**
     * Insere uma lista de clientes no banco de dados.
     * @param customers A lista de clientes a ser inserida.
     */
    private static void insertIntoTB_Customers(List<Customer> customers) {
        CustomerDAO customerDAO = new CustomerDAO();
        for (Customer customer : customers) {
            customerDAO.insert(customer);
        }
    }

    /**
     * Cria e executa as threads para contagem de clientes.
     * @param stateCustomerCounter O contador de clientes por estado.
     * @param cityCustomerCounter O contador de clientes por cidade.
     */
    private static void createAndExecuteTheThreads(StateCustomerCounter stateCustomerCounter, CityCustomerCounter cityCustomerCounter) {
        Thread threadState = new Thread(stateCustomerCounter);
        Thread threadCity = new Thread(cityCustomerCounter);

        threadState.start();
        threadCity.start();

        try {
            // Aguarda a finalização das duas threads antes de continuar
            threadState.join();
            threadCity.join();
        } catch (InterruptedException e) {
            System.err.println("A execução da thread foi interrompida: " + e.getMessage());
            Thread.currentThread().interrupt(); // Restaura o status de interrupção
        }
    }
}