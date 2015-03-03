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

/**
 * @author jakubchalupa
 * @since 23.02.15
 */
public class SearchServiceTest extends AbstractServiceTest {

    @Autowired
    private SearchDao searchDao;

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

        long l = System.currentTimeMillis();
        List<SearchResultWrapper> foundedStops = searchDao.findRidesNew(1, 5, date, 2);
        long executionTime = System.currentTimeMillis() - l;

        int i = 0;
    }
}
