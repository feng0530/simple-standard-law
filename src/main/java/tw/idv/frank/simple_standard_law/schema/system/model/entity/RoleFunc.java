package tw.idv.frank.simple_standard_law.schema.system.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role_func")
public class RoleFunc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_func_id")
    private Integer roleFuncId;

    @NotNull
    @Column(name = "role_id")
    private Integer roleId;

    @NotNull
    @Column(name = "func_id")
    private Integer funcId;

    @NotNull
    private String level;

    @Column(name = "create_time")
    private Date createTime;
}
