package com.ea.eamobile.nfsmw.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CarConst;
import com.ea.eamobile.nfsmw.model.CarChartlet;
import com.ea.eamobile.nfsmw.model.CarSlot;
import com.ea.eamobile.nfsmw.model.CarSlotConsumable;
import com.ea.eamobile.nfsmw.model.GotchaCar;
import com.ea.eamobile.nfsmw.model.UserCarFragment;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.CarData;
import com.ea.eamobile.nfsmw.protoc.Commands.CarSlotInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.ChartletInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.ConsumableData;
import com.ea.eamobile.nfsmw.service.gotcha.GotchaCarService;
import com.ea.eamobile.nfsmw.service.gotcha.GotchaExpenseService;
import com.ea.eamobile.nfsmw.service.gotcha.GotchaService;
import com.ea.eamobile.nfsmw.view.CarChartletView;
import com.ea.eamobile.nfsmw.view.CarSlotView;
import com.ea.eamobile.nfsmw.view.CarView;

@Service
public class CarDataMessageService {

    @Autowired
    private CarSlotService slotService;
    @Autowired
    private CarSlotConsumableService slotConsumbleService;
    @Autowired
    private GotchaService gotchaService;
    @Autowired
    private UserChartletService userChartletService;
    @Autowired
    private GotchaCarService gotchaCarService;
    @Autowired
    private GotchaExpenseService gotchaExpenseService;

    public CarData buildCarData(CarView view, long userId) throws SQLException {
        CarData.Builder builder = CarData.newBuilder();
        builder.setCarId(view.getCarId());
        builder.setType(view.getType());
        builder.setTier(view.getTier());
        builder.setScore(view.getScore());
        builder.setStatus(view.getStatus());
        builder.setPrice(view.getPrice());
        builder.setIsLock(view.isLock());
        builder.setChartletId(view.getChartletId());// TODO delete
        builder.setPriceType(view.getPriceType());
        builder.setUnlockMWLevel(view.getUnlockMwNum());
        builder.setRemainTime(view.getRemainTime());
        builder.setSellFlag(view.getSellFlag());
        builder.setIsSpecialCar(view.getIsSpecialCar());
        builder.setLimit(view.getLimit());
        builder.setMaxlimit(view.getMaxlimit());
        builder.setState(view.getStates());
        
        List<CarSlotInfo> slots = buildCarSlotInfos(view.getSlots());
        builder.addAllSlots(slots);
        // chartlet
        List<ChartletInfo> chartlets = buildChartletInfos(view.getCarId(), userId);
        builder.addAllChartletInfos(chartlets);
        if (view.getStatus() == CarConst.UNLOCK || view.getStatus() == CarConst.TIME_LIMITED_AVAILABLE) {
            // gotcha info
            setGotchaInfo(builder, userId, view);
        }
        return builder.build();
    }

    private void setGotchaInfo(CarData.Builder builder, long userId, CarView view) {
        GotchaCar gotchaCar = gotchaCarService.getGotchaCar(view.getCarId());
        if (gotchaCar != null) {
            builder.setTotalFragmentNumber(gotchaCar.getPartNum());
        }
        UserCarFragment frag = gotchaService.getUserCarFragment(userId, view.getCarId());
        builder.setFragmentNumber(frag == null ? 0 : frag.getCount());

        List<Commands.GotchaExpense> expenses = gotchaService.buildGotchaExpenseProtocModels(view.getCarId(), userId);
        if (gotchaCar != null) {
            builder.setRemainPriceType(view.getPriceType());
            builder.setRemainPrice(getRemainGotchaCarPrice(gotchaCar, frag, view));
        }
        builder.addAllGotchaExpense(expenses);
    }

    private int getRemainGotchaCarPrice(GotchaCar gotchaCar, UserCarFragment frag, CarView view) {
        float count = 0;
        if (frag != null) {
            count = frag.getCount();
        }
        count = Math.min(count, gotchaCar.getPartNum());
        return  (int)(view.getPrice() * (1 - (count / gotchaCar.getPartNum()*0.9)));
    }

