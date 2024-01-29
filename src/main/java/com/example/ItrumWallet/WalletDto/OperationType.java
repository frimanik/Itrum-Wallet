package com.example.ItrumWallet.WalletDto;

public enum OperationType {
    DEPOSIT("DEPOSIT"), WITHDRAW("WITHDRAW");

    private final String operationType;

    private OperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getType() {
        return operationType;
    }
}
