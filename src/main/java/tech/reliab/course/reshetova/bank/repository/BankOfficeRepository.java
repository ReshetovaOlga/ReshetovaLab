package tech.reliab.course.reshetova.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.reliab.course.reshetova.bank.entity.BankOffice;

import java.util.List;
import java.util.Optional;

public interface BankOfficeRepository extends JpaRepository<BankOffice, Integer> {

    Optional<BankOffice> findById(int id);

    void deleteById(int id);

    List<BankOffice> findAllByBankId(int bankId);
}
