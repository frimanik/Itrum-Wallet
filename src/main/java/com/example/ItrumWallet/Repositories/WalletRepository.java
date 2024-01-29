package com.example.ItrumWallet.Repositories;

import com.example.ItrumWallet.Entities.Wallet;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID>  {

   Optional<Wallet> findByValletId(UUID uuid);
}