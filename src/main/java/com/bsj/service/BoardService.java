package com.bsj.service;

import com.bsj.dao.BoardDAO;
import com.bsj.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class BoardService {
    @Autowired
    private BoardDAO boardDAO;

    public BoardVO getBoard(String boardName) {
        return boardDAO.getBoard(boardName);
    }

    public List<String> getBoardNames() {
        return boardDAO.getBoardNames();
    }
}
