package com.Hotels.YourHome.customException;


import lombok.*;

import java.io.Serial;


@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -572289715877544325L;
    private  int  errorCode = 400;
    private  String errorMessage = "Something went Wrong";

}