    private List<ChartletInfo> buildChartletInfos(String carId, long userId) throws SQLException {
        List<ChartletInfo> result = Collections.emptyList();
        List<CarChartletView> list = userChartletService.getChartletViewList(carId, userId);
        if (list != null && list.size() > 0) {
            result = new ArrayList<ChartletInfo>();
            for (CarChartletView view : list) {
                ChartletInfo.Builder builder = ChartletInfo.newBuilder();
                CarChartlet clet = view.getChartlet();
                builder.setId(clet.getId());
                builder.setName(clet.getName());
                builder.setDiffuseTexturePath(clet.getDiffuseTexturePath());
                builder.setDiffuseMaskPath(clet.getDiffuseMaskPath());
                builder.setBRDFPath(clet.getBrdfPath());
                builder.setBRDFSpecularPath(clet.getBrdfSpecularPath());
                builder.setNumberPlatePath(clet.getNumberPlatePath());
                builder.setSwatchColor(clet.getSwatchColor());
                builder.setSwatchColor2(clet.getSwatchColor2());
                builder.setPaintType(clet.getPaintType());
                builder.setUseVinylMap(clet.getUseVinylMap() != 0);
                builder.setPrice(clet.getPrice());
                builder.setPriceType(clet.getPriceType());
                builder.setTenancy(clet.getTenancy());// s
                builder.setRemainTime(view.getRemainTime());
                builder.setOwned(view.isOwned());
                builder.setSellFlag(view.getSellFlag());
                builder.setOrderId(view.getOrderId());
                result.add(builder.build());
            }
        }
        return result;
    }

    private List<CarSlotInfo> buildCarSlotInfos(List<CarSlotView> slots) {
        List<CarSlotInfo> result = Collections.emptyList();
        if (slots != null && slots.size() > 0) {
            result = new ArrayList<CarSlotInfo>();

            for (CarSlotView slot : slots) {
                CarSlotInfo.Builder builder = CarSlotInfo.newBuilder();
                builder.setSlotId(slot.getSlotId());
                builder.setLevel(slot.getLevel());
                builder.setStatus(slot.getStatus());
                builder.setRemainTime(slot.getRemainTime());
                builder.setScore(slot.getScore());
                builder.setDescription(slot.getDescription());
                builder.setNextAddScore(slot.getNextAddScore());
                int level = 1;
                int priceType = 1;
                int price = 0;
                CarSlot carSlot = slotService.getCarSlot(slot.getSlotId());
                if (carSlot != null) {
                    level = carSlot.getLevel();
                    price = carSlot.getPrice();
                    priceType = carSlot.getPriceType();
                    List<ConsumableData> consumbleList = getSlotConsumble(carSlot);
                    builder.addAllConsumble(consumbleList);
                }
                CarSlot nextCarSlot = slotService.getCarSlot(slot.getSlotId() + 1);
                if (slot.getStatus() == 1 && (nextCarSlot != null) && nextCarSlot.getCarId().equals(carSlot.getCarId())
                        && nextCarSlot.getSn() == carSlot.getSn()) {
                    price = nextCarSlot.getPrice();
                    priceType = nextCarSlot.getPriceType();
                }
                builder.setReachMaxLevel(level == CarConst.SLOT_MAX_LEVEL);
                builder.setPriceType(priceType);
                builder.setPrice(price);
                result.add(builder.build());
            }
        }

        return result;
    }

    private List<ConsumableData> getSlotConsumble(CarSlot carSlot) {
        List<ConsumableData> result = new ArrayList<Commands.ConsumableData>();
        List<CarSlotConsumable> carSlotConsumblelist = slotConsumbleService.getCarSlotConsumableListBySlotId(carSlot
                .getId());
        if (carSlotConsumblelist != null) {
            for (CarSlotConsumable carSlotConsumble : carSlotConsumblelist) {
                ConsumableData.Builder cdbuilder = ConsumableData.newBuilder();
                cdbuilder.setPrice(carSlotConsumble.getPrice());
                cdbuilder.setPriceType(carSlotConsumble.getPriceType());
                cdbuilder.setType(carSlotConsumble.getConType());
                cdbuilder.setRatio(carSlotConsumble.getValue());
                result.add(cdbuilder.build());

            }

        }
        return result;

    }
}
