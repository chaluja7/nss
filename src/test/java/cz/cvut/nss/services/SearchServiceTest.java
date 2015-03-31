package cz.cvut.nss.services;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;
import cz.cvut.nss.utils.EosDateTimeUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

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
    @Qualifier("jdbcSearchService")
    private SearchService searchService;

    @Test
    public void superTest() {

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat(EosDateTimeUtils.dateTimePattern);

        try {
            date = dateFormat.parse("08.12.2014 05:00");
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        long l = System.currentTimeMillis();
        List<SearchResultWrapper> foundedStops = searchService.findPathByDepartureDate(1, 5, date, 12, 2, 100);
        long executionTime = System.currentTimeMillis() - l;

        int i = 0;
    }


    @Test
    public void superTest2() {

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat(EosDateTimeUtils.dateTimePattern);

        try {
            date = dateFormat.parse("08.12.2014 10:40");
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        long l = System.currentTimeMillis();
        List<SearchResultWrapper> foundedStops = searchService.findPathByArrivalDate(1, 5, date, 12, 2, 100);
        long executionTime = System.currentTimeMillis() - l;

        int i = 0;
    }
}
