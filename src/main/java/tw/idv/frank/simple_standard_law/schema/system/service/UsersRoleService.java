package tw.idv.frank.simple_standard_law.schema.system.service;

import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersRoleReq;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersRoleRes;


public interface UsersRoleService {

    void updateUsersRole(UsersRoleReq req);

    UsersRoleRes findUsersRoleByUserId(Integer userId);
}
