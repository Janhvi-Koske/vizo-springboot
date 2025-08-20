package com.vizo.vizo;

import java.util.List;

public interface StoryService {
    Story createStory(Story story, User user);
    List<Story> findStoryByUserId(Integer userId) throws Exception;
}
