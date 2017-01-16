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
    public enum FilterType
    {
        WEATHER,
        TEMPERATURE,
        WIND
    }

    private HashMap<String, IWeatherDataFilter> mKeywordTable;
    private HashMap<String, FilterType> mTypeTable;
    private List<String> mKeywordList;

    private FilterType mLastFilterAccessed;

    public KeywordSystem()
    {
        this.mKeywordTable = new HashMap<>();
        this.mTypeTable = new HashMap<>();
        this.mKeywordList = new ArrayList<>();
    }

    public void addFilter(String pKeyWord, FilterType pType, IWeatherDataFilter pFilter)
    {
        this.mKeywordTable.put(pKeyWord, pFilter);
        this.mTypeTable.put(pKeyWord, pType);
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
                    mLastFilterAccessed = this.mTypeTable.get(keywordRegistered);
                    return filter.processData(pRawData);
                }
            }
        }

        return new ArrayList<>();
    }

    public FilterType getLastFilterAccessed()
    {
        return this.mLastFilterAccessed;
    }
}
