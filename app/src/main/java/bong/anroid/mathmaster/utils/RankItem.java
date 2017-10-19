package bong.anroid.mathmaster.utils;

/**
 * Created by Polarium on 2017-10-19.
 */

public class RankItem {
    String strRank;
    String strId;
    String strCountry;
    String strScore;
    String strDateTime;


    public RankItem(String strRank, String strId, String strCountry, String strScore, String strDateTime) {
        this.strRank = strRank;
        this.strId = strId;
        this.strCountry = strCountry;
        this.strScore = strScore;
        this.strDateTime = strDateTime;
    }

    public String getStrRank() {
        return strRank;
    }

    public void setStrRank(String strRank) {
        this.strRank = strRank;
    }

    public String getStrId() {
        return strId;
    }

    public void setStrId(String strId) {
        this.strId = strId;
    }

    public String getStrCountry() {
        return strCountry;
    }

    public void setStrCountry(String strCountry) {
        this.strCountry = strCountry;
    }

    public String getStrScore() {
        return strScore;
    }

    public void setStrScore(String strScore) {
        this.strScore = strScore;
    }

    public String getStrDateTime() {
        return strDateTime;
    }

    public void setStrDateTime(String strDateTime) {
        this.strDateTime = strDateTime;
    }
}
