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

    private Connection con;

    public CustomerDAO() {
        try {
            this.con = new ConnectionFactory().getConnection();
        } catch (DBException ex) {
            System.out.println("erro: "+ex.getMessage());
        }
    }

    public boolean insert(Customer customer) {

        String sql = "insert into tb_customers (id, nome,cpf,email,telefone,celular,rua,numero,bairro,cidade,cep,estado) "
                + " values (?,?,?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
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
        } catch (SQLException ex) {
            // Ignora o erro de chave primária duplicada para permitir a re-execução
            if (!ex.getSQLState().equals("23505")) { 
              System.out.println("Erro ao cadastrar o cliente, " + ex.getMessage());
            }
            return false;
        }
    }
}