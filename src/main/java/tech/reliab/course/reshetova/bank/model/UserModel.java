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
public class UserModel {

    private String fullName;
    private LocalDate birthDate;
    private String job;
}
