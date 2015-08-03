package com.ea.eamobile.nfsmw.service.command;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.CarConst;
import com.ea.eamobile.nfsmw.constants.CtaContentConst;
import com.ea.eamobile.nfsmw.constants.ProfileComparisonType;
import com.ea.eamobile.nfsmw.model.Purchase;
import com.ea.eamobile.nfsmw.model.PurchaseGift;
import com.ea.eamobile.nfsmw.model.PurchaseRecord;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.ItemMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBuyItemCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestStoreDetailCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseBuyItemCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseStoreDetailCommand;
import com.ea.eamobile.nfsmw.service.CtaContentService;
import com.ea.eamobile.nfsmw.service.PayService;
import com.ea.eamobile.nfsmw.service.PurchaseGiftService;
import com.ea.eamobile.nfsmw.service.PurchaseRecordService;
import com.ea.eamobile.nfsmw.service.PurchaseService;
import com.ea.eamobile.nfsmw.service.StoreMessageService;
import com.ea.eamobile.nfsmw.service.UserRaceActionService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;
import com.ea.eamobile.nfsmw.view.ResultInfo;

@Service
public class StoreDetailCommandService {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PurchaseRecordService purchaseRecordService;

    @Autowired
    private PayService payService;

    @Autowired
    private PushCommandService pushService;

    @Autowired
    private UserService userService;

    @Autowired
    private StoreMessageService storeMessageService;

    @Autowired
    CtaContentService ctaContentService;

    @Autowired
    PurchaseGiftService purchaseGiftService;

	@Autowired
	private UserRaceActionService userRaceActionService;
	
    @Autowired
    private MemcachedClient cache;

    public ResponseStoreDetailCommand getResponseStoreDetailCommand(RequestStoreDetailCommand reqcmd, User user) {
        ResponseStoreDetailCommand.Builder builder = ResponseStoreDetailCommand.newBuilder();
        long userId = user.getId();
        buildStoreDetailCommandFields(builder, reqcmd, userId);
        return builder.build();
    }

    public ResponseBuyItemCommand getResponseBuyItemCommand(RequestBuyItemCommand reqcmd, User user,
            Commands.ResponseCommand.Builder responseBuilder) {
        long userId = user.getId();
        ResponseBuyItemCommand.Builder builder = ResponseBuyItemCommand.newBuilder();
        buildBuyItemCommandFields(builder, reqcmd, userId, responseBuilder);
        return builder.build();
    }

    private void buildStoreDetailCommandFields(ResponseStoreDetailCommand.Builder builder,
            RequestStoreDetailCommand reqcmd, long userId) {
        List<ItemMessage> itemList = new ArrayList<ItemMessage>();
        List<Purchase> cacheList = purchaseService.getPurchaseList();
        List<Purchase> purchaseList = new ArrayList<Purchase>();
        purchaseList.addAll(cacheList);
        if (purchaseList != null) {
            for (Purchase purchase : purchaseList) {
                int currentTime = (int) (System.currentTimeMillis() / 1000);
                if ((currentTime < purchase.getLimitTime()) || purchase.getLimitTime() == CarConst.NO_TIME_LIMIT) {
                    ItemMessage.Builder itembuilder = ItemMessage.newBuilder();
                    if (purchase.getBuyCount() == -1) {
                        itembuilder.setBuyCount(purchase.getBuyCount());
                    } else {
                        if (purchase.getBuyCount() - storeMessageService.getBuyedNum(userId, purchase.getId()) == 0) {
                            continue;
                        }
                        itembuilder.setBuyCount(purchase.getBuyCount()
                                - storeMessageService.getBuyedNum(userId, purchase.getId()));
                    }
                    if (purchase.getLimitTime() == CarConst.NO_TIME_LIMIT) {
                        itembuilder.setBuyTimeLimit(-1);
                    } else {
                        itembuilder.setBuyTimeLimit(purchase.getLimitTime() - currentTime);
                    }
                    itembuilder.setDiscount(purchase.getIsDiscount() == 1);
                    itembuilder.setDiscountNum(purchase.getDiscountNum());
                    itembuilder.setDiscountPrice(purchase.getPrice());
                    itembuilder.setItemId(purchase.getId());
                    itembuilder.setItemType(purchase.getItemType());
                    itembuilder.setName(purchase.getName());
                    itembuilder.setPrice(purchase.getDisplayPrice());
                    itembuilder.setPriceType(purchase.getPriceType());
                    itembuilder.setSellFlag(purchase.getIsBestSell() * 4 + purchase.getIsHot() * 2
                            + purchase.getIsNew() * 1);
                    itemList.add(itembuilder.build());
                }
            }
        }
        builder.addAllItems(itemList);
    }

