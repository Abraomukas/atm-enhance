package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ATM {
    private List<BankNote> notes;

    private List<BankNote> withdrawal;
    private int currentBalance;

    public ATM() {
        notes = new ArrayList<>();
        withdrawal = new ArrayList<>();

        currentBalance = calculateBalance();
    }

    private int calculateBalance() {
        int balance = 0;

        try {
            calculateBalanceFromFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (BankNote note :
                notes) {
            balance += note.value;
        }

        return balance;
    }

    private void calculateBalanceFromFile() throws FileNotFoundException {
        String fileLocation = "src/test/resources/notes.txt";
        Scanner scanner = new Scanner(new File(fileLocation));

        while (scanner.hasNext()) {
            String note = scanner.next();
            switch (note) {
                case "100" -> notes.add(BankNote.HUNDRED);
                case "50" -> notes.add(BankNote.FIFTY);
                case "20" -> notes.add(BankNote.TWENTY);
                case "10" -> notes.add(BankNote.TEN);
                case "5" -> notes.add(BankNote.FIVE);
            }
        }
    }


    private void withdrawalScenario(int moneyToWithdraw) {
        if (moneyToWithdraw > 0) {
            if (moneyToWithdraw % 100 == 0 && notes.contains(BankNote.HUNDRED)) {
                notes.remove(BankNote.HUNDRED);
                moneyToWithdraw -= 100;
                withdrawal.add(BankNote.HUNDRED);

                withdrawalScenario(moneyToWithdraw);

            } else if (moneyToWithdraw % 50 == 0 && notes.contains(BankNote.FIFTY)) {
                notes.remove(BankNote.FIFTY);
                moneyToWithdraw -= 50;
                withdrawal.add(BankNote.FIFTY);

                withdrawalScenario(moneyToWithdraw);

            } else if (moneyToWithdraw % 20 == 0 && notes.contains(BankNote.TWENTY)) {
                notes.remove(BankNote.TWENTY);
                moneyToWithdraw -= 20;
                withdrawal.add(BankNote.TWENTY);

                withdrawalScenario(moneyToWithdraw);
            } else if (moneyToWithdraw % 10 == 0 && notes.contains(BankNote.TEN)) {
                notes.remove(BankNote.TEN);
                moneyToWithdraw -= 10;
                withdrawal.add(BankNote.TEN);

                withdrawalScenario(moneyToWithdraw);
            } else if (moneyToWithdraw % 5 == 0 && notes.contains(BankNote.FIVE)) {
                notes.remove(BankNote.FIVE);
                moneyToWithdraw -= 5;
                withdrawal.add(BankNote.FIVE);

                withdrawalScenario(moneyToWithdraw);
            }
        } else {
            displayNotes();
        }
    }

    private void displayNotes() {
        int hundredNotes = countNotes(BankNote.HUNDRED);
        int fiftyNotes = countNotes(BankNote.FIFTY);
        int twentyNotes = countNotes(BankNote.TWENTY);
        int tenNotes = countNotes(BankNote.TEN);
        int fiveNotes = countNotes(BankNote.FIVE);

        System.out.println("******* WITHDRAWAL ******* ");

        System.out.println((hundredNotes > 0 ? "*** 100 NOTES - " + hundredNotes + " ***" : ""));
        System.out.println((fiftyNotes > 0 ? "*** 50 NOTES - " + fiftyNotes + " ***" : ""));
        System.out.println((twentyNotes > 0 ? "*** 20 NOTES - " + twentyNotes + " ***" : ""));
        System.out.println((tenNotes > 0 ? "*** 10 NOTES - " + tenNotes + " ***" : ""));
        System.out.println((fiveNotes > 0 ? "*** 5 NOTES - " + fiveNotes + " ***" : ""));
    }

    private int countNotes(BankNote note) {
        int count = 0;

        for (BankNote n :
                withdrawal) {
            if (n.value == note.value) {
                count++;
            }
        }

        return count;
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("******* ATM *******");
        System.out.println("******* TYPE 0 TO EXIT *******");
        System.out.println("******* HOW MUCH MONEY WOULD YOU LIKE TO WITHDRAW? *******");
        System.out.println("*** (IT HAS TO BE A MULTIPLE OF FIVE) ***");
        System.out.println("*** MONEY IN THIS ATM - " + currentBalance + " AED ***");

        int moneyToWithdraw = scanner.nextInt();

        if (moneyToWithdraw > 0) {
            if (moneyToWithdraw % 5 == 0) {
                withdrawalScenario(moneyToWithdraw);
            } else {
                System.out.println("*** THE NUMBER YOU TYPED IS NOT A MULTIPLE OF FIVE ***");
                System.out.println("******* HOW MUCH MONEY WOULD YOU LIKE TO WITHDRAW? *******");
                System.out.println("*** (IT HAS TO BE A MULTIPLE OF FIVE) ***");
                moneyToWithdraw = scanner.nextInt();
            }
        }

        scanner.close();
        System.out.println("******* FAREWELL *******");
    }
}


