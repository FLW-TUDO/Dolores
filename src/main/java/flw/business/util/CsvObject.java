/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tilu
 */
public class CsvObject {
    private final String separator = ";";
    private final String originalData;
    private List<String[]> lcachedTableData = null;

    public CsvObject(String originalData)
    {
        this.originalData = originalData;
    }

    public List<String[]> getTableData()
    {
        if (lcachedTableData == null)
        {
            fillTableData();
        }
        return lcachedTableData;
    }

    private void fillTableData()
    {
        lcachedTableData = new ArrayList<String[]>();
        String[] arrLines = originalData.split("\r\n");
        
        if (arrLines.length == 1){
            arrLines = originalData.split("\n");
            for (String line : arrLines)
            {
                lcachedTableData.add(line.split(separator));
            }
        }
        else{
            for (String line : arrLines)
            {
                lcachedTableData.add(line.split(separator));
            }
        }
    }
}
