package starter.ebean.services.implementations;

import com.google.inject.Inject;
import io.ebean.EbeanServer;
import starter.ebean.dtos.ResponseMessage;
import starter.ebean.models.Cheque;
import starter.ebean.models.User;
import starter.ebean.models.query.QCheque;
import starter.ebean.services.ChequeService;

import java.util.List;

public class ChequeServiceImpl implements ChequeService{
    @Inject
    private EbeanServer ebean;
    @Override
    public Long addCheque(Cheque cheque) {
        ebean.insert(cheque);
        return cheque.getId();
    }

    @Override
    public Cheque findChequeById(Long id){
        return ebean.find(Cheque.class, id);
    }

    @Override
    public List<Cheque> findAll(){
        return new QCheque().findList();
    }

    @Override
    public ResponseMessage assignChequeToUser(Long chequeId, Long userId){
        Cheque cheque = ebean.find(Cheque.class, chequeId);
        User user = ebean.find(User.class, userId);
        cheque.setUser(user);
        ebean.save(user);

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setSuccess(true);
        responseMessage.setMessage("Successfully assigned cheque to user");
        return responseMessage;
    }
}
