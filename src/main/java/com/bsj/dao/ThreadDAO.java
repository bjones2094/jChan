package com.bsj.dao;

import com.bsj.vo.ThreadVO;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ThreadDAO extends DAOBase {
    public List<ThreadVO> getThreads(int boardID) {
        return getSqliteTemplate().queryForList("SELECT * FROM threads WHERE board = ? ORDER BY id DESC", boardID)
                .stream().map(ThreadDAO::buildThreadVO)
                .collect(Collectors.toList());
    }

    public ThreadVO getThread(int threadID) {
        Map<String, Object> result = getSqliteTemplate().queryForMap("SELECT * FROM threads WHERE id = ?", threadID);
        return buildThreadVO(result);
    }

    public int getThreadCount(int boardID) {
        return getSqliteTemplate().queryForObject("SELECT COUNT(id) FROM threads WHERE board = ?", Integer.class, boardID);
    }

    public int getOldestThreadID(int boardID) {
        return getSqliteTemplate().queryForObject("SELECT MIN(id) FROM threads WHERE board = ?", Integer.class, boardID);
    }

    public int createThread(int boardID, String threadName) throws SQLException {
        Date now = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Connection connection = getSqliteTemplate().getDataSource().getConnection();
        connection.setAutoCommit(false);

        String insertThreadQuery = "INSERT INTO threads (board, title, create_date) VALUES (?, ?, ?)";
        PreparedStatement insertStatement = connection.prepareStatement(insertThreadQuery);
        insertStatement.setInt(1, boardID);
        insertStatement.setString(2, threadName);
        insertStatement.setString(3, dateFormat.format(now));
        insertStatement.execute();
        connection.commit();

        Statement selectStatement = connection.createStatement();
        ResultSet rs = selectStatement.executeQuery("SELECT last_insert_rowid()");
        Integer nextThreadID = rs.getInt(1);

        connection.commit();
        connection.close();

        return nextThreadID;
    }

    public void deleteThread(int threadID) {
        getSqliteTemplate().update("DELETE FROM threads WHERE id = ?", threadID);
    }

    private static ThreadVO buildThreadVO(Map<String, Object> row) {
        ThreadVO thread = new ThreadVO();
        thread.setId((Integer) row.get("id"));
        thread.setBoardID((Integer) row.get("board"));
        thread.setTitle((String) row.get("title"));
        thread.setCreateDate((String) row.get("create_date"));
        return thread;
    }
}
