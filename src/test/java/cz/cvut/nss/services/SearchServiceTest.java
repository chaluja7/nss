package cz.cvut.nss.services;

import cz.cvut.nss.SearchWrappers.SearchResultWrapper;
import cz.cvut.nss.utils.DateTimeUtils;
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
        DateFormat dateFormat = new SimpleDateFormat(DateTimeUtils.DATE_TIME_PATTERN);

        try {
            date = dateFormat.parse("26.06.2015 19:13");
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        long l = System.currentTimeMillis();
        List<SearchResultWrapper> foundedStops = searchService.findPathByDepartureDate(2449, 2450, date, 19, 1, -1);
        long executionTime = System.currentTimeMillis() - l;

        int i = 0;
    }


    @Test
    public void superTest2() {

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat(DateTimeUtils.DATE_TIME_PATTERN);

        try {
            date = dateFormat.parse("08.12.2014 19:14");
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        long l = System.currentTimeMillis();
        List<SearchResultWrapper> foundedStops = searchService.findPathByArrivalDate(1, 5, date, 12, 10, 100);
        long executionTime = System.currentTimeMillis() - l;

        int i = 0;
    }
}
