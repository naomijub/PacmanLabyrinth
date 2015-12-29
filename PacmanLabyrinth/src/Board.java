import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.Random;

public class Board extends JPanel implements ActionListener{

	//board
		private Dimension d;
		private final Font font = new Font("Arial", Font.BOLD, 20);
		private final Font fontSmall = new Font("Times New Roman", Font.BOLD, 16);
		private final Color dotColor = new Color(192, 192, 0);
		private final Color winColor = new Color(20, 192, 0);
		private Color backColor;
		private Image img;
		
		//logic
		private boolean gameOn = false;
		private boolean dying = false;
		
		//const
		private final int blockSize = 24;
	    private final int nroBlocks = 15;
	    private final int scrSize = nroBlocks * blockSize;
	    private final int pacDelay = 2;
	    private final int pacCount = 4;
	    private final int maxGhosts =5;
	    private final int pacSpeed = 6;
	    
	    //pacman
	    private int pacanimcount = pacDelay;
	    private int pacDir = 1;
	    private int pacPos = 0;
	    private int nroGhosts = 1;
	    private int lifeLeft, score;
	    private int[] dx, dy;
	    private int[] ghostX, ghostY, ghostDx, ghostDy, ghostSpeed;
	    
	    //Images
	    private Image ghost;
	    private Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
	    private Image pacman3up, pacman3down, pacman3left, pacman3right;
	    private Image pacman4up, pacman4down, pacman4left, pacman4right;
	    
	    private int pacmanx, pacmany, pacmandx, pacmandy;
	    private int reqdx, reqdy, viewdx, viewdy;
	    
