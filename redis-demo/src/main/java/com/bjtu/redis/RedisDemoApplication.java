package com.bjtu.redis;

import com.bjtu.redis.Action;
import com.bjtu.redis.RedisUtil;
import com.bjtu.redis.ActionJsonHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

/**
 *  SpringBootApplication
 * 用于代替 @SpringBootConfiguration（@Configuration）、 @EnableAutoConfiguration 、 @ComponentScan。
 * <p>
 * SpringBootConfiguration（Configuration） 注明为IoC容器的配置类，基于java config
 * EnableAutoConfiguration 借助@Import的帮助，将所有符合自动配置条件的bean定义加载到IoC容器
 * ComponentScan 自动扫描并加载符合条件的组件
 */
@SpringBootApplication
public class RedisDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisDemoApplication.class, args);
        initStr();
        System.out.println("******NoSql about redis******");

        int num=1;
        while(true) {
            System.out.println(num+ ". 输入选择:");
            System.out.println("\t A. 增加count \t B. 读出count值 \t C.读取freq \t D.读取log \t E.项目介绍 \t F.获取所有count增加的非重复时间戳");
            System.out.print("choice>>");
            Scanner ms = new Scanner(System.in);
            String ch = ms.nextLine();
            if(!ch.equals("A")&&!ch.equals("B")&&!ch.equals("C")&&!ch.equals("D")&&!ch.equals("E")&&!ch.equals("F")){
                System.out.println("\t 输入格式错误，请输入A或B或C或D或E或F！");
            }else{
                //输入正确，执行action
                switch (ch){
                    case "A":
                        System.out.println("Action执行Action:pluscount");
                        ActionJsonHelper jsh1 = new ActionJsonHelper(new Action("pluscount"));
                        Doact da1 = new Doact(jsh1);
                        da1.Do();
                        break;
                    case "B":
                        System.out.println("Action执行Action:readcount");
                        ActionJsonHelper jsh2 = new ActionJsonHelper(new Action("readcount"));
                        Doact da2 = new Doact(jsh2);
                        da2.Do();
                        break;
                    case "C":
                        System.out.println("Action执行Action:readfreq");
                        ActionJsonHelper jsh3 = new ActionJsonHelper(new Action("readfreq"));
                        Doact da3 = new Doact(jsh3);
                        da3.Do();
                        break;
                    case "D":
                        System.out.println("Action执行Action:readlog");
                        ActionJsonHelper jsh4 = new ActionJsonHelper(new Action("readlog"));
                        Doact da4 = new Doact(jsh4);
                        da4.Do();
                        break;
                    case "E":
                        System.out.println("Action执行Action:readSTR");
                        ActionJsonHelper jsh5 = new ActionJsonHelper(new Action("readSTR"));
                        Doact da5 = new Doact(jsh5);
                        da5.Do();
                        break;
                    case "F":
                        System.out.println("Action执行Action:readSet");
                        ActionJsonHelper jsh6 = new ActionJsonHelper(new Action("readSet"));
                        Doact da6 = new Doact(jsh6);
                        da6.Do();
                        break;
                    default:
                        break;
                }

            }

            System.out.println("退出吗？");
            System.out.println("\t A. 是 \t B. 不");
            System.out.print("choice>>");
            Scanner sc = new Scanner(System.in);
            String ex = ms.nextLine();
            if(ex.equals("yes")||ex.equals("YES")||ex.equals("A")){
                break;
            }
            num++;
            System.out.println("**************************************");
        }

        System.out.println("******谢谢使用 ******");

    }


    private static void initStr(){
        String str = "*********************介绍********************************\n" +
                "1.使用key-String的incr方式实现累加counter\n" +
                "2.使用key-String的get方式获取计数counter数值和简介\n" +
                "3.使用key-String的set方式设置STR\n" +
                "4.使用key-Hash，使用key-hash方式实现freq\n" +
                "5.使用key-list，利用先进先出实现日志log\n" +
                "6.使用key-Set，利用集合的去重，获取counter所有操作的不重复时间点\n" +
                "*****************************************************";
        RedisUtil.setSession("str",str);
    }
}

