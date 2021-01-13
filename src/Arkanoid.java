import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.HashSet;

public class Arkanoid extends GraphicsProgram {

    // 窗口宽高
    public static final int APPLICATION_WIDTH = 610;
    public static final int APPLICATION_HEIGHT = 800;

    /* 动画每一帧间隔10ms*/
    private static final double DELAY = 1000 / 60.0;

    /* 水平速度：每一帧水平方向的移动距离 */
    private static double VELOCITY_Y;

    /* 竖直速度：每一帧竖直方向的移动距离 */
    private static double VELOCITY_X;

    /* 初始水平速度：每一帧水平方向的移动距离 */
    private static final double VY = 6;

    /* 初始竖直速度：每一帧竖直方向的移动距离 */
    private static final double VX = 6;

    /* 小球的半径 */
    private static final int BALL_RADIUS = 10;

    /* 小球的颜色 */
    private static final Color BALL_COLOR = Color.BLACK;

    /* 小球 */
    GOval ball;
    /* 小球此刻的水平速度 */
    double vx;
    /* 小球此刻的竖直速度 */
    double vy;

    /*砖块*/
    // 砖颜色是黑色
    public static final Color BRICK_COLOR = Color.ORANGE;

    // 砖的实心部分宽18,高8
    public static final int BRICK_WIDTH = 53;
    public static final int BRICK_HEIGHT = 50;

    // 每块实心砖的上下左右各有宽度为1的空白。
    // 注意：当两块砖挨着的时候，中间的空白有两遍，加起来就是2了。
    public static final int BRICK_MARGIN = 8;

    static int Brick_Row = 6;
    static int Brick_Column = 10;

    public GObject Obj_;

    //砖块边角的范围
    private static final int BRICKSCORNER = 0;

    //挡板
    GRect paddle;
    // 挡板的宽80,高10

    public static final int PWIDTH = 65;
    public static final int PHEIGHT = 30;

    public static int PADDLE_WIDTH;
    public static int PADDLE_HEIGHT;

    //奖励块
    GRect Reward;
    GOval rb;
    GOval rb2;
    public static double Reward_Width = 20;
    public static double Reward_Height = 20;
    public static int RewardBallLive = 6;
    public static double rbx;
    public static double rby;
    public static double rb2x;
    public static double rb2y;

    // 生命值
    int Live;
    //得分
    int Score;
    //击破的砖块数
    int Point;

    int StageNum = 1;

    static RandomGenerator randomGenerator = RandomGenerator.getInstance();

    //文字显示
    GLabel label = new GLabel("");
    GLabel label2 = new GLabel("");
    GLabel label3 = new GLabel("");
    GLabel label4 = new GLabel("");
    Font f = new Font("", Font.BOLD, 50);

