package com.bsj.dao;

import com.bsj.vo.AdminVO;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Repository
public class AdminDAO extends DAOBase {

    public AdminVO getAdmin(String username) {
        try {
            Map<String, Object> result = getSqliteTemplate().queryForMap("SELECT * FROM admins WHERE username = ?", username);
            return buildAdminVO(result);
        }
        catch(DataAccessException e) {
            return null;
        }
    }

    private static AdminVO buildAdminVO(Map<String, Object> row) {
        AdminVO admin = new AdminVO();
        admin.setId((Integer) row.get("id"));
        admin.setUsername((String) row.get("username"));
        admin.setPassword((String) row.get("password"));
        admin.setSalt((String) row.get("salt"));
        return admin;
    }
}