	    private final int[][] levelData1 = {
	    	{ 
	    	 3, 10, 10,  2, 10, 10, 10,  2, 10, 10, 14,  3, 10, 30, 39,
	    	 5,  3,  6,  5,  7, 11, 10,  4,  3,  10,  6,  1,  6, 11,  4,
	    	 5, 13,  5,  5,  9, 10, 10, 12,  5,  7,  5,  5,  9, 10,  12,
	    	 1,  2,  4,  9, 10, 10,  2, 10,  4,  5, 13,  5,  3,  6,  7,
	    	 5,  5,  9, 10, 10, 14,  5, 23,  5,  9, 10,  8,  4,  9, 12,
	    	 5,  9,  2, 10, 10,  6,  5,  5,  5,  3, 10,  2,  8, 10,  6,
	    	 5,  7,  5,  3,  6,  5,  5,  5,  5,  5, 11, 12,  7,  7,  13,
	    	 9,  4,  5, 13,  5,  5,  1,  8,  8,  8, 10, 10,  4,  1,  6,
	    	 7,  5,  9, 10, 12,  5, 13,  3, 10,  2, 10, 10,  4,  5,  5,
	    	 5,  1, 10, 10,  6,  9, 14,  5,  3, 12,  3, 14,  5,  5,  5,
	    	 5,  5,  3,  6,  1, 10, 14,  5,  5,  3, 12,  7,  5, 13,  5,
	    	 5,  5, 29,  5,  5, 11, 10, 12,  1,  8, 10,  4,  9,  6,  5,
	    	 1,  4,  7,  5,  9, 10, 10, 10, 12, 27, 10, 12,  7,  5,  5,
	    	 5,  5,  5,  9, 10,  6, 11, 10,  6, 11, 10, 10,  4,  5,  5,
	    	13,  9,  8, 10, 10,  8, 10, 10,  8, 10, 10, 10, 12,  9, 12
	    	},
	    	{ 
	    	 7,  3, 10, 10, 10, 10,  6, 27, 10, 10, 10, 10,  6,  3, 46,
	    	 5,  9, 10,  2, 10,  6,  9, 10, 10, 10, 10, 10, 12,  5, 23,
	    	 5, 11,  6, 29,  3,  4,  3, 10, 10, 10, 10, 10, 10, 12,  5,
	    	 1,  6,  9, 10, 12,  5,  5,  3, 10, 10, 10, 10, 10, 10, 12,
	    	 5,  5,  3, 10, 10, 12,  5,  9, 10,  2, 10,  6,  3, 10,  6,
	    	 5,  9, 12,  3, 10,  6,  9, 10, 10,  4,  3, 12,  9, 30,  5,
	    	 1, 10,  6,  5,  3, 12, 11, 10,  6,  5,  9,  6,  3,  6,  5,
	    	 9,  6,  5,  5,  1, 10, 10,  6,  5,  5,  3, 12,  5,  9, 12,
	    	 7,  5,  5,  5,  5,  3, 10, 12,  5,  5,  9, 10,  8, 10,  6,
	    	 1, 12,  5,  5, 13,  9,  6,  3, 12,  9, 10, 10, 10,  6,  5,
	    	 5, 23,  5,  5,  3, 10, 12,  9, 10, 10,  2, 10, 14,  5,  5,
	    	 9, 12,  5,  5,  5,  3,  2,  6,  3,  6,  9, 10,  6,  5,  5,
	    	 3, 10, 12,  5,  5,  5,  5,  5,  5,  9, 10,  6,  5,  1,  4,
	    	 1, 10, 10, 12, 29,  5,  5,  9,  8, 10,  6,  9, 12,  5,  5,
	    	 9, 10, 10, 10, 10, 12, 13, 27, 10, 10,  8, 10, 10, 12, 29
	    	},
	    	{
	    	11, 10,  6,  3,  6,  3,  6,  3,  6, 23,  3, 10, 10, 10, 46,
	    	 3,  6,  5,  5,  9, 12,  9, 12,  9, 12,  5,  3, 10, 10, 30,
	    	 5,  5,  5,  9, 10,  6,  3, 10, 10, 10, 12,  9, 10, 10,  6,
	    	 5,  5,  1, 10, 10, 12,  1, 10, 10, 10, 10,  6,  3, 10, 12,
	    	 5,  5,  5,  3, 10, 30,  9, 10, 10,  6, 23,  5,  9, 10,  6,
	    	 5,  9,  4,  9, 10,  2, 10, 10,  6,  5,  5,  9, 10,  6,  5,
	    	 5, 23,  9, 10,  6,  5,  3, 10, 12,  5,  5,  3,  6,  9, 12,
	    	 5,  9, 10,  6,  5,  5,  5,  3, 10, 12,  9, 12,  9, 10,  6,
	    	 5,  3,  6,  5,  5,  5,  5,  5, 11, 10, 10, 10,  2,  6,  5,
	    	 1, 12,  9, 12,  5,  5,  5,  9, 10, 10,  2,  6,  5,  5,  5,
	    	 5,  3, 10, 10, 12,  5,  9, 10, 10, 30,  5,  5,  5,  5,  5,
	    	 5,  1, 10, 10, 10,  8, 10, 10, 10,  2, 12,  1, 12,  5,  5,
	    	 5,  9, 10, 10, 10,  2, 10,  2, 10,  8, 10, 12,  3, 12,  5,
	    	 5, 27, 10, 10,  6,  5,  3, 12,  3, 10, 10, 10, 12, 27,  4,
	    	 9, 10, 10, 10,  8,  8,  8, 10,  8, 10, 10, 10, 10, 10, 12
	    	 }
	    };
	    
	    private final int validSpeeds[] = {1, 2, 3, 4};

        private int currentSpeed = 3;
        private int[] screenData;
        private int level;
        private Timer timer;
        
        public Board(){
        	loadImages();
        	initVar();
        	
        	addKeyListener(new TAdapter());
        	
        	setFocusable(true);
        	setBackground(Color.BLACK);
        	setDoubleBuffered(true);
        }
        
