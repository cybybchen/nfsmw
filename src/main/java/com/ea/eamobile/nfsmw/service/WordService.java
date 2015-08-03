package com.ea.eamobile.nfsmw.service;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.model.CensorWord;
import com.ea.eamobile.nfsmw.model.mapper.CensorWordMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Jan 24 14:15:32 CST 2013
 * @since 1.0
 */
@Service
public class WordService {

    @Autowired
    private CensorWordMapper censorWordMapper;
    @Autowired
    private MemcachedClient cache;

    public String getCensorWord(String word) {
        return censorWordMapper.getCensorWord(word);
    }

    @SuppressWarnings("unchecked")
    public List<String> getCensorWordList() {
        List<String> list = (List<String>) cache.get(CacheKey.CENSOR_WORD);
        if(list==null){
            list = censorWordMapper.getCensorWordList();
            Collections.sort(list); // sort for binary search
            cache.set(CacheKey.CENSOR_WORD, list);
        }
        return list;
    }

    public int insert(String word) {
        cache.delete(CacheKey.CENSOR_WORD);
        CensorWord cw = new CensorWord();
        cw.setContent(word);
        return censorWordMapper.insert(cw);
    }

    /**
     * 是否是敏感词 需要考虑被检查串包含关键词的情况
     * 
     * @param word
     * @return
     */
    public boolean isCensorable(String word) {
        if (StringUtils.isBlank(word)) {
            return true;
        }
        List<String> words = getCensorWordList();
        word = filterSpecialChar(word);
        if (Collections.binarySearch(words, word) > -1) {
            return true;
        }
        //check contains
        for(String str : words){
            if(word.contains(str)){
                return true;
            }
        }
        return false;
    }

    private static final String CHARS = "[` ~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
    private static final Pattern PATTERN_CHARS = Pattern.compile(CHARS);

    public String filterSpecialChar(String str) {
        Matcher matcher = PATTERN_CHARS.matcher(str);
        String filterchars = matcher.replaceAll("").trim();
        StringBuffer ret = new StringBuffer();
        for (int offset = 0; offset < filterchars.length();) {
            final int codepoint = filterchars.codePointAt(offset);
            if (Character.charCount(codepoint) == 1) {
                ret.append(filterchars.charAt(offset));
            }
            offset += Character.charCount(codepoint);
        }
        return ret.toString();
    }

}