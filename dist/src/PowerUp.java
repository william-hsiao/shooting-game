package shooter;

public class PowerUp extends Sprite{
    private int spawn, puCounter=0, spawnTimer;
    private final int puDuration;
    private final String type;
    PowerUp(int x, int y, int speedX, int speedY, String img, String type, int duration, int spawnTimer){
        super(x, y, speedX, speedY, img);
        this.type = type;
        this.puDuration = duration;
        this.spawn = 0;
        this.spawnTimer = spawnTimer;
    }
    
    public void activatePU(Player player){
        switch(type){
            case "plus1":
                player.setLives(player.getLives()+1);
                break;
            case "double":
                player.setBaseSMode(type);
                break;
            case "triple":
                player.setBaseSMode(type);
                break;
            case "fire5":
                player.setTempSMode(type);
                break;
            default:
                break;
        }
        spawn = 0;
    }
    
    public void deactivatePU(Player player){
        switch(type){
            case "":
                break;
            case "fire5":
                player.setTempSMode("");
                break;
            default:
                break;
        }
        player.setBaseSMode(player.getBaseSMode());
    }
    public void updateCounter(){
        puCounter++;
    }
    public int checkCounter(){
        return puCounter%puDuration;
    }
    public void setSpawn(int spawn){
        this.spawn = spawn;
    }
    public int getSpawn(){
        return spawn;
    }
    public int getCounter(){
        return puCounter;
    }
    public int getDuration(){
        return puDuration;
    }
    public void setspwnTimer(int spawnTimer){
        this.spawnTimer = spawnTimer;
    }
    public int getSpawnTimer(){
        return spawnTimer;
    }

}

