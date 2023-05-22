package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
// final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
public class MemberController {
    // 생성자 주입
    private final MemberService memberService;

    // @@@@@@@ 회원가입 관련
    @GetMapping("/member/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/member/save")
//    public String save(@RequestParam("memberEmail") String memberEmail,
//                       @RequestParam("memberPassword") String memberPassword,
//                       @RequestParam("memberName") String memberName) {
//        System.out.println("memberEmail = " + memberEmail + ", memberPassword = " + memberPassword + ", memberName = " + memberName);
//        System.out.println("MemberController.save");
//        return "index";
//    }

    public String save(@ModelAttribute MemberDTO memberDTO) {
        // 매개변수로 MemberDTO로 설정해두면 DTO에 있는 필드와 name속성값이 이름이 같다면 알아서 Setter메소드를 호출 -> 필드에 값을 삽입
        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);
        return "login";
    }


    // @@@@@@@ 로그인 관련
    @GetMapping("/member/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
       MemberDTO loginResult =  memberService.login(memberDTO);
       if (loginResult != null) {
           // login 성공
           session.setAttribute("loginEmail",loginResult.getMemberEmail());
           // 로그인한 회원의 이메일 정보를 session으로 설정
           return "main";
       } else {
           // login 실패
           return "login";
       }
    }

    // @@@@@@@ 회원목록 출력 관련
    @GetMapping("/member/")
    public String findAll(Model model) {
      List<MemberDTO> memberDTOList = memberService.findAll();
      // 어떠한 html로 가져갈 데이터가 있다면 model사용
      model.addAttribute("memberList",memberDTOList);
      return "list";
    }

    @GetMapping("/member/{id}")
    public String findById(@PathVariable Long id,Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member",memberDTO);
        return "detail";
    }

    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model) {
        String myEmail = (String)session.getAttribute("loginEmail");
        // session값이 object타입이기 때문에 강제타입변환해야 함
       MemberDTO memberDTO = memberService.updateForm(myEmail);
       model.addAttribute("updateMember", memberDTO);
       return "update";

    }

    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return "redirect:/member/" + memberDTO.getId();
        // 정보수정을 한 후에 detail을 띄워 줌
    }

    @GetMapping("/member/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
        return "redirect:/member/";
    }

    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        // 세션 삭제
        return "index";
    }

    @PostMapping("/member/email-check")
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {
        // ajax를 쓸려면 @ResponseBody를 써야 함 안그러면 에러발생
        System.out.println("memberEmail = " + memberEmail);
        String checkResult = memberService.emailCheck(memberEmail);
        return checkResult;
//        if (checkResult != null) {
//            return "ok";
//        } else {
//            return "no";
//        }
    }
}
