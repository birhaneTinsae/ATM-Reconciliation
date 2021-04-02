package com.enatbanksc.ATMReconciliation.exceptions;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UnAuthorizedActionException extends RuntimeException {
    private final String message;
}
