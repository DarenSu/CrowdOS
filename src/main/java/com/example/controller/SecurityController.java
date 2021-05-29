package com.example.controller;


import com.example.service.LivenessService;
import com.example.service.TaskService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 14:42
 */

/**
 * @Author:DarenSu
 * @Date: 2019/6/13
 * @Time: 14:42
 */

@RestController      //To indicate the module, here is the control module
@RequestMapping("/security")

public class SecurityController {

    @Autowired
    private UserService userService;
    @Autowired
    private TaskService bean_TaskService;
    @Autowired
    private LivenessService livenessService;
//    @Autowired
//    private UserServiceInterface userServiceInterface;


    private Object setuser1;

    //20210117  IOS安全协议返回
    @RequestMapping("getSecurity")
    public String GetSecurity(){
        String s = "移动APP应用尊重和保护利用用户的隐私所有的服务。为了向您提供更准确，更人性化的服务，本程序会按照本隐私权政策的规定使用和披露您的个人信息。\n" +
                "当您同意移动APP应用服务协议，您将被视为已同意本隐私政策的全部内容。本隐私政策属于服务协议移动APP应用\n" +
                "不可分割的一部分，未经您的许可之前，移动APP应用信息将不会被披露或向第三方提供。\n" +
                "1. 适用范围\n" +
                "a) 在您注册本程序帐号时，您根据本程序要求提供的个人注册信息；\n" +
                "b) 在您使用本程序网络服务，或访问本程序平台网页时，本程序自动接收并记录的您的浏览器和计算机上的信息，包括但不限于您的IP地址、浏览器的类型、使用的语言、访问日期和时间等数据；\n" +
                "c) 本程序通过合法途径从商业伙伴处取得的用户个人数据。\n" +
                "您了解并同意，以下信息不适用本隐私权政策：\n" +
                "a) 您在使用本程序平台提供的搜索服务时输入的关键字信息；\n" +
                "b) 本程序收集到的您在本程序发布的有关信息数据；\n" +
                "c) 违反法律规定或违反本程序规则行为及本程序已对您采取的措施。\n" +
                "2. 信息使用\n" +
                "a) 本程序不会向任何无关第三方提供、出售、出租、分享或交易您的个人信息，除非事先得到您的许可，或该第三方和本程序（含本程序关联公司）单独或共同为您提供服务，且在该服务结束后，其将被禁止访问包括其以前能够访问的所有这些资料。\n" +
                "b)本程序亦不允许任何第三方以任何手段收集、编辑、出售或者无偿传播您的个人信息。任何本程序平台用户如从事上述活动，一经发现，本程序有权立即终止与该用户的服务协议。\n" +
                "c) 为服务用户的目的，本程序可能通过使用您的个人信息，向您提供您感兴趣的信息，包括但不限于向您发出产品和服务信息，或者与本程序合作伙伴共享信息以便他们向您发送有关其产品和服务的信息（后者需要您的事先同意）。\n" +
                "3.信息存储和交换\n" +
                "信息和收集将存储在服务器移动APP应用你移动APP应用的信息，这些信息和数据可以被发送到你的国家，地区或移动APP应用收集海外信息和数据的位置和在国外访问，存储和显示。\n" +
                "4.信息安全\n" +
                "a）移动APP应用帐户具有安全功能，请保持您的用户名和密码信息。 移动APP应用通过用户密码加密等安全措施，以确保您的信息不丢失，不被滥用和变造。尽管有上述安全措施，但请注意，没有“绝对安全”的信息网络。\n" +
                "尤其是移动APP应用的用户名和密码泄漏，请马上联系移动APP应用服务，以便采取相应的措施移动APP应用。\n" +
                "5.本隐私政策的更改\n" +
                "a）如果决定更改隐私政策，我们会在本网站中以及我们认为适当的位置发布这些更改，以便您了解我们如何收集、使用您的个人信息，哪些人可以访问这些信息，以及在什么情况下我们会透露这些信息。\n" +
                "请您妥善保护自己的个人信息，仅在必要的情形下向他人提供。如您发现自己的个人信息泄密，尤其是本应用用户名及密码发生泄露，请您立即联络本应用，以便本应用采取相应措施。\n";
//        int t = 0;
//        System.out.println(t);
        return s;
    }



