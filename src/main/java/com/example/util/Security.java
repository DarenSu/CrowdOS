package com.example.util;


import java.security.MessageDigest;

public class Security {

    public static String getSecurityDetial(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {

            str = "移动APP应用尊重和保护利用用户的隐私所有的服务。为了向您提供更准确，更人性化的服务，本程序会按照本隐私权政策的规定使用和披露您的个人信息。\n" +
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
            encodeStr = str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeStr;
    }


}