/*

总结：

1、获取运行环境信息和回调接口。例如ApplicationContextIntializer、ApplicationListener。
完成后，通知所有SpringApplicationRunListener执行started()。

2、创建并准备Environment。
完成后，通知所有SpringApplicationRunListener执行environmentPrepared()

3、创建并初始化 ApplicationContext 。例如，设置 Environment、加载配置等
完成后，通知所有SpringApplicationRunListener执行contextPrepared()、contextLoaded()

4、执行 ApplicationContext 的 refresh，完成程序启动
完成后，遍历执行 CommanadLineRunner、通知SpringApplicationRunListener 执行 finished()

参考：
https://blog.csdn.net/zxzzxzzxz123/article/details/69941910
https://www.cnblogs.com/shamo89/p/8184960.html
https://www.cnblogs.com/trgl/p/7353782.html

分析：

1） 创建一个SpringApplication对象实例，然后调用这个创建好的SpringApplication的实例方法

public static ConfigurableApplicationContext run(Object source, String... args)

public static ConfigurableApplicationContext run(Object[] sources, String[] args)

2） SpringApplication实例初始化完成并且完成设置后，就开始执行run方法的逻辑了，
方法执行伊始，首先遍历执行所有通过SpringFactoriesLoader可以查找到并加载的
SpringApplicationRunListener，调用它们的started()方法。


public SpringApplication(Object... sources)

private final Set<Object> sources = new LinkedHashSet<Object>();

private Banner.Mode bannerMode = Banner.Mode.CONSOLE;

...

private void initialize(Object[] sources)

3） 创建并配置当前SpringBoot应用将要使用的Environment（包括配置要使用的PropertySource以及Profile）。

private boolean deduceWebEnvironment()

4） 遍历调用所有SpringApplicationRunListener的environmentPrepared()的方法，通知Environment准备完毕。

5） 如果SpringApplication的showBanner属性被设置为true，则打印banner。

6） 根据用户是否明确设置了applicationContextClass类型以及初始化阶段的推断结果，
决定该为当前SpringBoot应用创建什么类型的ApplicationContext并创建完成，
然后根据条件决定是否添加ShutdownHook，决定是否使用自定义的BeanNameGenerator，
决定是否使用自定义的ResourceLoader，当然，最重要的，
将之前准备好的Environment设置给创建好的ApplicationContext使用。

7） ApplicationContext创建好之后，SpringApplication会再次借助Spring-FactoriesLoader，
查找并加载classpath中所有可用的ApplicationContext-Initializer，
然后遍历调用这些ApplicationContextInitializer的initialize（applicationContext）方法
来对已经创建好的ApplicationContext进行进一步的处理。

8） 遍历调用所有SpringApplicationRunListener的contextPrepared()方法。

9） 最核心的一步，将之前通过@EnableAutoConfiguration获取的所有配置以及其他形式的
IoC容器配置加载到已经准备完毕的ApplicationContext。

10） 遍历调用所有SpringApplicationRunListener的contextLoaded()方法。

11） 调用ApplicationContext的refresh()方法，完成IoC容器可用的最后一道工序。

12） 查找当前ApplicationContext中是否注册有CommandLineRunner，如果有，则遍历执行它们。

13） 正常情况下，遍历执行SpringApplicationRunListener的finished()方法、
（如果整个过程出现异常，则依然调用所有SpringApplicationRunListener的finished()方法，
只不过这种情况下会将异常信息一并传入处理）


private <T> Collection<? extends T> getSpringFactoriesInstances(Class<T> type)

private <T> Collection<? extends T> getSpringFactoriesInstances(Class<T> type,
			Class<?>[] parameterTypes, Object... args)

public void setInitializers

private Class<?> deduceMainApplicationClass()

public ConfigurableApplicationContext run(String... args)

private void configureHeadlessProperty()

private SpringApplicationRunListeners getRunListeners(String[] args)

public static List<String> loadFactoryNames(Class<?> factoryClass, ClassLoader classLoader)


*/
