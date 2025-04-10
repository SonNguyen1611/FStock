package com.example.Fstock.dto.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponse {
    private int id;
    private String username;
    private Date createdAt;
    private String content;
    private int rating;
}
