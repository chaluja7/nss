package cz.cvut.nss.services.batch;

import cz.cvut.nss.entities.GoogleStation;
import org.springframework.batch.item.*;

/**
 * @author jakubchalupa
 * @since 14.04.15
 */
public class GoogleStationReader implements ItemStreamReader<GoogleStation> {

    @Override
    public GoogleStation read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {

    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {

    }

    @Override
    public void close() throws ItemStreamException {

    }

}