    private void buildBuyItemCommandFields(ResponseBuyItemCommand.Builder builder, RequestBuyItemCommand reqcmd,
            long userId, Commands.ResponseCommand.Builder responseBuilder) {
        Purchase purchase = purchaseService.getPurchase(reqcmd.getItemId());
        int currentTime = (int) (System.currentTimeMillis() / 1000);

        if (purchase == null || purchase.getIsAvailable() == 0
                || ((currentTime > purchase.getLimitTime()) && purchase.getLimitTime() != CarConst.NO_TIME_LIMIT)) {
            builder.setSuccess(false);
            builder.setMessage(ctaContentService.getCtaContent(CtaContentConst.COMMIDITY_NOT_EXISIT).getContent());
            return;
        }
        // if (commodity.getItemType() == 2) {
        // if (commodity.getEnergySerialNumber() != storeMessageService.getEnergySerialNumber(userId)) {
        // builder.setSuccess(false);
        // builder.setMessage("wrong energy serial num");
        // }
        // }
        if (purchase.getBuyCount() > 0) {
            int buynum = storeMessageService.getBuyedNum(userId, purchase.getId());
            if (buynum >= purchase.getBuyCount()) {
                builder.setSuccess(false);
                builder.setMessage(ctaContentService.getCtaContent(CtaContentConst.ITEM_ALREADY_BUYED).getContent());
                return;
            }

        }

        ResultInfo result = payService.buy(purchase, userId);
        builder.setSuccess(result.isSuccess());
        builder.setMessage(result.getMessage());
        if (result.isSuccess()) {
            User user = userService.getUser(userId);
            userService.regainEnergy(user);
            PurchaseRecord purchaseRecord = new PurchaseRecord();
            purchaseRecord.setBuyTime(currentTime);
            purchaseRecord.setPurchaseId(purchase.getId());
            purchaseRecord.setType(purchase.getItemType());
            purchaseRecord.setUserId(userId);
            purchaseRecordService.insert(purchaseRecord);

            addUserPurchase(purchase, user);
            pushService.pushUserInfoCommand(responseBuilder, user);
            pushService.pushStoreDetailCommand(responseBuilder, userId);
        }
    }

    private void addUserPurchase(Purchase purchase, User user) {
        PurchaseGift purchaseGift = purchaseGiftService.getPurchaseGiftByPurchaseId(purchase.getId());
        if (purchaseGift != null) {
            user.setEnergy(user.getEnergy() + purchaseGift.getEnergy());
            user.setGold(user.getGold() + purchaseGift.getGold());
            user.setMoney(user.getMoney() + purchaseGift.getMoney());
            user.setStarNum(user.getStarNum() + purchaseGift.getStarNum());
            user.setRpNum(user.getRpNum() + purchaseGift.getRpNum());
            userService.updateUser(user);

            // update rpNum in user_race_action
            int valueId = ProfileComparisonType.RP_NUM.getIndex();
            long userId = user.getId();
            cache.delete(CacheKey.USER_RACE_ACTION + userId + "_" + valueId);
            int rpNum = user.getRpNum();
            userRaceActionService.refreshDataAndCache(userId, valueId, rpNum);
        }
    }

}
