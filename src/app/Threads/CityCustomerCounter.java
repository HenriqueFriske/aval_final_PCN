package app.Threads;

import app.model.Customer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Eldair
 */
public class CityCustomerCounter implements Runnable {
    
    private List<Customer> customers;
    private Map<String, Integer> customersByCity;

    public CityCustomerCounter(List<Customer> customers) {
        this.customers = customers;
        this.customersByCity = new HashMap<>();
    }

    @Override
    public void run() {
        for (Customer customer : customers) {
            String city = customer.getAddress().getCity();
            customersByCity.put(city, customersByCity.getOrDefault(city, 0) + 1);
        }
    }
    
    public Map<String, Integer> getCustomersByCity() {
        return customersByCity;
    }
}