        private void initVar(){
        	screenData = new int[scrSize];
        	backColor = new Color(200, 20, 20);
        	
        	level = 0;
        	
        	d = new Dimension(400, 400);
        	dx = new int[4];
        	dy = new int[4];
        	
        	ghostX = new int[maxGhosts];
            ghostDx = new int[maxGhosts];
            ghostY = new int[maxGhosts];
            ghostDy = new int[maxGhosts];
            ghostSpeed = new int[maxGhosts];
            
            timer = new Timer(40, this);
            timer.start();
        } 
        
        @Override
        public void addNotify() {
            super.addNotify();

            initGame();
        }
        
        private void doAnim() {

            pacanimcount--;

            if (pacanimcount <= 0) {
                pacanimcount = pacDelay;
                pacPos = pacPos + pacDir;

                if (pacPos == (pacCount - 1) || pacPos == 0) {
                    pacDir = -pacDir;
                }
            }
        }
        
        private void playGame(Graphics2D g2d) {

            if (dying) {

                die();

            } else {

                movePacman();
                drawPacman(g2d);
                moveGhosts(g2d);

            }
        }
        
        private void showIntro(Graphics2D g2d) {

        	String str = "Press s to start.";
            
            g2d.setColor(new Color(0, 150, 200));
            g2d.fillRect(50, scrSize / 2 - 30, scrSize - 100, 50);
            g2d.drawRect(50, scrSize / 2 - 30, scrSize - 100, 50);
            g2d.setColor(Color.WHITE);
            g2d.setFont(fontSmall);
            g2d.drawString(str, scrSize / 3, scrSize / 2);
        }
        
        private void drawScore(Graphics2D g2d) {

            int i;
            String s;

            g2d.setFont(font);
            g2d.setColor(new Color(96, 128, 255));
            s = "Score: " + score;
            g2d.drawString(s, scrSize / 2 + 70, scrSize + 16);

            for (i = 0; i < lifeLeft; i++) {
                g2d.drawImage(pacman3right, i * 28 + 8, scrSize + 1, this);
            }
        }
        
        private void drawGameOver(Graphics2D g2d){
        	String str = "GAME OVER!!!";
        	String str2 =	"Score: " + score;
        	String str3 = "Press s to continue.";
        	
        	g2d.setColor(new Color(0, 200, 150));
            g2d.fillRect(50, scrSize / 2 - 30, scrSize - 100, 80);
            g2d.drawRect(50, scrSize / 2 - 30, scrSize - 100, 80);
            g2d.setColor(new Color(250, 15, 15));
            g2d.setFont(fontSmall);
            g2d.drawString(str, scrSize / 3, scrSize / 2);
            g2d.drawString(str2, (scrSize / 3) + 25 , (scrSize / 2) + 15);
            g2d.drawString(str3, (scrSize / 3) - 7 , (scrSize / 2) + 30);
        }
        
        private void die() {

            lifeLeft--;

            if (lifeLeft == 0) {
                gameOn = false;
            }

            continueLevel();
        }
        
