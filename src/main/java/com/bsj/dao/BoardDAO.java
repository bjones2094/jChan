package com.bsj.dao;

import com.bsj.vo.BoardVO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class BoardDAO extends DAOBase {
    public List<String> getBoardNames() {
        List<String> boardNames = new ArrayList();
        List<String> results = getSqliteTemplate().queryForList("SELECT name FROM boards", String.class);
        for(String name : results) {
            boardNames.add(name);
        }
        return boardNames;
    }

    public BoardVO getBoard(String boardName) {
        Map<String, Object> result = getSqliteTemplate().queryForMap("SELECT * FROM boards WHERE name = ?", boardName);
        return buildBoardVO(result);
    }

    public String getDirectory(int boardID) {
        return getSqliteTemplate().queryForObject("SELECT directory FROM boards WHERE id = ?", String.class, boardID);
    }

    private static BoardVO buildBoardVO(Map<String, Object> row) {
        BoardVO board = new BoardVO();
        board.setId((Integer) row.get("id"));
        board.setName((String) row.get("name"));
        board.setDescription((String) row.get("description"));
        board.setDirectory((String) row.get("directory"));
        board.setNsfw((Integer) row.get("nsfw") == 1);
        return board;
    }
}
