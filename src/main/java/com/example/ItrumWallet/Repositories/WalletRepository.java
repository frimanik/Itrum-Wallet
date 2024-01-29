package com.example.ItrumWallet.Repositories;

import com.example.ItrumWallet.Entities.Wallet;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID>  {

   @Lock(LockModeType.PESSIMISTIC_WRITE)

   Optional<Wallet> findByValletId(UUID uuid);
}
