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
@Table(name = "func")
public class Func {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "func_id")
    private Integer funcId;

    @NotBlank
    @Column(name = "func_name")
    private String funcName;

    @Column(name = "create_time")
    private Date createTime;
}