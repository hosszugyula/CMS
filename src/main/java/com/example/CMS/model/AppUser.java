package com.example.CMS.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    private Long userId;
    private String userName;
    private String encrytedPassword;

    @Override
    public String toString() {
        return this.userName + "/" + this.encrytedPassword;
    }

}
