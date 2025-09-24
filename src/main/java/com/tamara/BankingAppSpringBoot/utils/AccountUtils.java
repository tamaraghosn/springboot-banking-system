package com.tamara.BankingAppSpringBoot.utils;

import java.time.Year;

public class AccountUtils {


    public static final String ACCOUNT_EXISTS_CODE ="001";

    public static final String ACCOUNT_EXISTS_MESSAGE="This user already has an account Created!";

    public static final String ACCOUNT_NOT_EXISTS_CODE ="003";

    public static final String ACCOUNT_NOT_EXISTS_MESSAGE="This user doesn't have an account!";

    public static final String ACCOUNT_FOUND_CODE ="004";

    public static final String ACCOUNT_FOUND_SUCCESS="User is found";

    public static final String ACCOUNT_CREDITED_SUCCESS_CODE ="005";

    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE="Account credited successfully";


    public static final String ACCOUNT_CREATION_SUCCESS="002";

    public static final String ACCOUNT_CREATION_MESSAGE="Account has been successfully created!";

    public static String generateAccountNumber() {

        /*the account number need to start with the current year
         * 2025 + randomSixDigits
         * */
        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;

        // generate a random nb between min and max
        int randNumber= (int) Math.floor(Math.random() * (max - min + 1) + min);

//    convert the current year to string and the rand nb also to string to concatenate together
        String year = String.valueOf(currentYear);
        String randNb = String.valueOf(randNumber);

        StringBuilder accountNumber= new StringBuilder();

        accountNumber.append(year).append(randNb);

        return accountNumber.toString();

    }
}