        private void moveGhosts(Graphics2D g2d) {

            short i;
            int pos;
            int count;

            for (i = 0; i < nroGhosts; i++) {
                if (ghostX[i] % blockSize == 0 && ghostY[i] % blockSize == 0) {
                    pos = ghostX[i] / blockSize + nroBlocks * (int) (ghostY[i] / blockSize);

                    count = 0;

                    if ((screenData[pos] & 1) == 0 && ghostDx[i] != 1) {
                        dx[count] = -1;
                        dy[count] = 0;
                        count++;
                    }

                    if ((screenData[pos] & 2) == 0 && ghostDy[i] != 1) {
                        dx[count] = 0;
                        dy[count] = -1;
                        count++;
                    }

                    if ((screenData[pos] & 4) == 0 && ghostDx[i] != -1) {
                        dx[count] = 1;
                        dy[count] = 0;
                        count++;
                    }

                    if ((screenData[pos] & 8) == 0 && ghostDy[i] != -1) {
                        dx[count] = 0;
                        dy[count] = 1;
                        count++;
                    }

                    if (count == 0) {

                        if ((screenData[pos] & 15) == 15) {
                            ghostDx[i] = 0;
                            ghostDy[i] = 0;
                        } else {
                            ghostDx[i] = -ghostDx[i];
                            ghostDy[i] = -ghostDy[i];
                        }

                    } else {

                        count = (int) (Math.random() * count);

                        if (count > 3) {
                            count = 3;
                        }

                        ghostDx[i] = dx[count];
                        ghostDy[i] = dy[count];
                    }

                }

                ghostX[i] = ghostX[i] + (ghostDx[i] * ghostSpeed[i]);
                ghostY[i] = ghostY[i] + (ghostDy[i] * ghostSpeed[i]);
                drawGhost(g2d, ghostX[i] + 1, ghostY[i] + 1);

                if (pacmanx > (ghostX[i] - 12) && pacmanx < (ghostX[i] + 12)
                        && pacmany > (ghostY[i] - 12) && pacmany < (ghostY[i] + 12)
                        && gameOn) {

                    dying = true;
                }
            }
            
        }
        
        private void drawGhost(Graphics2D g2d, int x, int y) {

            g2d.drawImage(ghost, x, y, this);
        }
        
        private void nroGhostsLevel(){
        	int aux = level % 3;
        	switch (aux){
        	case 0: nroGhosts = 1;
        			break;
        	case 1: nroGhosts = 2;
        			break;
        	case 2: nroGhosts = 3;
        			break;
        	default: nroGhosts = 1;
        	}
        }
        
        private void movePacman() {

            int pos;
            int ch;

            if (reqdx == -pacmandx && reqdy == -pacmandy) {
                pacmandx = reqdx;
                pacmandy = reqdy;
                viewdx = pacmandx;
                viewdy = pacmandy;
            }

            if (pacmanx % blockSize == 0 && pacmany % blockSize == 0) {
                pos = pacmanx / blockSize + nroBlocks * (int) (pacmany / blockSize);
                ch = screenData[pos];

                if ((ch & 16) != 0) {
                    screenData[pos] =  (ch & 15);
                    score = score + 10;
                }
                
                if ((ch & 32) != 0) {
                    screenData[pos] =  (ch & 15);
                    score += 50;

                    level++;
                    
                    nroGhostsLevel();
                    
                    initLevel();
                }
                

                if (reqdx != 0 || reqdy != 0) {
                    if (!((reqdx == -1 && reqdy == 0 && (ch & 1) != 0)
                            || (reqdx == 1 && reqdy == 0 && (ch & 4) != 0)
                            || (reqdx == 0 && reqdy == -1 && (ch & 2) != 0)
                            || (reqdx == 0 && reqdy == 1 && (ch & 8) != 0))) {
                        pacmandx = reqdx;
                        pacmandy = reqdy;
                        viewdx = pacmandx;
                        viewdy = pacmandy;
                    }
                }

                if ((pacmandx == -1 && pacmandy == 0 && (ch & 1) != 0)
                        || (pacmandx == 1 && pacmandy == 0 && (ch & 4) != 0)
                        || (pacmandx == 0 && pacmandy == -1 && (ch & 2) != 0)
                        || (pacmandx == 0 && pacmandy == 1 && (ch & 8) != 0)) {
                    pacmandx = 0;
                    pacmandy = 0;
                }
            }
            pacmanx = pacmanx + pacSpeed * pacmandx;
            pacmany = pacmany + pacSpeed * pacmandy;
            
        
        }
        
        private void drawPacman(Graphics2D g2d) {

            if (viewdx == -1) {
                pacnanLeft(g2d);
            } else if (viewdx == 1) {
                pacmanRight(g2d);
            } else if (viewdy == -1) {
                pacmanUp(g2d);
            } else {
                pacmanDown(g2d);
            }
        }
        
