package StarRail.module.guide.challenge.materials;

import StarRail.module.guide.challenge.Challenge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static StarRail.util.MouseUtil.mouseOperation;
import static StarRail.util.basic.random.RandomGenerator.randomWait;

/**
 * 拟造花萼(金)【经验材料】
 *
 * @author : 真寻
 * 2023/8/7
 */
@Slf4j
@Component
public class EmpiricalMaterials extends Challenge {
    private final String[] name = {"回忆", "以太", "藏珍"};

    /**
     * 经验材料
     */
    public void empiricalMaterials() {
        // 拟造花萼(金)【经验材料】选择
        int empiricalMaterialSelection = properties.getEmpiricalMaterialSelection();

        log.info("开启行迹材料 {}之蕾·拟造花萼(金) 挑战", name[empiricalMaterialSelection - 1]);

        // 打开生存索引
        publicMethods.openSurvivalUi();
        randomWait();

        // 选择经验材料
        mouseOperation(guideCoordinates.getEmpirical());

        // 挑战
        challenge(empiricalMaterialSelection, "empirical_material_");

        // 结束挑战
        endChallenge("经验材料 " + name[empiricalMaterialSelection - 1] + "之蕾·拟造花萼");
    }
}
