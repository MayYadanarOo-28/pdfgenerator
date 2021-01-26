package pdfLib;

import libObj.Column;
import libObj.Row;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PDFGeneratorLib {
    public static void main(String[] args) {
        Row row1 = addCol(
                col(2, 12, "Hello"),
                col(2, 12, "gg"),
                col(2, 12, "world"));
        Row row2 = addCol(
                col(6, 14, "Hello", "world"),
                col(6, 14, "aa"));
        Row row3 = addCol(
                col(2, 12, "Hello", "world"),
                col(5, 16, "bb"));


        List<Row> rows = Arrays.asList(row1, row2, row3);
        String des = "src/main/resources/test.pdf";
        createPdf(des, rows);

    }

    public static void createPdf(String destinationFilePath, List<Row> rows) {
        try (final PDDocument document = new PDDocument()) {
            PDPage myPage = new PDPage();
            document.addPage(myPage);

            try (PDPageContentStream cont = new PDPageContentStream(document, myPage)) {
                // PDFont font = PDType0Font.load(document, new File("C:/Users/Developer1/workspace/pdfexport/public/font/NotoSansMyanmarRegular.ttf"));
                PDFont font = PDType1Font.TIMES_ROMAN;
                cont.setLeading(1.5f);
                float margin = 50;
                float width = myPage.getMediaBox().getWidth() / 12;
                float startY = myPage.getMediaBox().getUpperRightY() - margin;
                int i = 0;
                for (Row row : rows) {
                    float startX = myPage.getMediaBox().getLowerLeftX() + margin;
                    for (Column column : row.columns) {
                        createSingleText(column.value, cont, font, startX, startY - 18 * i, column.fontSize);
                        startX += width * column.columnSpan;
                    }
                    i++;
                }

            }

            document.save(destinationFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createSingleText(String text, PDPageContentStream cont, PDFont font, float startX, float startY, Integer fontSize) throws IOException {
        cont.beginText();
        cont.setFont(font, fontSize);
        cont.newLineAtOffset(startX, startY);
        cont.newLine();
        cont.showText(text);
        cont.endText();
    }

    public static Column col(Integer colSpan, Integer fontSize, String... string) {
        Column column = new Column();

        if (colSpan <= 12) {
            column.columnSpan = colSpan;
        } else {
            column.columnSpan = 12;
        }
        column.value = "";
        for (String a : string) {
            column.value += a + " ";
        }
        column.fontSize = fontSize;
        return column;
    }

    public static Row addCol(Column... columns) {
        Row row = new Row();
        List<Column> columnList = new ArrayList<>();
        for (Column c : columns) {
            Column column = new Column();
            column.value = c.value;
            column.columnSpan = c.columnSpan;
            column.fontSize = c.fontSize;
            columnList.add(column);
            row.columns = columnList;
        }

        return row;
    }

}
