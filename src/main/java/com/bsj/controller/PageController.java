package com.bsj.controller;

import com.bsj.service.AdminService;
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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@Scope("session")
@RequestMapping("/")
public class PageController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/")
    public String root(Model model,
                       HttpServletRequest request) {
        loadBoardNames(model);
        return "home";
    }

    @GetMapping("/admin")
    public String admin(Model model,
                        HttpServletRequest request) {
        loadBoardNames(model);
        return "admin";
    }

    @PostMapping("/admin/login")
    public String adminLogin(Model model,
                             HttpServletRequest request,
                             @RequestParam("username") String username,
                             @RequestParam("password") String password) {
        if(adminService.authenticateAdmin(username, password)) {
            request.getSession().setAttribute("admin", true);
            return root(model, request);
        }
        else {
            model.addAttribute("submitError", "Invalid username or password.");
            return admin(model, request);
        }
    }

    @PostMapping("/admin/logout")
    public String adminLogout(Model model,
                             HttpServletRequest request) {
        request.getSession().removeAttribute("admin");
        return admin(model, request);
    }

    @GetMapping("/{boardName}")
    public String board(Model model,
                        HttpServletRequest request,
                        @PathVariable String boardName) {
        loadBoardNames(model);
        BoardVO board = boardService.getBoard(boardName);
        Map<ThreadVO, ReplyVO> threads = threadService.getThreadsWithFirstReply(board.getId());

        model.addAttribute("board", board);
        model.addAttribute("threads", threads);

        return "board";
    }

    @GetMapping("/{boardName}/thread/{threadID}")
    public String thread(Model model,
                         HttpServletRequest request,
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
                               HttpServletRequest request,
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
            return thread(model, request, boardName, threadID);
        }
        else {
            model.addAttribute("submitError", errorMessage);
            return board(model, request, boardName);
        }
    }

    @PostMapping("/{boardName}/thread/{threadID}/submit")
    public String submitReply(Model model,
                              HttpServletRequest request,
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
        return thread(model, request, boardName, threadID);
    }

    @PostMapping("/{boardName}/thread/{threadID}/delete")
    public String deleteThread(Model model,
                               HttpServletRequest request,
                               @PathVariable String boardName,
                               @PathVariable Integer threadID,
                               @RequestParam("boardID") Integer boardID) {
        Boolean isAdmin = (Boolean) request.getSession().getAttribute("admin");
        if(isAdmin != null && isAdmin) {
            threadService.deleteThread(threadID, boardID);
            model.addAttribute("displayMessage", "Thread " + threadID + " deleted.");
        }
        else {
            model.addAttribute("submitError", "You must be logged in as an admin to delete a thread.");
        }
        return board(model, request, boardName);
    }

    private void loadBoardNames(Model model) {
        List<String> boardNames = boardService.getBoardNames();
        model.addAttribute("boards", boardNames);
    }
}
