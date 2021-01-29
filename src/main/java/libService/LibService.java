package libService;

import libObj.Column;
import libObj.Row;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LibService {
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
            column.value += a + ",";
        }
        column.fontSize = fontSize;
        return column;
    }
    public static void createNewText(String text, PDPageContentStream cont, PDFont font, Integer fontSize) throws IOException {

        cont.setFont(font, fontSize);
        cont.newLine();
        cont.showText(text);

    }

    public static void createPdf(String destinationFilePath, List<Row> rows) {
        try (final PDDocument document = new PDDocument()) {
            PDPage myPage = new PDPage();
            document.addPage(myPage);

            try (PDPageContentStream cont = new PDPageContentStream(document, myPage)) {
                // PDFont font = PDType0Font.load(document, new File("C:/Users/Developer1/workspace/pdfexport/public/font/NotoSansMyanmarRegular.ttf"));
                PDFont font = PDType1Font.TIMES_ROMAN;
                cont.setLeading(14.5f);


                float margin = 50;
                float width = myPage.getMediaBox().getWidth() / 12;
                float startY = myPage.getMediaBox().getUpperRightY() - margin;
                int i = 0;
                for (Row row : rows) {
                    int height=0;
                    float startX = myPage.getMediaBox().getLowerLeftX() + margin;
                    for (Column column : row.columns) {

                        String[] values = column.value.split(",", column.value.length());
                        cont.beginText();
                        cont.newLineAtOffset(startX, startY-i*18);
                        for(String s:values) {
                            createNewText(s,cont,font,column.fontSize);
                            height +=column.value.length();
                        }
                        cont.endText();
                        startX += width * column.columnSpan;
                    }

                    i++;
                    startY -=height/row.columns.size()/2;
                }

            }

            document.save(destinationFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
