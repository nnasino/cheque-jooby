package starter.ebean.services;

import starter.ebean.dtos.BranchDTO;
import starter.ebean.dtos.ResponseMessage;
import starter.ebean.models.Branch;
import starter.ebean.models.Branch;
import starter.ebean.models.User;

import java.util.List;

/** Service for handling manipulation of all {@link starter.ebean.models.Branch} objects
 *
 */
public interface BranchService {

    void mockBranch();
    Branch toEntity(BranchDTO branchDTO);

    /** Saves the specified {@link starter.ebean.dtos.BranchDTO} object to the database and returns
     * the db id
     * @param branch the Branch Data transfer object to be saved
     * @return the db id of the saved Branch
     */
    Long addBranch(BranchDTO branch, User addedBy);

    /** Finds the {@link starter.ebean.models.Branch} record with the specified db id
     *
     * @param id the db id of the required Branch
     * @return the {@link Branch} object if found or null if not found
     */
    Branch findBranchById(Long id);
/** Finds the {@link starter.ebean.models.Branch} record with the specified branch code
     *
     * @param code the branch code of the required Branch
     * @return the {@link Branch} object if found or null if not found
     */
    Branch findBranchByCode(String code);

    /** Returns a {@link List} of all the {@link starter.ebean.models.Branch}s in the database
     *
     * @return List of Branchs
     */
    List<Branch> findAll();

}