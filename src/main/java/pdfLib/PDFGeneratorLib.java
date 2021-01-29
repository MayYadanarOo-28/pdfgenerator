package pdfLib;


import libObj.Row;

import java.util.Arrays;
import java.util.List;

import static libService.LibService.*;

public class PDFGeneratorLib {
    public static void main(String[] args) {
        Row row1 = addCol(
                col(6, 12, "Hello","Hola","haha","Hello","Mg Mg","Mg Mg"),
                col(6, 12, "Hello","Hola","haha","Hello")

               );
        Row row2 = addCol(
                col(6, 14, "Aung Aung"),
                col(6, 15, "aa"));
        Row row3 = addCol(
                col(2, 12, "Hla Hla"),
                col(5, 16, "bb"));


        List<Row> rows = Arrays.asList(row1, row2, row3);
        String des = "src/main/resources/test.pdf";
        createPdf(des, rows);

    }


}
