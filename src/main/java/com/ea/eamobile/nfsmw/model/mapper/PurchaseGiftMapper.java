package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.PurchaseGift;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Jan 25 18:37:59 CST 2013
 * @since 1.0
 */
public interface PurchaseGiftMapper {

    public PurchaseGift getPurchaseGift(int id);

    public List<PurchaseGift> getPurchaseGiftList();

    public int insert(PurchaseGift purchaseGift);

    public void update(PurchaseGift purchaseGift);

    public void deleteById(int id);

    // @Cacheable(value = "memory", key = "'PurchaseGiftMapper.getPurchaseGiftByPurchaseId.'+#purchaseId")
    public PurchaseGift getPurchaseGiftByPurchaseId(int purchaseId);

}