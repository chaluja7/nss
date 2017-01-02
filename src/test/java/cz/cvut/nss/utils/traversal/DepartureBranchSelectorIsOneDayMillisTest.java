package cz.cvut.nss.utils.traversal;

import cz.cvut.nss.CSVFileReader;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;

/**
 * Test isOneDayMillis - data se ctou z gridu, kde mam vzdy parametr a pro nej ocekavany vysledek volani metody.
 * File - departureBranchSelectorIsOneDayMillisTestData.csv
 *
 * Data pro testovani objektu byla vygenerovana pomoci PAIRWISE TESTING v aplikaci allPairs.
 *
 * @author jakubchalupa
 * @since 02.01.17
 */
@RunWith(value = Parameterized.class)
public class DepartureBranchSelectorIsOneDayMillisTest {

    private long time;

    private boolean expectedOneDayMillis;

    public DepartureBranchSelectorIsOneDayMillisTest(String time, String expectedOneDayMillis) {
        this.time = Long.parseLong(time);
        this.expectedOneDayMillis = Boolean.parseBoolean(expectedOneDayMillis);
    }

    @Parameters(name = "Radek dat cislo {index}, time {0}, expectedOneDayMillis {1}")
    public static Collection<String[]> testingData() {
        return CSVFileReader.readCSVFileToCollection("src/test/resources/departureBranchSelectorIsOneDayMillisTestData.csv");
    }

    @Test
    public void testIsOneDayMillis() throws Exception {
        boolean oneDayMillis = DepartureBranchSelector.isOneDayMillis(time);
        Assert.assertEquals(expectedOneDayMillis, oneDayMillis);
    }

}