package retail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDetails {

    @Size(min = 3, max = 25)
    private String username;

    @Size(min = 7, max = 25)
    private String password;
}
