package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.PurchaseGift;
import com.ea.eamobile.nfsmw.model.mapper.PurchaseGiftMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Jan 25 18:38:00 CST 2013
 * @since 1.0
 */
@Service
public class PurchaseGiftService {

    @Autowired
    private PurchaseGiftMapper purchaseGiftMapper;

    public PurchaseGift getPurchaseGift(int id) {
        PurchaseGift ret = (PurchaseGift) InProcessCache.getInstance().get("getPurchaseGift." + id);
        if (ret != null) {
            return ret;
        }
        ret = purchaseGiftMapper.getPurchaseGift(id);
        InProcessCache.getInstance().set("getPurchaseGift." + id, ret);
        return ret;
    }

    public PurchaseGift getPurchaseGiftByPurchaseId(int purchaseId) {
        PurchaseGift ret = (PurchaseGift) InProcessCache.getInstance().get("getPurchaseGiftByPurchaseId." + purchaseId);
        if (ret != null) {
            return ret;
        }
        ret = purchaseGiftMapper.getPurchaseGiftByPurchaseId(purchaseId);
        InProcessCache.getInstance().set("getPurchaseGiftByPurchaseId." + purchaseId, ret);
        return ret;
    }

    public List<PurchaseGift> getPurchaseGiftList() {
        @SuppressWarnings("unchecked")
        List<PurchaseGift> ret = (List<PurchaseGift>) InProcessCache.getInstance().get("getPurchaseGiftList");
        if (ret != null) {
            return ret;
        }
        ret = purchaseGiftMapper.getPurchaseGiftList();
        InProcessCache.getInstance().set("getPurchaseGiftList", ret);
        return ret;
    }

    public int insert(PurchaseGift purchaseGift) {
        return purchaseGiftMapper.insert(purchaseGift);
    }

    public void update(PurchaseGift purchaseGift) {
        purchaseGiftMapper.update(purchaseGift);
    }

    public void deleteById(int id) {
        purchaseGiftMapper.deleteById(id);
    }

}