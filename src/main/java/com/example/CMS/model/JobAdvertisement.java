package com.example.CMS.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity()
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JobAdvertisement {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String scope;
    private String forwarder;
    private String content;

}
