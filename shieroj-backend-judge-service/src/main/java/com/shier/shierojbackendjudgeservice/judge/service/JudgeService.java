package com.shier.shierojbackendjudgeservice.judge.service;


import com.shier.shierojbackendmodel.model.entity.QuestionSubmit;

/**
 * 判题服务 ：执行代码
 *
 * @author Shier
 * @createTime 2023/8/30 星期三 12:04
 */
public interface JudgeService {
    /**
     * 判题
     *
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
