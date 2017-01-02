package cz.cvut.nss.utils.traversal;

import cz.cvut.nss.CSVFileReader;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;

/**
 * Test DepartureComparatoru - data se ctou z gridu, kde mam vzdy dvojici objektu k porovnani a ocekavany vysledek.
 * File - traversalBranchWrapperDepartureComparatorTestData.csv
 *
 * Data pro testovani objektu byla vygenerovana pomoci PAIRWISE TESTING v aplikaci allPairs.
 * Nasledne byly zkombinovany do vsech kombinaci.
 *
 * @author jakubchalupa
 * @since 30.12.16
 */
@RunWith(value = Parameterized.class)
public class TraversalBranchWrapperDepartureComparatorTest {

    //parametry pro Parametrized testy; jde o atributy TravesalBranchWrapper
    /**
     * nodeTime 1. objektu
     */
    private long nodeTime1;

    /**
     * travel time 1. objektu
     */
    private long travelTime1;

    /**
     * 1. objekt pres pulnoc?
     */
    private boolean overMidnight1;

    /**
     * nodeTime 2. objektu
     */
    private long nodeTime2;

    /**
     * travelTime 2. objektu
     */
    private long travelTime2;

    /**
     * 2. objekt pres pulnoc?
     */
    private boolean overMidnight2;

    /**
     * ocekavana navratova hodnota porovnani (-1; 0; 1)
     */
    private int compareExpectedResult;

    /**
     * Constructor musi prijimat vsechny argumenty typu String, protoze se data ctou z csv souboru jako Stringy
     */
    public TraversalBranchWrapperDepartureComparatorTest(String nodeTime1, String travelTime1, String overMidnight1,
                                                       String nodeTime2, String travelTime2, String overMidnight2,
                                                       String compareExpectedResult) {
        this.nodeTime1 = Long.parseLong(nodeTime1);
        this.travelTime1 = Long.parseLong(travelTime1);
        this.overMidnight1 = Boolean.parseBoolean(overMidnight1);
        this.nodeTime2 = Long.parseLong(nodeTime2);
        this.travelTime2 = Long.parseLong(travelTime2);
        this.overMidnight2 = Boolean.parseBoolean(overMidnight2);
        this.compareExpectedResult = Integer.parseInt(compareExpectedResult);
    }

    @Parameters(name = "Radek dat cislo {index}, NodeTime1 {0}, TravelTime1 {1}, OverMidnight1 {2}, NodeTime2 {3}, TravelTime2 {4}, OverMidnight2 {5}, Ocekavany vysledek {6}")
    public static Collection<String[]> testingData() {
        return CSVFileReader.readCSVFileToCollection("src/test/resources/traversalBranchWrapperDepartureComparatorTestData.csv");
    }

    @Test
    public void testCompareWithGrid() throws Exception {
        TraversalBranchWrapper wrapper1 = new TraversalBranchWrapper(null, nodeTime1, travelTime1, overMidnight1);
        TraversalBranchWrapper wrapper2 = new TraversalBranchWrapper(null, nodeTime2, travelTime2, overMidnight2);

        int compareResult = new TraversalBranchWrapperDepartureComparator().compare(wrapper1, wrapper2);
        Assert.assertEquals(compareExpectedResult, compareResult);
    }

}