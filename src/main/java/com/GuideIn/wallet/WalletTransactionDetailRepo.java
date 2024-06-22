package com.GuideIn.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface WalletTransactionDetailRepo extends JpaRepository<WalletTransactionDetail, Long>{
	List<WalletTransactionDetail> findByEmail(String email);
}
