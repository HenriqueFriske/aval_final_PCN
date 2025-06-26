package app.Threads;

import app.model.Customer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Eldair
 */
public class StateCustomerCounter implements Runnable {

    private List<Customer> customers;
    private Map<String, Integer> custermersByState;

    public StateCustomerCounter(List<Customer> customers) {
        this.customers = customers;
        this.custermersByState = new HashMap<>();
    }

    @Override
    public void run() {
        for (Customer customer : customers) {
            String state = customer.getAddress().getUf();
            custermersByState.put(state, custermersByState.getOrDefault(state, 0) + 1);
        }
    }

    public Map<String, Integer> getCustermersByState() {
        return custermersByState;
    }

}