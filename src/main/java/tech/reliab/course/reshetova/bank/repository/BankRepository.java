package tech.reliab.course.reshetova.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.reliab.course.reshetova.bank.entity.Bank;

import java.util.List;
import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Integer> {

    Optional<Bank> findById(int id);

    void deleteById(int id);

    Optional<Bank> findByName(String name);
}
