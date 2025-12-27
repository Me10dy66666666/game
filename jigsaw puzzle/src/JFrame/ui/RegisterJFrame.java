package JFrame.ui;

import UserClass.User;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class RegisterJFrame extends JFrame implements ActionListener,MouseListener {
    //成员变量方便其他方法使用
    public JButton registerButton    = new JButton(new ImageIcon("image\\register\\注册按钮.png"));
    public JButton fleshButton    = new JButton(new ImageIcon("image\\register\\重置按钮.png"));
    //显示密码框
    private JCheckBox showPassword = new JCheckBox(new ImageIcon("image\\login\\显示密码.png"));
    // 密码输入框
    private JPasswordField passwordText = new JPasswordField();
    private JTextField passwordJTextField;

    //再次输入密码的输入框
    private JPasswordField passwordTextAgain = new JPasswordField();
    // 用户名输入框
    JTextField usernameText = new JTextField();
    //注册成功标志位
    public boolean successRegister = false;

    public RegisterJFrame() {
        this.setSize(488,430);
        initRegisterJTextField();
        initRegisterJFrame();
        this.setVisible(true);
    }

    public void initRegisterJTextField() {
        // 用户名标签
        JLabel usernameLabel = new JLabel(new ImageIcon("image\\register\\注册用户名.png"));
        usernameLabel.setBounds(60, 140, 80, 30);
        this.getContentPane().add(usernameLabel);

        // 用户名输入框
        usernameText.setBounds(168, 140, 170, 30);
        this.getContentPane().add(usernameText);

        // 密码标签
        JLabel passwordLabel = new JLabel(new ImageIcon("image\\register\\注册密码.png"));
        passwordLabel.setBounds(60, 195, 80, 30);
        this.getContentPane().add(passwordLabel);

        // 密码输入框
        passwordText.setBounds(168, 195, 170, 30);
        this.getContentPane().add(passwordText);

        //显示密码按钮
        showPassword.setBounds(350, 195, 20, 29);
        this.getContentPane().add(showPassword);

        //再次输入密码的标签
        JLabel passwordLabelAgain = new JLabel(new ImageIcon("image\\register\\再次输入密码.png"));
        passwordLabelAgain.setBounds(40, 250, 120, 30);
        this.getContentPane().add(passwordLabelAgain);

        //再次输入密码的输入框
        passwordTextAgain.setBounds(168, 250, 170, 30);
        this.getContentPane().add(passwordTextAgain);

        //为显示密码添加事件监听
        showPassword.addActionListener( this);

    }

    private void initRegisterJFrame() {
        //创建注册界面的时候同时给这个界面设置一些属性（初始化）
        this.setSize(488,430);

        //创建JButton存放注册按钮和注册按下；创建JButton存放重置按钮和重置按下


        //添加按钮
        this.getContentPane().add(registerButton);
        this.getContentPane().add(fleshButton);


        //设置位置
        registerButton.setBounds(80,320,116,23);
        fleshButton.setBounds(280,320,116,23);

        //设置界面居中
        this.setLocationRelativeTo(null);
        //取消默认的居中方法，这样才能使得图片按照自己定义的坐标来摆放
        this.setLayout(null);

        //为按钮添加事件监听事件
        registerButton.addMouseListener( this);
        fleshButton.addMouseListener( this);

        JLabel registerBackground = new JLabel(new ImageIcon("image\\register\\background.png"));
        registerBackground.setBounds(0,0,488,430);
        this.getContentPane().add(registerBackground);

        this.setVisible(true);
    }

    //注册逻辑
    public boolean registerMethod() {
        if(usernameText.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"用户名不能为空");
        } else if(passwordText.getPassword().length==0||passwordTextAgain.getPassword().length==0){
            JOptionPane.showMessageDialog(this,"密码不能为空");
        }else{
            if(new String(passwordText.getPassword()).equals(new String(passwordTextAgain.getPassword()))){
                User user=new User(usernameText.getText(),new String(passwordText.getPassword()));
                File userFile = new File("UserData/UserInfo/userInfo.txt");
                if(!userFile.exists()){
                    System.out.println("查找文件路径: " + userFile.getAbsolutePath());
                    JOptionPane.showMessageDialog(this, "用户数据文件不存在，请检查路径");
                    return false;
                }
                FileReader fr = new FileReader(userFile);
                FileWriter fw = new FileWriter(userFile);
                for(String line:fr.readLines()){
                    if(line.contains(user.getName())){
                        JOptionPane.showMessageDialog(this, "用户名已存在");
                        return false;
                    }else{
                        fw.append("username="+user.getName()+"&"+"password="+user.getPassword()+"&count=0"+"\n");
                        JOptionPane.showMessageDialog(this, "注册成功");
                        this.dispose();
                        return true;
                    }
                }
            }else{
                JOptionPane.showMessageDialog(this,"输入的密码不一致");
                return false;
            }
        }



         return false;
    }

    //重置逻辑
    public void resetMethod(){
        usernameText.setText("");
        passwordText.setText("");
        passwordTextAgain.setText("");
    }
    //重写actionListener方法
    @Override
    public void actionPerformed(ActionEvent e) {
        {
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
        //注册按钮
        if(obj==registerButton){
        successRegister = registerMethod();
        if(successRegister){
            this.dispose();
            new LoginJFrame();
            }
        }
        //重置按钮
        if(obj==fleshButton){
            resetMethod();

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object obj=e.getSource();
        //注册按钮
        if(obj==registerButton){
            registerButton.setIcon(new ImageIcon("image\\register\\注册按下.png"));
        }
        //重置按钮
        if(obj==fleshButton){
            fleshButton.setIcon(new ImageIcon("image\\register\\重置按下.png"));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Object obj=e.getSource();
        //注册按钮
        if(obj==registerButton) {
            registerButton.setIcon(new ImageIcon("image\\register\\注册按钮.png"));
        }
        //重置按钮
        if(obj==fleshButton) {
            fleshButton.setIcon(new ImageIcon("image\\register\\重置按钮.png"));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}