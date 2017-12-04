package mini.jooby.services;

import mini.jooby.dtos.CustomerDTO;
import mini.jooby.models.Customer;
import mini.jooby.models.User;

/** Service for handling manipulation of all {@link Customer} objects
 *
 */
public interface CustomerService {
    /** Saves the specified {@link Customer} object to the database and returns
     * the db id
     * @param customerDTO the Customer object to be saved
     * @return the db id of the saved Customer
     */
    Customer addCustomer(CustomerDTO customerDTO, User addedBy);

    /** Finds the {@link Customer} record with the specified db id
     *
     * @param id the db id of the required Customer
     * @return the {@link Customer} object if found or null if not found
     */
    Customer findCustomerById(Long id);

    Customer findCustomerByCustomerNumber(String customerNumber);

}