        private void pacmanUp(Graphics2D g2d) {

            switch (pacPos) {
                case 1:
                    g2d.drawImage(pacman2up, pacmanx + 1, pacmany + 1, this);
                    break;
                case 2:
                    g2d.drawImage(pacman3up, pacmanx + 1, pacmany + 1, this);
                    break;
                case 3:
                    g2d.drawImage(pacman4up, pacmanx + 1, pacmany + 1, this);
                    break;
                default:
                    g2d.drawImage(pacman1, pacmanx + 1, pacmany + 1, this);
                    break;
            }
        }

        private void pacmanDown(Graphics2D g2d) {

            switch (pacPos) {
                case 1:
                    g2d.drawImage(pacman2down, pacmanx + 1, pacmany + 1, this);
                    break;
                case 2:
                    g2d.drawImage(pacman3down, pacmanx + 1, pacmany + 1, this);
                    break;
                case 3:
                    g2d.drawImage(pacman4down, pacmanx + 1, pacmany + 1, this);
                    break;
                default:
                    g2d.drawImage(pacman1, pacmanx + 1, pacmany + 1, this);
                    break;
            }
        }

        private void pacnanLeft(Graphics2D g2d) {

            switch (pacPos) {
                case 1:
                    g2d.drawImage(pacman2left, pacmanx + 1, pacmany + 1, this);
                    break;
                case 2:
                    g2d.drawImage(pacman3left, pacmanx + 1, pacmany + 1, this);
                    break;
                case 3:
                    g2d.drawImage(pacman4left, pacmanx + 1, pacmany + 1, this);
                    break;
                default:
                    g2d.drawImage(pacman1, pacmanx + 1, pacmany + 1, this);
                    break;
            }
        }

        private void pacmanRight(Graphics2D g2d) {

            switch (pacPos) {
                case 1:
                    g2d.drawImage(pacman2right, pacmanx + 1, pacmany + 1, this);
                    break;
                case 2:
                    g2d.drawImage(pacman3right, pacmanx + 1, pacmany + 1, this);
                    break;
                case 3:
                    g2d.drawImage(pacman4right, pacmanx + 1, pacmany + 1, this);
                    break;
                default:
                    g2d.drawImage(pacman1, pacmanx + 1, pacmany + 1, this);
                    break;
            }
        }

        private void drawMaze(Graphics2D g2d) {

            short i = 0;
            int x, y;

            for (y = 0; y < scrSize; y += blockSize) {
                for (x = 0; x < scrSize; x += blockSize) {

                    g2d.setColor(backColor);
                    g2d.setStroke(new BasicStroke(2));

                    if ((screenData[i] & 1) != 0) { 
                        g2d.drawLine(x, y, x, y + blockSize - 1);
                    }

                    if ((screenData[i] & 2) != 0) { 
                        g2d.drawLine(x, y, x + blockSize - 1, y);
                    }

                    if ((screenData[i] & 4) != 0) { 
                        g2d.drawLine(x + blockSize - 1, y, x + blockSize - 1,
                                y + blockSize - 1);
                    }

                    if ((screenData[i] & 8) != 0) { 
                        g2d.drawLine(x, y + blockSize - 1, x + blockSize - 1,
                                y + blockSize - 1);
                    }

                    if ((screenData[i] & 16) != 0) { 
                        g2d.setColor(dotColor);
                        g2d.fillRect(x + 11, y + 11, 7, 7);
                    }
                    
                    if ((screenData[i] & 32) != 0) { 
                        g2d.setColor(winColor);
                        g2d.fillRect(x + 11, y + 11, 8, 8);
                    }

                    i++;
                }
            }
            
        
        }
        
        private void initGame() {

        	lifeLeft = 3;
            score = 0;
            initLevel();
            nroGhosts = 1;
            currentSpeed = 3;
        }

