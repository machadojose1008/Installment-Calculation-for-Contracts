package model.services;

import model.entities.Contract;
import model.entities.Installment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ContractService {

    private OnlinePaymentService paymentService;
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ContractService(OnlinePaymentService paymentService){
        this.paymentService = paymentService;
    }
    public void processContract(Contract contract, Integer months){
        // 1- generate a list of installments
            //Generate empty list of Installments
            //Installment = (dueDate, value)
            // dueDate is generated based in the initial date from the contract and the amount of months
            //value is generated paymentService.paymentFee(paymentService.interest)
        double basicQuota = contract.getTotalValue()/months;
        List<Installment> installments = new ArrayList<>(months);
        for(int i=0; i<months;i++){
            LocalDate dueDate = contract.getDate().plusMonths(i+1);
            double interest = paymentService.interest(basicQuota,i+1) + basicQuota;
            double fee = paymentService.paymentFee(interest);
            double quota = fee + interest;
            installments.add(new Installment(dueDate,quota));
        }


        // 2 - add the list of installment to the contract received
        contract.setInstallments(installments);
    }
}
