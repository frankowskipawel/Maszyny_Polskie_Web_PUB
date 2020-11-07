package pl.maszyny_polskie.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private int id;
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String country;
    private String active;
    private String roles;
}
