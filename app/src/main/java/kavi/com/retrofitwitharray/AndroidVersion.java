package kavi.com.retrofitwitharray;

/**
 * Created by dd on 9/8/18.
 */

public class AndroidVersion {



   String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(String priceUsd) {
        this.priceUsd = priceUsd;
    }

    public String getChangePercent24Hr() {
        return changePercent24Hr;
    }

    public void setChangePercent24Hr(String changePercent24Hr) {
        this.changePercent24Hr = changePercent24Hr;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public AndroidVersion(String name, String priceUsd, String changePercent24Hr, String symbol) {
        this.name = name;
        this.priceUsd = priceUsd;
        this.changePercent24Hr = changePercent24Hr;
        this.symbol = symbol;
    }

    String priceUsd;

    String changePercent24Hr;
    String symbol;
}