        private void initLevel() {

            int i;
            int aux = level % 3;
            for (i = 0; i < nroBlocks * nroBlocks; i++) {
                screenData[i] = levelData1[aux][i];
            }
            
            continueLevel();
            
        }

        private void continueLevel() {

        	Random rg = new Random();
        	
            short i;
            int dx = 1;
            int random;

            for (i = 0; i < nroGhosts; i++) {

                ghostY[i] = 4 * blockSize;
                ghostX[i] = 4 * blockSize;
                ghostDy[i] = 0;
                ghostDx[i] = dx;
                dx = -dx;
                random = rg.nextInt(131) % 4;

                ghostSpeed[i] = validSpeeds[random];
            }

            pacmanx = 0 * blockSize;
            pacmany = 0 * blockSize;
            pacmandx = 0;
            pacmandy = 0;
            reqdx = 0;
            reqdy = 0;
            viewdx = -1;
            viewdy = 0;
            dying = false;
         
        }

        private void loadImages() {

            ghost = new ImageIcon("images/Ghost.gif").getImage();
            pacman1 = new ImageIcon("images/PacMan1.gif").getImage();
            pacman2up = new ImageIcon("images/PacMan1up.gif").getImage();
            pacman3up = new ImageIcon("images/PacMan2up.gif").getImage();
            pacman4up = new ImageIcon("images/PacMan3up.gif").getImage();
            pacman2down = new ImageIcon("images/PacMan1down.gif").getImage();
            pacman3down = new ImageIcon("images/PacMan2down.gif").getImage();
            pacman4down = new ImageIcon("images/PacMan3down.gif").getImage();
            pacman2left = new ImageIcon("images/PacMan1left.gif").getImage();
            pacman3left = new ImageIcon("images/PacMan2left.gif").getImage();
            pacman4left = new ImageIcon("images/PacMan3left.gif").getImage();
            pacman2right = new ImageIcon("images/PacMan1right.gif").getImage();
            pacman3right = new ImageIcon("images/PacMan2right.gif").getImage();
            pacman4right = new ImageIcon("images/PacMan3right.gif").getImage();

        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            doDrawing(g);
        }

        private void doDrawing(Graphics g) {

            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(Color.black);
            g2d.fillRect(0, 0, d.width, d.height);

            drawMaze(g2d);
            drawScore(g2d);
            doAnim();

            if (gameOn) {
                playGame(g2d);
            } else {
            	if( lifeLeft == 0){
            		drawGameOver(g2d);
            	}else{
            		showIntro(g2d);
            	}
            }

            g2d.drawImage(img, 5, 5, this);
            Toolkit.getDefaultToolkit().sync();
            g2d.dispose();
        }

        class TAdapter extends KeyAdapter {

            @Override
            public void keyPressed(KeyEvent e) {

                int key = e.getKeyCode();

                if (gameOn) {
                    if (key == KeyEvent.VK_LEFT) {
                        reqdx = -1;
                        reqdy = 0;
                    } else if (key == KeyEvent.VK_RIGHT) {
                        reqdx = 1;
                        reqdy = 0;
                    } else if (key == KeyEvent.VK_UP) {
                        reqdx = 0;
                        reqdy = -1;
                    } else if (key == KeyEvent.VK_DOWN) {
                        reqdx = 0;
                        reqdy = 1;
                    } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                        gameOn = false;
                    } else if (key == KeyEvent.VK_PAUSE) {
                        if (timer.isRunning()) {
                            timer.stop();
                        } else {
                            timer.start();
                        }
                    }
                } else {
                    if (key == 's' || key == 'S') {
                        gameOn = true;
                        initGame();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

                int key = e.getKeyCode();

                if (key == Event.LEFT || key == Event.RIGHT
                        || key == Event.UP || key == Event.DOWN) {
                    reqdx = 0;
                    reqdy = 0;
                }
            }
        }

       // @Override
        public void actionPerformed(ActionEvent e) {

            repaint();
        } 
}
