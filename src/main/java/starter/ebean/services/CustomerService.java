package starter.ebean.services;

import starter.ebean.dtos.CustomerDTO;
import starter.ebean.models.Customer;

/** Service for handling manipulation of all {@link starter.ebean.models.Customer} objects
 *
 */
public interface CustomerService {
    /** Saves the specified {@link starter.ebean.models.Customer} object to the database and returns
     * the db id
     * @param customerDTO the Customer object to be saved
     * @return the db id of the saved Customer
     */
    Long addCustomer(CustomerDTO customerDTO);

    /** Finds the {@link starter.ebean.models.Customer} record with the specified db id
     *
     * @param id the db id of the required Customer
     * @return the {@link Customer} object if found or null if not found
     */
    Customer findCustomerById(Long id);

    Customer findCustomerByCustomerNumber(String customerNumber);

}
