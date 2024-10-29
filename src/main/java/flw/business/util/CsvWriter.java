/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Writes a CSV to export and save a game (DoloresGameInfo)
 * @author stbi
 */
public class CsvWriter {
    private final String CRLF = "\r\n";
    private String delimiter = ",";

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public void exportCsv(List<List<String>> twoDimensionalData, String filename) {
        try {
            
            FileWriter writer = new FileWriter(filename);

            for (int i = 0; i < twoDimensionalData.size(); i++) {
                for (int j = 0; j < twoDimensionalData.get(i).size(); j++) {
                    writer.append(
                            twoDimensionalData.get(i).get(j));
                
                    //Das Trennzeichen einfuegen
                    if (j < twoDimensionalData.get(i).size() - 1) {
                        writer.append(delimiter);
                    }
                }
                //Das Trennzeichen und das Zeilenende einfuegen
                if (i < twoDimensionalData.size() - 1) {
                    writer.append(delimiter + CRLF);
                }
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
}
