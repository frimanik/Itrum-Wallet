package com.example.ItrumWallet.WalletDto;

import jakarta.validation.constraints.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@Scope("prototype")
public class WalletDto {

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;

    private OperationType operationType;

    private UUID valletId;

    public WalletDto(UUID valletId, BigDecimal amount, OperationType operationType) {
        this.valletId = valletId;
        this.amount = amount;
        this.operationType=operationType;
    }
    public WalletDto() {
    }

    public UUID getValletId() {
        return valletId;
    }

    public void setValletId(UUID valletId) {
        this.valletId = valletId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getOperationType() {
        return operationType.getType();
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }
}
