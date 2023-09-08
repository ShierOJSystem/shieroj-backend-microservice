package com.shier.shierojbackendjudgeservice.judge.service.impl;

import cn.hutool.json.JSONUtil;
import com.shier.shierojbackendcommon.common.ErrorCode;
import com.shier.shierojbackendcommon.exception.BusinessException;
import com.shier.shierojbackendjudgeservice.judge.JudgeManager;
import com.shier.shierojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.shier.shierojbackendjudgeservice.judge.codesandbox.CodeSandBoxProxy;
import com.shier.shierojbackendjudgeservice.judge.codesandbox.CodeSandboxFactory;
import com.shier.shierojbackendjudgeservice.judge.service.JudgeService;
import com.shier.shierojbackendjudgeservice.judge.strategy.JudgeContext;
import com.shier.shierojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.shier.shierojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.shier.shierojbackendmodel.model.codesandbox.JudgeInfo;
import com.shier.shierojbackendmodel.model.dto.question.JudgeCase;
import com.shier.shierojbackendmodel.model.entity.Question;
import com.shier.shierojbackendmodel.model.entity.QuestionSubmit;
import com.shier.shierojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.shier.shierojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Shier
 * @createTime 2023/8/30 星期三 12:09
 * 判题服务实现类
 */
@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionFeignClient questionFeignClient;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type:example}")
    private String judgeType;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        // 1、传入题目的提交 id，获取到对应的题目、提交信息（包含代码、编程语言等）
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        // 通过提交的信息中的题目id 获取到题目的全部信息
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionFeignClient.getQuestionById(questionId);
        if (questionId == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        // 2、如果题目提交状态不为等待中
        if (!questionSubmit.getSubmitState().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        // 3、更改判题（题目提交）的状态为 “判题中”，防止重复执行，也能让用户即时看到状态
        QuestionSubmit updateQuestionSubmit = new QuestionSubmit();
        updateQuestionSubmit.setId(questionSubmitId);
        updateQuestionSubmit.setSubmitState(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean updateState = questionFeignClient.updateQuestionSubmitById(updateQuestionSubmit);
        if (!updateState) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新失败");
        }
        // 4、调用沙箱，获取到执行结果
        CodeSandBox codeSandbox = CodeSandboxFactory.newInstance(judgeType);
        codeSandbox = new CodeSandBoxProxy(codeSandbox);
        String submitLanguage = questionSubmit.getSubmitLanguage();
        String submitCode = questionSubmit.getSubmitCode();
        // 获取输入用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCasesList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        // 通过Lambda表达式获取到每个题目的输入用例
        List<String> inputList = judgeCasesList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        // 调用沙箱
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(submitCode)
                .language(submitLanguage)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();
        // 5、根据沙箱的执行结果，设置题目的判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCasesList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        // 进行判题
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        // 6、修改判题结果
        updateQuestionSubmit = new QuestionSubmit();
        updateQuestionSubmit.setId(questionSubmitId);
        updateQuestionSubmit.setSubmitState(QuestionSubmitStatusEnum.SUCCEED.getValue());
        updateQuestionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        updateState = questionFeignClient.updateQuestionSubmitById(updateQuestionSubmit);
        if (!updateState) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新失败");
        }
        // 再次查询数据库，返回最新提交信息
        QuestionSubmit questionSubmitResult = questionFeignClient.getQuestionSubmitById(questionId);
        return questionSubmitResult;
    }
}
