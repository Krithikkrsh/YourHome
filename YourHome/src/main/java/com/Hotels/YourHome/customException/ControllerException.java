package com.Hotels.YourHome.customException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ControllerException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 5652001142891644445L;
    private int errorCode;
    private String errorMessage;

}
