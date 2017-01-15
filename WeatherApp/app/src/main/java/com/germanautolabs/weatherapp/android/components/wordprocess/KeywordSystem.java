package com.germanautolabs.weatherapp.android.components.wordprocess;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Joao on 1/15/2017.
 */

public class KeywordSystem
{
    private HashMap<String, IWeatherDataFilter> mKeywordTable;
    private List<String> mKeywordList;

    public KeywordSystem()
    {
        this.mKeywordTable = new HashMap<>();
        this.mKeywordList = new ArrayList<>();
    }

    public void addFilter(String pKeyWord, IWeatherDataFilter pFilter)
    {
        this.mKeywordTable.put(pKeyWord, pFilter);
        this.mKeywordList.add(pKeyWord);
    }

    public List<String> getData(List<String> pWordsRecognized, String pRawData)
    {
        for (String recognizedWord : pWordsRecognized)
        {
            for (String keywordRegistered : this.mKeywordList)
            {
                if (keywordRegistered.equalsIgnoreCase(recognizedWord))
                {
                    IWeatherDataFilter filter = this.mKeywordTable.get(keywordRegistered);
                    return filter.processData(pRawData);
                }
            }
        }

        return new ArrayList<>();
    }
}
