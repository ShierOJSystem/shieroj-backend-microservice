package com.shier.shierojbackendjudgeservice.judge.codesandbox.impl;


import com.shier.shierojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.shier.shierojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.shier.shierojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * 第三方代码沙箱（调用网上现成的代码沙箱）
 */
public class ThirdPartyCodeSandbox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
