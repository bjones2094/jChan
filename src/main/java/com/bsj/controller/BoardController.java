package com.bsj.controller;

import com.bsj.service.BoardService;
import com.bsj.service.ImageService;
import com.bsj.service.ReplyService;
import com.bsj.service.ThreadService;
import com.bsj.util.ValidationUtil;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/")
    public String root(Model model) {
        loadBoardNames(model);
        return "home";
    }

    @GetMapping("/{boardName}")
    public String board(Model model, @PathVariable String boardName) {
        loadBoardNames(model);
        BoardVO board = boardService.getBoard(boardName);
        Map<ThreadVO, ReplyVO> threads = threadService.getThreadsWithFirstReply(board.getId());

        model.addAttribute("board", board);
        model.addAttribute("threads", threads);

        return "board";
    }

    @GetMapping("/{boardName}/thread/{threadID}")
    public String thread(Model model,
                         @PathVariable String boardName,
                         @PathVariable Integer threadID) {
        loadBoardNames(model);

        BoardVO board = boardService.getBoard(boardName);
        ThreadVO thread = threadService.getThread(threadID);
        List<ReplyVO> replies = replyService.getReplies(threadID);

        model.addAttribute("board", board);
        model.addAttribute("thread", thread);
        model.addAttribute("replies", replies);

        return "thread";
    }

    @PostMapping("/{boardName}/submit")
    public String submitThread(Model model,
                               @PathVariable String boardName,
                               @RequestParam("threadName") String threadName,
                               @RequestParam("replyContent") String replyContent,
                               @RequestParam("boardID") Integer boardID,
                               @RequestParam(value = "imageUpload", required = false) MultipartFile imageUpload) {
        String errorMessage = ValidationUtil.validateReplySubmission(replyContent, imageUpload);
        if(StringUtils.isBlank(errorMessage)) {
            int threadID = threadService.createThread(boardID, threadName);
            int replyID = replyService.createReply(threadID, replyContent);
            if(imageUpload != null && !imageUpload.isEmpty()) {
                String imagePath = imageService.saveImage(threadID, replyID, boardID, imageUpload);
                replyService.associateImageToReply(replyID, imagePath);
            }
            return thread(model, boardName, threadID);
        }
        else {
            model.addAttribute("submitError", errorMessage);
            return board(model, boardName);
        }
    }

    @PostMapping("/{boardName}/thread/{threadID}/submit")
    public String submitReply(Model model,
                              @PathVariable String boardName,
                              @PathVariable Integer threadID,
                              @RequestParam("replyContent") String replyContent,
                              @RequestParam("boardID") Integer boardID,
                              @RequestParam(value = "imageUpload", required = false) MultipartFile imageUpload) {
        String errorMessage = ValidationUtil.validateReplySubmission(replyContent, imageUpload);
        if(StringUtils.isBlank(errorMessage)) {
            int replyID = replyService.createReply(threadID, replyContent);
            if(imageUpload != null && !imageUpload.isEmpty()) {
                String imagePath = imageService.saveImage(threadID, replyID, boardID, imageUpload);
                replyService.associateImageToReply(replyID, imagePath);
            }
        }
        else {
            model.addAttribute("submitError", errorMessage);
        }
        return thread(model, boardName, threadID);
    }

    private void loadBoardNames(Model model) {
        List<String> boardNames = boardService.getBoardNames();
        model.addAttribute("boards", boardNames);
    }
}
