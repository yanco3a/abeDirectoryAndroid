package com.directory.abe.Models;

import java.io.Serializable;
import java.sql.Timestamp;

public class Stat implements Serializable {
    private Timestamp statTimeEnd;
    private Timestamp statTimeStart;
    private int statTrade_id;
    private int stat_id;

    public Stat(int stat_id, int statTrade_id, Timestamp statTimeStart, Timestamp statTimeEnd) {
        this.stat_id = stat_id;
        this.statTrade_id = statTrade_id;
        this.statTimeStart = statTimeStart;
        this.statTimeEnd = statTimeEnd;
    }

    public int getStat_id() {
        return this.stat_id;
    }

    public void setStat_id(int stat_id) {
        this.stat_id = stat_id;
    }

    public Timestamp getStatTimeEnd() {
        return this.statTimeEnd;
    }

    public void setStatTimeEnd(Timestamp statTimeEnd) {
        this.statTimeEnd = statTimeEnd;
    }

    public Timestamp getStatTimeStart() {
        return this.statTimeStart;
    }

    public void setStatTimeStart(Timestamp statTimeStart) {
        this.statTimeStart = statTimeStart;
    }

    public int getStatTrade_id() {
        return this.statTrade_id;
    }

    public void setStatTrade_id(int statTrade_id) {
        this.statTrade_id = statTrade_id;
    }
}
