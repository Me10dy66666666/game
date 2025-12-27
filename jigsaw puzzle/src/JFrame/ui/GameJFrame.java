package JFrame.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import static javax.swing.border.BevelBorder.LOWERED;

//游戏主界面
public class GameJFrame extends JFrame implements KeyListener , ActionListener{

    //定义x,y来确定编号0图片的位置
    public int x=0;
    public int y=0;

    //定义一个统一的路径变量
    public String path="image";
    //定义一个二维字符串数组保存不同图片类型的文件名来初始化路径
    public String[] pathArr=new String[]{"animal","girl","sport"};
    //定义一个整数随机图片类型的编号
    public int randFileNum=0;


    //定义正确的图片编号数组来判断胜利
    public int[][] win=new int[][]{
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,0}
    };

    //计步器，统计操作次数
    public int step=0;

    //用来管理数据，加载图片的时候根据二维数组的数据来加载图片
    public  int [][]arr001=new int [4][4];

    //创建选项下面的条目对象(方便其他方法的调用）
    JMenuItem replayItem = new JMenuItem("重新游戏");
    JMenuItem reLoginItem = new JMenuItem("重新重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");
    JMenuItem accountItem = new JMenuItem("联系方式");
    JMenuItem animalItem = new JMenuItem("动物");
    JMenuItem girlItem = new JMenuItem("美女");
    JMenuItem sportItem = new JMenuItem("运动");
    public GameJFrame() {
        //初始化图片路径
        initPath();
        //初始化界面
        initJFrame();
        //初始化菜单
        initMenu();

        //初始化数据
        initData();
        //初始化图片（根据打乱之后的结果加载图片
        initImage();

        //设置界面可视化
        this.setVisible(true);
    }




    //方法化
