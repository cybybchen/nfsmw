package com.ea.eamobile.nfsmw.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ea.eamobile.nfsmw.constants.CarConst;
import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.model.Car;
import com.ea.eamobile.nfsmw.model.CtaContent;
import com.ea.eamobile.nfsmw.model.FeedContent;
import com.ea.eamobile.nfsmw.model.News;
import com.ea.eamobile.nfsmw.model.OperateBatch;
import com.ea.eamobile.nfsmw.model.OperateChangeRecord;
import com.ea.eamobile.nfsmw.model.Resource;
import com.ea.eamobile.nfsmw.model.ResourceVersion;
import com.ea.eamobile.nfsmw.model.SystemConfig;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserCar;
import com.ea.eamobile.nfsmw.model.UserFilter;
import com.ea.eamobile.nfsmw.service.CarService;
import com.ea.eamobile.nfsmw.service.CtaContentService;
import com.ea.eamobile.nfsmw.service.FeedContentService;
import com.ea.eamobile.nfsmw.service.GarageLeaderboardService;
import com.ea.eamobile.nfsmw.service.NewsService;
import com.ea.eamobile.nfsmw.service.OperateBatchService;
import com.ea.eamobile.nfsmw.service.OperateChangeRecordService;
import com.ea.eamobile.nfsmw.service.ResourceService;
import com.ea.eamobile.nfsmw.service.ResourceVersionService;
import com.ea.eamobile.nfsmw.service.SystemConfigService;
import com.ea.eamobile.nfsmw.service.UserCarService;
import com.ea.eamobile.nfsmw.service.UserCarSlotService;
import com.ea.eamobile.nfsmw.service.UserFilterService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.service.WordService;
import com.ea.eamobile.nfsmw.utils.ConfigUtil;
import com.ea.eamobile.nfsmw.view.ResourceVersionView;

@Controller
@RequestMapping("/nfsmw/admin")
public class AdminAction {
	private static final Logger logger = LoggerFactory.getLogger(AdminAction.class);
    protected static final String COOKIE_NAME = "ticket";

    @Autowired
    ResourceVersionService versionService;
    @Autowired
    ResourceService resourceService;
    @Autowired
    UserService userService;

    @Autowired
    CtaContentService ctaContentService;
    @Autowired
    WordService wordService;
    @Autowired
    NewsService newsService;
    @Autowired
    FeedContentService feedContentService;
    @Autowired
    CarService carService;
    @Autowired
    UserCarService userCarService;
    @Autowired
    UserCarSlotService userCarSlotService;
    @Autowired
    GarageLeaderboardService garageLeaderboardService;
    @Autowired
    OperateBatchService operateBatchService;
    @Autowired
    OperateChangeRecordService operateChangeRecordService;
    @Autowired
    SystemConfigService configService;
    @Autowired
    UserFilterService filterService;

    public static Long[] getResourceTesters() {
        if (StringUtils.isNotBlank(ConfigUtil.RESOURCE_TESTER)) {
            String[] data = ConfigUtil.RESOURCE_TESTER.split(",");
            Long[] ids = new Long[data.length];
            for (int i = 0; i < data.length; i++) {
                ids[i] = Long.parseLong(data[i]);
            }
            return ids;
        }
        return null;
    }

    @RequestMapping("")
    public String home(Model model, HttpServletResponse response) {
        return login("", "", model, response);
    }

