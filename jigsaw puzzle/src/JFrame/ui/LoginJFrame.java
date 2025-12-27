package JFrame.ui;

import UserClass.User;
import cn.hutool.core.io.file.FileReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;

//登陆界面
public class LoginJFrame extends JFrame implements ActionListener,MouseListener {

    //成员变量方便其他方法使用
    public JButton loginButton    = new JButton(new ImageIcon("image\\login\\登录按钮.png"));
    public JButton registerButton    = new JButton(new ImageIcon("image\\login\\注册按钮.png"));
    //显示密码框
    private JCheckBox showPassword = new JCheckBox(new ImageIcon("image\\login\\显示密码.png"));
    // 密码输入框
    private JPasswordField passwordText = new JPasswordField();
    private JTextField passwordJTextField;

    //验证码内容文本框
    JLabel checkCode = new JLabel();
    //验证码输入框
    JTextField checkCodeText = new JTextField();
    // 用户名输入框
    JTextField usernameText = new JTextField();
    //登录成功标志位
    public boolean successLogin=false;
    public LoginJFrame() {
        initLoginJTextField();
        initLoginJFrame();

    }
//方法化

//登录逻辑
    public boolean loginMethod(){
        if(usernameText.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"用户名不能为空");
        } else if(passwordText.getPassword().length==0){
            JOptionPane.showMessageDialog(this,"密码不能为空");
        }
        // 比较验证码是否正确
        else if(checkCodeText.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "验证码不能为空");
        } else if(!CodeUtil.confirmCode(checkCodeText.getText(), checkCode.getText())) {
            System.out.println("验证码错误");
            JOptionPane.showMessageDialog(this, "验证码错误");
            //更新验证码
            String code = CodeUtil.getCode();
            checkCode.setText(code);
        } else {
            //如果用户名并不保存在本地输出用户名不存在
            if(!usernameText.getText().isEmpty()){

                File userFile = new File("UserData/UserInfo/userInfo.txt");
                if (!userFile.exists()) {
                    // 调试信息：打印实际查找的路径
                    System.out.println("查找文件路径: " + userFile.getAbsolutePath());
                    JOptionPane.showMessageDialog(this, "用户数据文件不存在，请检查路径");
                    return false;
                }
                FileReader fr = new FileReader(userFile);

                if(fr.readLines()==null){
                    JOptionPane.showMessageDialog(this,"用户名不存在");
                }
                else{//如果用户名存在比较密码是否正确,正确则进入游戏并关闭登录窗口，否则用户名不存在请注册
                    for(String line:fr.readLines()){
                        String userName=line.split("&")[0].split("=")[1];
                        String passWord=line.split("&")[1].split("=")[1];
                     if(userName.equals(usernameText.getText())){
                         if(passWord.equals(new String(passwordText.getPassword()))){

                             this.dispose();//关闭当前类的实例对象
                             return true;


                         }else{
                             JOptionPane.showMessageDialog(this,"密码错误");
                             break;
                       }
                     }
                    }
                    JOptionPane.showMessageDialog(this,"用户名不存在");
                }
            }
        }




        return false;
    }

    //设置登录输入的文本框方法
    public void initLoginJTextField() {
        // 用户名标签
        JLabel usernameLabel = new JLabel(new ImageIcon("image\\login\\用户名.png"));
        usernameLabel.setBounds(100, 140, 60, 30);
        this.getContentPane().add(usernameLabel);

        // 用户名输入框
        usernameText.setBounds(168, 140, 170, 30);
        this.getContentPane().add(usernameText);

        // 密码标签
        JLabel passwordLabel = new JLabel(new ImageIcon("image\\login\\密码.png"));
        passwordLabel.setBounds(100, 195, 60, 30);
        this.getContentPane().add(passwordLabel);

        // 密码输入框
        passwordText.setBounds(168, 195, 170, 30);
        this.getContentPane().add(passwordText);

        //显示密码按钮
        showPassword.setBounds(350, 195, 20, 29);
        this.getContentPane().add(showPassword);
        //为显示密码添加事件监听
        showPassword.addActionListener( this);

        //验证码标签
        JLabel checkCodeLabel = new JLabel(new ImageIcon("image\\login\\验证码.png"));
        checkCodeLabel.setBounds(100, 250, 60, 30);
        this.getContentPane().add(checkCodeLabel);

        //验证码输入框
        checkCodeText.setBounds(168, 250, 85, 30);
        this.getContentPane().add(checkCodeText);

        //设置验证码的随机
        String code = CodeUtil.getCode();

        checkCode.setBounds(260, 250, 40, 30);
        checkCode.setText( code);
        checkCode.addMouseListener( this);
        this.getContentPane().add(checkCode);

    }

    //初始化登陆界面
    private void initLoginJFrame() {

        //创建登陆界面的时候同时给这个界面设置一些属性（初始化）
        this.setSize(488,430);

        //添加按钮
        this.getContentPane().add(loginButton);
        this.getContentPane().add(registerButton);

        //设置位置
        loginButton.setBounds(80,320,116,23);
        registerButton.setBounds(280,320,116,23);

        //设置界面居中
        this.setLocationRelativeTo(null);
        //取消默认的居中方法，这样才能使得图片按照自己定义的坐标来摆放
        this.setLayout(null);



        //为按钮添加事件监听事件
        loginButton.addMouseListener( this);
        registerButton.addMouseListener( this);

        JLabel registerBackground = new JLabel(new ImageIcon("image\\login\\background.png"));
        registerBackground.setBounds(0,0,488,430);
        this.getContentPane().add(registerBackground);

        this.setVisible(true);
    }

    //重写actionListener方法
    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj=e.getSource();
        if(obj==showPassword){
            if(showPassword.isSelected()){
                //设置密码可见 //将JPasswordField替换为JTextField
                String password=new String(passwordText.getPassword());
                getContentPane().remove(passwordText);
                passwordJTextField = new JTextField(password);
                passwordJTextField.setBounds(168, 195, 170, 30);
                this.getContentPane().add(passwordJTextField);



            }else{
                //设置密码不可见
                getContentPane().remove(passwordJTextField);
                passwordText = new JPasswordField(passwordJTextField.getText());
                passwordText.setBounds(168, 195, 170, 30);
                this.getContentPane().add(passwordText);
            }
        }

    }

    //重写鼠标监听方法
    @Override
    public void mouseClicked(MouseEvent e) {

        Object obj=e.getSource();
        //登录按钮
        if(obj==loginButton){
           successLogin= loginMethod();
           System.out.println(successLogin);
            if(successLogin){
                //System.out.println(successLogin);
                new GameJFrame();
            }
        }
        //注册按钮
        if(obj==registerButton){
            this.dispose();
            new RegisterJFrame();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object obj=e.getSource();
        //登录按钮
        if(obj==loginButton){
            loginButton.setIcon(new ImageIcon("image\\login\\登录按下.png"));
        }
        //注册按钮
        if(obj==registerButton){
            registerButton.setIcon(new ImageIcon("image\\login\\注册按下.png"));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Object obj=e.getSource();
        //登录按钮
        if(obj==loginButton) {
            loginButton.setIcon(new ImageIcon("image\\login\\登录按钮.png"));
        }
        //注册按钮
        if(obj==registerButton) {
            registerButton.setIcon(new ImageIcon("image\\login\\注册按钮.png"));
        }

        //验证码区域
        if(obj==checkCode){
            String code = CodeUtil.getCode();
            checkCode.setText( code);
        }


    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