//初始化图片路径
    private void initPath() {
        //初始化图片路径
        int randFileName=new Random().nextInt(3);
        if(randFileName==0){
            randFileNum=new Random().nextInt(1,9);
            path+="\\"+pathArr[randFileName]+"\\"+pathArr[randFileName]+randFileNum;
        }
        else if(randFileName==1){
            randFileNum=new Random().nextInt(1,14);
            path+="\\"+pathArr[randFileName]+"\\"+pathArr[randFileName]+randFileNum;
        }else{
            randFileNum=new Random().nextInt(1,11);
            path+="\\"+pathArr[randFileName]+"\\"+pathArr[randFileName]+randFileNum;
        }

    }




    private void initData() {


        int[] arr={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        Random rand=new Random();

        //洗牌算法
        for(int i= arr.length-1;i>0;i--){
            int index=rand.nextInt(i+1);
            int  temp=arr[index];
            arr[index]=arr[i];
            arr[i]=temp;
        }



        for(int i=0;i< arr.length;i++){
            //记录编号为0的图片的位置
            if(arr[i]==0){
                x=i/4;
                y=i%4;
            }
            arr001[i/4][i%4]=arr[i];
        }



    }



    private void initImage() {

        //为了达成图片移动的效果，清除之前的图片再在后面移动的事件中加载
        this.getContentPane().removeAll();

        //判断是否胜利，有没有继续初始化的必要
        if(isWin()){
            //显示胜利的图标
            JLabel winLabel=new JLabel(new ImageIcon("image\\win.png"));
            winLabel.setBounds(203, 283, 197, 73);
            this.getContentPane().add(winLabel);
        }

        //统计步数
        JLabel stepLabel=new JLabel("步数："+step);
        stepLabel.setBounds(50, 30, 100, 20);
        this.getContentPane().add(stepLabel);


        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
                int number=arr001[i][j];
                //创建一个JLabel对象
                JLabel imageLabel=new JLabel(new ImageIcon(path+"\\"+number+".jpg"));//创建一个ImageIcon对象

                //指定图片位置(以隐藏容器的左上角为原点，横向x轴，纵向y轴。以图片的左上角为坐标点进行设置位置
                imageLabel.setBounds(105*j+83, 105*i+134, 105, 105);
                //给图片添加边框
                imageLabel.setBorder(new BevelBorder(LOWERED));
                //把管理容器添加到界面当中
                //this.add(imageLabel);
                this.getContentPane().add(imageLabel);


            }
        }
        //添加背景图片
        JLabel background=new JLabel(new ImageIcon("image\\background.png"));
        background.setBounds(40,40,508,560);
        //把图片添加进去
        this.getContentPane().add(background);

        //刷新界面
        this.getContentPane().repaint();


    }

    private void initMenu() {
        //初始化菜单
        //创建陈哥菜单对象
        JMenuBar jMenuBar = new JMenuBar();

        //创建菜单上面的两个选项对象（功能  关于我们）
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJmenu = new JMenu("关于我们");

        JMenu changeJMenu = new JMenu("更换图片");

        //将每一个选项下面的条目添加到选项当中
        functionJMenu.add(changeJMenu);//创建图片更换的子菜单
        changeJMenu.add(animalItem);
        changeJMenu.add(girlItem);
        changeJMenu.add(sportItem);

        functionJMenu.add(replayItem);
        functionJMenu.add(reLoginItem);
        functionJMenu.add(closeItem);
        aboutJmenu.add(accountItem);

        //给条目绑定事件
        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);
        animalItem.addActionListener(this);
        girlItem.addActionListener(this);
        sportItem.addActionListener(this);

        //将菜单的两个选项添加到菜单当中
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJmenu);

        //给整个界面设置菜单
        this.setJMenuBar(jMenuBar);

    }

    private void initJFrame() {
        //设置界面的宽高
        this.setSize(603,680);
        //设置界面的标题
        this.setTitle("拼图单机版v1.0");
        //设置界面置顶
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置游戏的关门模式
       this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //取消默认的居中方法，这样才能使得图片按照自己定义的坐标来摆放
        this.setLayout(null);

        //给整个界面添加键盘监听事件
        this.addKeyListener(this);
    }

    //用于判断当前图片结果与最终能胜利结果是否相同
    public boolean isWin(){
        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
                //只要有一个不一样就返回false
                if(arr001[i][j]!=win[i][j]){
                    return false;
                }
            }
        }
        return true;
    }


    //重写keyListener中的代码
    @Override
    public void keyTyped(KeyEvent e) {

    }

    //按下不松
    @Override
    public void keyPressed(KeyEvent e) {
        int code=e.getKeyCode();

        if(code==65){
            //把界面中所有的元素全部删除
            this.getContentPane().removeAll();
            //加载一张完整的图片
            JLabel All=new JLabel(new ImageIcon(path+"/all.jpg"));
            //设置图片位置
            All.setBounds(83,134,420,420);
            this.getContentPane().add(All);
            //添加背景图片
            JLabel background=new JLabel(new ImageIcon("image/background.png"));
            background.setBounds(40,40,508,560);
            //将背景图片添加到界面当中
            this.getContentPane().add(background);
            //刷新界面
            this.getContentPane().repaint();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //判断游戏是否胜利。如果胜利就不能再继续玩了而应该重开
        if(isWin()){
            return;
        }

        int code=e.getKeyCode();
        switch(code){
            case 37->{
                System.out.println("向左移动");
                if(y==3) {
                    return;
                }
                arr001[x][y]=arr001[x][y+1];
                arr001[x][y+1]=0;
                y++;
                step++;
                initImage();
            }
            case 38->{
                System.out.println("向上移动");
                //解决越界的系统异常
                if(x==3) {
                    return;
                }
                //空白方块下面的方块向上移动，x+1表示空白方块下方的方块
                //把空白方块下面的数字赋值给空白方块（做一个交换）
                arr001[x][y]=arr001[x+1][y];
                arr001[x+1][y]=0;
                //空白方块原本的位置发生改变
                x++;
                //每移动一次就要统计一次，下面的初始化操作会把数据进行更新
                step++;
                //再通过初始化图像的方法加载图片
                initImage();


            }
            case 39->{
                System.out.println("向右移动");
                if(y==0) {
                    return;
                }
                arr001[x][y]=arr001[x][y-1];
                arr001[x][y-1]=0;
                y--;
                step++;
                initImage();
            }
            case 40->{
                System.out.println("向下移动");
                if(x==0) {
                    return;
                }
                arr001[x][y]=arr001[x-1][y];
                arr001[x-1][y]=0;
                x--;
                step++;
                initImage();
            }
            case 65->{
                initImage();
            }
            //一键通关
            case 87->{
                arr001=new int [][] {
                        {1,2,3,4},
                        {5,6,7,8},
                        {9,10,11,12},
                        {13,14,15,0}
                };
                initImage();
            }
        }
    }

    //重写ActionListener中的方法
    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj=e.getSource();
        if(obj==replayItem){
            //重新开始游戏
            step=0;
            initData();
            initImage();

        }else if(obj==reLoginItem){
            //重新登录
            //关闭当前的游戏界面
            this.setVisible(false);
            //打开的登陆界面
            new LoginJFrame();
        }else if(obj==closeItem){
            //关闭游戏(虚拟机)
            System.exit(0);
        }else if(obj==accountItem){
            //联系作者(公众号）
            JDialog jDialog=new JDialog();
            //创建一个管理图片的容器对象
            JLabel jLabel=new JLabel(new ImageIcon("image/about.png"));
            //设置图片位置
            jLabel.setBounds(0,0,258,258);
            //把图片添加到对话框中
            jDialog.add(jLabel);
            //设置对话框的大小
            jDialog.setSize(344,344);
            //弹框置顶
            jDialog.setAlwaysOnTop(true);
            //设置对话框居中
            jDialog.setLocationRelativeTo(null);
            //弹框弹框不关闭无法操作下面的内容
            jDialog.setModal(true);
            //显示弹框
            jDialog.setVisible(true);
        } else if (obj==animalItem) {
            randFileNum=(int)(Math.random()*8+1);
            path="image/animal/animal"+randFileNum;
            initData();
            initImage();


        } else if (obj==girlItem) {
            randFileNum=(int)(Math.random()*13+1);
            path="image/girl/girl"+randFileNum;
            initData();
            initImage();

        } else if (obj==sportItem) {
            randFileNum=(int)(Math.random()*10+1);
            path="image/sport/sport"+randFileNum;
            initData();
            initImage();
        }
    }
}
