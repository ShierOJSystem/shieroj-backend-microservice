package com.shier.shierojbackendserviceclient.service;

import com.shier.shierojbackendmodel.model.entity.Question;
import com.shier.shierojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 公共题目服务接口
 * @author Shier
 * @createDate 2023-08-25 17:33:25
 */
@FeignClient(name = "shieroj-backend-question-service", path = "/api/question/inner")
public interface QuestionFeignClient {

    /**
     * 根据 id 获取题目信息
     *
     * @param questionId
     * @return
     */
    @GetMapping("/get/id")
    Question getQuestionById(@RequestParam("questionId") long questionId);

    /**
     * 根据 id 获取到提交题目信息
     * @param questionSubmitId
     * @return
     */
    @GetMapping("/question_submit/get/id")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId);

    /**
     * 根据id 更新提交题目信息
     * @param questionSubmit
     * @return
     */
    @PostMapping("/question_submit/update")
    boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit);


    /**
     * 保存数据
     * @param question
     * @return
     */
    @PostMapping("/question/save")
    boolean updateQuestion(@RequestBody Question question);
}
