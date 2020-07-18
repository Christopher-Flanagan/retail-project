package retail.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Table
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NonNull
    @Column(length = 25)
    @Size(min = 3, max = 25)
    private String firstName;

    @NonNull
    @Column(length = 25)
    @Size(min = 3, max = 25)
    private String lastName;

    @NonNull
    @Column(length = 25)
    @Size(min = 3, max = 25)
    private String username;

    @NonNull
    @Email
    private String emailAddress;

    @NonNull
    @Column(length = 25)
    @Size(min = 7, max = 25)
    private String password;

}
