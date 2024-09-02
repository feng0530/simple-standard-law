package tw.idv.frank.simple_standard_law.schema.system.service;

import tw.idv.frank.simple_standard_law.schema.system.model.dto.RoleFuncAddReq;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.RoleFuncRes;

import java.util.List;

public interface RoleFuncService {
    
    void updateRoleFunc(RoleFuncAddReq req);

    List<RoleFuncRes> findRoleFuncByRoleId(Integer roleId);
}
