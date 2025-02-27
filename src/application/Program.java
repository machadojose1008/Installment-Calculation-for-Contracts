package application;


import model.entities.Contract;
import model.entities.Installment;
import model.services.ContractService;
import model.services.PaypalService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);


        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("Enter with the contract data: ");
        System.out.print("Number: ");
        int number = sc.nextInt();
        System.out.print("Date (dd/MM/yyyy): ");
        sc.nextLine();
        LocalDate date = LocalDate.parse(sc.nextLine(),fmt);
        System.out.print("Contract total value: ");
        double totalValue = sc.nextDouble();

        Contract c = new Contract(number,date,totalValue);

        System.out.print("Enter the number of installments: ");
        int months = sc.nextInt();

        ContractService contractService = new ContractService(new PaypalService());

        contractService.processContract(c,months);


        System.out.println("\nInstallments: ");
        for(Installment installment: c.getInstallments()){
            System.out.println(installment);
        }


        sc.close();
    }
}
