package com.Hotels.YourHome.responses;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.Instant;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateResponse {
    public int status = 201;
    public String message = "Successfully Updated";
    public Timestamp updateAt = Timestamp.from(Instant.now());
}
