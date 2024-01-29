package com.example.ItrumWallet.Controllers;

import com.example.ItrumWallet.Entities.Wallet;
import com.example.ItrumWallet.Exceptions.InsufficientBalanceException;
import com.example.ItrumWallet.Exceptions.WalletNotFoundException;
import com.example.ItrumWallet.Repositories.WalletRepository;
import com.example.ItrumWallet.Services.WalletInteractionService;
import com.example.ItrumWallet.WalletDto.WalletDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class WalletInteractionController {
    private final WalletRepository walletRepository;

    private final WalletInteractionService walletInteractionService;

    @Autowired
    public WalletInteractionController(WalletRepository walletRepository,WalletInteractionService walletInteractionService) {
        this.walletRepository = walletRepository;
        this.walletInteractionService=walletInteractionService;
    }

    @PostMapping("api/v1/wallet")
    public Wallet UpdateWallet(@Valid @RequestBody WalletDto walletDto) {
         return walletRepository.save(walletInteractionService.updateWallet(walletDto,walletRepository));
    }

    @GetMapping("api/v1/wallets/{WALLET_UUID}")
    public String GetWallet(@Valid @PathVariable UUID WALLET_UUID) {
        return walletInteractionService.getWalletBalance(WALLET_UUID,walletRepository);
    }
}
