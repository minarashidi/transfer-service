package com.rashidi.transferservice.repository;

import com.rashidi.transferservice.domain.Deposit;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositRepository extends CrudRepository<Deposit, Long> {

  Optional<Deposit> findByRequestUid(String requestUid);
}
