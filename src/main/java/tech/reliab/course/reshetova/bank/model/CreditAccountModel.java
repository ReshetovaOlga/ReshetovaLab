package tech.reliab.course.reshetova.bank.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditAccountModel {

    private int userId;
    private int bankId;
    private LocalDate startDate;
    private int loanTermMonths;
    private double loanAmount;
    private double interestRate;
    private int employeeId;
    private int paymentAccountId;
}