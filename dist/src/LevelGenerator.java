package shooter;
import java.util.ArrayList;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class LevelGenerator {
    private boolean gameStart = false, gameOver=false;
    private final int dimX = 994, dimY = 621;
    private int level=0, counter=0, puSpawned=0, levelDuration=800, playerSelect=1;
    private final String[] PU = new String[]{"puFire5","puDouble","puTriple"};
    
    
    // Level Design /////////////////////////////////////////////////////////////////////////////////////////////////
    // <editor-fold>
    private final String[][] lvl = new String[][] {
        {"N","N","N","N","N","N","N"},
        {"N","N","N","N","N","N"},
        {"TS","N","N","N"},
        {""}};
    private final int[][] x = new int[][]{
        {100,300,500,700,200,400,600},
        {75,150,225,300,375,450},
        {0,0,0,0},
        {}};
    private final int[][] spdX = new int[][]{
        {-1,-1,-1,-1,-1,-1,-1},
        {2,-2,2,-2,2,-2},
        {0,0,0,0},
        {}};
    private final int[][] spdY = new int[][]{
        {0,0,0,0,0,0,0},
        {1,1,1,1,1,1},
        {0,0,0,0},
        {}};
    private final int[][] spawnTimer = new int[][]{
        {0,0,0,0,400,400,400},
        {120,40,130,50,140,60},
        {50,100,100,100},
        {}};
    private final int[][] randX = new int[][]{
        {10,10,10,10,10,10,10},
        {10,10,10,10,10,10},
        {dimX-20,dimX-20,dimX-20,dimX-20},
        {}};
    private final int[][] randSpdX = new int[][]{
        {2,2,2,2,2,2,2},
        {2,2,2,2,2,2},
        {2,3,3,3},
        {}};
    private final int[][] randSpdY = new int[][]{
        {2,2,2,2,2,2,2},
        {2,2,2,2,2,2},
        {2,3,3,3},
        {}};
    // </editor-fold>
    
    // Strings[] ////////////////////////////////////////////////////////////////////////////////////////////////////
    // <editor-fold>
    String  doubleimg = "resources/pu/double.png",
            tripleimg = "resources/pu/triple.png",
            fire5img = "resources/pu/fire5.png",
            plus1img = "resources/pu/plus1.png";
    
    String[] debImg = {
                "resources/debris/meteor1.png","resources/debris/meteor2.png",
                "resources/debris/meteor3.png","resources/debris/meteor4.png",
                "resources/debris/meteor5.png","resources/debris/meteor6.png",
                "resources/debris/meteor7.png","resources/debris/meteor8.png",
                "resources/debris/meteor9.png","resources/debris/meteor10.png",
                "resources/debris/meteor11.png","resources/debris/meteor12.png",
                "resources/debris/meteor13.png","resources/debris/meteor14.png",
                "resources/debris/meteor15.png","resources/debris/meteor16.png",
                "resources/debris/meteor17.png","resources/debris/meteor18.png"},
            enemyImg = {
                "resources/enemy/enemy.png"},
            enemyMsImg = {
                "resources/missile/r3x3.png",
                "resources/missile/r3x4.png"},
            startmenuImg = {
                "resources/menu/startmenu_p1.png",
                "resources/menu/startmenu_p2.png"},
            gameoverImg = {
                "resources/menu/gameover.png"},
            p1img = {
                "resources/player/player1single.png",
                "resources/player/player1double.png",
                "resources/player/player1triple.png"},
            p1ms = {
                "resources/missile/g3x5.png",
                "resources/missile/b50x20.png"},
            p2img = {
                "resources/player/player1single.png",
                "resources/player/player1double.png",
                "resources/player/player1triple.png"},
            p2ms = {
                "resources/missile/g3x5.png",
                "resources/missile/b50x20.png"};
    // </editor-fold>
    
    public Menu startmenu = new Menu(dimX/2,dimY/2,0,0,startmenuImg[0],startmenuImg),
        gameovermenu = new Menu(dimX/2,dimY/2,0,0,gameoverImg[0],gameoverImg);
    

    public void generateEnemies(ArrayList<Enemy> enemy){
        int i;
        for (i=0;i<lvl[level].length;i++){
            switch(lvl[level][i]){
                case "N":
                    enemy.add(normal(x[level][i]+rand(0,randX[level][i]),-50,spdX[level][i]+rand(1,randSpdX[level][i]),spdY[level][i]+rand(1,randSpdY[level][i]), spawnTimer[level][i]));
                    break;
                case "TS":
                    enemy.add(tripleshooter(x[level][i]+rand(0,randX[level][i]),-50,spdX[level][i]+rand(1,randSpdX[level][i]),spdY[level][i]+rand(1,randSpdY[level][i]), spawnTimer[level][i]));
                    break;
                default:
                    break;
            }
        }
    }
    public void generatePU(ArrayList<PowerUp> powerUp, String type, int spdX, int spdY, int spawnTimer){
        switch(type){
            case "puDouble":
                powerUp.add(puDouble(randDimX(),spdX,spdY,spawnTimer));
                break;
            case "puTriple":
                powerUp.add(puTriple(randDimX(),spdX,spdY,spawnTimer));
                break;
            case "puFire5":
                powerUp.add(puFire5(randDimX(),spdX,spdY,spawnTimer));
                break;
            case "puPlus1":
                powerUp.add(puPlus1(randDimX(),spdX,spdY,spawnTimer));
                break;
            default:
                break;
        }
    }
    public void generateDebris(ArrayList<BgDebris> bgDebris){
        if (counter%15==0){
            bgDebris.add(new BgDebris(randDimX(),-50, 0, randSpd(), randDImg()));
        }
    }
    public void levelcounter(ArrayList<Enemy> enemy, ArrayList<PowerUp> powerUp){
        int i;
        for (i=0;i<enemy.size();i++){
            if (enemy.get(i).getSpawn()==0 && enemy.get(i).getY()<0){
                if(enemy.get(i).getSpawnTimer()==counter){
                    enemy.get(i).setSpawn(1);
                }
            }
        }
        for (i=0;i<powerUp.size();i++){
            if (powerUp.get(i).getSpawn()==0 && powerUp.get(i).getY()<0){
                if(powerUp.get(i).getSpawnTimer()==counter){
                    powerUp.get(i).setSpawn(1);
                }
            }
        }
        counter++;
    }
    
    
    // Enemies /////////////////////////////////////////////////////////////////////////////////////////
    public Enemy normal(int x, int y, int speedX, int speedY, int spawnTimer){
            return new Enemy("N",x,y,speedX,speedY,enemyImg[0],1,1,enemyMsImg[1],1, spawnTimer, randOffsetY());
    }
    public Enemy tripleshooter(int x, int y, int speedX, int speedY, int spawnTimer){
            return new Enemy("TS",x,y,speedX,speedY,enemyImg[0],1,2,enemyMsImg[0],2, spawnTimer, randOffsetY());
    }
    
    // PowerUps ////////////////////////////////////////////////////////////////////////////////////////
    public PowerUp puDouble(int x, int speedX, int speedY, int spawnTimer){
        return new PowerUp(x,-50,speedX,speedY,doubleimg,"double",1, spawnTimer);
    }
    public PowerUp puTriple(int x, int speedX, int speedY, int spawnTimer){
        return new PowerUp(x,-50,speedX,speedY,tripleimg,"triple",1,spawnTimer);
    }
    public PowerUp puFire5(int x, int speedX, int speedY, int spawnTimer){
        return new PowerUp(x,-50,speedX,speedY,fire5img,"fire5",300,spawnTimer);
    }
    public PowerUp puPlus1(int x, int speedX, int speedY, int spawnTimer){
        return new PowerUp(x,-50,speedX,speedY,plus1img,"plus1",1,spawnTimer);
    }
    
    
    // Getters, Setters, Basic Functions /////////////////////////////////////////////////////////////
    public void drawStartMenu(Graphics g){
        startmenu.drawImg(g);
    }
    public void setGameStart(boolean status, ArrayList<Player> player){
        this.gameStart = status;
        if (playerSelect == 1){
            player.add(new Player(240, 550, 10, 10, p1img[0], p1img, p1ms));
        }
        else{
            player.add(new Player(240, 550, 10, 10, p1img[0], p1img, p1ms));
            player.add(new Player(240, 550, 10, 10, p2img[0], p2img, p2ms));
        }
    }
    public boolean getGameStart(){
        return gameStart;
    }
    public boolean getGameOver(){
        return gameOver;
    }
    public void setGameOver(boolean status){
        this.gameOver = status;
    }
    public void setPlayerSelect(int players){
        this.playerSelect = players;
    }
    public int getPlayerSelect(){
        return playerSelect;
    }
    public void resetLevelCounter(){
        counter = 0;
    }
    public void setPUSpawned(int spawn){
        this.puSpawned = spawn;
    }
    public int getPUSpawned(){
        return puSpawned;
    }
    public int rand(int a, int b){
        return ThreadLocalRandom.current().nextInt(a,b);
    }
    public String randPU(){
        int i;
        i = ThreadLocalRandom.current().nextInt(0, PU.length);
        return PU[i];
    }
    public int randSpd(){
        return ThreadLocalRandom.current().nextInt(1, 4);
    }
    public String randDImg(){
        int i;
        i = ThreadLocalRandom.current().nextInt(0, debImg.length);
        return debImg[i];
    }
    public int randDimX(){
        return ThreadLocalRandom.current().nextInt(0, dimX);
    }
    public int randOffsetY(){
        return ThreadLocalRandom.current().nextInt(0, 50);
    }    
    public void setLevel(int level){
        this.level = level;
    }
    public int getLevel(){
        return level;
    }
    public int getCounter(){
        return counter;
    }
    public int getDimX(){
        return dimX;
    }
    public int getDimY(){
        return dimY;
    }
    public void setLevelDuration(int time){
        this.levelDuration = time;
    }
    public int getLevelDuration(){
        return levelDuration;
    }
}
