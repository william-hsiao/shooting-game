package shooter;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class MyJPanel extends JPanel
    implements ActionListener, KeyListener{
    int dimX = 994, dimY = 621;
    Timer timer;
    int n;

    ArrayList<Player> player = new ArrayList<>();
    ArrayList<Enemy> enemy = new ArrayList<>();
    ArrayList<PowerUp> powerUp = new ArrayList<>();
    ArrayList<BgDebris> bgDebris = new ArrayList<>();
    LevelGenerator level = new LevelGenerator();
    Color[] colours = {Color.white, Color.orange};
    
    public MyJPanel(){
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(10, this);
        timer.start();
        n = 5;
    }
        
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int i, j;
        if (!level.getGameStart()){
            level.drawStartMenu(g);
        }
        else{
            for (i=0;i<bgDebris.size();i++){
                bgDebris.get(i).drawImg(g);
            }
            g.setColor(Color.WHITE);
            g.drawString("LEVEL "+(level.getLevel()+1), dimX/2-30, 15);
            for (i=0;i<player.size();i++){
                g.setColor(colours[i]);
                g.drawString("Score: "+player.get(i).getScore(), 5, 15+20*i);
                g.drawString("Lives: "+player.get(i).getLives(), dimX-60, 15+20*i);
                if (player.get(i).getSpawn()==1 || player.get(i).getSpawn()==3){
                    player.get(i).drawImg(g);
                }
            }
            for(i=0;i<enemy.size();i++){
                if(enemy.get(i).getSpawn()==1){
                    enemy.get(i).drawImg(g);
                }
            }
            for (i=0;i<player.size();i++){
                for (j=0;j<player.get(i).missile.size();j++){
                    player.get(i).missile.get(j).drawImg(g);
                }
            }
            for (i=0;i<enemy.size();i++){
                for (j=0;j<enemy.get(i).missile.size();j++){
                    enemy.get(i).missile.get(j).drawImg(g);
                }
            }
            for (i=0;i<powerUp.size();i++){
                if (powerUp.get(i).getSpawn()==1){
                    powerUp.get(i).drawImg(g);
                }
            }
            if(level.getGameOver()){
                level.gameovermenu.drawImg(g);
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e){
        Dimension dim=getSize();
        dimX = dim.width;
        dimY = dim.height;
        int i, j, k;
        
        //Move Debris
        for (i=0;i<bgDebris.size();i++){
            bgDebris.get(i).moveY();
            if(dimY < bgDebris.get(i).getY()){
                bgDebris.remove(i);
                i--;
            }
        }
        
        //Move Enemies + Enemy Missiles
        for (i=0; i<enemy.size(); i++){
            if (enemy.get(i).getSpawn()==1){
                enemy.get(i).moveX();
                if((enemy.get(i).getX() < 0) || ( enemy.get(i).getX() > (dimX - enemy.get(i).getWidth()))){
                    enemy.get(i).setSpeedX(-(enemy.get(i).getSpeedX())); 
                }
                enemy.get(i).moveY();
                if(enemy.get(i).getY() < dimY){
                    enemy.get(i).shoot();
                }
                else{
                    enemy.get(i).setSpawn(0);
                }
            }
            enemy.get(i).moveMissile(dimY);
        }
        
        //Move Player Missiles
        for (i=0;i<player.size();i++){
            player.get(i).moveMissile();
        }
        
        //Move PowerUps
        for (i=0;i<powerUp.size();i++){
            if (powerUp.get(i).getSpawn()==1){
                powerUp.get(i).moveX();
                powerUp.get(i).moveY();
                if(dimY < powerUp.get(i).getY()){
                    powerUp.remove(i);
                    i--;
                }
            }
        }

        //Player Missile Collision Detection
        for (i=0;i<player.size();i++){    
            for (j=0; j<player.get(i).missile.size(); j++){
                for (k=0; k<enemy.size(); k++){
                    if (enemy.get(k).getSpawn()==1 && enemy.get(k).getY()>=0){
                        if (    
                                player.get(i).missile.get(j).getY()<=enemy.get(k).getYHeight()
                                && player.get(i).missile.get(j).getYHeight()>=enemy.get(k).getY() 
                                && player.get(i).missile.get(j).getX()<=enemy.get(k).getXWidth()
                                && player.get(i).missile.get(j).getXWidth()>=enemy.get(k).getX()){
//                                collisionDetect(player.get(i).missile.get(j), enemy.get(k))){
                            player.get(i).missile.remove(j);
                            enemy.get(k).hit(level, player.get(i));
                            j--;
                            break;
                        }
                    }
                }
            }

            //Player Sprite Collision Detection
            for (j=0; j<enemy.size(); j++){
                //Enemy Missiles
                for (k=0; k<enemy.get(j).missile.size(); k++){
                    if (    player.get(i).getSpawn()==1 
                            && player.get(i).getY()<=enemy.get(j).missile.get(k).getYHeight()
                            && player.get(i).getYHeight()>=enemy.get(j).missile.get(k).getY()
                            && player.get(i).getX()<=enemy.get(j).missile.get(k).getXWidth()
                            && player.get(i).getXWidth()>=enemy.get(j).missile.get(k).getX()){
                        enemy.get(j).missile.remove(k);
                        player.get(i).hit();
                        checkPlayerLives();
                        k--;
                        break;
                    }
                    //Enemy Sprites
                    if (    player.get(i).getSpawn()==1
                            && player.get(i).getY()<=enemy.get(j).getYHeight()
                            && player.get(i).getYHeight()>=enemy.get(j).getY() 
                            && player.get(i).getX()<=enemy.get(j).getXWidth()
                            && player.get(i).getXWidth()>=enemy.get(j).getX()){
                        player.get(i).hit();
                        checkPlayerLives();
                    }
                }
            }
        }
        
        //PowerUps
        for (i=0;i<powerUp.size();i++){
            for (j=0;j<player.size();j++){
                if (    player.get(j).getSpawn()==1 && powerUp.get(i).getSpawn()==1
                        && player.get(j).getY()<=powerUp.get(i).getYHeight()
                        && player.get(j).getYHeight()>=powerUp.get(i).getY() 
                        && player.get(j).getX()<=powerUp.get(i).getXWidth()
                        && player.get(j).getXWidth()>=powerUp.get(i).getX()){
                    powerUp.get(i).activatePU(player.get(i));
                }
                if (powerUp.get(i).getSpawn()==0 && powerUp.get(i).getY()>=0){
                    powerUp.get(i).updateCounter();
                    if (powerUp.get(i).checkCounter() == 0){
                        powerUp.get(i).deactivatePU(player.get(j));
                        powerUp.remove(i);
                        i--;
                    }
                }
            }
        }
        
        //Spawn PU
        for (i=0;i<player.size();i++){
            if (player.get(i).getScore()>0 && player.get(i).getScore()%(10*(level.getLevel()+1))==0 && level.getPUSpawned()==0){
                level.generatePU(powerUp, level.randPU(), 0, 2, level.getCounter());
                level.setPUSpawned(1);
            }
        }
        
        //Remove Enemies
        for (i=0; i<enemy.size(); i++){
            if (enemy.get(i).getSpawn()==0){
                if (enemy.get(i).getY()>=0){
                    if (enemy.get(i).missile.isEmpty()){
                        enemy.remove(i);
                        i--;
                    }
                }
            }
        }
        
        if (level.getGameStart() && !level.getGameOver()){
            level.generateDebris(bgDebris);
            for (i=0;i<player.size();i++){
                if(level.getLevel()==0 && player.get(i).getScore()<20 || 
                        level.getLevel()==1 && player.get(i).getScore()<50 || 
                        level.getLevel()==2 && player.get(i).getScore()<100){
                    if (level.getCounter()==level.getLevelDuration()){
                        level.resetLevelCounter();
                        level.generateEnemies(enemy);
                    }
                }
            }
            //TEMP RESPAWN + LEVEL
            if (enemy.isEmpty()){
                for (i=0;i<player.size();i++){
                    if (player.get(i).getScore()>=20 && level.getLevel()==0){
                        level.setLevel(1);
                        level.generatePU(powerUp, "puPlus1", 0, 2, 5);
                        level.setLevelDuration(200);
                    }
                    else if (player.get(i).getScore()>=50 && level.getLevel()==1){
                        level.setLevel(2);
                        level.generatePU(powerUp, "puPlus1", 0, 2, 5);
                        level.setLevelDuration(250);
                    }
                    else if (player.get(i).getScore()>=100 && level.getLevel()==2){
                        level.setLevel(3);
                    }
                }
                level.resetLevelCounter();
                level.generateEnemies(enemy);
            }
        }
        level.levelcounter(enemy, powerUp);
        for (i=0;i<player.size();i++){
            player.get(i).hitFlash();
            player.get(i).incSCounter();
        }
//        System.out.println(dim.width+" "+dim.height);
        repaint();
    }
    @Override
    public void keyTyped(KeyEvent e){
    }
    @Override
    public void keyPressed(KeyEvent e){
        int i, key = e.getKeyCode();
        for (i=0;i<player.size();i++){
            player.get(i).setRes(dimX, dimY);
        }
        if (level.getGameStart()){
            switch (key) {
                case KeyEvent.VK_LEFT:
                        player.get(0).inputLeft();
                        break;
                case KeyEvent.VK_RIGHT:
                    player.get(0).inputRight();
                    break;
                case KeyEvent.VK_UP:
                    player.get(0).inputUp();
                    break;
                case KeyEvent.VK_DOWN:
                    player.get(0).inputDown();
                    break;
                case KeyEvent.VK_PAGE_UP:
                    for (i=0;i<player.size();i++){
                        player.get(i).setSpawn(1);
                    }
                    break;
                case KeyEvent.VK_1:
                    for (i=0;i<player.size();i++){
                        player.get(0).setBaseSMode("single");
                    }
                    System.out.println("P1 SMODE = 1");
                    break;
                case KeyEvent.VK_2:
                    for (i=0;i<player.size();i++){
                        player.get(0).setBaseSMode("double");
                    }
                    System.out.println("P1 SMODE = 2");
                    break;
                case KeyEvent.VK_3:
                    for (i=0;i<player.size();i++){
                        player.get(0).setBaseSMode("triple");
                    }
                    System.out.println("P1 SMODE = 3");
                    break;
                case KeyEvent.VK_4:
                    for (i=0;i<player.size();i++){
                        player.get(0).setBaseSMode("singleL");
                    }
                    System.out.println("P1 SMODE = 4");
                    break;
                case KeyEvent.VK_HOME:
                    for (i=0;i<player.size();i++){
                        player.get(0).setLives(player.get(0).getLives()+1);
                    }
                    break;
                case KeyEvent.VK_END:
                    for (i=0;i<player.size();i++){
                        player.get(0).setScore(player.get(0).getScore()+1);
                    }
                    break;
                default:
                    break;
            }
            if (level.getPlayerSelect()==1){
                switch (key) {
                    case KeyEvent.VK_SPACE:
                        if(player.get(0).getSpawn()==1){
                            player.get(0).shoot();
                        } 
                        break;
                    default:
                        break;
                }
            }
            else{
                switch (key) {
                    case KeyEvent.VK_SLASH:
                        if(player.get(0).getSpawn()==1){
                            player.get(0).shoot();
                        } 
                        break;
                    case KeyEvent.VK_A:
                        player.get(1).inputLeft();
                        break;
                    case KeyEvent.VK_D:
                        player.get(1).inputRight();
                        break;
                    case KeyEvent.VK_W:
                        player.get(1).inputUp();
                        break;
                    case KeyEvent.VK_S:
                        player.get(1).inputDown();
                        break;
                    case KeyEvent.VK_G:
                        if(player.get(1).getSpawn()==1){
                            player.get(1).shoot();
                        } 
                        break;
                    default:
                        break;
                }
            }
        }
        else{
            switch (key) {
                case KeyEvent.VK_UP:
                    if (level.getPlayerSelect() == 2){
                        level.startmenu.updateMenuImg(1);
                        level.setPlayerSelect(1);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (level.getPlayerSelect() == 1){
                        level.startmenu.updateMenuImg(2);
                        level.setPlayerSelect(2);
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    level.setGameStart(true, player);
                    break;
                default:
                    break;
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e){
    }   
    public void checkPlayerLives(){
        if (player.size()==1){
            if (player.get(0).getLives()==0){
                level.setGameOver(true);
            }
        }
        else{
            if (player.get(0).getLives()==0 && player.get(1).getLives()==0){
                level.setGameOver(true);
            }
        }
    }
    
    public boolean collisionDetect(Object a, Object b){
        if (a instanceof Player && b instanceof Missile){
            Player A = (Player)a;
            Missile B = (Missile)b;
            return A.getY()<=B.getYHeight() && A.getYHeight()>=B.getY()
                    && B.getX()<=B.getXWidth() && B.getXWidth()>=B.getX();
        }
        else if (a instanceof Player && b instanceof Enemy){
            Player A = (Player)a;
            Enemy B = (Enemy)b;
            return A.getY()<=B.getYHeight() && A.getYHeight()>=B.getY()
                    && B.getX()<=B.getXWidth() && B.getXWidth()>=B.getX();
        }
        else if (a instanceof Missile && b instanceof Enemy){
            Missile A = (Missile)a;
            Enemy B = (Enemy)b;
            return A.getY()<=B.getYHeight() && A.getYHeight()>=B.getY()
                    && B.getX()<=B.getXWidth() && B.getXWidth()>=B.getX();
        }
        else {
            Player A = (Player)a;
            PowerUp B = (PowerUp)b;
            return A.getY()<=B.getYHeight() && A.getYHeight()>=B.getY()
                    && B.getX()<=B.getXWidth() && B.getXWidth()>=B.getX();
        }

//            //player - missile, player - enemy, player missile - enemy, player - powerup
    }
}