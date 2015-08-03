package com.ea.eamobile.nfsmw.view;

import java.util.List;

public class CareerGhostListView {
    private int ghostNum;

    private List<Integer> careerIdList;

    private long createTime;

    // 0:没查过,1:是最大的,2:不是最大的
    private int isMostRaceTime;

    // 0:没查过,1:是最小的,2:不是最小的
    private int isLeastRaceTime;

    public int getGhostNum() {
        return ghostNum;
    }

    public void setGhostNum(int ghostNum) {
        this.ghostNum = ghostNum;
    }

    public List<Integer> getCareerIdList() {
        return careerIdList;
    }

    public void setCareerIdList(List<Integer> careerIdList) {
        this.careerIdList = careerIdList;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getIsMostRaceTime() {
        return isMostRaceTime;
    }

    public void setIsMostRaceTime(int isMostRaceTime) {
        this.isMostRaceTime = isMostRaceTime;
    }

    public int getIsLeastRaceTime() {
        return isLeastRaceTime;
    }

    public void setIsLeastRaceTime(int isLeastRaceTime) {
        this.isLeastRaceTime = isLeastRaceTime;
    }

    @Override
    public String toString() {
        String str = "ghostNum:" + ghostNum + " careerIdList" + careerIdList + " isMostRaceTime" + isMostRaceTime
                + " isLeastRaceTime" + isLeastRaceTime;
        return str;
    }
}
