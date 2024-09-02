package tw.idv.frank.simple_standard_law.schema.system.model.dto;

import lombok.Data;

import java.util.Map;

@Data
public class RoleFuncAddReq {

    private Integer roleId;

    private Map<Integer, String> funcList;
}
