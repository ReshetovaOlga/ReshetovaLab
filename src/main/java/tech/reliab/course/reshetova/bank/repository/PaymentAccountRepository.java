package tech.reliab.course.reshetova.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.reliab.course.reshetova.bank.entity.PaymentAccount;

import java.util.Optional;

public interface PaymentAccountRepository extends JpaRepository<PaymentAccount, Integer> {

    Optional<PaymentAccount> findById(int id);

    void deleteById(int id);

    Optional<PaymentAccount> findAllByUserId(int userId);
}
