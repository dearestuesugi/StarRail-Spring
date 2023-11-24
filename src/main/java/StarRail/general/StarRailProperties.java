package StarRail.general;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 此类封装全局属性
 *
 * @author : 真寻
 * 2023/11/13
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "config")
public class StarRailProperties {
    /**
     * 游戏路径
     */
    private String starRailPath;

    /**
     * 是否使用支援角色
     */
    private Boolean usingSupportRoles;

    /**
     * 历战余响标识
     */
    private Boolean appointedDaySign;

    /**
     * 历战余响选择
     */
    private Integer weeklyChallengeSelection;

    /**
     * 历战余响完成时间
     */
    private String weeklyChallengeCompletionTime;

    /**
     * 专属材料委托数量
     */
    private Integer exclusiveMaterials;

    /**
     * 经验材料/信用点委托数量
     */
    private Integer empiricalMaterials;

    /**
     * 每日委托开始时间
     */
    private String delegationStartTime;

    /**
     * 拟造花萼(金)【经验材料】
     */
    private Integer empiricalMaterialSelection;

    /**
     * 拟造花萼(赤)【行迹材料】
     */
    private Integer traceMaterialSelection;

    /**
     * 凝滞虚影选择
     */
    private Integer breakthroughMaterialSelection;

    /**
     * 遗器选择
     */
    private Integer legacySelection;

    /**
     * 体力刷取选择
     */
    private Integer brushMaterial;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String cipher;
}
