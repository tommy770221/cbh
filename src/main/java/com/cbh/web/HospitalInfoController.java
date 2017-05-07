package com.cbh.web;
import com.cbh.entity.HospitalInfo;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/hospitalinfoes")
@Controller
@RooWebScaffold(path = "hospitalinfoes", formBackingObject = HospitalInfo.class)
public class HospitalInfoController {
}
