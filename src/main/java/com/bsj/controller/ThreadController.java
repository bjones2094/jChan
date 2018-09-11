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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/")
public class ThreadController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/{boardName}/thread/{threadID}")
    public String thread(Model model,
                         @PathVariable String boardName,
                         @PathVariable Integer threadID) {
        BoardVO board = boardService.getBoard(boardName);
        ThreadVO thread = threadService.getThread(threadID);
        List<ReplyVO> replies = replyService.getReplies(threadID);

        model.addAttribute("board", board);
        model.addAttribute("thread", thread);
        model.addAttribute("replies", replies);

        return "thread";
    }

    @PostMapping("/{boardName}/thread/{threadID}/submit")
    public ModelAndView submitReply(HttpServletRequest request,
                                    RedirectAttributes redirectAttributes,
                                    @PathVariable String boardName,
                                    @PathVariable Integer threadID,
                                    @RequestParam("replyContent") String replyContent,
                                    @RequestParam("boardID") Integer boardID,
                                    @RequestParam(value = "imageUpload", required = false) MultipartFile imageUpload) throws Exception {
        String errorMessage = ValidationUtil.validateReplySubmission(replyContent, imageUpload);
        if(StringUtils.isBlank(errorMessage)) {
            replyService.createReply(boardID, threadID, replyContent, imageUpload);
        }
        else {
            redirectAttributes.addFlashAttribute("submitError", errorMessage);
        }
        RedirectView redirectView = new RedirectView(request.getContextPath() + "/" + boardName + "/thread/" + threadID);
        return new ModelAndView(redirectView);
    }

    @PostMapping("/{boardName}/thread/{threadID}/delete")
    public ModelAndView deleteThread(HttpServletRequest request,
                                     RedirectAttributes redirectAttributes,
                                     @PathVariable String boardName,
                                     @PathVariable Integer threadID,
                                     @RequestParam("boardID") Integer boardID) {
        Boolean isAdmin = (Boolean) request.getSession().getAttribute("admin");
        if(isAdmin != null && isAdmin) {
            threadService.deleteThread(threadID, boardID);
            redirectAttributes.addFlashAttribute("displayMessage", "Thread " + threadID + " deleted.");
        }
        else {
            redirectAttributes.addFlashAttribute("submitError", "You must be logged in as an admin to delete a thread.");
        }
        RedirectView redirectView = new RedirectView(request.getContextPath() + "/" + boardName);
        return new ModelAndView(redirectView);
    }

    @PostMapping("/{boardName}/thread/{threadID}/{replyID}/delete")
    public ModelAndView deleteThread(HttpServletRequest request,
                                     RedirectAttributes redirectAttributes,
                                     @PathVariable String boardName,
                                     @PathVariable Integer threadID,
                                     @PathVariable Integer replyID,
                                     @RequestParam("boardID") Integer boardID) {
        Boolean isAdmin = (Boolean) request.getSession().getAttribute("admin");
        if(isAdmin != null && isAdmin) {
            replyService.deleteReply(boardID, replyID);
            redirectAttributes.addFlashAttribute("displayMessage", "Reply " + replyID + " deleted.");
        }
        else {
            redirectAttributes.addFlashAttribute("submitError", "You must be logged in as an admin to delete a reply.");
        }
        RedirectView redirectView = new RedirectView(request.getContextPath() + "/" + boardName + "/thread/" + threadID);
        return new ModelAndView(redirectView);
    }
}
