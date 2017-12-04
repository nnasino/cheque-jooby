package mini.jooby.services;

import mini.jooby.dtos.ChequeDTO;
import mini.jooby.dtos.ResponseMessage;
import mini.jooby.models.Cheque;
import mini.jooby.models.User;

import java.util.List;

/** Service for handling manipulation of all {@link Cheque} objects
 *
 */
public interface ChequeService {

    /** Saves the specified {@link Cheque} object to the database and returns
     * the db id
     * @param chequeDTO the Cheque object to be saved
     * @return the db id of the saved Cheque
     */
    Long addCheque(ChequeDTO chequeDTO, User addedBy);

    /** Finds the {@link Cheque} record with the specified db id
     *
     * @param id the db id of the required Cheque
     * @return the {@link Cheque} object if found or null if not found
     */
    Cheque findChequeById(Long id);

    /** Returns a {@link List} of the {@link Cheque}s in the database
     *
     * @return List of Cheques
     */
    List<Cheque> findPage(int page, int pageSize);

    ResponseMessage assignChequeToUser(Long chequeId, Long userId);
}
