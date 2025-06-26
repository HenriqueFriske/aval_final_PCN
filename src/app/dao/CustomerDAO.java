package app.dao;

import app.utils.db.ConnectionFactory;
import app.utils.db.DBException;
import app.model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Eldair F. Dornelles
 */
public class CustomerDAO {

    /**
     * Insere um cliente na tabela tb_customers.
     * Se o cliente já existir (mesmo ID), ele não será inserido novamente.
     * @param customer O objeto Customer a ser inserido.
     * @return true se a inserção for bem-sucedida, false caso contrário.
     */
    public boolean insert(Customer customer) {
        String sql = "INSERT INTO tb_customers (id, nome, cpf, email, telefone, celular, rua, numero, bairro, cidade, cep, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = new ConnectionFactory().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, customer.getId());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getCpf());
            stmt.setString(4, customer.getEmail());
            stmt.setString(5, customer.getHomePhone());
            stmt.setString(6, customer.getCellPhone());
            stmt.setString(7, customer.getAddress().getStreet());
            stmt.setInt(8, Integer.parseInt(customer.getAddress().getAddressNumber()));
            stmt.setString(9, customer.getAddress().getNeighborhood());
            stmt.setString(10, customer.getAddress().getCity());
            stmt.setString(11, customer.getAddress().getZipCode());
            stmt.setString(12, customer.getAddress().getUf());

            stmt.executeUpdate();
            return true;

        } catch (DBException ex) {
            System.err.println("Erro de conexão com o banco de dados: " + ex.getMessage());
        } catch (SQLException ex) {
            // O código de erro '23505' corresponde a uma violação de chave única (cliente já existe)
            if ("23505".equals(ex.getSQLState())) {
                // Silenciosamente ignora a tentativa de inserir um cliente duplicado
            } else {
                System.err.println("Erro de SQL ao inserir cliente: " + ex.getMessage());
            }
        } catch (NumberFormatException ex) {
            System.err.println("Erro de formato de número para o cliente ID " + customer.getId() + ": " + ex.getMessage());
        }
        return false;
    }
}