    /*
        //20210117  IOS security protocol return
    @RequestMapping("getSecurity")
    public String GetSecurity(){
        String s = "The mobile APP application respects and protects all services that utilize the user’s privacy. " +
                "In order to provide you with more accurate and user-friendly services, this program will use and " +
                "disclose your personal information in accordance with the provisions of this privacy policy. \n" +
                "When you agree to the mobile APP application service agreement, you will be deemed to have agreed to" +
                " the entire content of this privacy policy. This privacy policy is an inseparable part of the " +
                "service agreement mobile APP application. Without your permission, the mobile APP application " +
                "information will not be disclosed or provided to a third party as an integral part. Without your " +
                "permission, the mobile APP application Information will not be disclosed or provided to third parties\n" +
                "1. Scope of application\n" +
                "a) When you register for this program account, you provide personal registration information " +
                "according to the requirements of this program；\n" +
                "b) When you use the program's network services or visit the program's platform webpage, the program " +
                "automatically receives and records the information on your browser and computer, including but not " +
                "limited to your IP address, browser type, and language used , Date and time of access; \n" +
                "c) This program obtains user personal data from business partners through legal channels. \n" +
                "You understand and agree that the following information does not apply to this privacy policy: \n" +
                "a) Keyword information you enter when using the search service provided by this program platform;\n" +
                "b) Relevant information and data collected by this program that you publish in this program; \n" +
                "c) Violation of the law or violation of the rules of this procedure and the measures this procedure " +
                "has taken against you.\n" +
                "2. Information use\n" +
                "a) This program will not provide, sell, rent, share, or trade your personal information to any " +
                "unrelated third party, unless you have obtained your permission in advance, or the third party and " +
                "the program (including the program's affiliates) separately or jointly provide you Service, and " +
                "after the service ends, it will be prohibited from accessing all of these materials that it was able" +
                " to access before. \n" +
                "b)This program also does not allow any third party to collect, edit, sell or disseminate your " +
                "personal information by any means. If any user of this program platform engages in the above " +
                "activities, once discovered, this program has the right to immediately terminate the service " +
                "agreement with the user. \n" +
                "c) For the purpose of serving users, this program may use your personal information to provide you " +
                "with information that you are interested in, including but not limited to sending you product and " +
                "service information, or sharing information with partners of this program so that they can send you " +
                "information Information about its products and services (the latter requires your prior consent). \n" +
                "3.Information storage and exchange\n" +
                "The information and collection will be stored on the server Mobile APP application. The information" +
                " and data of your mobile APP application can be sent to your country, region or the location where " +
                "the mobile APP application collects overseas information and data and is accessed, stored and " +
                "displayed abroad.。\n" +
                "4.Information security\n" +
                "a）The mobile APP application account has a security function, please keep your username and password" +
                " information. The mobile APP uses security measures such as user password encryption to ensure that " +
                "your information is not lost, abused and altered. Despite the aforementioned security measures, " +
                "please note that there is no \"absolutely secure\" information network.\n" +
                "Especially if the user name and password of the mobile APP application are leaked, please contact " +
                "the mobile APP application service immediately to take corresponding measures.\n" +
                "5.Changes to this privacy policy\n" +
                "a）If you decide to change the privacy policy, we will post these changes on this website and where " +
                "we deem appropriate so that you can understand how we collect and use your personal information, " +
                "who can access this information, and under what circumstances we will disclose these messages\n" +
                "Please properly protect your personal information and only provide it to others when necessary. If " +
                "you find that your personal information has been leaked, especially the user name and password of " +
                "this app, please contact this app immediately so that this app can take corresponding measures\n";
//        int t = 0;
//        System.out.println(t);
        return s;
    }

    */

}
