package tw.idv.frank.simple_standard_law.schema.system.service.impl;

import org.hibernate.usertype.LoggableUserType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.idv.frank.simple_standard_law.schema.system.model.dao.RoleFuncMapper;
import tw.idv.frank.simple_standard_law.schema.system.model.dao.RoleMapper;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.RoleAddReq;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.RoleFuncAddReq;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.RoleRes;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.RoleUpdateReq;
import tw.idv.frank.simple_standard_law.schema.system.service.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleFuncMapper roleFuncMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void addRole(RoleAddReq req) {
        roleMapper.addRole(req);

        if(req.getRoleFunc() != null) {
            addRoleFunc(req);
        }
    }

    @Override
    public List<RoleRes> findRoleList() {
        return roleMapper.findRoleList()
                .stream()
                .map(role -> modelMapper.map(role, RoleRes.class))
                .toList();
    }

    @Override
    public void updateRole(RoleUpdateReq req) {
        roleMapper.updateRole(req);
    }

    @Override
    public void deleteRole(Integer roleId) {
        roleMapper.deleteRole(roleId);
    }

    private void addRoleFunc(RoleAddReq req) {
        RoleFuncAddReq roleFuncAddReq = req.getRoleFunc();
        roleFuncAddReq.setRoleId(req.getRoleId());
        roleFuncMapper.addRoleFunc(roleFuncAddReq);
    }
}
