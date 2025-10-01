package com.tamara.BankingAppSpringBoot.service.imp;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tamara.BankingAppSpringBoot.entity.Transaction;
import com.tamara.BankingAppSpringBoot.entity.User;
import com.tamara.BankingAppSpringBoot.repo.TransactionRepository;
import com.tamara.BankingAppSpringBoot.repo.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.result.Output;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class BankStatement {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;


    private static final String FILE = "C:\\Users\\USER\\bankstatements\\Mystatement.pdf";

    /*Retrieve list of transactions wihtin a date range given an account number
    *generate a pdf file of transcations
    * send the file via email
    * */

    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate) throws DocumentException, FileNotFoundException {

        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
        User userToGenerateStat =userRepository.findByAccountNumber(accountNumber);

        List<Transaction> transactionList = transactionRepository.findByAccountNumberAndCreatedAtBetween(accountNumber, start, end);

         /* The method sets up a PDF document with A4 size, decides where it will be saved
                and prepares it soo you can start writing content (like transactions, headers, or tables) inside it. */

        Rectangle statementSize= new Rectangle(PageSize.A4);
        Document document = new Document(statementSize);
        log.info("setting statement size of the document");
        OutputStream outputStream = new FileOutputStream(FILE);
        PdfWriter.getInstance(document, outputStream);

        // Open the document to start adding content
        document.open();

        PdfPTable bankInfoTable = new PdfPTable(1);
        PdfPCell bankName = new PdfPCell(new Phrase("TG Bank"));
        bankName.setBorder(0);
        bankName.setBackgroundColor(BaseColor.BLUE);
        bankName.setPadding(20f);
        PdfPCell bankAddress = new PdfPCell(new Phrase("Beirut, Lebanon"));
        bankAddress.setBorder(0);
        bankInfoTable.addCell(bankName);
        bankInfoTable.addCell(bankAddress);
        PdfPTable statementInfo = new PdfPTable(2);
        PdfPCell customerInfo = new PdfPCell(new Phrase("Start Date: " +startDate) );
        customerInfo.setBorder(0);
        PdfPCell statement = new PdfPCell(new Phrase("STATEMENT OF ACCOUNT"));
        statement.setBorder(0);
        PdfPCell stopDate = new PdfPCell(new Phrase("End Date: " + endDate));
        stopDate.setBorder(0);
        PdfPCell nameofCustomer= new PdfPCell(new Phrase("Customer Name: "
                + userToGenerateStat.getFirstName() + " "
                + userToGenerateStat.getLastName() + " "
                + userToGenerateStat.getOtherName()));
        PdfPCell space = new PdfPCell();
        PdfPCell address = new PdfPCell(new Phrase("Cutsomer Address " +
                userToGenerateStat.getAddress()
                ));
        address.setBorder(0);


        return transactionList;


//        List<Transaction> transactionList = transactionRepository.findAll().stream().filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
//                .filter(transaction -> transaction.getCreatedAt().isAfter(start))
//                .filter(transaction -> transaction.)






    }


}
