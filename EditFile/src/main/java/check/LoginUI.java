package check;

import com.zhkj.exe.EditStart;
import com.zhkj.ui.UITools;

import java.awt.Color;
import java.awt.Container;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * @author: FYL
 * @time: 2019/1/11
 * @email:347933430@qq.com
 * @describe: check
 */
public class LoginUI extends JFrame{
    static JFrame f = null;
    //界面运行方法
    public static void run(final int width,final int height){
        if(f == null)f = new LoginUI(width, height);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
//                Log.appendErr("开始绘制界面...");
                Toolkit kit=Toolkit.getDefaultToolkit();
                f.setTitle("验证登录");
                int w = (kit.getScreenSize().width - width) / 2;
                int h = (kit.getScreenSize().height - height) / 2;
                f.setLocation(w, h);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setSize(width, height);
                f.setVisible(true);
                f.setResizable(false);
            }
        });
    }
    JButton getVcode;
    public LoginUI(int width, int height){
        //设置整体布局模式(这里使用的绝对定位)
        setLayout(null);
        //设置一个容器
        final Container container=getContentPane();
        //号码输入框
        final JTextField phone = new JTextField("输入手机号码",20);
        //x,y,w,h  左上角坐标，宽，高
        phone.setBounds(20,20,250,35);
        //限制只能输入数字
        phone.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){
                }else{
                    e.consume(); //关键，屏蔽掉非法输入
                }
            }
        });
        //设置默认提示
        phone.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                //获取焦点时，清空提示内容
                String temp = phone.getText();
                if(temp.equals("输入手机号码")) {
                    phone.setText("");
                    phone.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent focusEvent) {
                 //失去焦点时，没有输入内容，显示提示内容
                String tmp = phone.getText();
                if(tmp.equals("")) {
                    phone.setForeground(Color.GRAY);
                    phone.setText("输入手机号码");
                }else{
                    phone.setForeground(Color.BLACK);
                }
            }
        });
        add(phone);//添加手机输入框

        //验证码输入框
        final JTextField vcode = new JTextField(20);
        //x,y,w,h  左上角坐标，宽，高
        vcode.setBounds(20,70,130,35);
        vcode.setFocusable(true);
        add(vcode);//添加验证码输入框

        //获取验证码按钮
        getVcode = new JButton("获取验证码");
        getVcode.setBounds(170, 70, 100, 35);
        getVcode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //点击获取验证码
                if(phone.getText().equals("")||phone.equals("输入手机号码")){
                    return;
                }else{
                    if(isPhone(phone.getText())){
                        //请求网络，发送验证码，倒计时
                        new CountingThread().start();
                        getVcode.setEnabled(false);
                    }else{
                        phone.setText("请输入正确的手机号码！");
                    }
                }
            }
        });
        add(getVcode);//添加验证码按钮

        //验证按钮
        final JButton yaz = new JButton("验证");
        yaz.setBounds(135, 160, 130, 50);
        yaz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //判断验证码是否正确
                String vcodes = vcode.getText();
                if(vcodes==null||vcodes.equals("")){return;}
                //点击验证
                dispose();
//                LoginUI.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
                UITools.run(600, 800);
            }
        });
        add(yaz);//验证按钮


    }

    class CountingThread extends Thread{
        int time = 60;
        @Override
        public void run() {
            while (time>=0){
                if(time==0){
                    getVcode.setText("重发");
                    getVcode.setEnabled(true);
                    return;
                }
                getVcode.setText(time+"秒");
                time--;
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 验证是否是手机
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            if(m.find()){
                return true;
            }
            return false;
        }
    }


}
