package tw.idv.frank.simple_standard_law.schema.system.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class UsersFunc {

    private String subjectName;

    private List<String> authorities;
}
