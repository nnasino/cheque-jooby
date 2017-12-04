package mini.jooby.services;

import mini.jooby.dtos.LoanDTO;
import mini.jooby.models.Loan;
import mini.jooby.models.User;

import java.util.List;

/** Service for handling manipulation of all {@link Loan} objects
 *
 */
public interface LoanService {
    /** Saves the specified {@link Loan} object to the database and returns
     * the db id
     * @param loanDTO the Loan object to be saved
     * @return the db id of the saved Loan
     */
    Long addLoan(LoanDTO loanDTO, User addedBy);
    /** Finds the {@link Loan} record with the specified db id
     *
     * @param id the db id of the required Loan
     * @return the {@link Loan} object if found or null if not found
     */
    Loan findLoanById(Long id);
    /** Returns a {@link List} of the {@link Loan}s in the database
     *
     * @return List of Loans
     */
    List<Loan> findPage(int page, int pageSize);

    /** Returns a {@link List} of the {@link Loan}s in attached to the
     * user specified
     * @param userId the id of the user
     *
     * @return List of Loans attached to the user or an empty list if no loans are found
     */
    List<Loan> findLoansForUser(Long userId);


}
