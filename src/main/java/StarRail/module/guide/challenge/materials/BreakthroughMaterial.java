package StarRail.module.guide.challenge.materials;

import StarRail.module.guide.challenge.Challenge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static StarRail.util.MouseUtil.mouseOperation;
import static StarRail.util.basic.random.RandomGenerator.randomWait;

/**
 * 凝滞虚影【角色晋阶材料】
 *
 * @author : 真寻
 * 2023/8/7
 */
@Slf4j
@Component
public class BreakthroughMaterial extends Challenge {
    private final String[] name = {"空海", "巽风", "鸣雷", "炎华", "锋芒", "霜晶", "幻光", "冰棱", "震厄", "天人"};

    /**
     * 角色晋阶材料
     */
    public void breakthroughMaterial() {
        // 凝滞虚影【角色晋阶材料】选择
        int breakthroughMaterialSelection = properties.getBreakthroughMaterialSelection();

        log.info("开启 {}之形·凝滞虚影 挑战", name[breakthroughMaterialSelection - 1]);

        // 打开生存索引
        publicMethods.openSurvivalUi();
        randomWait();

        // 选择凝滞虚影
        mouseOperation(guideCoordinates.getBreakthrough());

        // 挑战
        challenge(breakthroughMaterialSelection, "breakthrough_material_");

        // 结束挑战
        endChallenge("角色晋阶材料 " + name[breakthroughMaterialSelection - 1] + "之形·凝滞虚影");
    }
}
