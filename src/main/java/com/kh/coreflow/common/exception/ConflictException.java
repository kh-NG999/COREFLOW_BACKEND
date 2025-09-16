package com.kh.coreflow.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ConflictException extends ResponseStatusException {
    public ConflictException(String reason) { super(HttpStatus.CONFLICT, reason); }
}
