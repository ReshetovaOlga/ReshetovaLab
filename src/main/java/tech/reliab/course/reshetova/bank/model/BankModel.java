package tech.reliab.course.reshetova.bank.model;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import tech.reliab.course.reshetova.bank.entity.*;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankModel {

    private String name;
    private int rating;
    private double totalMoney;
    private double interestRate;
    private List<BankOffice> offices;
    private List<BankAtm> atms;
    private List<Employee> employees;
    private List<CreditAccount> creditAccounts;
    private List<PaymentAccount> paymentAccounts;
}
