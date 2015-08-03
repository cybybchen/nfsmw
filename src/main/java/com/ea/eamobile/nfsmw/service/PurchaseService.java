package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.Purchase;
import com.ea.eamobile.nfsmw.model.mapper.PurchaseMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Nov 26 20:29:22 CST 2012
 * @since 1.0
 */
@Service
public class PurchaseService {

    @Autowired
    private PurchaseMapper purchaseMapper;


    public Purchase getPurchase(int id) {
        Purchase ret = (Purchase) InProcessCache.getInstance().get("getPurchase."+id);
        if (ret != null) {
            return ret;
        }
        ret = purchaseMapper.getPurchase(id);
        InProcessCache.getInstance().set("getPurchase."+id, ret);
        return ret;
    }


    public List<Purchase> getPurchaseList() {
        @SuppressWarnings("unchecked")
        List<Purchase> ret = (List<Purchase>) InProcessCache.getInstance().get("getPurchaseList");
        if (ret != null) {
            return ret;
        }
        ret = purchaseMapper.getPurchaseList();
        InProcessCache.getInstance().set("getPurchaseList", ret);
        return ret;
    }

    public int insert(Purchase purchase) {
        return purchaseMapper.insert(purchase);
    }

    public void update(Purchase purchase) {
        purchaseMapper.update(purchase);
    }

    public void deleteById(int id) {
        purchaseMapper.deleteById(id);
    }

}