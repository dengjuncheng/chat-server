package com.peanut.utils;

import com.peanut.constant.Constants;
import com.peanut.dao.mapper.BlackListMapper;
import com.peanut.pojo.vo.CheckResult;
import com.spreada.utils.chinese.ZHConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class SensitiveWordUtil {
    private static List<String> blackList;
    @Autowired
    private  BlackListMapper blackListMapper;

    @PostConstruct
    public void init(){
        blackList = blackListMapper.selectAll();
    }

    public static CheckResult checkSensitive(String content){
        content = content.toLowerCase();

        String checkNum = content.replaceAll(Constants.PATTERN_COMMENT_CONTENT, "");
        ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
        content = converter.convert(content);
        //汉字数字替换
        String[] ch_num = {"一","二","三","四","五","六","七","八","九","零","壹","贰","叁","肆","伍","陆","柒","捌","玖","①","②","③","④","⑤","⑥","⑦","⑧","⑨","〇","１","２","３","４","５","６","７","８","９","０","⑴","⑵","⑶","⑷","⑸","⑹","⑺","⑻","⑼","⒈","⒉","⒊","⒋","⒌","⒍","⒎","⒏","⒐",
                "one","two","three","four","five","six","seven","eight","nine","zero"};
        String[] num = {"1","2","3","4","5","6","7","8","9","0","1","2","3","4","5","6","7","8","9","1","2","3","4","5","6","7","8","9","0","1","2","3","4","5","6","7","8","9","0","1","2","3","4","5","6","7","8","9","1","2","3","4","5","6","7","8","9",
                "1","2","3","4","5","6","7","8","9","0"};
        for(int i=0;i<ch_num.length;i++){
            checkNum= checkNum.replaceAll(ch_num[i],num[i]);
        }

        //content = content.replaceAll(Constants.PATTERN_COMMENT_CONTENT, "");
        // 如果出现连续8位及以上的数字，直接进pending
        Pattern pat = Pattern.compile("\\d{8,}");
        Matcher mat = pat.matcher(checkNum);
        if (mat.find()) {
            return new CheckResult(1, "出现连续8位数字");
        }

        content+= "|"+checkNum.replaceAll("[^\u4e00-\u9fa5]", "")+checkNum.replaceAll("[^a-zA-Z]", "")+checkNum.replaceAll("[^0-9]", "");


        for (String keyword :
                blackList) {
            if (keyword.length() < 1) {
                continue;
            }
            //logger.info("敏感词："+keyWord);
            String raw = keyword;
            keyword = keyword.toLowerCase();
            if (content.contains(keyword)) {
                return new CheckResult(1, "包含非法关键字\"" + raw + "\"");
            }
        }

        //没有敏感词时返回-1
        return new CheckResult(0, "") ;
    }
}
