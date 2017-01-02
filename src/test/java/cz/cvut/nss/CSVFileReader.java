package cz.cvut.nss;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author materialy k cviceni ZKS
 * @since 02.01.17
 */
public class CSVFileReader {

    // for TestNG data provider
    public static Iterator<String[]> readCSVFileToIterator(String fileName) {
        List<String[]> records = new ArrayList<>();
        String record;

        try {
            BufferedReader file = new BufferedReader(new FileReader(fileName));
            while ((record = file.readLine()) != null) {
                String fields[] = record.split(";");
                records.add(fields);
            }
            file.close();
            return records.iterator();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // for JUnit data provider
    public static Collection<String[]> readCSVFileToCollection(String fileName) {
        List<String[]> records = new ArrayList<>();
        String record;

        try {
            BufferedReader file = new BufferedReader(new FileReader(fileName));
            while ((record = file.readLine()) != null) {
                String fields[] = record.split(";");
                records.add(fields);
            }
            file.close();
            return records;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
