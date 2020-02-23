package com.example.reactivespringdemo.payloads;

import com.example.reactivespringdemo.documents.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MovieEvent {
    private Movie movie;
    private Date when;
    private String user;
}
