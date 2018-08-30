package com.bsj.controller;

import com.bsj.dao.BoardDAO;
import com.bsj.dao.ReplyDAO;
import com.bsj.dao.ThreadDAO;
import com.bsj.vo.BoardVO;
import com.bsj.vo.ReplyVO;
import com.bsj.vo.ThreadVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class BoardController {
    @Autowired
    private BoardDAO boardDAO;

    @Autowired
    private ThreadDAO threadDAO;

    @Autowired
    private ReplyDAO replyDAO;

    @GetMapping("/")
    public String root(Model model) {
        loadBoardNames(model);
        return "home";
    }

    @GetMapping("/{boardName}")
    public String board(Model model, @PathVariable String boardName) {
        loadBoardNames(model);
        BoardVO board = boardDAO.getBoard(boardName);
        Map<ThreadVO, ReplyVO> threads = threadDAO.getThreadsWithFirstReply(board.getId());

        model.addAttribute("board", board);
        model.addAttribute("threads", threads);

        return "board";
    }

    @PostMapping("/{boardName}/submit")
    public String boardSubmit(Model model,
                              @PathVariable String boardName,
                              @RequestParam("threadName") String threadName,
                              @RequestParam("replyContent") String replyContent,
                              @RequestParam("boardID") Integer boardID) {
        if(StringUtils.isNotBlank(replyContent)) {
            threadDAO.createThread(boardID, threadName, replyContent);
        }
        else {
            model.addAttribute("submitError", "Thread description cannot be blank.");
        }
        return board(model, boardName);
    }

    @GetMapping("/{boardName}/thread/{threadID}")
    public String thread(Model model,
                         @PathVariable String boardName,
                         @PathVariable Integer threadID) {
        loadBoardNames(model);

        BoardVO board = boardDAO.getBoard(boardName);
        ThreadVO thread = threadDAO.getThread(threadID);
        List<ReplyVO> replies = replyDAO.getReplies(threadID);

        model.addAttribute("board", board);
        model.addAttribute("thread", thread);
        model.addAttribute("replies", replies);

        return "thread";
    }

    @PostMapping("/{boardName}/thread/{threadID}/submit")
    public String threadSubmit(Model model,
                               @PathVariable String boardName,
                               @PathVariable Integer threadID,
                               @RequestParam("replyContent") String replyContent) {
        replyDAO.createReply(threadID, replyContent);
        return thread(model, boardName, threadID);
    }

    private void loadBoardNames(Model model) {
        List<String> boardNames = boardDAO.getBoardNames();
        model.addAttribute("boards", boardNames);
    }
}
