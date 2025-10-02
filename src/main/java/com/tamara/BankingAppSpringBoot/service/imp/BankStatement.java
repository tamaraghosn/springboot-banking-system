package com.tamara.BankingAppSpringBoot.service.imp;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tamara.BankingAppSpringBoot.dto.EmailDetails;
import com.tamara.BankingAppSpringBoot.entity.Transaction;
import com.tamara.BankingAppSpringBoot.entity.User;
import com.tamara.BankingAppSpringBoot.repo.TransactionRepository;
import com.tamara.BankingAppSpringBoot.repo.UserRepository;
import com.tamara.BankingAppSpringBoot.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@AllArgsConstructor
@Slf4j
public class BankStatement {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    private static final String FILE = "C:\\Users\\USER\\bankstatements\\Mystatement.pdf";

    /*Retrieve list of transactions wihtin a date range given an account number
     *generate a pdf file of transcations
     * send the file via email
     * */

    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate) throws DocumentException, FileNotFoundException {

        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
        User userToGenerateStat = userRepository.findByAccountNumber(accountNumber);

        List<Transaction> transactionList = transactionRepository.findByAccountNumberAndCreatedAtBetween(accountNumber, start, end);

         /* The method sets up a PDF document with A4 size, decides where it will be saved
                and prepares it soo you can start writing content (like transactions, headers, or tables) inside it. */

        Rectangle statementSize = new Rectangle(PageSize.A4);
        Document document = new Document(statementSize, 50, 50, 50, 50);
        log.info("setting statement size of the document");
        OutputStream outputStream = new FileOutputStream(FILE);
        PdfWriter.getInstance(document, outputStream);

        // Open the document to start adding content
        document.open();
        // === BANK INFO HEADER ===
        Font bankFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.WHITE);
        PdfPTable bankInfoTable = new PdfPTable(1);
        PdfPCell bankName = new PdfPCell(new Phrase("TG Bank", bankFont));
        bankName.setHorizontalAlignment(Element.ALIGN_CENTER);
        bankName.setBorder(0);
        bankName.setBackgroundColor(BaseColor.BLUE);
        bankName.setPadding(20f);

        PdfPCell bankAddress = new PdfPCell(new Phrase("Beirut, Lebanon"));
        bankAddress.setBorder(0);
        bankAddress.setHorizontalAlignment(Element.ALIGN_CENTER);

        bankInfoTable.addCell(bankName);
        bankInfoTable.addCell(bankAddress);
        bankInfoTable.setSpacingAfter(20f);

        // === STATEMENT INFO ===
        Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        PdfPTable statementInfo = new PdfPTable(2);
        statementInfo.setWidthPercentage(100);

        statementInfo.addCell(getInfoCell("Start Date: " + startDate));
        statementInfo.addCell(getInfoCell("STATEMENT OF ACCOUNT", boldFont, Element.ALIGN_RIGHT));
        statementInfo.addCell(getInfoCell("End Date: " + endDate));
        statementInfo.addCell(getInfoCell("Customer Name: " +
                userToGenerateStat.getFirstName() + " " +
                userToGenerateStat.getLastName()));
        statementInfo.addCell(getInfoCell(""));
        statementInfo.addCell(getInfoCell("Customer Address: " + userToGenerateStat.getAddress()));
        statementInfo.setSpacingAfter(20f);

        // === TRANSACTION TABLE ===
        PdfPTable transactionTable = new PdfPTable(4);
        transactionTable.setWidthPercentage(100);
        transactionTable.setSpacingBefore(10f);

        Font headerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);

        transactionTable.addCell(getHeaderCell("DATE", headerFont));
        transactionTable.addCell(getHeaderCell("TRANSACTION TYPE", headerFont));
        transactionTable.addCell(getHeaderCell("AMOUNT", headerFont));
        transactionTable.addCell(getHeaderCell("STATUS", headerFont));

        AtomicInteger rowIndex = new AtomicInteger();
        transactionList.forEach(transaction -> {
            BaseColor bg = (rowIndex.getAndIncrement() % 2 == 0) ? BaseColor.LIGHT_GRAY : BaseColor.WHITE;

            transactionTable.addCell(getDataCell(transaction.getCreatedAt().toString(), bg));
            transactionTable.addCell(getDataCell(transaction.getTransactionType(), bg));
            transactionTable.addCell(getDataCell(transaction.getAmount().toString(), bg, Element.ALIGN_RIGHT));
            transactionTable.addCell(getDataCell(transaction.getStatus(), bg));
        });

        // === ADD TO DOCUMENT ===
        document.add(bankInfoTable);
        document.add(statementInfo);
        document.add(transactionTable);


        document.close();

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(userToGenerateStat.getEmail())
                .subject("STATEMENT OF ACCOUNT")
                .messageBody("Kindly find your requested account statement attached!")
                .attachment(FILE)
                .build();
        emailService.sendEmailWithAttachment(emailDetails);

        return transactionList;
    }

    // === HELPER METHODS ===
    private PdfPCell getHeaderCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(BaseColor.BLUE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(8f);
        return cell;
    }

    private PdfPCell getDataCell(String text, BaseColor bg) {
        return getDataCell(text, bg, Element.ALIGN_LEFT);
    }

    private PdfPCell getDataCell(String text, BaseColor bg, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setBackgroundColor(bg);
        cell.setHorizontalAlignment(alignment);
        cell.setPadding(6f);
        return cell;
    }

    private PdfPCell getInfoCell(String text) {
        return getInfoCell(text, new Font(Font.FontFamily.HELVETICA, 10), Element.ALIGN_LEFT);
    }

    private PdfPCell getInfoCell(String text, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(0);
        cell.setHorizontalAlignment(alignment);
        cell.setPadding(5f);
        return cell;
    }

}


