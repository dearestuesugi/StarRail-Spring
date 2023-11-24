package StarRail.module.guide.challenge.materials;

import StarRail.module.guide.challenge.Challenge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static StarRail.util.MouseUtil.mouseOperation;
import static StarRail.util.basic.random.RandomGenerator.randomWait;

/**
 * 拟造花萼(赤)【行迹材料】
 *
 * @author : 真寻
 * 2023/8/7
 */
@Slf4j
@Component
public class TraceMaterial extends Challenge {
    private final String[] name = {"毁灭", "存护", "巡猎", "丰饶", "智识", "同谐", "虚无"};

    /**
     * 行迹材料
     */
    public void traceMaterial() {
        // 拟造花萼(赤)【行迹材料】选择
        int traceMaterialSelection = properties.getTraceMaterialSelection();

        log.info("开启行迹材料 {}之蕾·拟造花萼(赤) 挑战", name[traceMaterialSelection - 1]);

        // 打开生存索引
        publicMethods.openSurvivalUi();
        // 选择行迹材料
        mouseOperation(guideCoordinates.getTrace());
        randomWait();

        // 挑战
        challenge(traceMaterialSelection, "trace_material_");

        // 结束挑战
        endChallenge("行迹材料 " + name[traceMaterialSelection - 1] + "之蕾·拟造花萼");
    }
}
