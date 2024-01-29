package com.example.ItrumWallet;

import com.example.ItrumWallet.Entities.Wallet;
import com.example.ItrumWallet.Exceptions.InsufficientBalanceException;
import com.example.ItrumWallet.Exceptions.WalletNotFoundException;
import com.example.ItrumWallet.Repositories.WalletRepository;
import com.example.ItrumWallet.Services.WalletInteractionService;
import com.example.ItrumWallet.WalletDto.OperationType;
import com.example.ItrumWallet.WalletDto.WalletDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//Ideally there should be integration test that would send requests directly to controllers
@SpringBootTest
class WalletInteractionServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletInteractionService walletInteractionService;

    @Test
    void testUpdateWalletDeposit() {

        UUID walletId = UUID.randomUUID();
        WalletDto walletDto = new WalletDto(walletId, BigDecimal.TEN, OperationType.DEPOSIT);
        Wallet existingWallet = new Wallet(walletId, BigDecimal.ZERO);
        Mockito.when(walletRepository.findByValletId(walletId)).thenReturn(Optional.of(existingWallet));

        Wallet updatedWallet = walletInteractionService.updateWallet(walletDto, walletRepository);

        assertEquals(BigDecimal.TEN, updatedWallet.getBalance());
    }

    @Test
    void testUpdateWalletWithdrawSufficientBalance() {

        UUID walletId = UUID.randomUUID();
        WalletDto walletDto = new WalletDto(walletId, BigDecimal.TEN, OperationType.WITHDRAW);
        Wallet existingWallet = new Wallet(walletId, BigDecimal.TEN);
        Mockito.when(walletRepository.findByValletId(walletId)).thenReturn(Optional.of(existingWallet));

        Wallet updatedWallet = walletInteractionService.updateWallet(walletDto, walletRepository);

        assertEquals(BigDecimal.ZERO, updatedWallet.getBalance());
    }

    @Test
    void testUpdateWalletWithdrawInsufficientBalance() {

        UUID walletId = UUID.randomUUID();
        WalletDto walletDto = new WalletDto(walletId, BigDecimal.TEN, OperationType.WITHDRAW);
        Wallet existingWallet = new Wallet(walletId, BigDecimal.ONE);
        Mockito.when(walletRepository.findByValletId(walletId)).thenReturn(Optional.of(existingWallet));

        assertThrows(InsufficientBalanceException.class, () -> {
            walletInteractionService.updateWallet(walletDto, walletRepository);
        });
    }

    @Test
    void testGetWalletBalance() {

        UUID walletId = UUID.randomUUID();
        Wallet existingWallet = new Wallet(walletId, BigDecimal.TEN);
        Mockito.when(walletRepository.findByValletId(walletId)).thenReturn(Optional.of(existingWallet));

        String balance = walletInteractionService.getWalletBalance(walletId, walletRepository);

        assertEquals("10", balance);
    }

    @Test
    void testGetWalletBalanceNotFound() {

        UUID walletId = UUID.randomUUID();
        Mockito.when(walletRepository.findByValletId(walletId)).thenReturn(Optional.empty());

        assertThrows(WalletNotFoundException.class, () -> {
            walletInteractionService.getWalletBalance(walletId, walletRepository);
        });
    }
}
