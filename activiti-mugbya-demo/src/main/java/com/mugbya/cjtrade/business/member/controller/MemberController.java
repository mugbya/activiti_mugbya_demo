package com.mugbya.cjtrade.business.member.controller;

import com.mugbya.cjtrade.business.member.model.Member;
import com.mugbya.cjtrade.business.member.service.MemberService;
import com.mugbya.core.collection.BaseDto;
import com.mugbya.core.collection.Dto;
import com.mugbya.core.common.CommonController;
import com.mugbya.core.utils.WebUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * @author mugbya
 * @version 2014-07-22.
 */
@RestController
public class MemberController extends CommonController {
    @Resource
    private MemberService memberService;

    @RequestMapping(value = "member/start.json")
    public void applyMember(Member member,HttpServletRequest request){
        System.out.println(member);
        String applyUser =  WebUtil.getLoginUser(request).getUsername();

        //或者将申请人放在业务表单中
        //member.setApplyUser(applyUser);

        // 设置申请人
        Map<String ,Object> variables = new HashMap<>();
        variables.put("applyUser",applyUser);

        memberService.start( member, variables);
    }

    @RequestMapping(value = "member/tasklist.json")
    public Object tasklist(HttpServletRequest request){
        Dto params = new BaseDto();
        return success(memberService.UsertaskList(params,request));
    }

    @RequestMapping(value = "member/handler.json")
    public void handler(String taskId, String userId){
        //System.out.println(taskId + " 噢噢噢噢 "+  userId);
        memberService.handlerTask(taskId, userId);
    }

    /**
     * 得到审批成功的member
     */
    @RequestMapping(value = "member/add.jon")
    public void getMemberAll(String pro){
         memberService.getAllMember();
    }
}
