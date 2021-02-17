package libService;

import libObj.Column;
import libObj.Paragraph;
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
            column.paragraphs = c.paragraphs;
            column.columnSpan = c.columnSpan;
            column.align =c.align;
            columnList.add(column);
            row.columns = columnList;
        }
        return row;
    }

    public static Column col(List<Paragraph> paragraphs, Integer colSpan,String align) {
        Column column = new Column();

        if (colSpan <= 12) {
            column.columnSpan = colSpan;
        } else {
            column.columnSpan = 12;
        }

        column.paragraphs = paragraphs;
        column.align=align;
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
                    int height = 0;
                    float startX = myPage.getMediaBox().getLowerLeftX() + margin;
                    for (Column column : row.columns) {
                        int align;

                        if (column.align.equals("left")) {
                            align=0;
                        } else if (column.align.equals("center")) {
                            if(column.columnSpan.equals(12)){

                                align=238-column.paragraphs.size();
                            }
                            else
                            align =50;
                        }
                        else{
                            if(column.columnSpan.equals(12)){
                                align=440-column.paragraphs.size();
                            }
                            else
                            align=80;
                        }
                        cont.beginText();
                        cont.newLineAtOffset(startX+align, startY - i * 17);
                        for (Paragraph paragraph : column.paragraphs) {
                            createNewText(paragraph.text, cont, font, paragraph.fontSize);
                            height += column.paragraphs.size();
                        }
                        cont.endText();
                        startX += width * column.columnSpan;

                    }
                    i++;
                    startY -= height / row.columns.size() * 5;
                }

            }
            document.save(destinationFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
