package StarRail.module.menu;//package StarRail.module.menu;

import StarRail.general.Coordinates;
import StarRail.general.Record;
import StarRail.general.StarRailProperties;
import StarRail.module.guide.PublicMethods;
import StarRail.util.MatchingUi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static StarRail.config.StartRailProfiles.updateStartRailProfiles;
import static StarRail.util.MouseUtil.detectAndClick;
import static StarRail.util.MouseUtil.mouseOperation;
import static StarRail.util.TimeUtil.dateTimeFormat;
import static StarRail.util.TimeUtil.getDuration;
import static StarRail.util.basic.random.RandomGenerator.randomWait;
import static StarRail.util.basic.keyboard.Keyboard.simulatingKeyPress;

/**
 * 菜单模块
 *
 * @author : 真寻
 * 2023/7/26
 */
@Slf4j
@Component
public class Menu {
    @Autowired
    private StarRailProperties properties;
    @Autowired
    private Coordinates.MenuCoordinates coordinates;
    @Autowired
    private Record record;
    @Autowired
    private MatchingUi matchingUi;
    @Autowired
    private PublicMethods publicMethods;

    /**
     * 菜单页面操作
     */
    public void menu() {
        // 打开菜单
        simulatingKeyPress("esc");

        if (matchingUi.menuUi()) {
            // 清委托
            cleanEntrust();

            // 领取邮件
            emailRewards();
        }

        // 关闭菜单
        simulatingKeyPress("esc");
        matchingUi.emailRewardsUi();
    }

    /**
     * 委托领取操作
     *
     * @param number 领取的委托数量
     */
    private void pickUpDispatchActions(int number) {
        for (int i = 0; i < number; i++) {
            // 领取
            detectAndClick(coordinates.getMenuArr2(), "领取");

            // 再次派遣
            detectAndClick(coordinates.getMenuArr3(), "再次派遣");

            // 等待UI
            randomWait(2100, 2600);
        }
    }

    /**
     * 清理委托
     */
    private void cleanEntrust() {
        log.info("领取委托奖励");
        // 当前委托进度
        long result = getDuration(properties.getDelegationStartTime());

        // 委托时间间隔（小时）
        int entrustTimeInterval = 20;

        // 委托完成，领取奖励
        if (result >= entrustTimeInterval) {
            // 点击委托
            detectAndClick(coordinates.getMenuArr1(), "委托");

            // 领取专属材料委托奖励
            pickUpDispatchActions(properties.getExclusiveMaterials());

            // 领取经验材料/信用点委托奖励
            mouseOperation(coordinates.getMenuArr5());
            pickUpDispatchActions(properties.getEmpiricalMaterials());

            // 返回功能页面
            mouseOperation(coordinates.getMenuArr4());

            if (matchingUi.menuUi()) {
                // 更新委托开始时间

                updateStartRailProfiles("delegationStartTime", dateTimeFormat());
                // 更新今日委托进度
                record.setEntrustProgress(true);

                log.info("委托奖励已领取");
                return;
            }
        }
        String info = " 小时";
        // 委托未完成
        result = entrustTimeInterval - result;
        if (entrustTimeInterval - result == entrustTimeInterval - 1) {
            result = 72000 - getDuration(properties.getDelegationStartTime(), "S");
            info = " 秒";
        }

        log.warn("委托派遣剩余时间 {} {}", result, info);
    }

    /**
     * 领取邮件
     */
    private void emailRewards() {
        log.info("领取邮件奖励");
        String info = "当前没有邮件奖励可领";

        // 打开邮箱
        mouseOperation(coordinates.getMenuArr6());

        // 如果没有邮件可领取，则退出
        if (matchingUi.emailRewardsUi()) {
            // 全部领取
            detectAndClick(coordinates.getMenuArr7(), "全部领取");

            // 点击空白处关闭
            publicMethods.clickBlankClose();

            info = "邮箱奖励领取完毕";
        }

        // 关闭邮箱
        mouseOperation(coordinates.getMenuArr8());

        if (matchingUi.emailRewardsUi()) {
            log.info(info);
        }
    }
}

