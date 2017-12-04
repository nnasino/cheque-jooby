package starter.ebean.services;

import starter.ebean.dtos.ChequeDTO;
import starter.ebean.dtos.ResponseMessage;
import starter.ebean.models.Cheque;
import starter.ebean.models.User;

import java.util.List;

/** Service for handling manipulation of all {@link starter.ebean.models.Cheque} objects
 *
 */
public interface ChequeService {

    /** Saves the specified {@link starter.ebean.models.Cheque} object to the database and returns
     * the db id
     * @param cheque the Cheque object to be saved
     * @return the db id of the saved Cheque
     */
    Long addCheque(ChequeDTO cheque);

    /** Finds the {@link starter.ebean.models.Cheque} record with the specified db id
     *
     * @param id the db id of the required Cheque
     * @return the {@link Cheque} object if found or null if not found
     */
    Cheque findChequeById(Long id);

    /** Returns a {@link List} of the {@link starter.ebean.models.Cheque}s in the database
     *
     * @return List of Cheques
     */
    List<Cheque> findPage(int page, int pageSize);

    ResponseMessage assignChequeToUser(Long chequeId, Long userId);
}
