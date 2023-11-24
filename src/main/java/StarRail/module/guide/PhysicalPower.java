package StarRail.module.guide;

import StarRail.general.Coordinates;
import StarRail.general.Record;
import StarRail.general.StarRailProperties;
import StarRail.module.guide.challenge.materials.BreakthroughMaterial;
import StarRail.module.guide.challenge.materials.EmpiricalMaterials;
import StarRail.module.guide.challenge.materials.TraceMaterial;
import StarRail.module.guide.challenge.relic.Relic;
import StarRail.module.guide.challenge.weekly.WeeklyChallenge;
import StarRail.util.MatchingUi;
import StarRail.util.basic.random.RandomGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static StarRail.util.basic.keyboard.Keyboard.simulatingKeyPress;
import static StarRail.util.basic.screen.ScreenUtil.gettingNumbers;
import static StarRail.util.basic.screen.ScreenUtil.saveErrorImage;

/**
 * 体力 模块
 *
 * @author : 真寻
 * 2023/7/27
 */
@Slf4j
@Component
public class PhysicalPower {
    @Autowired
    private Record record;
    @Autowired
    private StarRailProperties properties;
    @Autowired
    private Coordinates.GuideCoordinates coordinates;
    @Autowired
    private TraceMaterial traceMaterial;
    @Autowired
    private EmpiricalMaterials empiricalMaterials;
    @Autowired
    private BreakthroughMaterial breakthroughMaterial;
    @Autowired
    private Relic relic;
    @Autowired
    private MatchingUi matchingUi;
    @Autowired
    private WeeklyChallenge weeklyChallenge;
    @Autowired
    private PublicMethods publicMethods;

    /**
     * 清理体力
     */
    public void cleanPhysicalPower() {
        long sTime = System.currentTimeMillis();

        // 体力刷取选择
        int brushMaterial = properties.getBrushMaterial();
        // 历战余响标识
        boolean appointedDaySign = properties.getAppointedDaySign();

        // 单次消耗体力
        int singleConsumptionPhysicalStrength = switch (brushMaterial) {
            case 1, 2 -> 60;
            case 3 -> 30;
            case 4 -> 40;
            default -> 0;
        };
        record.setChallengeCost(singleConsumptionPhysicalStrength);

        // 获取当前体力值
        int physicalPowerValue = getPhysicalPowerValue(true);
        record.setPhysicalPowerValue(physicalPowerValue);

        // 判断是否需要清理体力
        int number = physicalPowerValue / singleConsumptionPhysicalStrength;
        if (number == 0) {
            log.info("当前体力值 {} 无需清理", physicalPowerValue);
            simulatingKeyPress("esc");
            matchingUi.mainUi();
            return;
        } else {
            log.info("当前体力值 " + physicalPowerValue + " ，开始清理体力");
        }

        // 开启再来一次
        if (number >= 2) {
            record.setTryAgain(true);
        }


        // 历战余响
        if (!appointedDaySign && physicalPowerValue >= 90) {
            weeklyChallenge.weeklyChallenge();
        }

        // 体力刷取选择
        switch (brushMaterial) {
            // 刷经验材料
            case 1 -> empiricalMaterials.empiricalMaterials();
            // 刷行迹材料
            case 2 -> traceMaterial.traceMaterial();
            // 刷角色晋阶材料
            case 3 -> breakthroughMaterial.breakthroughMaterial();

            // 刷遗器
            case 4 -> relic.tunnelOfErosion();
        }
        // 更新体力
        physicalPowerValue = getPhysicalPowerValue(true);
        record.setPhysicalPowerValue(physicalPowerValue);

        // 再次判断是否需要清理体力
        number = physicalPowerValue / 10;
        record.setTryAgain(number >= 2);

        // 默认刷经验材料
        empiricalMaterials.empiricalMaterials();

        long eTime = System.currentTimeMillis();
        log.info("体力清理完成, 体力剩余 " + record.getPhysicalPowerValue() + " ,耗时 " + (eTime - sTime) / 1000 + " 秒");
    }

    /**
     * 获取体力值
     *
     * @param openSurvival 【可选】true 打开生存索引 默认不打开
     * @return 当前体力值
     */
    private int getPhysicalPowerValue(boolean... openSurvival) {
        for (boolean b : openSurvival) {
            if (b) {
                publicMethods.openSurvivalUi();
            }
        }
        int temp;
        int number = 0;
        do {
            temp = gettingNumbers(coordinates.getPhysicalPowerValue1());
            if (temp == -1) {
                temp = gettingNumbers(coordinates.getPhysicalPowerValue2());
            }
            if (number > 3) {
                saveErrorImage();
                log.error("获取体力值出错, 获取的值为 {}", temp);
                System.exit(-1);
            }
            number++;
        } while (temp > 240 || temp < 0);

        simulatingKeyPress("esc");
        RandomGenerator.randomWait();

        return temp;
    }
}
