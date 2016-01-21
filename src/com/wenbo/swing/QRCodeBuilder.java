package com.wenbo.swing;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

import com.wenbo.util.QRCodeUtil;
import com.wenbo.util.TextUtils;
/**
 * 二维码生成器
 * @Description 
 * @author <a href="http://my.oschina.net/chenbo">chenbo</a>
 * @date 2016年1月20日 下午5:16:53
 * @version V1.0
 */
public class QRCodeBuilder extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2995703556410990463L;
	private JPanel contentPane;
	private JLabel codeimage;
	private JTextArea textArea;
	private JLabel warnmsg;
	private JLabel logo_prew;
	private JLabel lbllogol;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QRCodeBuilder frame = new QRCodeBuilder();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public QRCodeBuilder() {
		setTitle("二维码生成器");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//
		setBounds(100, 100, 586, 492);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setLayout(null);//不设置布局方式
		setContentPane(contentPane);
		
		JLabel label = new JLabel("二维码内容:");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label.setBounds(28, 26, 73, 15);
		contentPane.add(label);
		
		JButton button = new JButton("生成二维码");
		button.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		button.setBounds(311, 95, 121, 23);
		button.addActionListener(this);
		button.setActionCommand("build");
		contentPane.add(button);
		
	     textArea = new JTextArea();
	     textArea.setWrapStyleWord(true);//设置断行不断字
	     textArea.setLineWrap(true);//设置自动换行
	     textArea.setRows(6);
		textArea.setToolTipText("输入网址,数字,或者文字");
		textArea.setBounds(111, 10, 419, 64);
		contentPane.add(textArea);
		
		 codeimage = new JLabel("");
		codeimage.setBounds(111, 144, 300, 300);
	
		contentPane.add(codeimage);
		
		warnmsg = new JLabel("");
		warnmsg.setForeground(Color.RED);
		warnmsg.setBounds(28, 119, 502, 15);
		contentPane.add(warnmsg);
		
		logo_prew = new JLabel("");
		logo_prew.setIcon(new ImageIcon(QRCodeBuilder.class.getResource("/res/intel_logo.png")));
		logo_prew.setBounds(10, 179, 59, 54);
		contentPane.add(logo_prew);
		
		lbllogol = new JLabel("默认logo");
		lbllogol.setBounds(10, 243, 59, 15);
		contentPane.add(lbllogol);
		
		JButton choicelogo = new JButton("选择logo");
		choicelogo.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		choicelogo.setBounds(170, 95, 93, 23);
		choicelogo.addActionListener(this);
		choicelogo.setActionCommand("file");
		contentPane.add(choicelogo);
	}
	
	private void showQRCode(String content){
		BufferedImage bufferimage;
		try {
			bufferimage = QRCodeUtil.createQRCode(content,null,true);
			ImageIcon icon = new ImageIcon(bufferimage);
			codeimage.setIcon(icon);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(command.equals("build")){
			String content = textArea.getText();
			if(TextUtils.isEmpty(content)){
				warnmsg.setText("请输入内容~");
				return;
			}else{
				warnmsg.setText("");
				showQRCode(content);
			}
		}else if(command.equals("file")){
			//打开文件选择器
			JFileChooser jfc=new JFileChooser();  
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setFileFilter(new ImageFileFilter());
			jfc.showDialog(new JLabel(), "选择");  
			File file=jfc.getSelectedFile();  
			if(file==null)
				return;
			if(file.isFile()){
				warnmsg.setText("选择文件: "+file.getAbsolutePath());
				ImageIcon icon = new ImageIcon(file.getAbsolutePath());
				logo_prew.setIcon(icon);
			}
		}
		
	}
	/**
	 * 
	 * @Description 
	 * @author <a href="http://my.oschina.net/chenbo">chenbo</a>
	 * @date 2016年1月21日 下午4:38:37
	 * @version V1.0
	 */
	class ImageFileFilter extends FileFilter{

		@Override
		public boolean accept(File file) {
			if(file.isDirectory()){
				return true;
			}
			if(file.getName().endsWith("jpg")||file.getName().endsWith("jpeg")||file.getName().endsWith("gif")||file.getName().endsWith("png")){
				return true;
			}
			return false;
		}

		@Override
		public String getDescription() {
			
			return "图片文件(*.jpg, *.jpeg, *.gif, *.png)";
		}
		
	}
}
