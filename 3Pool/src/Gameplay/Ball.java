package Gameplay;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Ball extends JComponent implements ActionListener, KeyListener, MouseListener, MouseMotionListener {

	double vx = 0;
	double vy = 0;
	double vx2 = 0;
	double vy2 = 0;
	double vx3 = 0;
	double vy3 = 0;
	double bm = 5, bm2 = 5, bm3 = 5;
	double powerx, powery;
	double mx;
	double my;
	double cushionCount2 = 0;
	double cushionCount1 = 0;
	double touched12 = 0;
	double touched13 = 0;
	double touched23 = 0;
	public boolean player1 = true;
	public boolean player2 = false;
	public boolean cueBall1 = true;
	public boolean cueBall2 = false;
	int player1score = 0;
	int player2score = 0;
	int winner = 0;
	double fx = 0;
	double fy = 0;
	boolean end = false;
	boolean cuevisibility = true;
	boolean shootallthetime = false; // cheatcode,
	int seconds = 0,minutes = 0;
	int pwrx =0;
	int pwry = 0;
	int max = 50;
	int p1high = 0;
	int p2high = 0;

	Ellipse2D.Double ball = new Ellipse2D.Double(225, 275, 25, 25);
	Ellipse2D.Double ball2 = new Ellipse2D.Double(475, 250, 25, 25);
	Ellipse2D.Double ball3 = new Ellipse2D.Double(475, 300, 25, 25);

	public JTextField p1 = new JTextField("player1");
	public JTextField p2 = new JTextField("player2");
	public JTextField pw = new JTextField("power");
	public JTextField pt = new JTextField("turn");
	public JTextField tm = new JTextField("timer");
	public JTextField ph1 = new JTextField("player1high");
	public JTextField ph2 = new JTextField("player2high");


	Image img3 = Toolkit.getDefaultToolkit().getImage("C:\\Users\\furka\\Desktop\\wallpaper.png");
	
	URL imageurl = getClass().getResource("/Background.png");
	Image img1 = Toolkit.getDefaultToolkit().getImage(imageurl);
	
	URL imageurl2 = getClass().getResource("/cue.png");
	private BufferedImage img2 = ImageIO.read(imageurl2);
	
	Timer t = new Timer(5, this);
	
	
	private final Date createdDate = new java.util.Date();
	
	boolean mousepressed = false;
	
	public void init() {

		
		p1.setBounds(915, 150, 100, 25);
		p2.setBounds(1045, 150, 100, 25);
		pw.setBounds(930, 250, 200, 25);
		pt.setBounds(930, 350, 200, 25);
		tm.setBounds(930, 450, 200, 25);
		ph1.setBounds(900, 90, 115, 25);
		ph2.setBounds(1045, 90, 115, 25);
		p1.setEditable(false);
		p2.setEditable(false);
		pt.setEditable(false);
		pw.setEditable(false);
		tm.setEditable(false);
		ph1.setEditable(false);
		ph2.setEditable(false);
		p1.setBackground(Color.CYAN);
		p2.setBackground(Color.CYAN);
		pt.setBackground(Color.CYAN);
		pw.setBackground(Color.CYAN);
		tm.setBackground(Color.CYAN);
		ph1.setBackground(Color.CYAN);
		ph2.setBackground(Color.CYAN);
		p1.setHorizontalAlignment(JTextField.CENTER);
		p2.setHorizontalAlignment(JTextField.CENTER);
		pt.setHorizontalAlignment(JTextField.CENTER);
		pw.setHorizontalAlignment(JTextField.CENTER);
		tm.setHorizontalAlignment(JTextField.CENTER);
		ph1.setHorizontalAlignment(JTextField.CENTER);
		ph2.setHorizontalAlignment(JTextField.CENTER);
		p1.setText("Player 1 : " + String.valueOf(player1score));
		p2.setText("Player 2 : " + String.valueOf(player2score));
		pw.setText(String.valueOf((powerx + powery) / 2));
		pt.setText("Player 1's Turn");
		tm.setText("Time : 0"); 
		ph1.setText("Player 1 Highest : " + String.valueOf(p1high));
		ph2.setText("Player 2 Highest : " + String.valueOf(p2high));

	}

	public void update() {

		p1.setText("Player 1 : " + String.valueOf(player1score));
		p2.setText("Player 2 : " + String.valueOf(player2score));
		ph1.setText("Player 1 Highest :  " + String.valueOf(p1high));
		ph2.setText("Player 2 Highest :  " + String.valueOf(p2high));
		
		/*
		 * if(player1) pt.setText("Player 1's Turn"); else
		 * pt.setText("Player 2's Turn");
		 */

	}

	public Ball() throws IOException {
		t.start();

	}

	public void winnerconditions() {
		if (player1score == 50) {
			winner = 1;
			JOptionPane.showMessageDialog(null, "Player 1 Wins!", "Endgame!", JOptionPane.INFORMATION_MESSAGE);
		} else if (player2score == 50){
			winner = 2;
			JOptionPane.showMessageDialog(null, "Player 2 Wins!", "Endgame!", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void rules() {
		if (cueBall1 == true) {
			if (cushionCount1 >= 3 && (touched12 > 0) && (touched13 > 0)) {
				player1score++;
				if(player1score > p1high)
					p1high = player1score;
			} else {
				cueBall1 = false;
				cueBall2 = true;
				player1 = false;
				player2 = true;
				player1score--;
				pt.setText("Player 2's Turn");
			}
		} else if (cueBall2 == true) {
			if (cushionCount2 >= 3 && (touched12 > 0) && (touched23 > 0)) {
				player2score++;
				if(player2score > p2high)
					p2high = player2score;
			} else {
				cueBall2 = false;
				cueBall1 = true;
				player2 = false;
				player1 = true;
				player2score--;
				pt.setText("Player 1's Turn");
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		java.util.Date now = new java.util.Date();      
		seconds = (int)((now.getTime() - this.createdDate.getTime()) / 1000);		
		tm.setText("Time : " + seconds/60 + ":" + Math.abs(((seconds/60)*60-seconds))); 
		
		
		ball.x += vx;
		ball.y += vy;
		ball2.x += vx2;
		ball2.y += vy2;
		ball3.x += vx3;
		ball3.y += vy3;
		vx *= 0.997;
		vx2 *= 0.997;
		vx3 *= 0.997;
		vy *= 0.997;
		vy2 *= 0.997;
		vy3 *= 0.997;

		if (ball.x < 75 || ball.x > 825 - ball.width)
			vx = -vx;
		cushionCount1++;

		if (ball.y < 105 || ball.y > 455 - ball.height)
			vy = -vy;
		cushionCount1++;

		if (ball2.x < 75 || ball2.x > 825 - ball2.width)
			vx2 = -vx2;
		cushionCount2++;

		if (ball2.y < 105 || ball2.y > 455 - ball2.height)
			vy2 = -vy2;
		cushionCount2++;

		if (ball3.x < 75 || ball3.x > 825 - ball3.width)
			vx3 = -vx3;

		if (ball3.y < 105 || ball3.y > 455 - ball3.height)
			vy3 = -vy3;

		double Ux12 = Math.abs(ball2.x - ball.x);
		double Uy12 = Math.abs(ball2.y - ball.y);
		double Ux13 = Math.abs(ball3.x - ball.x);
		double Uy13 = Math.abs(ball3.y - ball.y);
		double Ux23 = Math.abs(ball3.x - ball2.x);
		double Uy23 = Math.abs(ball3.y - ball2.y);

		double Umag12 = Math.hypot(Ux12, Uy12);
		double Umag13 = Math.hypot(Ux13, Uy13);
		double Umag23 = Math.hypot(Ux23, Uy23);

		if (Umag12 <= 25) {
            touched12++;


            double distance3 = Math.sqrt((Ux12 * Ux12) + (Uy12 * Uy12));
            double nx3 = Ux12 / distance3;
            double ny3 = Uy12 / distance3;
            double tx3 = -ny3;
            double dpTan5 = vx * tx3 + vy * nx3;
            double dpTan6 = vx2 * tx3 + vy2 * nx3;
            double dpNorm5 = vx * nx3 + vy * ny3;
            double dpNorm6 = vx2 * nx3 + vy2 * ny3;
            double m5 = (dpNorm5 * 0.2 + 1.4 * dpNorm6) / 1.8;
            double m6 = (dpNorm6 * -0.2 + 1.4 * dpNorm5) / 1.8;
            double overlap1 = 0.5 * (Umag12 - 25);
            ball.x -= overlap1 * (ball.x - ball2.x) / distance3;
            ball.y -= overlap1 * (ball.y - ball2.y) / distance3;
            ball2.x += overlap1 * (ball.x - ball2.x) / distance3;
            ball2.y += overlap1 * (ball.y - ball2.y) / distance3;

            vx = tx3 * dpTan5 + nx3 * m5;
            vy = nx3 * dpTan5 + ny3 * m5;
            vx2 = tx3 * dpTan6 + nx3 * m6;
            vy2 = nx3 * dpTan6 + ny3 * m6;


        }
else if (Umag13 <= 25) {

            touched13++;
            double distance = Math.sqrt((Ux13 * Ux13) + (Uy13 * Uy13));
            double nx = Ux13 / distance;
            double ny = Uy13 / distance;
            double tx = -ny;
            double dpTan1 = vx * tx + vy * nx;
            double dpTan2 = vx3 * tx + vy3 * nx;
            double dpNorm1 = vx * nx + vy * ny;
            double dpNorm2 = vx3 * nx + vy3 * ny;
            double m1 = (dpNorm1 * 0.2 + 1.4 * dpNorm2) / 1.8;
            double m2 = (dpNorm2 * -0.2 + 1.4 * dpNorm1) / 1.8;
            double overlap2 = 0.5 * (Umag13 - 25);
            ball.x -= overlap2 * (ball.x - ball3.x) / distance;
            ball.y -= overlap2 * (ball.y - ball3.y) / distance;
            ball3.x += overlap2 * (ball.x - ball3.x) / distance;
            ball3.y += overlap2 * (ball.y - ball3.y) / distance;

            vx = tx * dpTan1 + nx * m1;
            vy = nx * dpTan1 + ny * m1;
            vx3 = tx * dpTan2 + nx * m2;
            vy3 = nx * dpTan2 + ny * m2;

        }
else if (Umag23 <= 25) {

            touched23++;
            double distance2 = Math.sqrt((Ux23 * Ux23) + (Uy23 * Uy23));
            double nx2 = Ux23 / distance2;
            double ny2 = Uy23 / distance2;
            double tx2 = -ny2;
            double dpTan3 = vx2 * tx2 + vy2 * nx2;
            double dpTan4 = vx3 * tx2 + vy3 * nx2;
            double dpNorm3 = vx2 * nx2 + vy2 * ny2;
            double dpNorm4 = vx3 * nx2 + vy3 * ny2;
            double m3 = (dpNorm3 * 0.2 + 1.4 * dpNorm4) / 1.8;
            double m4 = (dpNorm4 * -0.2 + 1.4 * dpNorm3) / 1.8;
            double overlap3 = 0.5 * (Umag23 - 25);
            ball2.x -= overlap3 * (ball2.x - ball3.x) / distance2;
            ball2.y -= overlap3 * (ball2.y - ball3.y) / distance2;
            ball3.x += overlap3 * (ball2.x - ball3.x) / distance2;
            ball3.y += overlap3 * (ball2.y - ball3.y) / distance2;

            vx2 = tx2 * dpTan3 + nx2 * m3;
            vy2 = nx2 * dpTan3 + ny2 * m3;
            vx3 = tx2 * dpTan4 + nx2 * m4;
            vy3 = nx2 * dpTan4 + ny2 * m4;

        }

		if (vx == 0 && vy == 0 && vx2 == 0 && vy2 == 0 && vx3 == 0 && vy3 == 0 && end == true) {

			end = false;
			rules();
			cushionCount1 = 0;
			cushionCount2 = 0;
			touched13 = 0;
			touched12 = 0;
			touched23 = 0;
			update();
			cuevisibility = true;
			

		}

		if (Math.abs(vx) < 0.05)
			vx = 0;
		if (Math.abs(vx2) < 0.05)
			vx2 = 0;
		if (Math.abs(vx3) < 0.05)
			vx3 = 0;
		if (Math.abs(vy) < 0.05)
			vy = 0;
		if (Math.abs(vy2) < 0.05)
			vy2 = 0;
		if (Math.abs(vy3) < 0.05)
			vy3 = 0;

		
		repaint();
		winnerconditions();
		
		
		
	}

	public void poverup() {
		pwrx +=0.02;
		pwry +=0.02;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(img1, 50, 80, this);
		g2d.setColor(Color.WHITE);
		g2d.fill(ball);
		g2d.setColor(Color.YELLOW);
		g2d.fill(ball2);
		g2d.setColor(Color.RED);
		g2d.fill(ball3);
		if (cuevisibility) {
			if (player1) {
				AffineTransform trans = new AffineTransform();
				double angle = (Math.atan2(mx - ball.x, ball.y - my) + Math.PI / 2);
				trans.translate(ball.x + 13 + 12 * Math.cos(angle), ball.y + 15 + 12 * Math.sin(angle));
				trans.rotate(angle);
				AffineTransformOp op = new AffineTransformOp(trans, AffineTransformOp.TYPE_BILINEAR);
				g.drawImage(op.filter(img2, null), 0, 0, this);
			} else {
				AffineTransform trans = new AffineTransform();
				double angle = Math.atan2(mx - ball2.x, ball2.y - my) + Math.PI / 2;
				trans.translate(ball2.x + 13 + 12 * Math.cos(angle), ball2.y + 15 + 12 * Math.sin(angle));
				trans.rotate(angle);
				AffineTransformOp op = new AffineTransformOp(trans, AffineTransformOp.TYPE_BILINEAR);
				g.drawImage(op.filter(img2, null), 0, 5, this);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

		if (e.getKeyChar() == KeyEvent.VK_SPACE) {
			fx += (mx - ball.x) / 150.0d;
			fy += (my - ball.y) / 150.0d;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			shootallthetime = true;
			pw.setText("Cheat Code Activated!");
		}

	}

	public void keyPressed(KeyEvent e) {	
		if (e.getKeyCode() == KeyEvent.VK_F1) {
			shootallthetime = true;
			System.out.println("activated");
			vx=5;
		}
		// CHEAT CODE HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (e.getKeyChar() == KeyEvent.VK_SPACE) {
			vx += fx;
			vy += fy;
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (cueBall1 == true) {
			if (e.getButton() == MouseEvent.BUTTON1 && cuevisibility == true
					|| e.getButton() == MouseEvent.BUTTON1 && shootallthetime == true) {
				mx = getMousePosition().x;
				my = getMousePosition().y;
				powerx = (mx - ball.x) / max;
				powery = (my - ball.y) / max;
				vx = powerx;
				vy = powery;
				end = true;
				pw.setText(String.valueOf((vx + vy) / 2));
				cuevisibility = false;
				mousepressed = false;
				
			}
		} else if (e.getButton() == MouseEvent.BUTTON1 && cuevisibility == true
				|| e.getButton() == MouseEvent.BUTTON1 && shootallthetime == true) {
			mx = getMousePosition().x;
			my = getMousePosition().y;
			powerx = (mx - ball2.x) / max;
			powery = (my - ball2.y) / max;
			vx2 = powerx;
			vy2 = powery;
			end = true;
			pw.setText(String.valueOf((powerx + powery) / 2));
			cuevisibility = false;
		}
		if(e.getButton() == MouseEvent.BUTTON3) {
			shootallthetime = true;
		}else if (e.getButton() == MouseEvent.BUTTON2) {
			max = 1;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

		mx = getMousePosition().x;
		my = getMousePosition().y;

	}
}