package com.hncboy.chatgpt.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author rxm
 * @description
 * @Date 2023/4/9
 */
public class StringUtil {

  public static int countChineseAndEnglish(String str) {
    int chineseCount = 0;
    int englishCount = 0;

    // 中文字符的Unicode范围为4E00-9FA5
    String chineseRegex = "[\\u4e00-\\u9fa5]";
    // 英文单词的正则表达式，匹配由大小写字母组成的字符串
    String englishRegex = "[a-zA-Z]+";

    Pattern chinesePattern = Pattern.compile(chineseRegex);
    Matcher chineseMatcher = chinesePattern.matcher(str);

    Pattern englishPattern = Pattern.compile(englishRegex);
    Matcher englishMatcher = englishPattern.matcher(str);

    while (chineseMatcher.find()) {
      chineseCount++;
    }

    while (englishMatcher.find()) {
      englishCount++;
    }
    return chineseCount + englishCount;

  }


}
