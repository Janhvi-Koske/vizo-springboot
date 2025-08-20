package com.vizo.vizo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/comments/post/{postId}")
    public Comment createComment(@RequestBody Comment comment,
                                 @RequestHeader("Authorization") String jwt,
                                 @PathVariable("postId") Integer postId) throws Exception {
        User user = userService.findUserByJwt(jwt);
        return commentService.createComment(comment, postId, user.getId());
    }

    @PutMapping("/api/comments/like/{commentId}")
    public Comment likeComment(@RequestHeader("Authorization") String jwt,
                               @PathVariable Integer commentId) throws Exception {
        User user = userService.findUserByJwt(jwt);
        return commentService.likeComment(commentId, user.getId());
    }
}
