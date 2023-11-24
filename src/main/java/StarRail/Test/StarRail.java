package StarRail.Test;

import StarRail.config.StartRailProfiles;
import StarRail.module.guide.PublicMethods;
import StarRail.module.guide.challenge.weekly.WeeklyChallenge;
import StarRail.ui.SimpleInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import static StarRail.util.basic.random.RandomGenerator.randomWait;

/**
 * @author : 真寻
 * 2023/11/21
 */
@SpringBootApplication(scanBasePackages = "StarRail")
public class StarRail implements CommandLineRunner {
    @Autowired
    private WeeklyChallenge weeklyChallenge;

    public static void main(String[] args) {
        // 关闭无头模式,允许使用图形化界面,若关闭则报java.awt类错
        SpringApplicationBuilder builder = new SpringApplicationBuilder(StarRail.class);
        builder.headless(false).run(args);
    }


    @Autowired
    private PublicMethods publicMethods;
    @Autowired
    private StartRailProfiles startRailProfiles;
    @Override
    public void run(String... args) {



//        new Thread(() -> {
//            randomWait(2000);
////            publicMethods.openGuideUi();
//            weeklyChallenge.weeklyChallenge();
//        }).start();

    }
}
