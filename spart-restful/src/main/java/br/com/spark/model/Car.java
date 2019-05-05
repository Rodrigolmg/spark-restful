package br.com.spark.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    private Long id;
    private String name;
    private String brand;
    private Long year;
}

