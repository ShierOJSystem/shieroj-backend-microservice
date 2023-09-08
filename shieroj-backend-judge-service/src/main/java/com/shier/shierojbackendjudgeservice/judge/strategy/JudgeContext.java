package com.shier.shierojbackendjudgeservice.judge.strategy;

import com.shier.shierojbackendmodel.model.codesandbox.JudgeInfo;
import com.shier.shierojbackendmodel.model.dto.question.JudgeCase;
import com.shier.shierojbackendmodel.model.entity.Question;
import com.shier.shierojbackendmodel.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文（用于定义在策略中传递的参数）
 * @author Shier
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;

}
