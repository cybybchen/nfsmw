package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.Purchase;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Nov 26 20:29:21 CST 2012
 * @since 1.0
 */
public interface PurchaseMapper {

    public Purchase getPurchase(int id);

    public List<Purchase> getPurchaseList();

    public int insert(Purchase purchase);

    public void update(Purchase purchase);

    public void deleteById(int id);

}