//
//        PdfPTable bankInfoTable = new PdfPTable(1);
//        PdfPCell bankName = new PdfPCell(new Phrase("TG Bank"));
//        bankName.setBorder(0);
//        bankName.setBackgroundColor(BaseColor.BLUE);
//        bankName.setPadding(20f);
//        PdfPCell bankAddress = new PdfPCell(new Phrase("Beirut, Lebanon"));
//        bankAddress.setBorder(0);
//        bankInfoTable.addCell(bankName);
//        bankInfoTable.addCell(bankAddress);
//
//
//        PdfPTable statementInfo = new PdfPTable(2);
//        PdfPCell customerInfo = new PdfPCell(new Phrase("Start Date: " +startDate) );
//        customerInfo.setBorder(0);
//        PdfPCell statement = new PdfPCell(new Phrase("STATEMENT OF ACCOUNT"));
//        statement.setBorder(0);
//        PdfPCell stopDate = new PdfPCell(new Phrase("End Date: " + endDate));
//        stopDate.setBorder(0);
//        PdfPCell nameofCustomer= new PdfPCell(new Phrase("Customer Name: "
//                + userToGenerateStat.getFirstName() + " "
//                + userToGenerateStat.getLastName() + " "
//                + userToGenerateStat.getOtherName()));
//        PdfPCell space = new PdfPCell();
//        PdfPCell address = new PdfPCell(new Phrase("Cutsomer Address " +
//                userToGenerateStat.getAddress()
//                ));
//        address.setBorder(0);
//
//        statementInfo.addCell(customerInfo);
//        statementInfo.addCell(statement);
//        statementInfo.addCell(endDate);
//        statementInfo.addCell(nameofCustomer);
//        statementInfo.addCell(space);
//        statementInfo.addCell(address);
//
//
//        PdfPTable transactionTable = new PdfPTable(4);
//        PdfPCell date = new PdfPCell(new Phrase("DATE"));
//        date.setBackgroundColor(BaseColor.BLUE);
//        date.setBorder(0);
//        PdfPCell transactionType = new PdfPCell(new Phrase("TRANSACTION TYPE"));
//        transactionType.setBackgroundColor(BaseColor.BLUE);
//        transactionType.setBorder(0);
//        PdfPCell transactionAmount = new PdfPCell(new Phrase("TRANSACTION AMOUNT"));
//        transactionAmount.setBackgroundColor(BaseColor.BLUE);
//        transactionAmount.setBorder(0);
//        PdfPCell status = new PdfPCell(new Phrase("STATUS"));
//        status.setBackgroundColor(BaseColor.BLUE);
//        status.setBorder(0);
//
//        transactionTable.addCell(date);
//        transactionTable.addCell(transactionType);
//        transactionTable.addCell(transactionAmount);
//        transactionTable.addCell(status);
//
//        transactionList.forEach(transaction -> {
//            transactionTable.addCell(new Phrase(transaction.getCreatedAt().toString()));
//            transactionTable.addCell(new Phrase(transaction.getTransactionType()));
//            transactionTable.addCell(new Phrase(transaction.getAmount().toString()));
//            transactionTable.addCell(new Phrase(transaction.getStatus()));
//
//        });
//
//        document.add(bankInfoTable);
//        document.add(statementInfo);
//        document.add(transactionTable);
//        document.close();





