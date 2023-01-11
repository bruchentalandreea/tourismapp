package com.example.springdemo.validators;
/*
import com.example.springdemo.dto.UserDTO;
import com.example.springdemo.errorhandler.IncorrectParameterException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class UserFieldValidator {

    private static final Log LOGGER = LogFactory.getLog(UserFieldValidator.class);
    private static final EmailFieldValidator EMAIL_VALIDATOR = new EmailFieldValidator() ;

    private UserFieldValidator(){}

    public static void validateInsertOrUpdate(UserDTO userDTO) {

        List<String> errors = new ArrayList<>();
        if (userDTO == null) {
            errors.add("personDTO is null");
            throw new IncorrectParameterException(UserDTO.class.getSimpleName(), errors);
        }
        if (userDTO.getEmail() == null || !EMAIL_VALIDATOR.validate(userDTO.getEmail())) {
            errors.add("Person email has invalid format");
        }

        if (!errors.isEmpty()) {
            LOGGER.error(errors);
            throw new IncorrectParameterException(UserFieldValidator.class.getSimpleName(), errors);
        }
    }
}
*/