package starter.ebean.services.implementations;

import com.google.inject.Inject;
import io.ebean.EbeanServer;
import starter.ebean.models.Cheque;
import starter.ebean.services.ChequeService;

public class ChequeServiceImpl implements ChequeService{
    @Inject
    private EbeanServer ebean;
    @Override
    public Long addCheque(Cheque cheque) {
        ebean.insert(cheque);
        return cheque.getId();
    }
}