    /**
     * Method: Init
     * -----------------------
     * 初始化
     */
    public void init() {
//        if (StageNum == 1){
        if (StageNum < 2) {
            try {
                Image bg = ImageIO.read(new FileInputStream(".//src//imge1.jpg"));
                GImage gImage = new GImage(bg);
                gImage.setSize(540, 360);
                add(gImage, 50, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Image bg = ImageIO.read(new FileInputStream(".//src//imge2.jpg"));
                GImage gImage = new GImage(bg);
                gImage.setSize(540, 360);
                add(gImage, 50, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (StageNum == 1) {
            PADDLE_WIDTH = PWIDTH;
            PADDLE_HEIGHT = PHEIGHT;
        }
        VELOCITY_X = VX + (StageNum - 1);
        VELOCITY_Y = VY + (StageNum - 1);
        if (null != rb) {
            remove(rb);
        }
        if (null != rb2) {
            remove(rb2);
        }
        rb = null;
        rb2 = null;
        rbx = -VELOCITY_X;
        rby = -VELOCITY_Y;
        rb2x = VELOCITY_X;
        rb2y = -VELOCITY_Y;
        Live = 3;
        Point = 0;
        label.setLabel("CLICK TO START");  //游戏信息
        add(label, 0.5 * APPLICATION_WIDTH - 30, 0.7 * APPLICATION_HEIGHT);
        label2.setLabel("LIVE = " + "❤❤❤".substring(0, Live)); //生命值
        add(label2, 40, APPLICATION_HEIGHT - 60);
        label3.setLabel("Score = " + Score); //分数
        add(label3, APPLICATION_WIDTH - 100, APPLICATION_HEIGHT - 60);
        label4.setLabel("STAGE " + StageNum);    //关卡数
        add(label4, 40, APPLICATION_HEIGHT - 80);
        makeBall();   // 往屏幕上添加小球
        makeBricks(); // 往屏幕上添加砖块
        makePaddle();  //往屏幕上添加挡板
        vx = randomGenerator.nextChoice(-VELOCITY_X, VELOCITY_X);        // 水平速度
        vy = -VELOCITY_Y;        // 竖直速度
//        }else if (StageNum == 2){
//            PADDLE_WIDTH = PWIDTH -10;
//            PADDLE_HEIGHT = PHEIGHT;
//            VELOCITY_X = VX + 2;
//            VELOCITY_Y = VY + 2;
//            Live = 3;
//            Point = 0;
//            label.setLabel("CLICK TO START");  //游戏信息
//            add(label, 0.5 * APPLICATION_WIDTH - 30, 0.5 * APPLICATION_HEIGHT);
//            label2.setLabel("LIVE = " + "❤❤❤".substring(0, Live)); //生命值
//            add(label2, 40, APPLICATION_HEIGHT - 60);
//            label3.setLabel("Score = " + Score); //分数
//            add(label3, APPLICATION_WIDTH - 100, APPLICATION_HEIGHT - 60);
//            label4.setLabel("STAGE 2");    //关卡数
//            add(label4, 40, APPLICATION_HEIGHT - 80);
//            makeBall();   // 往屏幕上添加小球
//            makeBricks(); // 往屏幕上添加砖块
//            makePaddle();  //往屏幕上添加挡板
//            vx = randomGenerator.nextChoice(-VELOCITY_X, VELOCITY_X);        // 水平速度
//            vy = -VELOCITY_Y;        // 竖直速度
//        }
    }

    public void run() {
        //启动鼠标
        addMouseListeners();
        label.setLabel("");
        //noinspection Infinite Loop Statement
        while (Live > 0) {
            waitForClick();
            while (label.getLabel().equals("")) { //label出现提示时停止循环，下一条命
                // 检查是否撞墙
                checkCollision();

                //检查是否撞到砖块
                checkBrickCollision();

                if (Reward != null) {
                    checkReward();
                }
                if (rb != null || rb2 != null) {
                    checkRewardBall2(rb, 1);
                    checkRewardBall2(rb2, 2);
                    checkRewardBall(rb, 1);
                    checkRewardBall(rb2, 2);


                }

                // 移动小球的位置
                ball.move(vx, vy);

                // 延迟
                pause(DELAY);
            }
            if (label.getLabel() == "GG") {
                waitForClick();
                if (Point < Brick_Column * Brick_Row) {
                    label.setLabel("");
                    remove(ball);
                    if (null != rb) {
                        remove(rb);
                        remove(rb2);
                    }
                    rb = null;
                    rb2 = null;
                    VELOCITY_X = VX;
                    VELOCITY_Y = VY;
                    Live -= 1;
                    paddle.setSize(PADDLE_WIDTH, PADDLE_HEIGHT);
                    label2.setLabel("LIVE = " + "❤❤❤".substring(0, Live));
                    makeBall();   // 往屏幕上添加小球
                    vx = randomGenerator.nextChoice(-VELOCITY_X, VELOCITY_X);        // 水平速度
                    vy = -VELOCITY_Y;        // 竖直速度}
                }
            } else if (label.getLabel() == ("You Win The Game!")) {
                waitForClick();
                clear();
                try {
                    Image bg = ImageIO.read(new FileInputStream(".//src//imge3.jpg"));
                    GImage gImage = new GImage(bg);
                    gImage.setSize(605,720);
                    add(gImage, 5, 10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                GLabel label0 = new GLabel("Congratulation!");
                label0.setFont(f);
                add(label0, 50, 0.5*APPLICATION_HEIGHT);
            } else {
                clear();
                init();
                run();
            }
        }
        StageNum = 1;
        Score = 0;
        clear();
        init();
        run();
    }

    /**
     * Method: Check Collision
     * -----------------------
     * 检查小球是否和墙相撞，如果相撞，改变小球运动方向
     */
    void checkCollision() {
        // 小球碰到上墙，反弹,碰到地板gg
        if (hitBottomWall(ball)) {
            if (Live == 1) {
                label.setLabel("CLICK TO  RESTART");
            } else {
                label.setLabel("GG");
            }
        } else if (hitTopWall(ball)) {
            vy = VELOCITY_Y;
        }

        // 小球碰到左右两侧的墙，水平反弹
        if (hitLeftWall(ball)) {
            vx = VELOCITY_X;
        } else if (hitRightWall(ball)) {
            vx = -VELOCITY_X;
        }
    }

    /**
     * Method: Check Brick Collision
     * -----------------------
     * 检查小球是否和砖块撞，如果相撞，砖块消失
     */
    void checkBrickCollision() {
        GObject obj = getCollidingObject();
        double ballX = ball.getX() + BALL_RADIUS;
        double ballY = ball.getY() + BALL_RADIUS;
        if (obj != null) {
            if (obj.getHeight() == PADDLE_HEIGHT) {  //碰到挡板
                paddle.setColor(ball.getColor());
                ball.setColor(BALL_COLOR);
                if ((ballX - obj.getX() > BRICKSCORNER) && (-ballX + PADDLE_WIDTH + obj.getX() > BRICKSCORNER)) { //碰到挡板中间时
                    vy = -VELOCITY_Y;
                } else {  // 碰到挡板边缘
                    vx = -vx;
                    vy = -vy;
                }
            } else if (obj.getHeight() == BRICK_HEIGHT) {  //碰到砖块
                remove(obj);  //小球碰到砖块，砖块消失
                Score += (Math.abs(vx) - VELOCITY_X) + (Math.abs(vy) - VELOCITY_Y) + 1;  //球速度越快加分越多
                label3.setLabel("Score = " + Score);
                ball.setColor(obj.getColor());
                Point += 1;
                if (Point == Brick_Column * Brick_Row) {
                    StageNum += 1;
                    label.setLabel("NEXT STAGE");
                }
                if ((ballX - obj.getX() > BRICKSCORNER) & (-ballX + BRICK_WIDTH + obj.getX() > BRICKSCORNER)) {
                    if (ballY - obj.getY() - 0.5 * BRICK_HEIGHT > 0) { //碰到砖块下方
                        vy = -vy;
                    } else {  //碰到砖块上方
                        vy = -vy;
                    }
                } else if ((obj.getY() + BRICKSCORNER < ballY) & (obj.getY() + BRICK_HEIGHT - BRICKSCORNER > ballY)) { //碰到砖块侧面
                    vx = -vx;
                } else {
                    if (ballX < obj.getX() + BRICKSCORNER) {
                        if (ballY > obj.getY() + BRICK_HEIGHT - BRICKSCORNER) {   //碰到砖块左下
                            vx = -VELOCITY_X;
                            vy = VELOCITY_Y;
                        } else {  //碰到砖块左上
                            vx = -VELOCITY_X;
                            vy = -VELOCITY_Y;
                        }
                    } else {
                        if (ballY > obj.getY() + BRICK_HEIGHT - BRICKSCORNER) {  //碰到砖块右下
                            vx = VELOCITY_X;
                            vy = VELOCITY_Y;
                        } else {  //碰到砖块右上
                            vx = VELOCITY_X;
                            vy = -VELOCITY_Y;
                        }
                    }
                }
                double a = randomGenerator.nextDouble(0.0, 1.0);
                if (a >= 1 - 20.0 / (Brick_Row * Brick_Column)) { //部分砖块可以加长挡板和加速球
                    makeReward(obj.getX() + 0.5 * BRICK_WIDTH, obj.getY());
                }
                if (a <= 20.0 / (Brick_Row * Brick_Column)) { //部分砖块产生分裂球
                    if (rb == null && rb2 == null) {
                        rb = makeRewardBall(rb, obj.getX(), obj.getY());
                        rb2 = makeRewardBall(rb2, obj.getX() + BRICK_WIDTH - BALL_RADIUS, obj.getY());
                    }
                }
            }
        }
        Obj_ = null;
    }

    //    检测奖励胶囊是否碰到挡板
    void checkReward() {
        GObject obj1 = getElementAt(Reward.getX(), Reward.getY()); //球的4个矩形定位点
        GObject obj2 = getElementAt(Reward.getX() + Reward_Width, Reward.getY());
        GObject obj3 = getElementAt(Reward.getX(), Reward.getY() + Reward_Height);
        GObject obj4 = getElementAt(Reward.getX() + Reward_Width, Reward.getY() + Reward_Height);
        HashSet<GObject> list = new HashSet<>();
        Collections.addAll(list, obj3, obj4, obj1, obj2);
        Reward.move(0, VELOCITY_Y * 0.5);
        for (GObject gobj : list) {
            if (gobj != null && gobj.getHeight() == PADDLE_HEIGHT) {
                remove(Reward);
                Reward = null;
                PADDLE_WIDTH += 300;
                paddle.setSize(PADDLE_WIDTH, PADDLE_HEIGHT);
                VELOCITY_X = 1.5 * VELOCITY_X;
                VELOCITY_Y = 1.5 * VELOCITY_Y;
                if (PADDLE_WIDTH >= getWidth()) {
                    label.setLabel("You Win The Game!");
                }
                break;
            }
        }
        if (Reward != null && Reward.getY() > getHeight()) {
            remove(Reward);
            Reward = null;
        }

    }

    void checkRewardBall(GObject r, int num) {
        if (null != r) {
            GObject obj1 = getElementAt(r.getX(), r.getY()); //球的4个矩形定位点
            GObject obj2 = getElementAt(r.getX() + BALL_RADIUS, r.getY());
            GObject obj3 = getElementAt(r.getX(), r.getY() + BALL_RADIUS);
            GObject obj4 = getElementAt(r.getX() + BALL_RADIUS, r.getY() + BALL_RADIUS);
            HashSet<GObject> list = new HashSet<>();
            Collections.addAll(list, obj3, obj4, obj1, obj2);
            if (num == 1) {
                r.move(rbx, rby);
            } else {
                r.move(rb2x, rb2y);
            }
            for (GObject gobj : list) {
                if (gobj != null && gobj.getHeight() == BRICK_HEIGHT && RewardBallLive >= 0) {
                    remove(gobj);
                    Point += 1;
                    if (Point == Brick_Column * Brick_Row) {
                        StageNum += 1;
                        label.setLabel("NEXT STAGE");
                    }
                    RewardBallLive -= 1;
                }
            }
            if (RewardBallLive <= 0) {
                if (num == 1) {
                    remove(rb);
                    rb = null;
                } else {
                    remove(rb2);
                    rb2 = null;
                }


            }
        }

    }

    void checkRewardBall2(GOval g, int num) {
        if (null != g) {
            if (num == 1) {      // 小球碰到上下墙，反弹
                if (hitBottomWall(g)) {
                    rby = -rby;
                } else if (hitTopWall(g)) {
                    rby = -rby;
                }

                // 小球碰到左右两侧的墙，水平反弹
                if (hitLeftWall(g)) {
                    rbx = -rbx;
                } else if (hitRightWall(g)) {
                    rbx = -rbx;
                }
            } else {
                if (hitBottomWall(g)) {
                    rb2y = -rb2y;
                } else if (hitTopWall(g)) {
                    rb2y = -rb2y;
                }

                // 小球碰到左右两侧的墙，水平反弹
                if (hitLeftWall(g)) {
                    rb2x = -rb2x;
                } else if (hitRightWall(g)) {
                    rb2x = -rb2x;
                }
            }
        }
    }


    /**
     * Method: GET COLLiDINGOBJECT
     * ------------------------------
     * 检测小球当前撞到的砖块
     */
    GObject getCollidingObject() {
//        GObject obj1 = getElementAt(ball.getX() + BALL_RADIUS*0.4, ball.getY() + BALL_RADIUS*0.4); //球的8个定位点
//        GObject obj2 = getElementAt(ball.getX() + BALL_RADIUS * 1.6, ball.getY() +0.4);
//        GObject obj3 = getElementAt(ball.getX() + BALL_RADIUS*0.4, ball.getY() + BALL_RADIUS * 1.6);
//        GObject obj4 = getElementAt(ball.getX() + BALL_RADIUS * 1.6, ball.getY() + BALL_RADIUS * 1.6);
//        GObject obj5 = getElementAt(ball.getX() + BALL_RADIUS, ball.getY());
//        GObject obj6 = getElementAt(ball.getX(), ball.getY() + BALL_RADIUS);
//        GObject obj7 = getElementAt(ball.getX() + BALL_RADIUS*2, ball.getY() + BALL_RADIUS);
//        GObject obj8 = getElementAt(ball.getX() + BALL_RADIUS*2, ball.getY() + BALL_RADIUS * 2);
        GObject obj1 = getElementAt(ball.getX(), ball.getY()); //球的4个矩形定位点
        GObject obj2 = getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY());
        GObject obj3 = getElementAt(ball.getX(), ball.getY() + BALL_RADIUS * 2);
        GObject obj4 = getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY() + BALL_RADIUS * 2);
//        int a = 1;
//        GObject obj1 = getElementAt(ball.getX()-a, ball.getY()-a); //球的4个定位点
//        GObject obj2 = getElementAt(ball.getX() + BALL_RADIUS * 2+a, ball.getY()-a);
//        GObject obj3 = getElementAt(ball.getX()-a, ball.getY() + BALL_RADIUS * 2+a);
//        GObject obj4 = getElementAt(ball.getX() + BALL_RADIUS * 2+a, ball.getY() + BALL_RADIUS * 2+a);
        HashSet<GObject> list = new HashSet<>();
        Collections.addAll(list, obj1, obj3, obj2, obj4);
//        HashSet<GObject> list2 = new HashSet<>();
        for (GObject gobj : list) {
            if (gobj != null && gobj.getHeight() != 360) {
                return gobj;
            }
        }
        return null;
    }

    /**
     * Method: Hit Bottom Wall.
     * -----------------------
     * 判断小球是否击中了底部边界
     */
    boolean hitBottomWall(GObject g) {
        return g.getY() >= getHeight() - g.getHeight() * 0.8;
    }

    /**
     * Method: Hit Top Wall
     * -----------------------
     * 判断小球是否击中了顶部边界
     */
    boolean hitTopWall(GObject g) {
        return g.getY() <= 0;
    }

    /**
     * Method: Hit Right Wall
     * -----------------------
     * 判断小球是否击中了右侧边界
     */
    boolean hitRightWall(GObject g) {
        return g.getX() >= getWidth() - g.getWidth();
    }

    /**
     * Method: Hit Left Wall
     * -----------------------
     * 判断小球是否击中了左侧边界
     */
    boolean hitLeftWall(GObject g) {
        return g.getX() <= 0;
    }

    /**
     * Method: Make bal
     * -----------------------
     * 画出一个小球来
     */
    public void makeBall() {
        double size = BALL_RADIUS * 2;
        ball = new GOval(size, size);
        // 设置小球为实心
        ball.setFilled(true);

        // 填充颜色
        ball.setColor(BALL_COLOR);

        // 添加到画布上的位置
        add(ball, APPLICATION_WIDTH * 0.5 - BALL_RADIUS, 600);

    }

    public GOval makeRewardBall(GOval g, double x, double y) {
        RewardBallLive = 6;
        rbx = randomGenerator.nextChoice(-VELOCITY_X - 2, VELOCITY_X + 2);
        rby = randomGenerator.nextChoice(-VELOCITY_Y - 3, VELOCITY_Y + 3);
        rb2x = -rbx;
        rb2y = -VELOCITY_Y - 3;
        double size = BALL_RADIUS;
        g = new GOval(size, size);
        // 设置小球为实心
        g.setFilled(true);

        // 填充颜色
        g.setColor(randomGenerator.nextColor());

        add(g, x, y);
        return g;
    }

    public void makeBricks() {
        for (int i = 0; i < Brick_Row; i++) {
            for (int j = 0; j < Brick_Column; j++) {
                GRect brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
                brick.setFilled(true);
                brick.setColor(randomGenerator.nextColor());
                double x = j * (BRICK_MARGIN + BRICK_WIDTH) + BRICK_MARGIN + 3;
                double y = i * (BRICK_MARGIN + BRICK_HEIGHT) + BRICK_MARGIN - 2;
                add(brick, x, y);
            }
        }
    }

    public void makePaddle() {
        paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
        paddle.setFilled(true);
        paddle.setColor(ball.getColor());
        add(paddle, (APPLICATION_WIDTH - PADDLE_WIDTH) * 0.5, APPLICATION_HEIGHT - 50);
    }

    public void makeReward(double x, double y) {
        if (Reward == null) {
            Reward = new GRect(Reward_Width, Reward_Height);
            Reward.setFilled(true);
            Reward.setColor(randomGenerator.nextColor());
            add(Reward, x, y);
        }
    }

    public void mouseMoved(MouseEvent e) {  //挡板跟随鼠标移动
        paddle.setLocation(e.getX() - 0.5 * PADDLE_WIDTH, APPLICATION_HEIGHT - 50);
    }

}
