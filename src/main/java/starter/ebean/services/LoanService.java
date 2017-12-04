package starter.ebean.services;

import starter.ebean.dtos.LoanDTO;
import starter.ebean.models.Loan;
import starter.ebean.models.User;

import java.util.List;

/** Service for handling manipulation of all {@link starter.ebean.models.Loan} objects
 *
 */
public interface LoanService {
    /** Saves the specified {@link starter.ebean.models.Loan} object to the database and returns
     * the db id
     * @param loanDTO the Loan object to be saved
     * @return the db id of the saved Loan
     */
    Long addLoan(LoanDTO loanDTO, User addedBy);
    /** Finds the {@link starter.ebean.models.Loan} record with the specified db id
     *
     * @param id the db id of the required Loan
     * @return the {@link Loan} object if found or null if not found
     */
    Loan findLoanById(Long id);
    /** Returns a {@link List} of the {@link starter.ebean.models.Loan}s in the database
     *
     * @return List of Loans
     */
    List<Loan> findPage(int page, int pageSize);

    /** Returns a {@link List} of the {@link starter.ebean.models.Loan}s in attached to the
     * user specified
     * @param userId the id of the user
     *
     * @return List of Loans attached to the user or an empty list if no loans are found
     */
    List<Loan> findLoansForUser(Long userId);


}
