package com.bsj.controller;

import com.bsj.service.BoardService;
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
import java.util.Map;

@Controller
@RequestMapping("/")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private ThreadService threadService;

    @GetMapping("/{boardName}")
    public String board(Model model,
                        @PathVariable String boardName) {
        BoardVO board = boardService.getBoard(boardName);
        Map<ThreadVO, ReplyVO> threads = threadService.getThreadsWithFirstReply(board.getId());

        model.addAttribute("board", board);
        model.addAttribute("threads", threads);

        return "board";
    }

    @PostMapping("/{boardName}/submit")
    public ModelAndView submitThread(HttpServletRequest request,
                                     RedirectAttributes redirectAttributes,
                                     @PathVariable String boardName,
                                     @RequestParam("threadName") String threadName,
                                     @RequestParam("replyContent") String replyContent,
                                     @RequestParam("boardID") Integer boardID,
                                     @RequestParam(value = "imageUpload", required = false) MultipartFile imageUpload) throws Exception {
        RedirectView redirectView;
        String errorMessage = ValidationUtil.validateReplySubmission(replyContent, imageUpload);
        if(StringUtils.isBlank(errorMessage)) {
            int threadID = threadService.createThread(boardID, threadName, replyContent, imageUpload);
            redirectView = new RedirectView(request.getContextPath() + "/" + boardName + "/thread/" + threadID);
        }
        else {
            redirectAttributes.addFlashAttribute("submitError", errorMessage);
            redirectView = new RedirectView(request.getContextPath() + "/" + boardName);
        }
        return new ModelAndView(redirectView);
    }
}