    /**
     * 管理页面登录 TODO add filter ip
     * 
     * @param userName
     * @param password
     * @param model
     * @param response
     * @return
     */
    @RequestMapping("login")
    public String login(@RequestParam(value = "username", required = false, defaultValue = "") String userName,
            @RequestParam(value = "password", required = false, defaultValue = "") String password, Model model,
            HttpServletResponse response) {
    	logger.debug("111userName={}, password={}", userName, password);
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            return "admin/login";
        }
        logger.debug("222userName={}, password={}", userName, password);
        if (userName.equals(ConfigUtil.ADMIN_USER) && password.equals(ConfigUtil.ADMIN_PASSWORD)) {
            String ticket = buildCookie();
            Cookie cookie = new Cookie(COOKIE_NAME, ticket);
            cookie.setMaxAge(60 * 30);
            response.addCookie(cookie);
            return "redirect:version";
        }
        model.addAttribute("message", "username or password error.");
        return "error";
    }

    protected String buildCookie() {
        String username = ConfigUtil.ADMIN_USER;
        String password = ConfigUtil.ADMIN_PASSWORD;
        return DigestUtils.md5Hex(username + password);
    }

    @RequestMapping("version")
    public String versionHome(@RequestParam(value = "id", required = false, defaultValue = "0") int versionId,
            Model model, HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        List<ResourceVersion> tempversions = versionService.getResourceVersionList();
        List<ResourceVersionView> versions = new ArrayList<ResourceVersionView>();
        for (ResourceVersion resourceVersion : tempversions) {
            ResourceVersionView resourceVersionView = new ResourceVersionView();
            long real = resourceVersion.getCreateTime();
            resourceVersionView.setCreateTime(new Date(real * 1000L));
            resourceVersionView.setId(resourceVersion.getId());
            resourceVersionView.setStatus(resourceVersion.getStatus());
            resourceVersionView.setVersion(resourceVersion.getVersion());
            resourceVersionView.setGameEdition(resourceVersion.getGameEdition());
            versions.add(resourceVersionView);
        }
        model.addAttribute("versions", versions);
        // update
        if (versionId != 0) {
            ResourceVersion tempversion = versionService.getResourceVersion(versionId);
            ResourceVersionView version = new ResourceVersionView();
            long real = tempversion.getCreateTime();
            version.setCreateTime(new Date(real * 1000L));
            version.setId(tempversion.getId());
            version.setStatus(tempversion.getStatus());
            version.setVersion(tempversion.getVersion());
            version.setGameEdition(tempversion.getGameEdition());
            model.addAttribute("version", version);
        }
        return "admin/version";
    }

    /**
     * 更新资源的版本号 慎用！
     * 
     * @param id
     * @param status
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("versionUpdate")
    public String versionUpdate(@RequestParam("id") int id, @RequestParam("status") int status,
            @RequestParam("gameEdition") int gameEdition, Model model, HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        ResourceVersion version = versionService.getResourceVersion(id);
        if (version != null) {
            if (versionService.validStatus(status)) {
                version.setStatus(status);
                version.setGameEdition(gameEdition);
                versionService.update(version);
            } else {
                model.addAttribute("message", "status type is wrong.");
                return "error";
            }
        }
        return versionHome(0, model, request);
    }

    @RequestMapping("version/add")
    public String versionAdd(@RequestParam("version") int version, @RequestParam("status") int status,
            @RequestParam("gameEdition") int gameEdition, @RequestParam("targetVersion") int targetVersion,
            Model model, HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        // 版本存在不能添加
        if (versionService.isExist(version)) {
            model.addAttribute("msg", "DLC版本号已存在");
            return versionHome(0, model, request);
        }
        ResourceVersion rv = new ResourceVersion();
        rv.setStatus(status);
        rv.setVersion(version);
        rv.setCreateTime((int) (System.currentTimeMillis() / 1000));
        rv.setGameEdition(gameEdition);
        versionService.insert(rv);
        // 给不同游戏版本建立时，选择复制哪个版本的资源 默认不复制
        if (targetVersion > 0 && version != targetVersion) {
            resourceService.copy(targetVersion, version);
        }
        return versionHome(0, model, request);
    }

    @RequestMapping("version/clearcache")
    public String versionClear(HttpServletRequest request, Model model) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        versionService.clear();
        resourceService.clear();
        return versionHome(0, model, request);
    }

    protected boolean isLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals(COOKIE_NAME) && cookie.getValue().equals(buildCookie())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 查询要修改的用户
     * 
     * @param id
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("user")
    public String userSearch(@RequestParam(value = "id", required = false) Integer id, Model model,
            HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        // get tester by config
        List<User> users = userService.getUsersByIds(getResourceTesters());
        model.addAttribute("users", users);
        // search user
        if (id != null) {
            User user = userService.getUser(id);
            model.addAttribute("user", user);
        }
        return "admin/user";
    }

    @RequestMapping("user/delete")
    public String userDelete(@RequestParam("userId") long userId, Model model, HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        userService.delete(userId);
        return "admin/user";
    }

    /**
     * 修改用户的下载版本号
     * 
     * @param id
     * @param version
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("userUpdate")
    public String userUpdate(@RequestParam(value = "txtId") int id, @RequestParam("txtVersion") int version,
            Model model, HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        User user = userService.getUser(id);
        if (user != null) {
            user.setVersion(version);
            userService.updateUser(user);
            model.addAttribute("message", "modify success.");
        }
        return "admin/user";
    }

    /**
     * 添加过滤用户
     * 
     * @param userId
     * @param option
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("user/addFilter")
    public String addFilter(@RequestParam(value = "userId") long userId, @RequestParam("option") int option,
            Model model, HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        UserFilter filter = filterService.getUserFilter(option, userId);
        if (filter != null) {
            model.addAttribute("message", "is exist.");
        } else {
            filter = new UserFilter();
            filter.setFilterOption(option);
            filter.setUserId(userId);
            filterService.insert(filter);
            model.addAttribute("message", "add success.");
        }
        return "admin/user";
    }

    @RequestMapping(value = "resource")
    public String resource(@RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "orderid", required = false) Integer orderId,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "version", required = false) Integer version, Model model, HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        // if (id != null) {
        // // update
        // Resource res = resourceService.getResource(id);
        // res.setOrderId(orderId);
        // res.setStatus(status);
        // resourceService.update(res);
        // }
        List<Resource> list = null;
        if (version != null) {
            list = resourceService.getResourceListByVersion(version);
        } else {
            list = resourceService.getResourceList();
        }
        model.addAttribute("resources", list);
        return "admin/resource";
    }

    @RequestMapping(value = "resource/delete")
    public String resource(@RequestParam(value = "id") Integer id, Model model, HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        if (id != null) {
            Resource res = resourceService.getResource(id);
            resourceService.delete(id);
            return resource(null, null, null, res.getVersion(), model, request);
        }
        return "admin/resource";
    }

    @RequestMapping(value = "ctacontent")
    public String ctaContentHome(Model model, HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        List<CtaContent> ctacontentList = ctaContentService.getCtaContentList();
        model.addAttribute("ctacontentList", ctacontentList);
        return "admin/ctacontent";

    }

    @RequestMapping(value = "ctacontentUpdate")
    public String updateCtacontent(Model model, HttpServletRequest request,
            @RequestParam(value = "id", required = false) Integer id) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        CtaContent ctacontent = ctaContentService.getCtaContent(id);
        model.addAttribute("ctaContent", ctacontent);
        model.addAttribute("message", "please update");
        return "admin/ctaContentUpdate";

    }

    @RequestMapping(value = "updateCtaContent")
    public String doupdateCtacontent(Model model, HttpServletRequest request,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "comment", required = false) String comment) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        CtaContent ctacontent = ctaContentService.getCtaContent(id);
        ctacontent.setContent(content);
        ctacontent.setComments(comment);
        ctaContentService.update(ctacontent);
        model.addAttribute("ctaContent", ctacontent);
        model.addAttribute("message", "update success");
        return "admin/ctaContentUpdate";

    }

    @RequestMapping(value = "feedcontent")
    public String feedContentHome(Model model, HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        List<FeedContent> feedcontentList = feedContentService.getFeedContentList();
        model.addAttribute("feedcontentList", feedcontentList);
        model.addAttribute("message", "");
        return "admin/feedcontent";

    }

    @RequestMapping(value = "feedcontentUpdate")
    public String updateFeedcontent(Model model, HttpServletRequest request,
            @RequestParam(value = "id", required = false) Integer id) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        FeedContent feedcontent = feedContentService.getFeedContent(id);
        model.addAttribute("feedContent", feedcontent);
        model.addAttribute("message", "please update");
        return "admin/feedContentUpdate";

    }

    @RequestMapping(value = "feedcontentDelete")
    public String deleteFeedcontent(Model model, HttpServletRequest request,
            @RequestParam(value = "id", required = false) Integer id) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        feedContentService.deleteById(id);
        List<FeedContent> feedcontentList = feedContentService.getFeedContentList();
        model.addAttribute("feedcontentList", feedcontentList);
        model.addAttribute("message", "please update");
        return "admin/feedcontent";

    }

    @RequestMapping(value = "updateFeedContent")
    public String doupdateCtacontent(Model model, HttpServletRequest request,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "content", required = false) String content) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        FeedContent feedcontent = feedContentService.getFeedContent(id);
        feedcontent.setContent(content);
        feedContentService.update(feedcontent);
        model.addAttribute("feedContent", feedcontent);
        model.addAttribute("message", "update success");
        return "admin/feedContentUpdate";

    }

    @RequestMapping(value = "addnewfeed")
    public String addNewTournamentOnlineId(@RequestParam(value = "content", required = false) String content,
            Model model, HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        FeedContent feedContent = new FeedContent();
        feedContent.setContent(content);
        feedContentService.insert(feedContent);
        List<FeedContent> feedcontentList = feedContentService.getFeedContentList();
        model.addAttribute("feedcontentList", feedcontentList);
        model.addAttribute("message", "success");
        return "admin/feedcontent";
    }

    @RequestMapping(value = "sendcar")
    public String addNewTournamentOnlineId(Model model, HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        model.addAttribute("message", "");
        return "admin/sendcar";
    }

    @RequestMapping(value = "addnewcar")
    public String addnewcar(@RequestParam(value = "userIdList", required = false) String userIdList,
            @RequestParam(value = "carId", required = false) String carId, Model model, HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        String[] paramList = userIdList.split("\r\n");
        List<Long> idList = new ArrayList<Long>();
        for (int i = 0; i < paramList.length; i++) {
            long temp = Long.parseLong(paramList[i]);
            idList.add(temp);

        }
        StringBuffer buf = new StringBuffer();
        String message = "";
        OperateBatch operateBatch = new OperateBatch();

        operateBatchService.insert(operateBatch);

        for (Long userId : idList) {
            User user = userService.getUser(userId);
            if (user == null) {
                buf.append("failed ,user is null userId is " + userId);
                continue;
            }
            Car car = carService.getCar(carId);
            if (car == null) {
                buf.append("failed ,car is null car id is" + carId);
                continue;
            }
            UserCar userCar = userCarService.getUserCarByUserIdAndCarId(userId, carId);
            if (userCar != null) {
                buf.append("failed ,user car is not null userId is " + userId + "carId is" + carId);
                continue;
            }
            userCar = new UserCar();
            userCar.setCarId(car.getId());
            userCar.setUserId(userId);
            userCar.setScore(car.getScore());
            userCar.setStatus(car.needTransport() ? CarConst.TRANSPORTING : CarConst.OWN);
            userCar.setChartletId(0);
            userCar.setCreateTime((int) (System.currentTimeMillis() / 1000));
            userCarService.insert(userCar);
            userCarSlotService.initLevelOneCarSlot(userCar);
            OperateChangeRecord operateChangeRecord = new OperateChangeRecord();
            operateChangeRecord.setAddCar(carId);
            operateChangeRecord.setAddEnergy(0);
            operateChangeRecord.setAddGold(0);
            operateChangeRecord.setAddMoney(0);
            operateChangeRecord.setAddFragCount(0);
            operateChangeRecord.setFragmentId(0);
            operateChangeRecord.setCurrentFragCount(0);
            operateChangeRecord.setOriginalFragCount(0);
            operateChangeRecord.setBatchNum(operateBatch.getId());
            operateChangeRecord.setOriginalEnergy(user.getEnergy());
            operateChangeRecord.setOriginalGold(user.getGold());
            operateChangeRecord.setOriginalMoney(user.getMoney());
            operateChangeRecord.setCurrentEnergy(user.getEnergy());
            operateChangeRecord.setCurrentGold(user.getGold());
            operateChangeRecord.setCurrentMoney(user.getMoney());
            operateChangeRecord.setUserId(userId);
            operateChangeRecordService.insert(operateChangeRecord);

        }
        message = buf.toString();
        if (message.length() < 1) {
            message = "success";
        }
        model.addAttribute("message", message);
        return "admin/sendcar";
    }

    @RequestMapping(value = "addUserInfo")
    public String addUserInfo(@RequestParam(value = "userIdList", required = false) String userIdList,
            @RequestParam(value = "addMoney", required = false, defaultValue = "0") int addMoney,
            @RequestParam(value = "addGold", required = false, defaultValue = "0") int addGold,
            @RequestParam(value = "addEnergy", required = false, defaultValue = "0") int addEnergy, Model model,
            HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        StringBuffer buf = new StringBuffer();
        String[] paramList = userIdList.split("\r\n");
        List<Long> idList = new ArrayList<Long>();
        for (int i = 0; i < paramList.length; i++) {
            long temp = Long.parseLong(paramList[i]);
            idList.add(temp);

        }
        String message = "";
        OperateBatch operateBatch = new OperateBatch();

        operateBatchService.insert(operateBatch);

        for (Long userId : idList) {
            User user = userService.getUser(userId);
            if (user == null) {
                buf.append("failed ,user is null userId is " + userId);
                continue;
            }
            OperateChangeRecord operateChangeRecord = new OperateChangeRecord();
            operateChangeRecord.setOriginalEnergy(user.getEnergy());
            operateChangeRecord.setOriginalGold(user.getGold());
            operateChangeRecord.setOriginalMoney(user.getMoney());
            user.setMoney(user.getMoney() + addMoney);
            user.setGold(user.getGold() + addGold);
            user.setEnergy(user.getEnergy() + addEnergy);
            userService.updateUser(user);
            operateChangeRecord.setCurrentEnergy(user.getEnergy());
            operateChangeRecord.setCurrentGold(user.getGold());
            operateChangeRecord.setCurrentMoney(user.getMoney());
            operateChangeRecord.setAddEnergy(addEnergy);
            operateChangeRecord.setAddGold(addGold);
            operateChangeRecord.setAddCar("");
            operateChangeRecord.setAddMoney(addMoney);
            operateChangeRecord.setBatchNum(operateBatch.getId());
            operateChangeRecord.setUserId(userId);
            operateChangeRecord.setAddFragCount(0);
            operateChangeRecord.setFragmentId(0);
            operateChangeRecord.setCurrentFragCount(0);
            operateChangeRecord.setOriginalFragCount(0);
            operateChangeRecordService.insert(operateChangeRecord);

        }
        message = buf.toString();
        if (message.length() < 1) {
            message = "success";
        }
        model.addAttribute("message", message);
        return "admin/sendcar";
    }

    @RequestMapping(value = "word")
    public String addCensorWord(Model model, HttpServletRequest request,
            @RequestParam(value = "word", required = false) String word) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        if (StringUtils.isNotBlank(word)) {
            String msg = null;
            String cw = wordService.getCensorWord(word);
            if (StringUtils.isNotBlank(cw)) {
                msg = word + " is exist.";
            } else {
                wordService.insert(word);
                msg = "done.";
            }
            model.addAttribute("msg", msg);
        }
        return "admin/word";

    }

    /*********************** message of day ***********************/
    @RequestMapping(value = "news")
    public String showNews(Model model, HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        String boards = newsService.getBoard();
        String actions = newsService.getAction();
        String css = newsService.getCss();
        String contact = newsService.getContact();
        model.addAttribute("board", boards);
        model.addAttribute("action", actions);
        model.addAttribute("css", css);
        model.addAttribute("contact", contact);
        // set mode switch
        SystemConfig config = configService.getSystemConfig(Const.MOD_CONFIG);
        String modStatus = config == null ? "0" : config.getValue();
        model.addAttribute("modStatus", modStatus);
        return "admin/news";
    }

    @RequestMapping(value = "news/save")
    public String saveNews(@RequestParam("board") String board, @RequestParam("action") String action,
            @RequestParam("css") String css, @RequestParam("contact") String contact, Model model,
            HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        News boardNews = newsService.getNewsByType(NewsService.BOARD_TYPE);
        if (boardNews != null) {
            // update
            boardNews.setContent(board);
            newsService.update(boardNews);
        } else {
            // insert
            newsService.save(NewsService.BOARD_TYPE, board);
        }
        News actionNews = newsService.getNewsByType(NewsService.ACTION_TYPE);
        if (actionNews != null) {
            // update
            actionNews.setContent(action);
            newsService.update(actionNews);
        } else {
            // insert
            newsService.save(NewsService.ACTION_TYPE, action);
        }
        News cssNews = newsService.getNewsByType(NewsService.CSS_TYPE);
        if (cssNews != null) {
            cssNews.setContent(css);
            newsService.update(cssNews);
        } else {
            newsService.save(NewsService.CSS_TYPE, css);
        }
        // ct
        News contactNews = newsService.getNewsByType(NewsService.CONTACT_TYPE);
        if (contactNews != null) {
            contactNews.setContent(contact);
            newsService.update(contactNews);
        } else {
            newsService.save(NewsService.CONTACT_TYPE, contact);
        }
        return showNews(model, request);
    }

    /*************************** system config *****************************/
    @RequestMapping(value = "system/config/mod")
    public String systemConfig(Model model, HttpServletRequest request, @RequestParam("status") int status) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        configService.updateModStatus(status);
        return showNews(model, request);
    }

}
