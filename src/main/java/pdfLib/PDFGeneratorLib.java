package pdfLib;


import libObj.Paragraph;
import libObj.Row;

import java.util.Arrays;
import java.util.List;

import static libService.LibService.*;

public class PDFGeneratorLib {
    public static void main(String[] args) {
        Paragraph paragraph1=new Paragraph();
        paragraph1.text="Hello";
        paragraph1.fontSize=12;
        Paragraph paragraph2=new Paragraph();
        paragraph2.text="What";
        paragraph2.fontSize=20;

        List<Paragraph> paragraphs1 = Arrays.asList(paragraph1,paragraph2,paragraph1,paragraph1,paragraph1);
        List<Paragraph> paragraphs2 = Arrays.asList(paragraph2,paragraph2);


        Row row1 = addCol(
                col(paragraphs1,6,"left"),col(paragraphs2,6,"left"));
        Row row2 = addCol(
                col(paragraphs1,2,"left"),col(paragraphs2,6,"left"));
        Row row3 = addCol(
                col(paragraphs1,12,"center"));
        Row row4 = addCol(
                col(paragraphs1,12,"right"));

        List<Row> rows = Arrays.asList(row1,row2,row3,row4);
        String des = "src/main/resources/test.pdf";
        createPdf(des, rows);

    }


}
