package com.jpmc.netbanking.scheduler.utils;


import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jpmc.netbanking.scheduler.dto.TransactionDto;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.List;
@Slf4j
public class PdfUtils {
    private PdfUtils(){}
    public static void generatePdf(String fileName, List<TransactionDto> transactions) throws Exception {
        Document document =new Document();
        try {
            String filePath = "C:\\Reports\\"+fileName + LocalDateTime.now().toLocalDate() + ".pdf";
            FileOutputStream outputStream = new FileOutputStream(filePath);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.add(new Paragraph("Account Statement"));
            document.add(new Paragraph("---------------------------------------------------------"));

            PdfPTable table = new PdfPTable(3); // 5 columns
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Set column widths
            float[] columnWidths = {2f, 2f, 2f};
            table.setWidths(columnWidths);
            // Add table header
            addTableHeader(table);
            transactions.stream().filter(s->s!=null).forEach(transaction->
                    addRows(table, transaction)  );
            document.add(table);
            document.close();
            log.info("PDF file saved: ");


        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
    private static void addTableHeader(PdfPTable table) {
        PdfPCell cell;

        cell = new PdfPCell(new Paragraph("Date"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Amount"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Type"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

    }

    private static void addRows(PdfPTable table, TransactionDto transaction) {
        PdfPCell cell;

        cell = new PdfPCell(new Paragraph(transaction.getTransaction_date().toString()));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(String.valueOf(transaction.getAmount())));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(transaction.getTransaction_type().toString()));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }


}
