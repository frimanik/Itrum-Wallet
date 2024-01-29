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

@Service
public class WalletInteractionService {


    @Transactional
    public Wallet updateWallet(WalletDto walletDto, WalletRepository walletRepository) {
        Wallet wallet;
             wallet = walletRepository.findByValletId(walletDto.getValletId()).orElseThrow(() -> new WalletNotFoundException("Wallet not found with ID: " + walletDto.getValletId()));
            if (walletDto.getOperationType().equals(OperationType.DEPOSIT.getType())) {
                wallet.setBalance(wallet.getBalance().add(walletDto.getAmount()));
            } else if (walletDto.getOperationType().equals(OperationType.WITHDRAW.getType())) {
                var result = wallet.getBalance().subtract(walletDto.getAmount());
                if (result.longValue()>=0)
                    wallet.setBalance(result);
                else throw new InsufficientBalanceException("Current balance is insufficient for withdrawal.");
            }
        return wallet;
    }

    @Transactional()
    public String getWalletBalance(UUID WALLET_UUID, WalletRepository walletRepository) {
        Wallet wallet = walletRepository.findByValletId(WALLET_UUID).orElseThrow(() -> new WalletNotFoundException("Wallet not found with ID: " + WALLET_UUID));
        return wallet.getBalance().toString();
    }
}
