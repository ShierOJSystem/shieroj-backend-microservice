package com.shier.shierojbackendjudgeservice.judge;

import com.shier.shierojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.shier.shierojbackendjudgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.shier.shierojbackendjudgeservice.judge.strategy.JudgeContext;
import com.shier.shierojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.shier.shierojbackendmodel.model.codesandbox.JudgeInfo;
import com.shier.shierojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 *
 * @author Shier
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getSubmitLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}