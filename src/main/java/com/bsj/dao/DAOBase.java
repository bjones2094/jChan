package com.bsj.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public abstract class DAOBase {
    @Autowired
    private JdbcTemplate sqliteTemplate;

    protected int getNextID(String tableName) {
        return sqliteTemplate.queryForObject("SELECT seq FROM sqlite_sequence WHERE name = ?", Integer.class, tableName) + 1;
    }

    public JdbcTemplate getSqliteTemplate() {
        return sqliteTemplate;
    }
}
