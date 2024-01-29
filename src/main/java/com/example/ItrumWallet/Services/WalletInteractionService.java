package com.example.ItrumWallet.Services;

import com.example.ItrumWallet.Entities.Wallet;
import com.example.ItrumWallet.Exceptions.InsufficientBalanceException;
import com.example.ItrumWallet.Exceptions.WalletNotFoundException;
import com.example.ItrumWallet.Repositories.WalletRepository;
import com.example.ItrumWallet.WalletDto.OperationType;
import com.example.ItrumWallet.WalletDto.WalletDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class WalletInteractionService {

    private final Lock balanceUpdateLock = new ReentrantLock();

    @Transactional
    public Wallet updateWallet(WalletDto walletDto, WalletRepository walletRepository) {
        Wallet wallet;
        try {
            // Multithreading is not optimal.
            // Ideally, fine-grained locking should have been used by locking each wallet separately.
            balanceUpdateLock.lock();
            wallet = walletRepository.findByValletId(walletDto.getValletId()).orElseThrow(() -> new WalletNotFoundException("Wallet not found with ID: " + walletDto.getValletId()));
            if (walletDto.getOperationType().equals(OperationType.DEPOSIT.getType())) {
                wallet.setBalance(wallet.getBalance().add(walletDto.getAmount()));
            } else if (walletDto.getOperationType().equals(OperationType.WITHDRAW.getType())) {
                var result = wallet.getBalance().subtract(walletDto.getAmount());
                if (result.longValue()>=0)
                    wallet.setBalance(result);
                else throw new InsufficientBalanceException("Current balance is insufficient for withdrawal.");
            }
        } finally {
            balanceUpdateLock.unlock();
        }
        return wallet;
    }

    public String getWalletBalance(UUID WALLET_UUID, WalletRepository walletRepository) {
        Wallet wallet = walletRepository.findByValletId(WALLET_UUID).orElseThrow(() -> new WalletNotFoundException("Wallet not found with ID: " + WALLET_UUID));
        return wallet.getBalance().toString();
    }
}
