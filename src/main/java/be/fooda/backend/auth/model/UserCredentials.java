package be.fooda.backend.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// A (temporary) class just to represent the user credentials
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCredentials {
    private String username;
    private String password;
}
