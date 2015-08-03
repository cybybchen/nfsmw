package com.ea.eamobile.nfsmw.view;

import java.io.Serializable;
import java.util.List;

public class TrackView implements Serializable {

    private static final long serialVersionUID = 8940818684486651010L;

    private int trackId;

    private int finishRatio;

    private List<Integer> carTypes;

    private int star;

    private int userStar;

    private int unlock;

    private int isNew;

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public int getFinishRatio() {
        return finishRatio;
    }

    public void setFinishRatio(int finishRatio) {
        this.finishRatio = finishRatio;
    }

    public List<Integer> getCarTypes() {
        return carTypes;
    }

    public void setCarTypes(List<Integer> carTypes) {
        this.carTypes = carTypes;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getUserStar() {
        return userStar;
    }

    public void setUserStar(int userStar) {
        this.userStar = userStar;
    }

    public int getUnlock() {
        return unlock;
    }

    public void setUnlock(int unlock) {
        this.unlock = unlock;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    @Override
    public String toString() {
        return "TrackView=[trackId:" + trackId + ",finishRatio:" + finishRatio + ",cartype:" + carTypes + ",star:"
                + star + ",userStar:" + userStar + ",unlock:" + unlock + ",isNew:" + isNew + "]";
    }

}
