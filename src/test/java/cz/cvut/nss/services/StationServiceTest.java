package cz.cvut.nss.services;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;
import cz.cvut.nss.dao.SearchDao;
import cz.cvut.nss.utils.EosDateTimeUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by jakubchalupa on 20.11.14.
 */
public class StationServiceTest extends AbstractServiceTest {

    @Autowired
    SearchDao searchDao;

    @Test
    public void testCreateAndGet() {
        //TODO this :)
        assertTrue(true);
    }

    @Test
    public void superTest() {

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat(EosDateTimeUtils.dateTimePattern);

        try {
            date = dateFormat.parse("12.12.2014 05:00");
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        List<SearchResultWrapper> foundedStops = searchDao.findRides(1, 5, date, 2);

        int i = 1;


    }

}
