package com.shier.shierojbackendquestionservice.controller.inner;

import com.shier.shierojbackendmodel.model.entity.Question;
import com.shier.shierojbackendmodel.model.entity.QuestionSubmit;
import com.shier.shierojbackendquestionservice.service.QuestionService;
import com.shier.shierojbackendquestionservice.service.QuestionSubmitService;
import com.shier.shierojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户内部服务（仅内部调用）
 *
 * @author Shier 2023/9/6 15:39
 */
@RestController
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    /**
     * 根据 id 获取题目信息
     *
     * @param questionId
     * @return
     */
    @GetMapping("/get/id")
    @Override
    public Question getQuestionById(@RequestParam("questionId") long questionId) {
        return questionService.getById(questionId);
    }

    /**
     * 根据 id 获取到提交题目信息
     *
     * @param questionSubmitId
     * @return
     */
    @GetMapping("/question_submit/get/id")
    @Override
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId) {
        return questionSubmitService.getById(questionSubmitId);
    }

    /**
     * 根据id 更新提交题目信息
     *
     * @param questionSubmit
     * @return
     */
    @PostMapping("/question_submit/update")
    @Override
    public boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }

    /**
     * 保存数据
     *
     * @param question
     * @return
     */
    @PostMapping("/question/save")
    @Override
    public boolean updateQuestion(@RequestBody Question question) {
        return questionService.updateById(question);
    }
}
