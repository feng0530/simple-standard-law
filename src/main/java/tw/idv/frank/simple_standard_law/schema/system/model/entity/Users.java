package tw.idv.frank.simple_standard_law.schema.system.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class Users {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @NotBlank
    @Column(name = "user_name")
    private String userName;

    @NotBlank
    private String account;

    @NotBlank
    private String password;

    @Column(name = "create_time")
    private Date createTime;
}
