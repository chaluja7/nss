package cz.cvut.nss.utils.traversal;

import cz.cvut.nss.CSVFileReader;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;

/**
 * Test timeIsOverMidnight - data se ctou z gridu, kde mam vzdy parametry a pro ne ocekavany vysledek volani metody.
 * File - departureBranchSelectorTimeIsOverMidnightTestData.csv
 *
 * Data pro testovani objektu byla vygenerovana pomoci PAIRWISE TESTING v aplikaci allPairs.
 * Metoda porovnani muze vyhodit vyjimku - to take kontroluji pomoci csv konfigurace.
 *
 * @author jakubchalupa
 * @since 02.01.17
 */
@RunWith(value = Parameterized.class)
public class DepartureBranchSelectorTimeIsOverMidnightTest {

    private long startTimeInMillis;

    private long currentTimeInMillis;

    private boolean expectedException;

    private boolean expectedResult;

    public DepartureBranchSelectorTimeIsOverMidnightTest(String startTimeInMillis, String currentTimeInMillis, String expectedException, String expectedResult) {
        this.startTimeInMillis = Long.parseLong(startTimeInMillis);
        this.currentTimeInMillis = Long.parseLong(currentTimeInMillis);
        this.expectedException = Boolean.parseBoolean(expectedException);
        this.expectedResult = Boolean.parseBoolean(expectedResult);
    }

    @Parameters(name = "Radek dat cislo {index}, startTimeInMillis {0}, currentTimeInMillis {1}, expectedResult {2}")
    public static Collection<String[]> testingData() {
        return CSVFileReader.readCSVFileToCollection("src/test/resources/departureBranchSelectorTimeIsOverMidnightTestData.csv");
    }

    @Test
    public void testTimeIsOverMidnight() {
        boolean exceptionThrown = false;
        boolean timeIsOverMidnight = false;

        try {
            timeIsOverMidnight = DepartureBranchSelector.timeIsOverMidnight(startTimeInMillis, currentTimeInMillis);
        } catch(Exception e) {
            exceptionThrown = true;
        }

        if(expectedException) {
            Assert.assertTrue(exceptionThrown);
        } else {
            Assert.assertFalse(exceptionThrown);
            Assert.assertEquals(expectedResult, timeIsOverMidnight);
        }
    }

}