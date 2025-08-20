package com.vizo.vizo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StoryController {

    @Autowired 
    private StoryService storyService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/story")
    public Story createStory(@RequestBody Story story, 
                             @RequestHeader("Authorization") String jwt) {
        User reqUser = userService.findUserByJwt(jwt);
        return storyService.createStory(story, reqUser);
    }

    @GetMapping("/api/story/user/{userId}")
    public List<Story> findUserStory(@PathVariable Integer userId, 
                                     @RequestHeader("Authorization") String jwt) throws Exception {
        userService.findUserByJwt(jwt);  // ✅ No need to store reqUser since you’re not using it
        return storyService.findStoryByUserId(userId);
    }
}
